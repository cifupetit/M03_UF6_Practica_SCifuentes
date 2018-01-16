package main;

import dao.DirectorDAO;
import dao.DirectorDAOFactory;
import dao.EmpleadoDAO;
import dao.EmpleadoDAOFactory;
import dao.EquipoDAO;
import dao.EquipoDAOFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Director;
import model.Empleado;
import model.Equipo;
import utilities.ConnectDB;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cifu
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int opcion;
        boolean salir = false;
        boolean timeToQuit;
        
        while (!salir) {
            System.out.println("====MENÚ====\n"
                + "1. Empleados\n"
                + "2. Equipos\n"
                + "3. Directores\n"
                + "4. Salir");
        
            try {
                opcion = Integer.parseInt(in.readLine());
            
                switch (opcion) {
                    case 1:
                        timeToQuit = false;
                        EmpleadoDAOFactory factoryEm = new EmpleadoDAOFactory();
                        EmpleadoDAO daoEm = factoryEm.createEmpleadoDAO();
                    
                        try (Connection con = ConnectDB.getInstance();) {
                            do {
                                timeToQuit = executeMenuEmpleado(con, in, daoEm);
                            } while (!timeToQuit);
                            
                        } catch (IOException ex) {
                            System.out.println("Error " + ex.getClass().getName());
                            System.out.println("Mensaje: " + ex.getMessage());
                        } catch (Exception ex) {
                            System.out.println("Error " + ex.getClass().getName());
                            System.out.println("Mensaje: " + ex.getMessage());
                        } finally {
                            try {
                                ConnectDB.closeConnection();
                            } catch (SQLException ex) {
                                System.out.println("Error al cerrar la conexión con las base de datos");
                            }
                        }
                    
                    break;
                    
                    case 2:
                        timeToQuit = false;
                        EquipoDAOFactory factoryEq = new EquipoDAOFactory();
                        EquipoDAO daoEq = factoryEq.createEquipoDAO();
                    
                        try (Connection con = ConnectDB.getInstance();) {
                            do {
                                timeToQuit = executeMenuEquipo(con, in, daoEq);
                            } while (!timeToQuit);
                            
                        } catch (IOException ex) {
                            System.out.println("Error " + ex.getClass().getName());
                            System.out.println("Mensaje: " + ex.getMessage());
                        } catch (Exception ex) {
                            System.out.println("Error " + ex.getClass().getName());
                            System.out.println("Mensaje: " + ex.getMessage());
                        } finally {
                            try {
                                ConnectDB.closeConnection();
                            } catch (SQLException ex) {
                                System.out.println("Error al cerrar la conexión con las base de datos");
                            }
                        }
                        break;
                        
                    case 3:
                        timeToQuit = false;
                        DirectorDAOFactory factoryD = new DirectorDAOFactory();
                        DirectorDAO daoD = factoryD.createDirectorDAO();
                    
                        try (Connection con = ConnectDB.getInstance();) {
                            do {
                                timeToQuit = executeMenuDirector(con, in, daoD);
                            } while (!timeToQuit);
                            
                        } catch (IOException ex) {
                            System.out.println("Error " + ex.getClass().getName());
                            System.out.println("Mensaje: " + ex.getMessage());
                        } catch (Exception ex) {
                            System.out.println("Error " + ex.getClass().getName());
                            System.out.println("Mensaje: " + ex.getMessage());
                        } finally {
                            try {
                                ConnectDB.closeConnection();
                            } catch (SQLException ex) {
                                System.out.println("Error al cerrar la conexión con las base de datos");
                            }
                        }
                        break;
                        
                    case 4:
                        salir = true;
                        break;
                        
                    default:
                        System.out.println("Introduzca una opción de 1 a 4");
                        break;
                }
                
            } catch (NumberFormatException ex) {
                System.out.println("Debe introducir un número...");
            } catch (IOException ex) {
                System.out.println(ex + ex.getMessage());
            } finally {
                if (salir) {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        System.out.println("Error en el cierre del Buffer");
                    }
                }
            }
        }
    }
    
    public static Empleado datosEmpleado (BufferedReader in, Connection con) {
        String nombre, apellido1, apellido2, direccion, fechaNacimiento;
        int telefono, idEquipo;
        double sueldo = 0;
        Empleado empleado = null;
        
        System.out.println("Introduzca los datos del nuevo empleado:\n"
                + "Los campos con * son obligatorios");
        
        try {
            do {
                System.out.println("*Nombre del nuevo empleado:");
                nombre = in.readLine();
            } while (nombre.isEmpty());
            
            do {
                System.out.println("*1er Apellido del nuevo empleado:");
                apellido1 = in.readLine();
            } while (apellido1.isEmpty());
            
            do {
                System.out.println("*2o Apellido del nuevo empleado:");
                apellido2 = in.readLine();
            } while (apellido2.isEmpty());
            
            do {
                System.out.println("Teléfono del nuevo empleado:\n"
                        + "¡Número de 9 dígitos!");
                telefono = Integer.parseInt(in.readLine());
            } while (intLength(telefono) != 9 || intLength(telefono) == 0);
            
            System.out.println("Dirección del nuevo empleado:");
            direccion = in.readLine();
            
            do {
                System.out.println("Fecha de nacimiento del nuevo empelado:\n"
                        + "¡En el formato [AAAA-MM-DD]!");
                fechaNacimiento = in.readLine();
            } while (!fechaValida(fechaNacimiento));
            
            do {
                System.out.println("Sueldo del nuevo empleado:\n"
                        + "¡Mayor que 0!");
                sueldo = Double.parseDouble(in.readLine());
            } while (sueldo <= 0);
            
            do {
                System.out.println("ID de equipo del nuevo empleado:\n"
                        + "¡Introduzca un ID de equipo existente!");
                idEquipo = Integer.parseInt(in.readLine());
            } while (!existeEq(idEquipo, con));
            
            empleado = new Empleado(nombre, apellido1, apellido2, telefono, direccion, fechaNacimiento, sueldo, idEquipo);
            
        } catch (NumberFormatException ex) {
            System.out.println("Introduzca un número...");
        } catch (IOException ex) {
            System.out.println("I/O Error");
        }
         return empleado;
    }
    
    public static boolean existeEq (int idEq, Connection con) {
        ResultSet rs = null;
        boolean existe = false;
        
        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM equipo WHERE id_equipo = " + idEq;
            rs = stmt.executeQuery(query);
            existe = rs.first();
        
        } catch (SQLException ex) {
            System.out.println("Error al comprobar equipo");
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar ResultSet en existeEq");
            }
        }
        return existe;
    }
    
    public static boolean existeDir (int idDir, Connection con) {
        ResultSet rs = null;
        boolean existe = false;
        
        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM director WHERE id_director = " + idDir;
            rs = stmt.executeQuery(query);
            existe = rs.first();
        
        } catch (SQLException ex) {
            System.out.println("Error al comprobar director");
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar ResultSet en existeDir");
            }
        }
        return existe;
    }
    
    public static boolean existeDirSinEquipo (int idDir, Connection con) {
        ResultSet rs = null;
        boolean existe = false;
        
        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM director WHERE id_director = " + idDir + " AND id_equipo IS NULL";
            rs = stmt.executeQuery(query);
            existe = rs.first();
        
        } catch (SQLException ex) {
            System.out.println("Error al comprobar director");
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar ResultSet en existeDir");
            }
        }
        return existe;
    }
    
    public static boolean fechaValida (String fecha) {
        //[AAAA-MM-DD]
        char[] auxFecha = fecha.toCharArray();
        if (fecha.length() == 10) {
            if (auxFecha[4] == '-' && auxFecha[7] == '-') {
                String[] datos = fecha.split("-");
                if (intLength(Integer.parseInt(datos[0])) == 4 && (Integer.parseInt(datos[1]) >= 00 && Integer.parseInt(datos[1]) <= 12) && (Integer.parseInt(datos[2]) >= 00 && Integer.parseInt(datos[2]) <= 12)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public static int intLength (int num) {
        int length = 0;
        
        while (num !=0) {
            num = num/10;
            length++;
        }
        return length;
    }
    
    public static boolean executeMenuEmpleado (Connection con, BufferedReader in, EmpleadoDAO daoEm) throws IOException, Exception{
        boolean salir = true;
        int opcion;
        Empleado empleado;
        
        while (salir) {
            System.out.println("EMPLEADOS\n"
                + "0. Añadir registros aleatorios\n"
                + "1. Añadir empleado\n"
                + "2. Eliminar empleado\n"
                + "3. Listar empleados\n"
                + "4. Buscar empleado\n"
                + "5. Listar empleados de menor a mayor sueldo\n"
                + "6. Listar empleados de cierto año\n"
                + "7. Empleado con sueldo más alto\n"
                + "8. Salir");
        
            try {
                opcion = Integer.parseInt(in.readLine());
            
                switch (opcion) {
                    case 0:
                        Empleado em = new Empleado("Marta", "Derecha", "Garcia", 654123526, "c/Gran Via n.5", "1864-11-12", 2000, 1);
                        Empleado em2 = new Empleado("Pepe", "Derecha", "Garcia", 677125516, "c/Fermí i Verges n.42", "1964-01-02", 2500, 2);
                        Empleado em3 = new Empleado("Antonio", "Izquierda", "Derecha", 654123526, "c/Gran Via n.5", "1864-11-12", 6000, 2);
                        daoEm.add(em, con);
                        daoEm.add(em2, con);
                        daoEm.add(em3, con);
                        break;
                            
                    case 1:
                        empleado = datosEmpleado(in, con);
                        daoEm.add(empleado, con);
                        System.out.println("Empleado " + empleado.getNombre() + " " + empleado.getApellido1() + " " + empleado.getApellido2() + " añadido con éxito");
                        
                        break;
                    
                    case 2:
                        System.out.println("Introduzca el ID del empleado a eliminar:");
                        int id = Integer.parseInt(in.readLine());
                        
                        daoEm.remove(id, con);
                        System.out.println("Empleado con id " + id + " eliminado con éxito");
                    
                    case 3:
                        ArrayList<Empleado> listaEmpleados = daoEm.list(con);
                        for (Empleado e : listaEmpleados) {
                            System.out.println(e.toString());
                        }
                        break;
                    
                    case 4:
                        System.out.println("Introduzca el ID de empleado a buscar:");
                        id = Integer.parseInt(in.readLine());
                        
                        empleado = daoEm.search(id, con);
                        if (empleado != null) {
                            System.out.println(empleado.toString());
                        } else {
                            System.out.println("Empleado con ID " + id + " no existe");
                        }
                        break;
                        
                    case 5:
                        ArrayList<Empleado> empleadosPorSueldoAsc = daoEm.listarPorSueldoAsc(con);
                        for (Empleado e : empleadosPorSueldoAsc) {
                            System.out.println(e.toString());
                        }
                        break;
                        
                    case 6:
                        int año;
                        do {
                            System.out.println("Introduzca el año del que quiere listar los empleados:");
                            año = Integer.parseInt(in.readLine());
                        } while (intLength(año) != 4);
                        
                        ArrayList<Empleado> empleadosCiertoAño = daoEm.listarPorAño(año, con);
                        for (Empleado e : empleadosCiertoAño) {
                            System.out.println(e.toString());
                        }
                        break;
                        
                    case 7:
                        empleado = daoEm.empMayorSueldo(con);
                        if (empleado != null) {
                            System.out.println(empleado.toString());
                        } else {
                            System.out.println("No existe ningún empleado");
                        }
                        break;
                    
                    case 8:
                        salir = false;
                        return true;
                    
                    default:
                        System.out.println("Indique una de las opciones posibles [1-8]");
                        break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Debe introduzir un número...");
            }
        }
        return false;
    }
    
    public static boolean executeMenuEquipo (Connection con, BufferedReader in, EquipoDAO daoEq) throws IOException, Exception{
        boolean salir = true;
        int opcion, id;
        Equipo equipo;
        String nombreEq;
        
        while (salir) {
            System.out.println("EQUIPOS\n"
                + "0. Añadir registros aleatorios\n"
                + "1. Añadir equipo\n"
                + "2. Actualizar proyecto de equipo\n"
                + "3. Actualizar día de reunión de equipo\n"
                + "4. Buscar equipo\n"
                + "5. Eliminar equipo\n"
                + "6. Listar equipos\n"
                + "7. Empleados asociados a un equipo\n"
                + "8. Salir");
        
            try {
                opcion = Integer.parseInt(in.readLine());
            
                switch (opcion) {
                    case 0:
                        Equipo eq = new Equipo("Investigación", 1);
                        Equipo eq2 = new Equipo("Desarrollo", 2);
                        daoEq.add(eq, con);
                        daoEq.add(eq2, con);
                        break;
                        
                    case 1:
                        System.out.println("Introduzca los datos del nuevo equipo: nombre");
                        nombreEq = in.readLine();
                        int idDirector;
                        
                        do {
                            System.out.println("¡Introduzca un ID de director existente sin equipo asignado!\n"
                            + "ID de director:");
                            idDirector = Integer.parseInt(in.readLine());
                        } while (!existeDirSinEquipo(idDirector, con));
                        
                        equipo = new Equipo(nombreEq, idDirector);
                        
                        daoEq.add(equipo, con);
                        System.out.println("Equipo " + equipo.getNombre() + " añadido con éxito");
                        
                        break;
                    
                    case 2:
                        System.out.println("Introduzca el ID de equipo que quiere actualizar:");
                        id = Integer.parseInt(in.readLine());
                        equipo = daoEq.search(id, con);
                        
                        if (equipo == null) {
                            System.out.println("Equipo con ID " + id + " no existe");
                            break;
                        }
                        
                        System.out.println("Introduzca el nombre del proyecto a asginar a este equipo:");
                        String proyecto = in.readLine();
                        
                        daoEq.updateProyecto(proyecto, equipo, con);
                        System.out.println("Proyecto del equipo con ID " + id + " actualizado con éxito");
                        break;
                        
                    case 3:
                        System.out.println("Introduzca el ID de equipo que quiere actualizar:");
                        id = Integer.parseInt(in.readLine());
                        equipo = daoEq.search(id, con);
                        
                        if (equipo == null) {
                            System.out.println("Equipo con ID " + id + " no existe");
                            break;
                        }
                        
                        int dia;
                        do {
                            System.out.println("Introduzca el dia de reunion a asginar a este equipo:\n"
                                    + "¡Número de 1 a 7, según los días de la semana!");
                            dia = Integer.parseInt(in.readLine());
                        } while (dia <= 0 || dia >=8);
                        
                        daoEq.updateDiaReunion(dia, equipo, con);
                        System.out.println("Dia de reunion del equipo con ID " + id + " actualizado con éxito");
                        break;
                        
                    case 4:
                        System.out.println("Introduzca el ID de equipo a buscar:");
                        id = Integer.parseInt(in.readLine());
                        
                        equipo = daoEq.search(id, con);
                        if (equipo != null) {
                            System.out.println(equipo.toString());
                        } else {
                            System.out.println("Equipo con ID " + id + " no existe");
                        }
                        
                        break;
                        
                    case 5:
                        System.out.println("Introduzca el ID de equipo a eliminar:");
                        id = Integer.parseInt(in.readLine());
                        
                        daoEq.remove(id, con);
                        System.out.println("Equipo con id " + id + " eliminado con éxito");
                    
                    case 6:
                        ArrayList<Equipo> listaEquipos = daoEq.list(con);
                        for (Equipo e : listaEquipos) {
                            System.out.println(e.toString());
                        }
                        break;
                    
                    case 7:
                        System.out.println("Indique el ID de equipo del que desea listar sus empleados:");
                        id = Integer.parseInt(in.readLine());
                        
                        ArrayList<Empleado> listaEmpleadosAsociados = daoEq.empleadosAsociados(id, con);
                        for (Empleado e : listaEmpleadosAsociados) {
                            System.out.println(e.toString());
                        }
                        break;
                    
                    case 8:
                        salir = false;
                        return true;
                    
                    default:
                        System.out.println("Indique una de las opciones posibles [1-8]");
                        break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Debe introduzir un número...");
            }
        }
        return false;
    }
    
    public static boolean executeMenuDirector (Connection con, BufferedReader in, DirectorDAO daoD) throws IOException, Exception{
        boolean salir = true;
        int opcion, id;
        Director director;
        
        while (salir) {
            System.out.println("DIRECTORES\n"
                + "0. Añadir registros aleatorios\n"
                + "1. Añadir director\n"
                + "2. Actualizar equipo de director\n"
                + "3. Buscar director\n"
                + "4. Eliminar director\n"
                + "5. Listar directores\n"
                + "6. Listar directores sin equipo asociado o actualizado\n"
                + "7. Mostrar director con el sueldo más alto\n"
                + "8. Salir");
        
            try {
                opcion = Integer.parseInt(in.readLine());
            
                switch (opcion) {
                    case 0:
                        Director d = new Director("Marta", "Derecha", "Garcia", 654123526, "c/Gran Via n.5", 2000);
                        Director d2 = new Director("Pepe", "Derecha", "Garcia", 677125516, "c/Fermí i Verges n.42", 2500);
                        Director d3 = new Director("Antonio", "Izquierda", "Derecha", 654123526, "c/Gran Via n.5", 6000);
                        daoD.add(d, con);
                        daoD.add(d2, con);
                        daoD.add(d3, con);
                        break;
                            
                    case 1:
                        director = datosDirector(in, con);
                        daoD.add(director, con);
                        System.out.println("Director " + director.getNombre() + " " + director.getApellido1() + " " + director.getApellido2() + " añadido con éxito");
                        
                        break;
                        
                    case 2:                        
                        do {
                            System.out.println("¡Introduzca un ID de director existente!\n"
                            + "ID de director a actualizar el equipo:");
                            id = Integer.parseInt(in.readLine());
                        } while (!existeDir(id, con));
                        
                        daoD.updateEquipo(id, con);
                        System.out.println("Equipo de director con ID " + id + " actualizado con éxito");
                        
                        break;
                        
                    case 3:
                        System.out.println("Introduzca el ID de director a buscar:");
                        id = Integer.parseInt(in.readLine());
                        
                        director = daoD.search(id, con);
                        if (director != null) {
                            System.out.println(director.toString());
                        } else {
                            System.out.println("Director con ID " + id + " no existe");
                        }
                        break;
                    
                    case 4:
                        System.out.println("Introduzca el ID del director a eliminar:");
                        id = Integer.parseInt(in.readLine());
                        
                        daoD.remove(id, con);
                        System.out.println("Director con ID " + id + " eliminado con éxito");
                    
                    case 5:
                        ArrayList<Director> listaDirectores = daoD.list(con);
                        for (Director di : listaDirectores) {
                            System.out.println(di.toString());
                        }
                        break;
                    
                    case 6:
                        ArrayList<Director> directoresSinEquipo = daoD.directoresSinEquipo(con);
                        for (Director di : directoresSinEquipo) {
                            System.out.println(di.toString());
                        }
                        break;
                        
                    case 7:
                        director = daoD.dirMayorSueldo(con);
                        if (director != null) {
                            System.out.println(director.toString());
                        } else {
                            System.out.println("Director con ID " + director.getId_director() + " no existe");
                        }
                        break;
                    
                    case 8:
                        salir = false;
                        return true;
                    
                    default:
                        System.out.println("Indique una de las opciones posibles [1-8]");
                        break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Debe introduzir un número...");
            }
        }
        return false;
    }

    private static Director datosDirector(BufferedReader in, Connection con) {
        String nombre, apellido1, apellido2, direccion;
        int telefono;
        double sueldo = 0;
        Director director = null;
        
        System.out.println("Introduzca los datos del nuevo director:\n"
                + "Los campos con * son obligatorios");
        
        try {
            do {
                System.out.println("*Nombre del nuevo director:");
                nombre = in.readLine();
            } while (nombre.isEmpty());
            
            do {
                System.out.println("*1er Apellido del nuevo director:");
                apellido1 = in.readLine();
            } while (apellido1.isEmpty());
            
            do {
                System.out.println("*2o Apellido del nuevo director:");
                apellido2 = in.readLine();
            } while (apellido2.isEmpty());
            
            do {
                System.out.println("Teléfono del nuevo director:\n"
                        + "¡Número de 9 dígitos!");
                telefono = Integer.parseInt(in.readLine());
            } while (intLength(telefono) != 9 || intLength(telefono) == 0);
            
            System.out.println("Dirección del nuevo director:");
            direccion = in.readLine();
            
            do {
                System.out.println("Sueldo del nuevo director:\n"
                        + "¡Mayor que 0!");
                sueldo = Double.parseDouble(in.readLine());
            } while (sueldo <= 0);
            
            director = new Director(nombre, apellido1, apellido2, telefono, direccion, sueldo);
            
        } catch (NumberFormatException ex) {
            System.out.println("Introduzca un número...");
        } catch (IOException ex) {
            System.out.println("I/O Error");
        }
         return director;
    }
    
}
