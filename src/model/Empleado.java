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
public class Empleado {
    private int id_empleado;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private int telefono;
    private String direccion;
    private String fecha_nacimiento;
    private double sueldo;
    private int id_equipo;

    public int getId_empleado() {
        return id_empleado;
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

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
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
        return "Empleado{" + "id_empleado=" + id_empleado + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", telefono=" + telefono + ", direccion=" + direccion + ", fecha_nacimiento=" + fecha_nacimiento + ", sueldo=" + sueldo + ", id_equipo=" + id_equipo + '}';
    }
    
}
