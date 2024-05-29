package org.example.repository.db;



import org.example.model.Product;
import org.example.repository.JdbcUtils;
import org.example.repository.ProductsRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ProductsDBRepository implements ProductsRepository {

    private JdbcUtils jdbcUtils;

    public ProductsDBRepository(Properties properties) {
        this.jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public Product findById(Integer integer) {
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from \"Products\" where id = ?")) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return extractEntity(resultSet);
            } else {
            }
        } catch (SQLException exception) {
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        Connection connection = jdbcUtils.getConnection();
        List<Product> entities = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from \"Products\"")) {
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
    public boolean save(Product entity) {
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into \"Products\"(price, name, description, quantity) values (?, ?, ?, ?)")) {
            preparedStatement.setDouble(1, entity.getPrice());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setInt(4, entity.getQuantity());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    @Override
    public Product delete(Integer integer) {
        return null;
    }

    @Override
    public boolean update(Product entity, Integer integer) {
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "update \"Products\" set price=?, name=?, description=?, quantity=? where id=?"
        )) {
            preparedStatement.setDouble(1, entity.getPrice());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setInt(4, entity.getQuantity());
            preparedStatement.setInt(5, integer);
            int numberRowsAffected = preparedStatement.executeUpdate();
            if (numberRowsAffected != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException exception) {
            return false;
        }
    }

    private Product extractEntity(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getDouble("price"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("quantity")
        );
    }
}
