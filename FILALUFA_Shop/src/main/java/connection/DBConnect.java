package connection;

import java.sql.*;

public class DBConnect {

    public Connection conn;
    public Statement stat;
    public ResultSet resul;
    public PreparedStatement pstet;


    public DBConnect(){
        try{
            String url =
                    "jdbc:sqlserver://JEHAAJEHA\\SQLEXPRESS:1433;" +
                            "databaseName=Sembako;" +
                            "user=sa;" +
                            "password=poltek;" +
                            "encrypt=true;" +
                            "trustServerCertificate=true;";


            conn =DriverManager.getConnection(url);
            stat = conn.createStatement();

        }catch (Exception e){
            System.out.println("error saat connect database"+e);
        }
    }

    public  static void main(String[] args){
        DBConnect Connection = new DBConnect();
        System.out.println("connection to databse successfully");
    }
}
