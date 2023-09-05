import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    PreparedStatement ps;

    public int countTable(Connection conn, String tableName){
        try{
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT Count(*) FROM " + tableName);
//            return result.getInt(1);
            int count =0;
            while(result.next()){
                count = result.getInt(1);
            }
            return count;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public void addValuesList(Connection conn){
        try{
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("Select * from user");
            while (result.next()){
                Database.userList.add(new User(result.getString(1), result.getString(2)));
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static ArrayList<User> userList= new ArrayList<>();
    public void addValuesUser(Connection conn, String name, String password){
        try{
            PreparedStatement ps = conn.prepareStatement("insert into user values('"+ name +"', '"+ password+"')");
            PreparedStatement ps1 = conn.prepareStatement("CREATE TABLE "+name+"table" + " (count int PRIMARY KEY AUTO_INCREMENT, Task varchar(255))");
            int i = ps.executeUpdate();
            int y = ps1.executeUpdate();
            System.out.println("Record inserted successfully");
            userList.add(new User(name, password));
        }catch(Exception e){
            System.out.println(e);
        }

    }

    public void deleteValuesUser(Connection conn, String name){
        String deleteQry = "DELETE FROM user where username='" + name + "'";
        String dropQry = "DROP TABLE "+name+"table";
        try {
            ps = conn.prepareStatement(deleteQry);
            ps = conn.prepareStatement(dropQry);
            ps.executeUpdate();
//            int y = ps1.executeUpdate();
            System.out.println("Record deleted successfully");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void changeUserName(Connection conn, String newName, String oldName){
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE user SET username='" + newName + "' WHERE username='" + oldName+ "'");
            PreparedStatement ps1 = conn.prepareStatement("ALTER TABLE "+oldName+"table rename "+newName+"table");
            int i = ps.executeUpdate();
//            int y = ps1.executeUpdate();
            System.out.println("name changed successfully");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void changeUserPassword(Connection conn, String newPassword, String oldPassword){
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE user SET password='" + newPassword + "' WHERE username='" + oldPassword + "'");
            int i = ps.executeUpdate();
            System.out.println("password changed successfully");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addTask(Connection conn, String tableName, String message){
        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tableName + "(Task) VALUES('"+message+"')");
            int i = ps.executeUpdate();
            System.out.println("Task added successfully");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void showTableTask(Connection conn, String tableName){
        try{
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + tableName);

            System.out.println("Number|   Tasks");
            while(result.next()){
                System.out.println(result.getInt(1) + "     |   " + result.getString(2));
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void deleteTask(Connection conn, String tableName, int taskNumber){
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tableName + "table WHERE count=" + taskNumber);
            int i = ps.executeUpdate();
            System.out.println("Task removed sucessfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
