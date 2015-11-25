package Controllers;

// @author Otaku
import Classes.Empleado;
import Classes.Proyecto;
import Models.Consultas;
import Views.JDialogs.JDInsertProy;
import Views.JDialogs.JDNewEmpleado;
import Views.JDialogs.JDNewProyect;
import Views.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//IMPORTS DEL iText
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controlador {

    private final VistaPrincipal p;
    private final Consultas consultas;
    JDNewEmpleado jdne;
    JDNewProyect jdnp;
    JDInsertProy jdip;
    ArrayList<Proyecto> ProjectX;
    ArrayList<Empleado> empleados;

    public Controlador(VistaPrincipal p) {
        this.p = p;
        this.consultas = new Consultas();
    }

    public void iniciar() {
//************ BOTONES DE LOS EMPLEADOS ****************
        // BOTON NUEVO EMPLEADO
        p.btnNewEmple.addActionListener((ActionEvent) -> {

            jdne = new JDNewEmpleado(p, true);
            jdne.txtNIF.setText("");
            jdne.txtNombre.setText("");
            jdne.txtApellidos.setText("");
            jdne.txtFchNaci.setText("--/--/--");

            //BOTON ACEPTA DE NUEVO EMPLEADO
            jdne.btnAceptar.addActionListener((ActionEvent e) -> {
                String nif, nombre, apell, fch;
                nif = jdne.txtNIF.getText();
                nombre = jdne.txtNombre.getText();
                apell = jdne.txtApellidos.getText();
                fch = jdne.txtFchNaci.getText();
                consultas.insertarEmpleado(nif, nombre, apell, fch);
                p.TablaEmpleados.setModel(this.getTablaEmple());
                jdne.dispose();
            });
            //BOTON CANCELAR DE NUEVO EMPLEADO
            jdne.btnCancelar.addActionListener((ActionEvent e) -> {
                jdne.dispose();
            });
            jdne.setVisible(true);
        });

        //MODIFICAR EMPLEADO
        p.btnModEmple.addActionListener((ActionEvent e) -> {
            jdne = new JDNewEmpleado(p, true);
            empleados = consultas.recogerEmpleados();
            if (p.TablaEmpleados.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(jdne, "Error, seleccione un empleado de la tabla primero");
            } else {
                Empleado auxilio = empleados.get(p.TablaEmpleados.getSelectedRow());
                jdne.txtNIF.setText(auxilio.getNif());
                jdne.txtApellidos.setText(auxilio.getApellido());
                jdne.txtFchNaci.setText(auxilio.getFechaNacimiento());
                jdne.txtNombre.setText(auxilio.getNombre());
                //BOTON ACEPTAR MODIFICAR EMPLEADO
                jdne.btnAceptar.addActionListener((ActionEvent a) -> {
                    String nif, nombre, apell, fch;
                    int id = auxilio.getId();
                    nif = jdne.txtNIF.getText();
                    nombre = jdne.txtNombre.getText();
                    apell = jdne.txtApellidos.getText();
                    fch = jdne.txtFchNaci.getText();
                    consultas.modificarEmpleado(id, nif, nombre, apell, fch);
                    p.TablaEmpleados.setModel(this.getTablaEmple());
                    jdne.dispose();
                });
                //BOTON CANCELAR MODIFICAR EMPLEADO
                jdne.btnCancelar.addActionListener((ActionEvent a) -> {
                    jdne.dispose();
                });
                jdne.setVisible(true);
            }
        });

        //DESPEDIR EMPLEADO
        p.btnDespedir.addActionListener((ActionEvent e) -> {
            empleados = consultas.recogerEmpleados();
            if (p.TablaEmpleados.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(jdne, "Error, seleccione un empleado de la tabla primero");
            } else {
                Empleado HELP = empleados.get(p.TablaEmpleados.getSelectedRow());
                consultas.deleteEmpleado(HELP.getId());
                p.TablaEmpleados.setModel(this.getTablaEmple());
            }
        });
//**********************************************
        //BOTON AGREGAR PROYECTO A EMPLEADO
        p.btnAgregaProye.addActionListener((ActionEvent e) -> {
            jdip = new JDInsertProy(p, true);
            ProjectX = consultas.recogerProyectos();
            empleados = consultas.recogerEmpleados();
            if (p.TablaEmpleados.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(jdne, "Error, seleccione un empleado de la tabla primero");
            } else {
                Empleado aux = empleados.get(p.TablaEmpleados.getSelectedRow());
                Proyecto auxil;

                jdip.JComboInsert.setModel(this.getComboBox());
                auxil = ProjectX.get(jdip.JComboInsert.getSelectedIndex());
                jdip.txtDescripcionInsert.setText(auxil.getDescripcion());

                jdip.JComboInsert.addActionListener((ActionEvent e1) -> {
                    Proyecto joe = ProjectX.get(jdip.JComboInsert.getSelectedIndex());
                    jdip.txtDescripcionInsert.setText(joe.getDescripcion());

                });

                jdip.btnConfirmarInsert.addActionListener((ActionEvent e1) -> {
                    Proyecto leñe = ProjectX.get(jdip.JComboInsert.getSelectedIndex());
                    int maximo;
                    try {
                        maximo = consultas.maximoEmple(leñe.getId());

                        if (maximo < leñe.getMaxEmple()) {
                            consultas.insertarEmpleProy(aux.getId(), leñe.getId());
                            jdip.dispose();
                        } else {
                            JOptionPane.showMessageDialog(p, "Ya esta el maximo de empleados por proyecto, seleccione otro o elimine un empleado del proyecto priemero");
                            jdip.dispose();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                jdip.btnCancelarInsert.addActionListener((ActionEvent e1) -> {
                    jdip.dispose();
                });
                jdip.setVisible(true);
            }
        });

//********** BOTONES DE LOS PROYECTOS ************
        //ACTION EVENT DEL JCOMBOBOX para actualizar la pantalla
        p.jComboBox1.addActionListener((ActionEvent e) -> {
            this.setTxtsProyecto(ProjectX.get(p.jComboBox1.getSelectedIndex()));
            p.TablaEmpleProyec.setModel(this.getTablaEmpleProy());
        });

        //NUEVO PROYECTO
        p.btnNewProyecto.addActionListener((ActionEvent e) -> {
            jdnp = new JDNewProyect(p, true);

            jdnp.txtNameProyect.setText("");
            jdnp.txtDescripcion.setText("");
            jdnp.txtFchEntrega.setText("--/--/--");
            jdnp.txtFchInicio.setText("--/--/--");
            jdnp.txtMaxEmple.setText("1");

            //BOTON ACEPTAR
            jdnp.btnAceptar.addActionListener((ActionEvent e1) -> {
                String titulo, descripcion, fchIn, fchEntr;
                int max_emple;
                titulo = jdnp.txtNameProyect.getText();
                max_emple = Integer.parseInt(jdnp.txtMaxEmple.getText());
                fchIn = jdnp.txtFchInicio.getText();
                fchEntr = jdnp.txtFchEntrega.getText();
                descripcion = jdnp.txtDescripcion.getText();
                consultas.insertarProyeto(titulo, fchIn, fchEntr, descripcion, max_emple);
                p.jComboBox1.setModel(this.getComboBox());
                jdnp.dispose();
            });
            //BOTON CANCELAR
            jdnp.btnCancelar.addActionListener((ActionEvent a) -> {
                jdnp.dispose();
            });
            jdnp.setVisible(true);
        });

        //BOTON MODIFICAR PROYECTO
        p.btnModProyecto.addActionListener((ActionEvent e) -> {
            jdnp = new JDNewProyect(p, true);
            ProjectX = consultas.recogerProyectos();
            Proyecto auxi = ProjectX.get(p.jComboBox1.getSelectedIndex());
            jdnp.txtDescripcion.setText(auxi.getDescripcion());
            jdnp.txtNameProyect.setText(auxi.getTitulo());
            jdnp.txtFchEntrega.setText(auxi.getFechaFin());
            jdnp.txtFchInicio.setText(auxi.getFechaInicio());
            jdnp.txtMaxEmple.setText(String.valueOf(auxi.getMaxEmple()));

            //BOTON ACEPTAR
            jdnp.btnAceptar.addActionListener((ActionEvent e1) -> {
                String titulo, descripcion, fchIn, fchEntr;
                int max_emple, id;
                id = auxi.getId();
                titulo = jdnp.txtNameProyect.getText();
                max_emple = Integer.parseInt(jdnp.txtMaxEmple.getText());
                fchIn = jdnp.txtFchInicio.getText();
                fchEntr = jdnp.txtFchEntrega.getText();
                descripcion = jdnp.txtDescripcion.getText();
                consultas.modificarProyecto(id, titulo, fchIn, fchEntr, descripcion, max_emple);
                p.jComboBox1.setModel(this.getComboBox());
                this.setTxtsProyecto(ProjectX.get(p.jComboBox1.getSelectedIndex()));
                jdnp.dispose();
            });
            //BOTON CANCELAR
            jdnp.btnCancelar.addActionListener((ActionEvent a) -> {
                jdnp.dispose();
            });
            jdnp.setVisible(true);
        });

        //BOTON BORRAR PROYECTO
        p.btnDeleteProyecto.addActionListener((ActionEvent e) -> {
            ProjectX = consultas.recogerProyectos();
            Proyecto HALP = ProjectX.get(p.jComboBox1.getSelectedIndex());
            consultas.deleteProyecto(HALP.getId());
            p.jComboBox1.setModel(this.getComboBox());
            this.setTxtsProyecto(ProjectX.get(p.jComboBox1.getSelectedIndex()));
        });
        //BOTON ELIMINA RELACION
        p.btnEliminaRelacion.addActionListener((ActionEvent e) -> {
            ProjectX = consultas.recogerProyectos();
            empleados = consultas.recogerEmpleProy(ProjectX.get(p.jComboBox1.getSelectedIndex()).getId());

            if (p.TablaEmpleProyec.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(p, "Error, seleccione un empleado de la tabla primero");
            } else {
                //System.out.println(empleados.get(p.TablaEmpleProyec.getSelectedRow()).getId() + " " + ProjectX.get(p.jComboBox1.getSelectedIndex()).getId());
                consultas.eliminarEmpleProy(empleados.get(p.TablaEmpleProyec.getSelectedRow()).getId(), ProjectX.get(p.jComboBox1.getSelectedIndex()).getId());
                p.TablaEmpleProyec.setModel(this.getTablaEmpleProy());
            }
        });

        //********** GENERAR PDF **************
        p.btnGeneraPdf.addActionListener((ActionEvent e) -> {
            this.generaPdf();
        });

        //****************INICIALIZADORES DE LOS MODELOS***************
        //Inicializadores de las Tablas y el ComboBox de los proyectos
        p.jComboBox1.setModel(this.getComboBox());
        this.setTxtsProyecto(ProjectX.get(p.jComboBox1.getSelectedIndex()));
        p.TablaEmpleProyec.setModel(this.getTablaEmpleProy());
        p.TablaEmpleados.setModel(this.getTablaEmple());
        p.setVisible(true);
    }
    //*********************

    public DefaultTableModel getTablaEmple() {
        DefaultTableModel modeloEmple = new DefaultTableModel();
        modeloEmple.addColumn("NIF");
        modeloEmple.addColumn("Nombre");
        modeloEmple.addColumn("Apellidos");
        modeloEmple.addColumn("Fecha Nacimiento");
        Object[] fila = new Object[4];

        ArrayList<Empleado> recogerEmpleados = consultas.recogerEmpleados();
        for (Empleado e : recogerEmpleados) {
            fila[0] = e.getNif();
            fila[1] = e.getNombre();
            fila[2] = e.getApellido();
            fila[3] = e.getFechaNacimiento();
            modeloEmple.addRow(fila);
        }
        return modeloEmple;
    }
//**********************

    public DefaultTableModel getTablaEmpleProy() {
        DefaultTableModel modeloEmpleProy = new DefaultTableModel();
        modeloEmpleProy.addColumn("NIF");
        modeloEmpleProy.addColumn("Nombre");
        modeloEmpleProy.addColumn("Apellidos");
        modeloEmpleProy.addColumn("Fecha Nacimiento");
        Object[] fila = new Object[4];

        ArrayList<Empleado> recogerEmpleados = consultas.recogerEmpleProy(ProjectX.get(p.jComboBox1.getSelectedIndex()).getId());
        for (Empleado e : recogerEmpleados) {
            fila[0] = e.getNif();
            fila[1] = e.getNombre();
            fila[2] = e.getApellido();
            fila[3] = e.getFechaNacimiento();
            modeloEmpleProy.addRow(fila);
        }
        return modeloEmpleProy;
    }
//**********************

    public DefaultComboBoxModel getComboBox() {
        DefaultComboBoxModel ComboModel = new DefaultComboBoxModel();
        ProjectX = consultas.recogerProyectos();
        for (Proyecto Pro : ProjectX) {
            ComboModel.addElement(Pro.getTitulo());
        }
        return ComboModel;
    }
//***********************

    public void setTxtsProyecto(Proyecto proyec) {
        p.txtFchEntrega.setText(proyec.getFechaFin());
        p.txtFchInicio.setText(proyec.getFechaInicio());
        p.txtNMaximo.setText(String.valueOf(proyec.getMaxEmple()));
        p.txtDescripcion.setText(proyec.getDescripcion());
    }
//************************

    public void generaPdf() {
        Document documento = new Document();
        FileOutputStream ficheroPdf;
        PdfPTable tabla_emple, tabla_proyec, tabla_relacional;
        empleados = consultas.recogerEmpleados();
        ProjectX = consultas.recogerProyectos();
        ArrayList<Empleado> relacional;

        try {
            ficheroPdf = new FileOutputStream("ejemplo.PDF");
            PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        try {
            //abrimos el documento para editarlo
            documento.open();
            //aqui agregamos todo el contenido del PDF...

            documento.add(new Paragraph("Este informe es una manera de imprimir la informacion, en forma de tablas, de la base de datos"));
            documento.add(new Paragraph(" "));
            //***************TABLA DE LOS EMPLEADOS**************
            documento.add(new Paragraph("Empleados de la compañia:"));
            documento.add(new Paragraph(" "));
            //inicio la tabla con las 4 columnas de los empleados
            tabla_emple = new PdfPTable(4);
            //cada 4 addCell, es una fila nueva
            tabla_emple.addCell("NIF");
            tabla_emple.addCell("Nombre");
            tabla_emple.addCell("Apellidos");
            tabla_emple.addCell("Fecha de Nacimiento");
            for (Empleado e : empleados) {
                tabla_emple.addCell(e.getNif());
                tabla_emple.addCell(e.getNombre());
                tabla_emple.addCell(e.getApellido());
                tabla_emple.addCell(e.getFechaNacimiento());
            }
            documento.add(tabla_emple);
            documento.add(new Paragraph(" "));

            //*********TABLA DE LOS PROYECTOS************
            documento.add(new Paragraph("Proyectos actuales en la compañia:"));
            documento.add(new Paragraph(" "));
            tabla_proyec = new PdfPTable(5);
            tabla_proyec.addCell("Titulo");
            tabla_proyec.addCell("Fecha de Inicio");
            tabla_proyec.addCell("Fecha de Entrega");
            tabla_proyec.addCell("Descripcion");
            tabla_proyec.addCell("Nº Maximo de empleados");
            for (Proyecto p : ProjectX) {
                tabla_proyec.addCell(p.getTitulo());
                tabla_proyec.addCell(p.getFechaInicio());
                tabla_proyec.addCell(p.getFechaFin());
                tabla_proyec.addCell(p.getDescripcion());
                tabla_proyec.addCell(String.valueOf(p.getMaxEmple()));
            }
            documento.add(tabla_proyec);
            documento.add(new Paragraph(" "));

            //TABLA RELACIONAL
            documento.add(new Paragraph("Tabla relacional de empleados por proyecto"));
            documento.add(new Paragraph(" "));
            tabla_relacional = new PdfPTable(4);

            for (Proyecto p : ProjectX) {
                Paragraph titulos = new Paragraph(p.getTitulo());
                titulos.setAlignment(1);
                PdfPCell celda = new PdfPCell(titulos);
                celda.setColspan(4);
                celda.setRowspan(2);
                tabla_relacional.addCell(celda);
                tabla_relacional.addCell("NIF");
                tabla_relacional.addCell("Nombre");
                tabla_relacional.addCell("Apellidos");
                tabla_relacional.addCell("Fecha de Nacimiento");
                relacional = consultas.recogerEmpleProy(p.getId());
                for (Empleado er : relacional) {
                    tabla_relacional.addCell(er.getNif());
                    tabla_relacional.addCell(er.getNombre());
                    tabla_relacional.addCell(er.getApellido());
                    tabla_relacional.addCell(er.getFechaNacimiento());
                }
                Paragraph fin = new Paragraph(" ");
                titulos.setAlignment(1);

                PdfPCell celda2 = new PdfPCell(fin);
                celda2.setColspan(4);
                celda2.setRowspan(2);
            }
            documento.add(tabla_relacional);

            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("Desarrollador del programa: Jairo Gallardo Zorrilla"));
            documento.add(new Paragraph("Cliente: Manuel Torres"));
            documento.add(new Paragraph(""));

            //cerramos el documento y se genera
            JOptionPane.showMessageDialog(null, "El archivo pdf se ha generado correctamente");
            documento.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }
}
