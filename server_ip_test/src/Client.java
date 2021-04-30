import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    //in questa classe dovremo rispettare il protocollo che abbiamo implementato lato server
    private Socket socket;
    private BufferedReader inSocket; //riceve dal Server
    private PrintWriter outSocket;  //manda al Server
    private BufferedReader inKeyboard;//reader della tastiera.
    private ArrayList<Username> onlineUsers;

    public Client() {

        try {
            socket = new Socket("localhost", 3000);
            //questa parte è normale API di Java, prendere e copiare
            inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outSocket = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);
            //leggere dalla tastiera del client
            inKeyboard = new BufferedReader(new InputStreamReader(System.in));


            login();
            play();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void login() {
        System.out.println("Inserisci il tuo Username:");
        try {
           String username = inKeyboard.readLine();
            outSocket.println(username);
            System.out.println(username+" you logged in.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void play(){
        System.out.println("Partita iniziata");
        System.out.println("Leggi i numeri e riscrivi");
        System.out.println("Premi invio per iniziare");
        try {
            inKeyboard.readLine();
            outSocket.println("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean ok = true;

        while(ok){

            try {
                //La prima cosa che facciamo qui è leggere
                //il primo messaggio che arriva dal Server
                String serverSeq = inSocket.readLine();
                System.out.println(serverSeq);
                Thread.sleep(500);
                generateSpace();
                String seq = inKeyboard.readLine();
                outSocket.println(seq);
                ok = Boolean.valueOf(inSocket.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateSpace() {
        for (int i=0; i<200; i++)
            System.out.println();
    }

    public static void main(String[] args){new Client();}

}