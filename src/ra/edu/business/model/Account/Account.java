package ra.edu.business.model.Account;

import ra.edu.business.model.Inputable;
import ra.edu.business.model.LengthContain;
import ra.edu.validate.StudentValidator;

public class Account implements Inputable{
    private int id;
    private String username;
    private String password;
    private AccountStatus status;
    private Role role;

    public Role getRole(){
        return role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public static Account currentAccount = null;

    public Account(){
        this.status = AccountStatus.ACTIVE;
        this.role = Role.STUDENT;
    }

    public AccountStatus getStatus(){
        return status;
    }

    public void setStatus(AccountStatus status){
        this.status = status;
    }

    public Account(int id, String username, String password, AccountStatus status, Role role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
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


    @Override
    public void inputData(){
        this.username = StudentValidator.studentNameValidate("Enter the username: ", new LengthContain(0, 100));
        this.password = StudentValidator.passwordValidate("Enter the password: ");

    }
}
