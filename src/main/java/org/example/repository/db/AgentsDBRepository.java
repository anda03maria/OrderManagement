package org.example.repository.db;



import org.example.model.Agent;
import org.example.repository.AgentsRepository;
import org.example.repository.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AgentsDBRepository implements AgentsRepository {

    private JdbcUtils jdbcUtils;

    public AgentsDBRepository(Properties properties) {
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public Agent findById(String s) {
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from \"Agents\" where id = ?"
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
    public List<Agent> findAll() {
        Connection connection = jdbcUtils.getConnection();
        List<Agent> entities = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from \"Agents\"")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    entities.add(extractEntity(resultSet));
                }
            }
        } catch (SQLException exception) {
        }
        return entities;
    }

    @Override
    public boolean save(Agent entity) {
        return false;
    }

    @Override
    public Agent delete(String s) {
        return null;
    }

    @Override
    public boolean update(Agent entity, String s) {
        return false;
    }

    private Agent extractEntity(ResultSet resultSet) throws SQLException {
        return new Agent(
                resultSet.getString("id"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("email")
                );
    }
}
