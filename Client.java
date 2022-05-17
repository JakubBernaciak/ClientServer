import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client{
    public static void main(String[] args){
        
        Scanner scanner = new Scanner(System.in);
        
        InetAddress ip = null;
        try{
        ip = InetAddress.getByName("localhost");
        }
        catch(UnknownHostException u){
            u.printStackTrace();
        }

        Socket socket;
        DataInputStream input;
        DataOutputStream output;
        try{
            socket = new Socket(ip,5000);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            while(true){
                System.out.println(input.readUTF());
                String notification = scanner.nextLine();
                output.writeUTF(notification);

                if(notification.equals("exit") ||  notification.equals("Exit")){
                    System.out.println("Closing this connection : " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                System.out.println(input.readUTF());
                try{
                    Integer delay = Integer.parseInt(scanner.nextLine());
                    if(delay <= 0){
                        System.out.println("Incorrect input data");
                        output.writeInt(-1);
                        continue;
                    }
                    output.writeInt(delay);
                }catch(NumberFormatException e){
                    System.out.println("Incorrect input");
                    output.writeInt(-1);
                    continue;
                }
                

                String received = input.readUTF();
                System.out.println(received);
            }
            scanner.close();
            input.close();
            output.close();
        }
        catch(IOException i){
            i.printStackTrace();
        }

    }
}