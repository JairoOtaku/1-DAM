package Models;

// @author Otaku
import java.sql.Connection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public final class Conexion {

    String user;
    String passw;
    String direcc;
    String bd;

    public Conexion() {
    }

    public void RecogeDatos() {
        try {
            File fe = new File("conecta.txt");
            FileReader fr = new FileReader(fe);
            BufferedReader br = new BufferedReader(fr);

            user = br.readLine();
            passw = br.readLine();
            direcc = br.readLine();
            bd = br.readLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al coger los datos de conexion");
        }
    }

    public Connection conectado() {
        RecogeDatos();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + direcc + "/" + bd, user, passw);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la Base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return con;
    }
}
