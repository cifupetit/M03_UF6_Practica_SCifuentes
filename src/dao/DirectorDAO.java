/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;
import model.Director;

/**
 *
 * @author cifu
 */
public interface DirectorDAO {
    
    public void add (Director d, Connection con)throws DAOException;
    
    public void updateEquipo (int idDirector, int idEquipo, Connection con)throws DAOException;
    
    public void remove (int idDirector, Connection con)throws DAOException;
    
    public Director search (int idDirector, Connection con)throws DAOException;
    
    public ArrayList<Director> list (Connection con)throws DAOException;
}
