package ra.edu.business.model.User;

public abstract class User{
    private int id;
    private String username;
    private String password;

    public static User currentUser = null;

    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(){
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public static void logout(){
        if(currentUser != null){
            currentUser = null;
            System.out.println("Logged out successfully");
            return;
        }
        System.out.println("Can't log out because user is not logged in");
    }


}
