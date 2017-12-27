/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author cifu
 */
public class Director {
    private int id_director;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private int telefono;
    private String direccion;
    private double sueldo;
    private int id_equipo;

    public Director(int id_director, String nombre, String apellido1, String apellido2, int telefono, String direccion, double sueldo, int id_equipo) {
        this.id_director = id_director;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.telefono = telefono;
        this.direccion = direccion;
        this.sueldo = sueldo;
        this.id_equipo = id_equipo;
    }

    public Director(String nombre, String apellido1, String apellido2, int telefono, String direccion, double sueldo) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.telefono = telefono;
        this.direccion = direccion;
        this.sueldo = sueldo;
    }

    public int getId_director() {
        return id_director;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getSueldo() {
        return sueldo;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    @Override
    public String toString() {
        return "Director{" + "id_director=" + id_director + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", telefono=" + telefono + ", direccion=" + direccion + ", sueldo=" + sueldo + ", id_equipo=" + id_equipo + '}';
    }
    
}
