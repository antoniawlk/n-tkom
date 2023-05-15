package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
   public TCPClient() {
    }

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {
    	
    	Socket clientSocket = new Socket(hostname, port);  
    	
    	clientSocket.getOutputStream().write(toServerBytes);
    	
    	InputStream inputstream = clientSocket.getInputStream();
    	
    	int temp = inputstream.read();
 	
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	
    	while (temp != -1) {
    		stream.write(temp);
    		temp = inputstream.read();
    	}
    
    	clientSocket.close();
    	
    	byte[] rtn = stream.toByteArray();
        return rtn;
    }
}
