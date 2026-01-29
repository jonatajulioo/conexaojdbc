import java.sql.SQLException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/crud_jdbc";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
