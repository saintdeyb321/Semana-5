/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.semana5;

import Controlador.Controlador;
import Modelo.EstudianteArray;
import Vista.IRegistro;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER 17
 */
public class Semana5 {

    public static void main(String[] args) {
        try {
            // Establecer el Look and Feel antes de crear el JFrame
            UIManager.setLookAndFeel(new HiFiLookAndFeel()); // Cambia el Look and Feel según prefieras
        } catch (Exception e) {
            e.printStackTrace();
        }

        IRegistro vista = new IRegistro();  // Crear la instancia de IRegistro
        DefaultTableModel modelo = new DefaultTableModel();  // Crear el modelo para la tabla
        EstudianteArray gestor = new EstudianteArray(modelo);  // Crear la instancia del gestor con el modelo

        // Pasar ambos parámetros al controlador
        Controlador c = new Controlador(vista, gestor);  
        vista.setVisible(true);  // Hacer visible la ventana
        vista.setLocationRelativeTo(null);  // Centrar la ventana en la pantalla
    }
}