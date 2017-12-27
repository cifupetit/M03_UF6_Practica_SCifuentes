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
public class Equipo {
    private int id_equipo;
    private String nombre;
    private String fecha_creacion;
    private String proyecto;
    private int dia_reunion;
    private int id_director;

    public Equipo(int id_equipo, String nombre, String fecha_creacion, String proyecto, int dia_reunion, int id_director) {
        this.id_equipo = id_equipo;
        this.nombre = nombre;
        this.fecha_creacion = fecha_creacion;
        this.proyecto = proyecto;
        this.dia_reunion = dia_reunion;
        this.id_director = id_director;
    }

    public Equipo(String nombre, int id_director) {
        this.nombre = nombre;
        this.id_director = id_director;
    }

    public Equipo(String nombre, String proyecto, int dia_reunion, int id_director) {
        this.nombre = nombre;
        this.proyecto = proyecto;
        this.dia_reunion = dia_reunion;
        this.id_director = id_director;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public String getProyecto() {
        return proyecto;
    }

    public int getDia_reunion() {
        return dia_reunion;
    }

    public int getId_director() {
        return id_director;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public void setDia_reunion(int dia_reunion) {
        this.dia_reunion = dia_reunion;
    }

    public void setId_director(int id_director) {
        this.id_director = id_director;
    }

    @Override
    public String toString() {
        return "Equipo{" + "id_equipo=" + id_equipo + ", nombre=" + nombre + ", fecha_creacion=" + fecha_creacion + ", proyecto=" + proyecto + ", dia_reunion=" + dia_reunion + ", id_director=" + id_director + '}';
    }
    
}
