import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader buffererdReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    
    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.buffererdReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = buffererdReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("Server: " + clientUsername + "has entered the chat!");

        } catch (IOException e) {
            closeChatroom(socket, buffererdReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isClosed()) {
            try{
            messageFromClient = buffererdReader.readLine();
            broadcastMessage(messageFromClient);
        } catch (IOException e){
            closeChatroom(socket, buffererdReader, bufferedWriter);
            break;
         }
        }
    }

    public void broadcastMessage(String messageToSend){
        for (ClientHandler clientHandler : clientHandlers){
            try{
                if (!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                } 
            } catch(IOException e){
                closeChatroom(socket, buffererdReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler (){
        clientHandlers.remove(this);
        broadcastMessage(messageToSend: "SERVER: " + clientUsername + " has left chat!");
    }
    public void closeChatroom(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClienthandler();
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
}
