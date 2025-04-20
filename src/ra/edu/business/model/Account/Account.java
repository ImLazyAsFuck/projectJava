package ra.edu.business.model.Account;

public class Account{
    private int id;
    private int accountId;
    private String username;
    private String password;
    private String email;
    private AccountStatus status;
    private Role role;

    public Role getRole(){
        return role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public static Account currentAccount = null;

    public Account(int id, int accountId, String username, String password, String email, AccountStatus status, Role role){
        this.id = id;
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public Account(){
    }

    public int getAccountId(){
        return accountId;
    }

    public void setAccountId(int accountId){
        this.accountId = accountId;
    }

    public AccountStatus getStatus(){
        return status;
    }

    public void setStatus(AccountStatus status){
        this.status = status;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
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
        if(currentAccount != null){
            currentAccount = null;
            System.out.println("Logged out successfully");
            return;
        }
        System.out.println("Can't log out because user is not logged in");
    }


}
