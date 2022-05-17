import java.io.*;
import java.net.*;

public class ClientHandler extends Thread{
    final DataInputStream input;
    final DataOutputStream output;
    final Socket socket;

    //Constructor
    public ClientHandler(Socket socket, DataInputStream input, DataOutputStream output){
        this.input = input;
        this.output = output;
        this.socket = socket;
    }

    @Override
    public void run(){
        String notification;
        Integer definetlyNotTimeToWait;
        while(true){
            try{
                output.writeUTF("Enter notification message or exit to terminate conncetion:");
                notification = input.readUTF();
                

                if(notification.equals("exit") || notification.equals("Exit")){
                    this.socket.close();
                    System.out.println("Concetion closed with "+this.socket);
                    break;
                }

                output.writeUTF("Enter delay: ");
                definetlyNotTimeToWait = input.readInt();

                if(definetlyNotTimeToWait == -1)
                    continue;

                Thread.sleep(1000*definetlyNotTimeToWait);
                output.writeUTF(notification);

            }
            catch(Exception i){
                System.out.print(i);
            }
        }

        try{
            this.input.close();
            this.output.close();
        }catch(IOException i){
            System.out.print(i);
        }
    }
}