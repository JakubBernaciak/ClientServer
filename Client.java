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
                if(notification.equals("")){
                    System.out.println("You can't send an empty message");
                    continue;
                }

                if(notification.equals("exit") ||  notification.equals("Exit")){
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                System.out.println(input.readUTF());
                try{
                    Integer delay = Integer.parseInt(scanner.nextLine());
                    if(delay < 0){
                        throw new NegativeTime("Time cannot be negative!");
                    }
                    output.writeInt(delay);
                }catch(NumberFormatException e){
                    System.out.println("Incorrect input");
                    output.writeInt(-1);
                    continue;
                }
                catch(NegativeTime n){
                    System.out.println(n.getMessage());
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
        catch(EOFException e){
            System.out.println("Server has stopped responding");
            return;
        }
        catch(ConnectException i){
            System.out.println("Server is not responding");
            return;
        }
        catch(SocketException e){
            System.out.println("Server has stopped responding");
            return;
        }
        catch(IOException i){
            i.printStackTrace();
        }

    }
}