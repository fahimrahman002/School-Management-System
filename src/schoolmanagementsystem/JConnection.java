package schoolmanagementsystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class JConnection {

    public static Connection connectdb() {
        String url="jdbc:mysql://localhost/";
        String dbName="school_management_system";
        String username="root";
        String pass="";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url+dbName, username, pass);
            System.out.println("Connected");
            return connection;
        } catch (ClassNotFoundException | SQLException sqle) {
            JOptionPane.showMessageDialog(null, "Connection failed");
            return null;
        }

    }
}
