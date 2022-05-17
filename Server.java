import java.io.*;
import java.net.*;

public class Server{
    public static void main(String[] args){

        ServerSocket serverSocket = null;
        try{
            System.out.println("Server starting...");
            serverSocket = new ServerSocket(5000);
        }catch(IOException i){
            System.out.println(i);
            return;
        }

        System.out.println("Server startted");
        while(true){
            Socket socket = null;
            
            try{
                socket = serverSocket.accept();

                System.out.println("New client connected: "+socket);

                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                Thread thread = new ClientHandler(socket, input, output);

                thread.start();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}