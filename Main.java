import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {

    static Scanner sc= new Scanner(System.in);

    public static void signup(){
    }

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/mainpracticeproject", "root", "");
            Database user = new Database();
            String name="";
            String username="";
            String passLog = "";
            String full = "";
            int wishList = 0;
            int checkNameSign=0;
            int checkNameLog = 0;
            user.addValuesList(conn);
            System.out.println(Database.userList);
//            for(User i: Database.userList){
//                System.out.println(i);
//            }

            while(true){
                    System.out.println("""
                        -------------Welcomer to ToDo List Platform---------
                        1 - Login
                        2 - Signup
                        3 - Exit
                        """);
                    int wish = sc.nextInt();
                switch (wish){
                    case 2->{
                        // For sign up
                        boolean check = true;
                        while(check){
                            System.out.println("Enter your name");
                            name = sc.next();
                            for(User i: Database.userList){
                                if(i.getUsername().equals(name)){
                                    checkNameSign = 1;
                                    System.out.println("Username is already taken");
                                }
                            }
                            if(checkNameSign!=1){
                                check=false;
                            }
                            checkNameSign=0;
                        }

                        System.out.println("Enter your password");
                        String password = sc.next();
                        user.addValuesUser(conn, name, password);

                    }
                    case 3->System.exit(0);
                    case 1->{
                    // For login
                        boolean check = true;
                        boolean work = true;
                        while(check){
                            System.out.println("Enter Username to login");
                            username = sc.next();
                           for(User i: Database.userList){
                               if (i.getUsername().equals(username)) {
                                   work = false;
                                   break;
                               }
                           }
                           if(work){
                               System.out.println("Please enter a valid name");
                           }
                           else{
                               check=false;
                           }

                        }
                        boolean check2 = true;
                        boolean work2 = true;
                        while(check2){
                            System.out.println("Enter Password to login");
                            passLog = sc.next();
                            full = username + " "+ passLog;
                            for(User i: Database.userList){
                                if(i.toString().equals(full)){
                                    work2 = false;
                                    break;
                                }
                            }
                            if(work2){
                                System.out.println("Please enter a valid password");
                            }
                            else{
                                check2=false;
                            }
                        }

                        System.out.println("--------------Welcome to your tasklist----------------");
                        wishList=0;
                        while (wishList!=6){
                            System.out.println("""
                                    1. View your task list
                                    2. Add task to task list
                                    3. Remove task from task list
                                    4 - Delete your account
                                    5. View Account details
                                    6. Log out
                                    """);
                            wishList = sc.nextInt();
                            switch (wishList){
                                case 1->{
                                    if(user.countTable(conn, username+"table") == 0){
                                        System.out.println("Your tasklist is empty..........");
                                    }
                                    else{
                                        user.showTableTask(conn, username+"table");
                                    }
                                }
                                case 2->{
                                    System.out.println("Please write ur task here");
                                    sc.nextLine();
                                    String task = sc.nextLine();
//                                  System.out.println(task);
                                    user.addTask(conn, username + "table", task);

                                }
                                case 3->{
                                    System.out.println("Please enter the task number u want to delete:");
                                    int taskNumber = sc.nextInt();
                                    user.deleteTask(conn, username, taskNumber);
                                }
//                                case 4->{
//                                    System.out.println("Please enter your old login name:");
//                                    String oldName = sc.next();
//                                    System.out.println("Please enter new login name u want:");
//                                    String newName = sc.next();
//                                    user.changeUserName(conn, newName, oldName);
//                                    username = newName;
//                                }
//                                case 5->{
//                                    System.out.println("Please enter your old password");
//                                    String oldPass = sc.next();
//                                    System.out.println("Please enter your new password");
//                                    String newPass = sc.next();
//                                    user.changeUserPassword(conn,newPass, oldPass);
//                                    passLog=newPass;
//                                }
                                case 5->{
                                    System.out.println("Username = " + username);
                                    System.out.println("Password = " + passLog);
                                }
                                case 4->{
//                                    System.out.println("Please enter your name to delete your account");
//                                    String deleteName = sc.next();
                                    user.deleteValuesUser(conn, username);
                                    System.exit(0);
                                }
                            }
                        }
                    }
                    default -> System.out.println("please a valid input");
                }

            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}