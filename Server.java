import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args){
        try{
            System.out.println("Server starting...");
            ServerSocket serverSocket = new ServerSocket(5000);

            Runtime.getRuntime().addShutdownHook(
            new Thread(() -> {
                try{
                    serverSocket.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                Runtime.getRuntime().halt(0);
            })
        );

        Server server = new Server();
        server.startServer(serverSocket);

        }
        catch(BindException e){
            System.out.println("Server already running");
            return;
        }
        catch(IOException i){
            System.out.println(i);
            return;
        }
    }   
    public void startServer(ServerSocket serverSocket){
        System.out.println("Server started");
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
