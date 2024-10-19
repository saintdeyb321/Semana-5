/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER 17
 */
public class EstudianteArray {
    private ArrayList<Estudiante> listaEstudiante;  // Lista de estudiantes en memoria
    private DefaultTableModel modelo;
    private EstudianteDAO estudianteDAO;  // Nueva instancia de DAO para interactuar con la base de datos

    // Constructor que acepta DefaultTableModel
    public EstudianteArray(DefaultTableModel modelo) {
        this.listaEstudiante = new ArrayList<>();
        this.modelo = modelo;
        this.estudianteDAO = new EstudianteDAO();  // Inicializamos el DAO
    }
    
    // Método para calcular la edad en función de la fecha de nacimiento
    public static int calcularEdad(Date fechaNacimiento) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(fechaNacimiento);
    int añoNacimiento = cal.get(Calendar.YEAR);
    int añoActual = Calendar.getInstance().get(Calendar.YEAR);
    return añoActual - añoNacimiento;
}

    // Método para actualizar la tabla con los datos del ArrayList
    public void actualizarTabla() {
        // Limpiar la tabla antes de añadir nuevos datos
        modelo.setRowCount(0);

        // Añadir filas a la tabla desde el ArrayList
        for (Estudiante estudiante : listaEstudiante) {
            Object[] fila = {
                estudiante.getCodigo(),
                estudiante.getNombre(),
                estudiante.getApellidos(),
                estudiante.getFacultad(),
                estudiante.getProvincia(),
                estudiante.getEdad()
            };
            modelo.addRow(fila);
        }
    }


    // Método para limpiar los campos del formulario
    public void limpiar(javax.swing.JTextField txtCodigo, javax.swing.JTextField txtNombre,
                                  javax.swing.JTextField txtApellido, javax.swing.JComboBox<String> cboFacultad,
                                  javax.swing.JComboBox<String> cboProvincia, com.toedter.calendar.JCalendar calendario) {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        cboFacultad.setSelectedIndex(-1);
        cboProvincia.setSelectedIndex(-1);
        calendario.setDate(new Date());

    }

   public void buscarPorNombre(String nombre) {
    // Limpiar la tabla actual
    modelo.setRowCount(0);

    // Filtrar la lista por nombre y agregar los resultados a la tabla
    for (Estudiante e : listaEstudiante) {
        if (e.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
            modelo.addRow(new Object[]{e.getCodigo(), e.getNombre(), e.getApellidos(), e.getFacultad(), e.getEdad(), e.getProvincia()});
        }
    }
}
   
    public ArrayList<Estudiante> getListaEstudiantes() {
        return listaEstudiante;
    }
}