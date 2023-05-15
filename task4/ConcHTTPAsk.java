import java.net.*;
import java.io.*;

public class ConcHTTPAsk {


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

                Runnable r = new MyRunnable(socket);
                new Thread(r).start();
            }
            catch (Exception ex) {
                System.err.println("SHIT SUCKS");
                System.exit(1);
            }

        }

    }
}

