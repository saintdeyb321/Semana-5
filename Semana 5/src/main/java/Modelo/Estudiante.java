    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ACER
 */
public class Estudiante {
    private String codigo;
    private String nombre;
    private String apellidos; 
    private String facultad;
    private int edad;
    private String provincia;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Estudiante() {
    }

    public Estudiante(String codigo, String nombre, String apellidos, String facultad, int edad, String provincia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidos = apellidos; // Asegúrate de usar el atributo correcto
        this.facultad = facultad;
        this.edad = edad;
        this.provincia = provincia;
    }
}
