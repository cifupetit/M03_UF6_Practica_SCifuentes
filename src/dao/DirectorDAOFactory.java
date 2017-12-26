/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author cifu
 */
public class DirectorDAOFactory {

    public DirectorDAO createDirectorDAO() {
        return new DirectorDAOJDBCImpl();
    }
    
}
