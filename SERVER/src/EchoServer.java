import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {

    private int port;
    private ServerSocket serverSocket;

    EchoServer (int port){
        this.port = port;
    }

    public void startServer() throws IOException{

        serverSocket = new ServerSocket(port);
        System.out.println("Server socket ready on port: "+port);

        Socket socket = serverSocket.accept();
        System.out.println("Received Client connection");

        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream());

        while(true){
            String line = in.nextLine();
            if (line.equals("quit")) break;
            else out.println("Received "+line);
            out.flush();
        }

        System.out.println("closing sockets");
        in.close();
        out.close();
        socket.close();
        serverSocket.close();


    }

    public static void main (String [] args){
        EchoServer server = new EchoServer(3000);

        try {
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
