package org.example.repository.db;


import org.example.model.Admin;
import org.example.repository.AdminsRepository;
import org.example.repository.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class AdminsDBRepository implements AdminsRepository {

    private JdbcUtils jdbcUtils;


    public AdminsDBRepository(Properties properties) {
        this.jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public Admin findById(String s) {
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from \"Admins\" where id = ?"
        )) {
            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return extractEntity(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Admin> findAll() {
        return null;
    }

    @Override
    public boolean save(Admin entity) {
        return false;
    }

    @Override
    public Admin delete(String s) {
        return null;
    }

    @Override
    public boolean update(Admin entity, String s) {
        return false;
    }

    private Admin extractEntity(ResultSet resultSet) throws SQLException{
        return new Admin(resultSet.getString("id"), resultSet.getString("password"));
    }
}
