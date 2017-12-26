/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cifu
 */
public class ConnectDB {
    
    private static Connection instance;
    
    private ConnectDB() {
    }
    
    public static Connection getInstance() throws SQLException {
        if (instance == null) {
            instance = DriverManager.getConnection(practica_java_uf6Connection.URL, practica_java_uf6Connection.USERNAME, practica_java_uf6Connection.PASSWORD);
            System.out.println("Base de datos conectada");
        }
        return instance;
    }
    
    public static void closeConnection() throws SQLException {
        if (instance != null) {
            instance.close();
            instance = null;
            System.out.println("Base de datos desconectada");
        }
    }
}
