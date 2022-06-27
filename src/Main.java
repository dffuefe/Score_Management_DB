import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        new GUI();

        //연결 되는지 테스트용 코드
        /*
        String driver = "org.mariadb.jdbc.Driver";
        String url = "jdbc:mariadb://127.0.0.1:3306/mydb";
        String ID = "root";
        String PW = "system";
        Connection conn;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,ID,PW);
            System.out.println("JDBC Connected");
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        */

    }
}
