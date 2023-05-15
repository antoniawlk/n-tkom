import java.net.*;
import java.io.*;

public class MyRunnable implements Runnable {
    Socket socket;

    //HTTP status codes
    StringBuilder ok = new StringBuilder("HTTP/1.1 200 OK\r\n\r\n");
     byte[] notFound = "HTTP/1.1 404 Not Found\r\n\r\n".getBytes();
    byte[] badRequest = "HTTP/1.1 400 Bad Request\r\n\r\n".getBytes();

    public MyRunnable(Socket s){
        this.socket = s;
    } 

    public void run(){

        try{

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        System.out.println("connected");

        ByteArrayOutputStream clientData = new ByteArrayOutputStream();
        int temp = in.read();

        while(true){
            clientData.write(temp);
            if(clientData.toString().endsWith("\r\n\r\n"))
                break;
            temp = in.read();
        }

            Boolean shutdown = false; 
            Integer timeout = null;
            Integer limit = null; 
            byte[] toServerBytes = null;
            Integer port = null;
            String hostname = null;
            String string = null;
            Boolean version = false;

            String input = clientData.toString();
            String[] clData = input.split("[?\\&\\=\\\r\n\\ ]");

            if(clData[0].equals("GET") && clData[1].equals("/ask")){
                    for(int i = 0; i < clData.length; i++){
                        if(clData[i].equals("hostname"))
                        hostname = clData[++i];

                        else if(clData[i].equals("port"))
                            port = Integer.parseInt(clData[++i]);
                        
                        else if(clData[i].equals("string"))
                            string = clData[++i];
                            
                        else if(clData[i].equals("shutdown"))
                            shutdown = Boolean.parseBoolean(clData[++i]);
    
                        else if(clData[i].equals("limit"))
                            limit = Integer.parseInt(clData[++i]);

                        else if(clData[i].equals("timeout"))
                            timeout = Integer.parseInt(clData[++i]);
                        
                        else if(clData[i].equals("HTTP/1.1"))
                            version = true;
                    }
                }

                if(string != null){
                    toServerBytes = string.getBytes();
                }

                for (String a : clData) {
                    System.out.println("'" + a + "'");
                }


            if(clData[1].equals("/ask") && hostname != null && port != null && version){
                    try{
                        TCPClient tcpClient = new TCPClient(shutdown, timeout, limit);
                        System.out.println("hostname ="+ hostname);
                        System.out.println("port ="+ port);
                        System.out.println("toserverbytes ="+ toServerBytes);
                        byte[] toClientBytes = tcpClient.askServer(hostname, port, toServerBytes);
                        System.out.println("received data");
                        String data = new String(toClientBytes);
                        ok.append(data);
                        out.write(ok.toString().getBytes());
                        System.out.println("ok");         
                    }
                    catch(Exception e){
                        out.write(notFound);
                        System.out.println("Not Found");
                    }
                } 
                else{
                    out.write(badRequest);
                    System.out.println("Bad Request");
                }
                System.out.println("--------REQUEST DONE---------");
                socket.close();
        }
        catch(Exception ex){
            System.err.println(":'(");
            System.exit(1);
        }

    }

}

