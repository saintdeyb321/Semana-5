/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ACER
 */
public class Conexion {
    private Connection con;

    public Connection getConnection() throws SQLException {
        // Cadena de conexión con usuario 'sa' y contraseña '123'
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Registro;user=sa;password=123;encrypt=false;trustServerCertificate=true;";

        try {
            // Cargar el driver de SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Establecer la conexión
            con = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }

        // Verificar que la conexión no sea nula o esté cerrada antes de devolverla
        if (con == null || con.isClosed()) {
            throw new SQLException("No se pudo establecer la conexión a la base de datos.");
        }

        return con;
    }
}