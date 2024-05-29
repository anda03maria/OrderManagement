package org.example.repository;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class JdbcUtils {

    private Properties jdbcProps;


    public JdbcUtils(Properties props){
        jdbcProps=props;
    }

    private Connection instance=null;

    private Connection getNewConnection(){
        String url=jdbcProps.getProperty("jdbc.url");
        Connection con=null;
        try {
            con= DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error getting connection "+e);
        }
        return con;
    }

    public Connection getConnection(){
        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();

        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return instance;
    }
}