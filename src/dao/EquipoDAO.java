/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;
import model.Equipo;

/**
 *
 * @author cifu
 */
public interface EquipoDAO {
    
    public void add (Equipo e, Connection con)throws DAOException;
    
    public void remove (String nombreDepartamento, Connection con)throws DAOException;
    
    public ArrayList<Equipo> list (Connection con)throws DAOException;
}
