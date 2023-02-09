package PROCDISTRIBUIDO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Conexion {

    private static Statement statement = null;
    private final String user = "tuuser";
    private final String password = "tupasssword";

    private Conexion() {
        String url = "jdbc:sqlserver://25.2.141.212;databaseName=test;encrypt=true;trustServerCertificate=true";
        try {
            Connection cnn = DriverManager.getConnection(url, user, password);
            statement = cnn.createStatement();
            System.out.println("UNIDAD1.Conexion exitosa");
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    public static Statement getStatement() {
        if ( statement == null ) {
            System.out.println("Hola");
            new Conexion();
        }
        return statement;
    }

}
