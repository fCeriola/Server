import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServeOneClient implements Runnable {
    //questa classe va a definire il protocollo (lato server), ovvero quello che
    // il server si aspetta di vedere arrivare e mandare.
    //ogni client deve essere eseguito su un thread diverso, altrimenti non posso avere client multipli
    private Socket socket;
    private BufferedReader inSocket; //riceve dal client
    private PrintWriter outSocket; //manda al client
    public ArrayList<Username> onlineUsers;

    ServeOneClient(Socket socket){
        this.socket = socket;
        System.out.println("Client Connected");
        try {
            //questa parte è normale API di Java, prendere e copiare
            inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outSocket = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
        login();
        manageClient();
    }

    //operazioni che fa il Client
    private void manageClient() {
        play();
        showRecords();
        close();
    }

    private void showRecords() {
    }

    private void play() {
        boolean ok=true;
        int l=1;
        
        while (ok){
            try {
            String seq = generateSeq(l);
            outSocket.println(seq);
            String clientSeq = inSocket.readLine();
            if (clientSeq.equals(seq))
                outSocket.println("true");
            else{
                outSocket.println("false");
                ok = false;
            }
            l++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void close() {
        try {
            socket.close();
            System.out.println("Client closed...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateSeq(int l) {
        String res = "";
        for (int i = 0; i<l; i++){
            int num = (int) (Math.random()*10);
            res += num ;
        }
        return res;
    }

    private void login() {
        try {
            Username user = new Username(inSocket.readLine());
            System.out.println(user.getUsername()+ " logged in the Server.");
//            onlineUsers.add(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
