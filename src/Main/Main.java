package Main;

// @author Otaku
import Controllers.Controlador;
import Views.VistaPrincipal;

public class Main {

    public static void main(String[] args) {
        new Controlador(new VistaPrincipal()).iniciar();
    }

}
