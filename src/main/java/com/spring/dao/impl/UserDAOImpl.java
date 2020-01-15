package com.spring.dao.impl;

import com.spring.dao.UserDAO;
import com.spring.exception.DAOException;
import com.spring.model.User;
import com.spring.util.DatabaseUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserDAOImpl extends DatabaseUtil implements UserDAO {
    @Override
    public Boolean existsByUsername(String username) {
        return fetchByUsername(username) != null;

    }

    @Override
    public Boolean existsByEmail(String email) {
        Boolean exist = false;
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    exist = true;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot fetch user", e);
        }
        return exist;
    }

    @Override
    public User findByUsername(String username) {
        return fetchByUsername(username);
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot create user", e);
        }
    }

    private User fetchByUsername(String username) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot fetch user", e);
        }
        return user;
    }
}
