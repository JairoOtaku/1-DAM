package Models;

// @author Otaku
import Classes.Empleado;
import Classes.Proyecto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Consultas {

    Conexion con;
    Statement stm;
    ResultSet rst;

    public Consultas() {
        this.con = new Conexion();
    }

    //************* EMPLEADOS *************
    public ArrayList<Empleado> recogerEmpleados() {
        ArrayList<Empleado> lEmple = new ArrayList<>();
        try {
            stm = con.conectado().createStatement();
            rst = stm.executeQuery("select * from empleado");
            while (rst.next()) {
                Empleado e = new Empleado();
                e.setId(rst.getInt("id"));
                e.setNif(rst.getString("Nif"));
                e.setNombre(rst.getString("Nombre"));
                e.setApellido(rst.getString("Apellido"));
                e.setFechaNacimiento(rst.getString("Fecha_Nacimiento"));
                lEmple.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lEmple;
    }

    public void insertarEmpleado(String nif, String nombre, String apellido, String fecha) {
        try {
            stm = con.conectado().createStatement();
            String sql;
            sql = "insert into empleado (Nombre, Apellido, NIF, Fecha_Nacimiento) VALUES ('" + nombre + "','" + apellido + "','" + nif + "','" + fecha + "')";
            stm.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modificarEmpleado(int id, String nif, String nombre, String apellido, String fecha) {
        try {
            stm = con.conectado().createStatement();
            String sql;
            sql = "UPDATE empleado SET Nombre='" + nombre + "',Apellido = '" + apellido + "' ,NIF = " + nif + " , Fecha_Nacimiento='" + fecha + "' WHERE id LIKE " + id + "";
            stm.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteEmpleado(int id) {
        try {
            stm = con.conectado().createStatement();
            String sql;
            sql = "SELECT count(empleado) AS total from empleado_proyecto where empleado=" + id;
            ResultSet ex = stm.executeQuery(sql);
            ex.first();
            int total = ex.getInt("total");
            if (total == 0) {
                int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desear despedir al empleado?");
//Descomentar para comprobar el resultado de los botones del ConfirmDIalog                
//System.out.println(i); 
                if (i == 0) {
                    sql = "DELETE FROM empleado where id=" + id;
                    stm.executeUpdate(sql);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede borrar un empleado asignado a un proyecto");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //************ PROYECTOS ************
    public ArrayList<Proyecto> recogerProyectos() {
        ArrayList<Proyecto> lProyect = new ArrayList<>();
        try {
            stm = con.conectado().createStatement();
            rst = stm.executeQuery("select * from proyecto");
            while (rst.next()) {
                Proyecto p = new Proyecto();
                p.setId(rst.getInt("id"));
                p.setTitulo(rst.getString("Titulo"));
                p.setDescripcion(rst.getString("Descripcion"));
                p.setFechaInicio(rst.getString("Fecha_Inicio"));
                p.setFechaFin(rst.getString("Fecha_Fin"));
                p.setMaxEmple(rst.getInt("max_emple"));
                lProyect.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lProyect;
    }

    public void insertarProyeto(String titulo, String fch_inicio, String fch_entrega, String descripcion, int max_emple) {
        try {
            stm = con.conectado().createStatement();
            String sql;
            sql = "insert into proyecto (Titulo,Fecha_Inicio,Fecha_Fin,Descripcion,max_emple) VALUES ('" + titulo + "','" + fch_inicio + "','" + fch_entrega + "','" + descripcion + "'," + max_emple + ")";
            stm.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modificarProyecto(int id, String titulo, String fch_inicio, String fch_entrega, String descripcion, int max_emple) {
        try {
            stm = con.conectado().createStatement();
            String sql;
            sql = "UPDATE proyecto SET Titulo='" + titulo + "',Fecha_Inicio='" + fch_inicio + "',Fecha_Fin='" + fch_entrega + "',Descripcion='" + descripcion + "',max_emple=" + max_emple + " WHERE id LIKE " + id + "";
            stm.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteProyecto(int id) {
        try {
            stm = con.conectado().createStatement();
            String sql;
            sql = "SELECT count(proyecto) AS total from empleado_proyecto where proyecto=" + id;
            ResultSet ex = stm.executeQuery(sql);
            ex.first();
            int total = ex.getInt("total");
            if (total == 0) {
                int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desear eliminar el proyecto?");
                if (i == 0) {
                    sql = "DELETE FROM proyecto where id=" + id;
                    stm.executeUpdate(sql);
                    // System.out.println("BORRADO EL PROYECTO");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede borrar un proyecto que tenga empleados asignados");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//***** MEZCLA *****
    public ArrayList<Empleado> recogerEmpleProy(int id) {
        ArrayList<Empleado> lEmple2 = new ArrayList<>();
        try {
            stm = con.conectado().createStatement();
            rst = stm.executeQuery("SELECT * FROM empleado WHERE id in (SELECT empleado FROM empleado_proyecto WHERE proyecto like " + id + ")");
            while (rst.next()) {
                Empleado e = new Empleado();
                e.setId(rst.getInt("id"));
                e.setNif(rst.getString("Nif"));
                e.setNombre(rst.getString("Nombre"));
                e.setApellido(rst.getString("Apellido"));
                e.setFechaNacimiento(rst.getString("Fecha_Nacimiento"));
                lEmple2.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lEmple2;
    }

    public void insertarEmpleProy(int id_emple, int id_proy) {
        try {
            stm = con.conectado().createStatement();
            String sql;
            sql = "insert into empleado_proyecto (empleado,proyecto) VALUES (" + id_emple + "," + id_proy + ")";
            stm.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarEmpleProy(int id_emple, int id_proy) {
        try {
            stm = con.conectado().createStatement();
            String sql;
            sql = "DELETE FROM empleado_proyecto WHERE empleado like " + id_emple + " AND proyecto like " + id_proy + "";
            stm.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int maximoEmple(int id) throws SQLException {
        int n;

        stm = con.conectado().createStatement();
        String sql;
        sql = "select COUNT(*) as total FROM empleado_proyecto WHERE proyecto LIKE " + id;
        ResultSet ex = stm.executeQuery(sql);
        ex.first();
        n = ex.getInt("total");

        return n;
    }
}
