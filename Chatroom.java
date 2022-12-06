import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Chatroom
 */


public class Chatroom {

    private ServerSocket serverSocket; // Listens to incoming clients and will make an object so they can communicate 

    public Chatroom(ServerSocket serverSocket){
        this.serverSocket = serverSocket;

    }

    public void startServer(){ // Start server keeps server running until Socket is closed

        try{
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept(); // Waits for client to connect 
                System.out.println("New User added!");
                ClientHandler clientHandler = new ClientHandler(socket); 

                Thread thread = new Thread(clientHandler); // makes a new thread every time a new client is added
                thread.start(); // starts the new thread
            }
        } catch (IOException e) {

            }

        }

         public void closeServerSocket(){
            try{
                if (serverSocket!= null){
                    serverSocket.close();
                }

            } catch (IOException e){
                e.printStackTrace();
            }
        }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        Chatroom server = new Chatroom(serverSocket);
        server.startServer();
    }
}