package src;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username; 

    public Client(Socket socket, String username) { // communication with Server and ClientHandler 
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e){
            closeChatroom(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(){ // sends message to client handler
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

                try (Scanner scanner = new Scanner(System.in)) {
                    while (socket.isConnected()){
                        String sendMessage = scanner.nextLine();
                        bufferedWriter.write(username + ": " + sendMessage);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }
                }
            } catch (IOException e){
                closeChatroom(socket, bufferedReader, bufferedWriter);
        }
            
    }
    
    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run(){
                String messageFromGroupChat;

                while (socket.isConnected()){
                    try{
                        messageFromGroupChat = bufferedReader.readLine();
                        System.out.print(messageFromGroupChat);
                    } catch (IOException e){
                        closeChatroom(socket, bufferedReader, bufferedWriter);

                    }
                }

            }
        }).start();
    }

    public void closeChatroom(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter Usernane: ");
            String username = scanner.nextLine();
            Socket socket = new Socket("localhost", 1234);
            Client client = new Client(socket, username);
            client.listenForMessage();
            client.sendMessage();
        }
    }
}
