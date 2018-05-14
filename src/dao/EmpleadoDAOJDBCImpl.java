/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Empleado;

/**
 *
 * @author cifu
 */
public class EmpleadoDAOJDBCImpl implements EmpleadoDAO {

    @Override
    public void add(Empleado e, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("INSERT INTO empleado(`nombre`,`apellido1`,`apellido2`,`telefono`,`direccion`,`fecha_nacimiento`,`sueldo`,`id_equipo`) VALUES (?,?,?,?,?,?,?,?)")) {
            stmt.setString(1, e.getNombre());
            stmt.setString(2, e.getApellido1());
            stmt.setString(3, e.getApellido2());
            stmt.setInt(4, e.getTelefono());
            stmt.setString(5, e.getDireccion());
            stmt.setString(6, e.getFecha_nacimiento());
            stmt.setDouble(7, e.getSueldo());
            stmt.setInt(8, e.getId_equipo());
            
            if (stmt.executeUpdate() != 1) {
                throw new DAOException("Error al añadir empleado");
            }
            
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al añadir empleado");
        }
    }

    @Override
    public void remove(int idEmpleado, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("DELETE FROM empleado WHERE `id_empleado`= ?")) {
            stmt.setInt(1, idEmpleado);
            
            if (stmt.executeUpdate() != 1) {
                throw new DAOException("Error al eliminar empleado");
            }
            
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al eliminar empleado");
        }
    }

    @Override
    public Empleado search(String nombreEmpleado, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM empleado WHERE `nombre`= ?")) {
            stmt.setString(1, nombreEmpleado);
            ResultSet rs = stmt.executeQuery();
                        
            if (!rs.next()) {
                return null;
            }
            Empleado e = new Empleado(rs.getInt("id_empleado"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("telefono"), rs.getString("direccion"), rs.getString("fecha_nacimiento"), rs.getDouble("sueldo"), rs.getInt("id_equipo"));
            rs.close();
            return e;
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al buscar empleado");
        }
    }

    @Override
    public ArrayList<Empleado> list(Connection con) throws DAOException {
        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM empleado";
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Empleado> empleados = new ArrayList<>();
            
            while (rs.next()) {
                empleados.add(new Empleado(rs.getInt("id_empleado"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("telefono"), rs.getString("direccion"), rs.getString("fecha_nacimiento"), rs.getDouble("sueldo"), rs.getInt("id_equipo")));
            }
            rs.close();
            return empleados;
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al mostrar todos los empleados");
        }
    }

    @Override
    public ArrayList<Empleado> listarPorSueldoAsc(Connection con) throws DAOException {
        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM empleado ORDER BY `sueldo`";
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Empleado> empleados = new ArrayList<>();
            
            while (rs.next()) {
                empleados.add(new Empleado(rs.getInt("id_empleado"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("telefono"), rs.getString("direccion"), rs.getString("fecha_nacimiento"), rs.getDouble("sueldo"), rs.getInt("id_equipo")));
            }
            rs.close();
            return empleados;
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al mostrar todos los empleados ordenados por sueldo");
        }
    }

    @Override
    public ArrayList<Empleado> listarPorAño(int año, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM empleado WHERE YEAR(`fecha_nacimiento`)=?")) {
            stmt.setInt(1, año);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Empleado> empleados = new ArrayList<>();
            
            while (rs.next()) {
                empleados.add(new Empleado(rs.getInt("id_empleado"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("telefono"), rs.getString("direccion"), rs.getString("fecha_nacimiento"), rs.getDouble("sueldo"), rs.getInt("id_equipo")));
            }
            rs.close();
            return empleados;
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al mostrar los empleados de cierto año");
        }
    }

    @Override
    public Empleado empMayorSueldo(Connection con) throws DAOException {
        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM empleado WHERE `sueldo`=(SELECT MAX(sueldo) FROM empleado)";
            ResultSet rs = stmt.executeQuery(query);
            
            if (!rs.next()) {
                return null;
            }
            Empleado e = new Empleado(rs.getInt("id_empleado"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("telefono"), rs.getString("direccion"), rs.getString("fecha_nacimiento"), rs.getDouble("sueldo"), rs.getInt("id_equipo"));
            rs.close();
            return e;
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al mostrar el empleado con mayor sueldo");
        }
    }
}
