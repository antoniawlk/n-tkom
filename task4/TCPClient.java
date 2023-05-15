
import java.net.*;
import java.io.*;

public class TCPClient {
    boolean shutdown;
    Integer timeout;
    Integer limit;
    
    public TCPClient(boolean shutdown, Integer timeout, Integer limit) {
        this.shutdown = shutdown;
        this.timeout = timeout;
        this.limit = limit;
    }

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {

        
        Socket clientSocket = new Socket(hostname, port); 

        if(timeout != null){
            clientSocket.setSoTimeout(timeout); 
        }
    	
        System.out.println("host");

        if(toServerBytes != null){
    	    clientSocket.getOutputStream().write(toServerBytes);
        }

        System.out.println("host");
        if(shutdown){
            clientSocket.shutdownOutput();
        }
    	
    	InputStream inputstream = clientSocket.getInputStream();
    	
    	int temp = inputstream.read();
 	
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();

        if(limit != null){
            clientSocket.setReceiveBufferSize(limit);
        }
        
        try {
    	
    	while (temp != -1 ) {
    		stream.write(temp);
    		temp = inputstream.read();
    	}
  
        }
        catch(SocketException e){}
        catch(SocketTimeoutException x){}

        clientSocket.close();

    	byte[] rtn = stream.toByteArray();

        return rtn;

        

       
    }
}
