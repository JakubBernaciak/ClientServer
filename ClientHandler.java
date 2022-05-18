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
    private void sendMessage(int delay,String notification){
        new Thread(()->{
            try{
                Thread.sleep(1000*delay);
                System.out.println("Sending message "+ notification + " to "+ this.socket);
                output.writeUTF(notification);
            }
            catch(IOException i){
                i.printStackTrace();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            
        }).start();
    }
    @Override
    public void run(){
        String notification;
        Integer definetlyNotTimeToWait;
        while(true){
            try{
                notification = input.readUTF();
                if(notification.equals("")){
                    continue;
                }

                if(notification.equals("exit") || notification.equals("Exit")){
                    this.socket.close();
                    System.out.println("Concetion closed with "+this.socket);
                    break;
                }

                definetlyNotTimeToWait = input.readInt();

                if(definetlyNotTimeToWait == -1)
                    continue;

                sendMessage(definetlyNotTimeToWait , notification);

            }
            catch(EOFException e){
                System.out.println("Concetion closed with "+this.socket);
                break;
            }
            catch(Exception i){
                System.out.print(i);
                break;
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