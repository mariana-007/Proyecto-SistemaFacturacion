package facturacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 */
public class Conexion {

    private static final String URL = "";
    private static final String USUARIO = "";
    private static final String CLAVE = ""; 

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
