import java.util.ArrayList;

public class Username {

    private final String username;
    private ArrayList<Username> onlineUsers;

    Username(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkUsedUsername(Username username){
            boolean isIn = onlineUsers.contains(username);
            return isIn;
        }

}



