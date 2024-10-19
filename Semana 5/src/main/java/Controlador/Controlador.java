/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Estudiante;
import Modelo.EstudianteArray;
import Modelo.EstudianteDAO;
import Vista.IRegistro;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.util.List; // Cambiar a java.util.List

/**
 *
 * @author USER 17
 */
public class Controlador {

    private EstudianteDAO dao; // Declarar el DAO
    private EstudianteArray gestor;
    private List<Estudiante> listaEstudiantes; // Declarar la lista de estudiantes
    private IRegistro vista;  // Tu clase vista, adaptada
    private DefaultTableModel modelo = new DefaultTableModel();

    public Controlador(IRegistro vista, EstudianteArray gestor) {
        this.dao = new EstudianteDAO(); // Inicializar el DAO
        this.vista = vista;
        this.gestor = gestor;
        
        // Inicializar la lista de estudiantes
        this.listaEstudiantes = new ArrayList<>();
        cargarEstudiantes(); // Cargar los estudiantes desde la base de datos

        // Añadir DocumentListener al campo de búsqueda
        this.vista.getTxtBuscador().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarPorNombre();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarPorNombre();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buscarPorNombre();
            }
        });

// Agregar MouseListener a la tabla
        agregarMouseListenerTabla();
    }

    // Método para cargar estudiantes desde la base de datos
    private void cargarEstudiantes() {
    listaEstudiantes = dao.listar(); // Llenar la lista con estudiantes desde la base de datos

    }

    public void guardarEstudiante() {
        // Validar campos
        if (vista.getTxtCodigo().getText().isEmpty() || vista.getTxtNombre().getText().isEmpty() || 
            vista.getTxtApellido().getText().isEmpty() || vista.getCboFacultad().getSelectedItem() == null || 
            vista.getCboProvincia().getSelectedItem() == null || vista.getCalendario().getDate() == null) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
            return;
        }

        Estudiante estudiante = new Estudiante();
        estudiante.setCodigo(vista.getTxtCodigo().getText());
        estudiante.setNombre(vista.getTxtNombre().getText());
        estudiante.setApellidos(vista.getTxtApellido().getText());
        estudiante.setFacultad(vista.getCboFacultad().getSelectedItem().toString());
        estudiante.setProvincia(vista.getCboProvincia().getSelectedItem().toString());

        // Obtener fecha de nacimiento y calcular la edad
        Date fechaNacimiento = vista.getCalendario().getDate();
        int edad = Modelo.EstudianteArray.calcularEdad(fechaNacimiento);  // Asegúrate que este método esté disponible
        estudiante.setEdad(edad);

        // Guardar en la base de datos
        try {
            dao.agregarEstudiante(estudiante, fechaNacimiento);  // Llamada al método del DAO para guardar el estudiante
            JOptionPane.showMessageDialog(null, "Los datos se guardaron con éxito en la base de datos.");
            cargarEstudiantes(); // Recargar la lista después de guardar
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el estudiante en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
        }

        // Limpiar los campos
        gestor.limpiar(vista.getTxtCodigo(), vista.getTxtNombre(), vista.getTxtApellido(), vista.getCboFacultad(), vista.getCboProvincia(), vista.getCalendario());
    }

    public void eliminarEstudiante() {
    // Verificar si hay una fila seleccionada
    int filaSeleccionada = vista.getTblEstudiante().getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(vista, "Por favor, seleccione un estudiante de la tabla para eliminar.");
        return;
    }

    // Obtener el código del estudiante de la fila seleccionada
    String codigo = vista.getTblEstudiante().getValueAt(filaSeleccionada, 0).toString();

    // Confirmación antes de eliminar
    int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de que deseas eliminar este estudiante?", "Confirmación", JOptionPane.YES_NO_OPTION);
    if (confirmacion == JOptionPane.YES_OPTION) {
        try {
            // Llamar al método del DAO para eliminar el estudiante por código
            dao.eliminarEstudiante(codigo);
            JOptionPane.showMessageDialog(vista, "Estudiante eliminado con éxito.");
            
            // Actualizar la tabla después de eliminar
            listar(vista.getTblEstudiante());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar el estudiante: " + e.getMessage());
        }
    }
}

    public void buscarPorNombre() {
        String nombreBuscado = vista.getTxtBuscador().getText();
        gestor.buscarPorNombre(nombreBuscado); // Actualiza la tabla según el texto buscado
    }

    public void actionPerformed(ActionEvent e) throws SQLException {
        if (e.getSource() == vista.getBtnListar()) {  // Asegúrate de utilizar el getter para acceder al botón
            listar(vista.getTblEstudiante());  // Llamamos al método listar cuando se presione el botón
        }
        if (e.getSource() == vista.getBtnEditar()) { // Asegúrate de que este sea el botón de editar
        editarEstudiante();
    }
    }

    public void listar(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        List<Estudiante> lista = dao.listar();  // Método del DAO para listar estudiantes
        modelo.setRowCount(0);  // Limpiar la tabla antes de añadir nuevos datos
        for (Estudiante estudiante : lista) {
            Object[] object = new Object[6];
            object[0] = estudiante.getCodigo();
            object[1] = estudiante.getNombre();
            object[2] = estudiante.getApellidos();
            object[3] = estudiante.getFacultad();
            object[4] = estudiante.getProvincia();
            object[5] = estudiante.getEdad();  // Si tienes el campo edad
            modelo.addRow(object);  // Añadimos la fila con los datos del estudiante
        }
        listaEstudiantes = lista; // Actualizar listaEstudiantes con los datos de la base de datos
    }

    // Método para agregar MouseListener a la tabla
    private void agregarMouseListenerTabla() {
        vista.getTblEstudiante().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = vista.getTblEstudiante().getSelectedRow();
                if (filaSeleccionada != -1) {
                    Estudiante estudianteSeleccionado = listaEstudiantes.get(filaSeleccionada); // Obtener el estudiante seleccionado
                    // Llenar los campos de texto con los datos del estudiante seleccionado
                    vista.getTxtCodigo().setText(estudianteSeleccionado.getCodigo());
                    vista.getTxtNombre().setText(estudianteSeleccionado.getNombre());
                    vista.getTxtApellido().setText(estudianteSeleccionado.getApellidos());
                    vista.getCboFacultad().setSelectedItem(estudianteSeleccionado.getFacultad());
                    vista.getCboProvincia().setSelectedItem(estudianteSeleccionado.getProvincia());
                }
            }
        });
    }
    
    public void editarEstudiante() throws SQLException {
    // Obtener el código del estudiante desde el campo de texto
    String codigo = vista.getTxtCodigo().getText();
    
    // Validar que el código no esté vacío
    if (codigo.isEmpty()) {
        JOptionPane.showMessageDialog(vista, "Por favor, ingrese el código del estudiante a editar.");
        return;
    }

    // Buscar el estudiante usando el DAO
    Estudiante estudiante = dao.buscarPorCodigo(codigo);
    
    // Validar que se haya encontrado el estudiante
    if (estudiante == null) {
        JOptionPane.showMessageDialog(vista, "Estudiante no encontrado.");
        return;
    }

    // Actualizar los datos del estudiante con la información de los campos de texto
    estudiante.setNombre(vista.getTxtNombre().getText());
    estudiante.setApellidos(vista.getTxtApellido().getText());
    estudiante.setFacultad(vista.getCboFacultad().getSelectedItem().toString());
    estudiante.setProvincia(vista.getCboProvincia().getSelectedItem().toString());
    
    // Obtener fecha de nacimiento y calcular la edad
    Date fechaNacimiento = vista.getCalendario().getDate();
    int edad = Modelo.EstudianteArray.calcularEdad(fechaNacimiento);
    estudiante.setEdad(edad);

    // Llamar al método del DAO para editar el estudiante
    try {
        dao.editarEstudiante(estudiante); // Asumiendo que este método está en el DAO
        JOptionPane.showMessageDialog(vista, "Datos del estudiante editados con éxito.");
        
        // Limpiar los campos
        gestor.limpiar(vista.getTxtCodigo(), vista.getTxtNombre(), vista.getTxtApellido(), vista.getCboFacultad(), vista.getCboProvincia(), vista.getCalendario());
        
        // Actualizar la tabla si es necesario
        listar(vista.getTblEstudiante()); // Volver a listar después de editar
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(vista, "Error al editar el estudiante: " + e.getMessage());
    }
}
}