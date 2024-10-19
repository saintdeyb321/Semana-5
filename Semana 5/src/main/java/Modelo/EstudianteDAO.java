/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; // Correcto
import java.util.Date;
import java.util.List; // Cambiar a java.util.List

/**
 *
 * @author ACER
 */
public class EstudianteDAO {
    Conexion conectar = new Conexion();  // Instancia de la clase que maneja la conexión a la base de datos
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Método para listar estudiantes desde la base de datos
    public List<Estudiante> listar() {
        List<Estudiante> datos = new ArrayList<>();
        String sql = "SELECT * FROM Persona";  // Consulta SQL para obtener todos los estudiantes

        try {
            con = conectar.getConnection();  // Obtener la conexión
            ps = con.prepareStatement(sql);  // Preparar la sentencia SQL
            rs = ps.executeQuery();  // Ejecutar la consulta y obtener los resultados

            // Recorrer los resultados y crear instancias de Estudiante
            while (rs.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setCodigo(rs.getString("codigo"));  // Obtener el código del estudiante
                estudiante.setNombre(rs.getString("nombre"));  // Obtener el nombre
                estudiante.setApellidos(rs.getString("apellidos"));  // Obtener los apellidos
                estudiante.setFacultad(rs.getString("facultad"));  // Obtener la facultad
                estudiante.setProvincia(rs.getString("provincia"));  // Obtener la provincia
                estudiante.setEdad(rs.getInt("edad"));  // Obtener la edad (si está incluida en la base de datos)

                // Añadir el estudiante a la lista
                datos.add(estudiante);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Manejo de excepciones
        }
        return datos;  // Retornar la lista de estudiantes
    }

public void agregarEstudiante(Estudiante estudiante, Date fechaNacimiento) throws SQLException {
    String sql = "INSERT INTO Persona (codigo, nombre, apellidos, facultad, provincia, edad) VALUES (?, ?, ?, ?, ?, ?)";
    
    try {
        con = conectar.getConnection(); // Obtener la conexión aquí
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, estudiante.getCodigo());
        pstmt.setString(2, estudiante.getNombre());
        pstmt.setString(3, estudiante.getApellidos());
        pstmt.setString(4, estudiante.getFacultad());
        pstmt.setString(5, estudiante.getProvincia());
        pstmt.setInt(6, estudiante.getEdad());

        pstmt.executeUpdate(); // Ejecuta la consulta
    } catch (SQLException e) {
        System.out.println("Error al agregar estudiante: " + e.getMessage());
        throw e; // Vuelve a lanzar la excepción para manejarla en el controlador
        } 
    }

public void editarEstudiante(Estudiante estudiante) throws SQLException {
    String sql = "UPDATE Persona SET nombre = ?, apellidos = ?, facultad = ?, provincia = ?, edad = ? WHERE codigo = ?";
    
    try (Connection con = conectar.getConnection(); 
         PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, estudiante.getNombre());
        pstmt.setString(2, estudiante.getApellidos());
        pstmt.setString(3, estudiante.getFacultad());
        pstmt.setString(4, estudiante.getProvincia());
        pstmt.setInt(5, estudiante.getEdad());
        pstmt.setString(6, estudiante.getCodigo()); // Asegúrate de que este campo sea único
        
        pstmt.executeUpdate(); // Ejecuta la consulta
    } catch (SQLException e) {
        System.out.println("Error al editar estudiante: " + e.getMessage());
        throw e; // Vuelve a lanzar la excepción para manejarla en el controlador
    }
}

public Estudiante buscarPorCodigo(String codigo) throws SQLException {
    Estudiante estudiante = null;
    String sql = "SELECT * FROM Persona WHERE codigo = ?"; // Asegúrate de que esta consulta sea correcta para tu base de datos

    try (Connection con = conectar.getConnection(); 
         PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, codigo);
        ResultSet rs = pstmt.executeQuery(); // Ejecutar la consulta

        if (rs.next()) { // Si hay resultados
            estudiante = new Estudiante();
            estudiante.setCodigo(rs.getString("codigo"));
            estudiante.setNombre(rs.getString("nombre"));
            estudiante.setApellidos(rs.getString("apellidos"));
            estudiante.setFacultad(rs.getString("facultad"));
            estudiante.setProvincia(rs.getString("provincia"));
            estudiante.setEdad(rs.getInt("edad"));
            // Si tienes otros atributos, los puedes asignar aquí
        }
    } catch (SQLException e) {
        System.out.println("Error al buscar estudiante: " + e.getMessage());
        throw e; // Vuelve a lanzar la excepción para manejarla en el controlador
    }

    return estudiante; // Devuelve el estudiante encontrado o null si no se encontró
}

public void eliminarEstudiante(String codigo) throws SQLException {
    String sql = "DELETE FROM Persona WHERE codigo = ?"; // Asegúrate de que la tabla y columna sean correctas

    try (Connection con = conectar.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setString(1, codigo); // Asignar el código del estudiante a eliminar
        pstmt.executeUpdate(); // Ejecutar la eliminación
    } catch (SQLException e) {
        System.out.println("Error al eliminar el estudiante: " + e.getMessage());
        throw e; // Lanzar la excepción para que el controlador la maneje
    }
}

}