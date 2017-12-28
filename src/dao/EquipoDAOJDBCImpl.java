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
import model.Equipo;

/**
 *
 * @author cifu
 */
public class EquipoDAOJDBCImpl implements EquipoDAO {

    @Override
    public void add(Equipo e, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("INSERT INTO equipo(`nombre`,`fecha_creacion`,`id_director`) VALUES (?,NOW(),?)")) {
            stmt.setString(1, e.getNombre());
            stmt.setInt(2, e.getId_director());
            
            if (stmt.executeUpdate() != 1) {
                throw new DAOException("Error al añadir equipo");
            }
            
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al añadir equipo");
        }
    }

    @Override
    public void remove(int idEquipo, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("DELETE FROM equipo WHERE `id_equipo`= ?")) {
            stmt.setInt(1, idEquipo);
            
            if (stmt.executeUpdate() != 1) {
                throw new DAOException("Error al eliminar equipo");
            }
            
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al eliminar equipo");
        }
    }

    @Override
    public Equipo search(int idEquipo, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM equipo WHERE `id_equipo`= ?")) {
            stmt.setInt(1, idEquipo);
            ResultSet rs = stmt.executeQuery();
                        
            if (!rs.next()) {
                return null;
            }
            Equipo d = new Equipo(rs.getInt("id_equipo"), rs.getString("nombre"), rs.getString("fecha_creacion"), rs.getString("proyecto"), rs.getInt("dia_reunion"), rs.getInt("id_director"));
            rs.close();
            return d;
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al buscar equipo");
        }
    }

    @Override
    public ArrayList<Equipo> list(Connection con) throws DAOException {
        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM equipo";
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Equipo> equipos = new ArrayList<>();
            
            while (rs.next()) {
                equipos.add(new Equipo(rs.getInt("id_equipo"), rs.getString("nombre"), rs.getString("fecha_creacion"), rs.getString("proyecto"), rs.getInt("dia_reunion"), rs.getInt("id_director")));
            }
            rs.close();
            return equipos;
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al mostrar todos los equipos");
        }
    }

    @Override
    public void updateProyecto(String proyecto, Equipo e, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("UPDATE equipo SET `proyecto`= ? WHERE `id_equipo`= ?")) {
            stmt.setString(1, proyecto);
            stmt.setInt(2, e.getId_equipo());
            
            if (stmt.executeUpdate() != 1) {
                throw new DAOException("Error al actualizar el proyecto de equipo");
            }
            
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al actualizar el proyecto de equipo");
        }
    }

    @Override
    public void updateDiaReunion(int diaReunion, Equipo e, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("UPDATE equipo SET `dia_reunion`= ? WHERE `id_equipo`= ?")) {
            stmt.setInt(1, diaReunion);
            stmt.setInt(2, e.getId_equipo());
            
            if (stmt.executeUpdate() != 1) {
                throw new DAOException("Error al actualizar el dia de reunion de equipo");
            }
            
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al actualizar el dia de reunion de equipo");
        }
    }

    @Override
    public ArrayList<Empleado> empleadosAsociados(int idEquipo, Connection con) throws DAOException {
        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM equipo AS eq, empleado AS em WHERE `eq.id_equipo`=" + idEquipo + " AND `em.id_equipo`=`eq.id_equipo`";
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Empleado> empleadosAsociados = new ArrayList<>();
            
            while (rs.next()) {
                empleadosAsociados.add(new Empleado(rs.getInt("id_empleado"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("telefono"), rs.getString("direccion"), rs.getString("fecha_nacimiento"), rs.getDouble("sueldo"), rs.getInt("id_equipo")));
            }
            rs.close();
            return empleadosAsociados;
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al mostrar los empleados asociados al equipo");
        }
    }

}
