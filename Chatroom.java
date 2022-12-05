import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.ServerSocket;
/**
 * Chatroom
 */


public class Chatroom {

    private ServerSocket serverSocket;

    public Chatroom(ServerSocket serverSocket){
        this.serverSocket = serverSocket;

    }

    public void startServer(){

        try{
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("New User added!");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

            }
        } catch (IOException e) {

        }
         public void closeServerSocket(){
            try{
                if (serverSocket!= null){
                    serverSocket.close();
                }

            }catch (IOException e){
                e.printStackTrace();
            }
        }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        Chatroom server = new Chatroom(serverSocket);
        server.startServer();
        System.out.println("Testing");
        System.out.println("Testing 2");
    }
}