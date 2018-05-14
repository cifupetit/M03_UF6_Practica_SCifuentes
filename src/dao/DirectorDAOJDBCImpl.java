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
import model.Director;

/**
 *
 * @author cifu
 */
public class DirectorDAOJDBCImpl implements DirectorDAO {

    @Override
    public void add(Director d, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("INSERT INTO director(`nombre`,`apellido1`,`apellido2`,`telefono`,`direccion`,`sueldo`) VALUES (?,?,?,?,?,?)")) {
            stmt.setString(1, d.getNombre());
            stmt.setString(2, d.getApellido1());
            stmt.setString(3, d.getApellido2());
            stmt.setInt(4, d.getTelefono());
            stmt.setString(5, d.getDireccion());
            stmt.setDouble(6, d.getSueldo());
            
            if (stmt.executeUpdate() != 1) {
                throw new DAOException("Error al añadir director");
            }
            
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al añadir director");
        }
    }

    @Override
    public void updateEquipo(int idDirector, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("UPDATE director SET `id_equipo`= (SELECT `id_equipo` FROM equipo WHERE `id_director`= ?) WHERE `id_director`= ?")) {
            stmt.setInt(1, idDirector);
            stmt.setInt(2, idDirector);
            
            if (stmt.executeUpdate() != 1) {
                throw new DAOException("Error al actualizar director");
            }
            
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al actualizar director");
        }
    }

    @Override
    public void remove(int idDirector, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("DELETE FROM director WHERE `id_director`= ?")) {
            stmt.setInt(1, idDirector);
            
            if (stmt.executeUpdate() != 1) {
                throw new DAOException("Error al eliminar director");
            }
            
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al eliminar director");
        }
    }

    @Override
    public Director search(String nombreDirector, Connection con) throws DAOException {
        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM director WHERE `nombre`= ?")) {
            stmt.setString(1, nombreDirector);
            ResultSet rs = stmt.executeQuery();
                        
            if (!rs.next()) {
                return null;
            }
            Director d = new Director(rs.getInt("id_director"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("telefono"), rs.getString("direccion"), rs.getDouble("sueldo"), rs.getInt("id_equipo"));
            rs.close();
            return d;
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al buscar director");
        }
    }

    @Override
    public Director dirMayorSueldo(Connection con) throws DAOException {
        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM director WHERE `sueldo`=(SELECT MAX(sueldo) FROM director)";
            ResultSet rs = stmt.executeQuery(query);
            
            if (!rs.next()) {
                return null;
            }
            Director d = new Director(rs.getInt("id_director"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("telefono"), rs.getString("direccion"), rs.getDouble("sueldo"), rs.getInt("id_equipo"));
            rs.close();
            return d;
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al mostrar el director con mayor sueldo");
        }
    }

    @Override
    public ArrayList<Director> directoresSinEquipo(Connection con) throws DAOException {
        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM director WHERE `id_equipo` IS NULL";
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Director> directores = new ArrayList<>();
            
            while (rs.next()) {
                directores.add(new Director(rs.getInt("id_director"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("telefono"), rs.getString("direccion"), rs.getDouble("sueldo"), rs.getInt("id_equipo")));
            }
            rs.close();
            return directores;
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al mostrar todos los directores sin equipo");
        }
    }
    
    @Override
    public ArrayList<Director> list(Connection con) throws DAOException {
        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM director";
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Director> directores = new ArrayList<>();
            
            while (rs.next()) {
                directores.add(new Director(rs.getInt("id_director"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getInt("telefono"), rs.getString("direccion"), rs.getDouble("sueldo"), rs.getInt("id_equipo")));
            }
            rs.close();
            return directores;
        } catch (SQLException ex) {
            throw new DAOException("Error en DAO al mostrar todos los directores");
        }
    }
}
