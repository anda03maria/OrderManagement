package org.example.repository.db;


import org.example.model.Order;
import org.example.repository.JdbcUtils;
import org.example.repository.OrdersRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class OrdersDBRepository implements OrdersRepository {

    private JdbcUtils jdbcUtils;


    public OrdersDBRepository(Properties properties) {
        this.jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public Order findById(Integer integer) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public boolean save(Order entity) {
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into \"Orders\"(agent_id, product_id, quantity, client_name, payment_method, courier, client_email) values " +
                        "(?,?,?,?,?,?,?)")) {
            preparedStatement.setString(1, entity.getAgentId());
            preparedStatement.setInt(2, entity.getProductId());
            preparedStatement.setInt(3, entity.getQuantity());
            preparedStatement.setString(4, entity.getClientName());
            preparedStatement.setString(5, entity.getPaymentMethod().toString());
            preparedStatement.setString(6, entity.getCourierType().toString());
            preparedStatement.setString(7, entity.getClientEmail());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    @Override
    public Order delete(Integer integer) {
        return null;
    }

    @Override
    public boolean update(Order entity, Integer integer) {
        return false;
    }
}
