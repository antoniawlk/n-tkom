import java.net.*;
import java.io.*;

public class HTTPAsk {


    public static void main(String[] args) throws IOException {

        //HTTP status codes
        StringBuilder ok = new StringBuilder("HTTP/1.1 200 OK\r\n\r\n");
        byte[] notFound = "HTTP/1.1 404 Not Found\r\n\r\n".getBytes();
        byte[] badRequest = "HTTP/1.1 400 Bad Request\r\n\r\n".getBytes();

        int serverPort = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(serverPort);


        while(true){
             
            try{

                System.out.println("waiting");
            Socket socket = serverSocket.accept();
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

         

            //for(int i=1; i<clData.length; i++){
               // String[] sub = clData[i].split("=");
                //if(sub[0].equals("shutdown"))
                //    shutdown = Boolean.parseBoolean(sub[1]);
                
               // else if(sub[0].equals("timeout"))
                //    timeout = Integer.parseInt(sub[1]);
                
                //else if(sub[0].equals("limit"))
                //    limit = Integer.parseInt(sub[1]);

              //  else if(sub[0].equals("port"))
                //    port = Integer.parseInt(sub[1]);
               // else 
                //    toServerBytes = sub[1].getBytes();
            //}


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
            catch (Exception ex) {
                System.err.println("SHIT SUCKS");
                System.exit(1);
            }

        }

    }
}

