package com.spring.dao.impl;

import com.spring.dao.DepartmentDAO;
import com.spring.exception.DAOException;
import com.spring.model.Department;
import com.spring.util.DatabaseUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentDAOImpl extends DatabaseUtil implements DepartmentDAO {
    @Override
    public void add(Department department) throws DAOException {
        String sql = "INSERT INTO tblDepartments (dpName) VALUES (?)";
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, department.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot create department", e);
        }
    }

    @Override
    public List<Department> getAll() throws DAOException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM tblDepartments";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    departments.add(new Department(
                            resultSet.getInt("dpID"),
                            resultSet.getString("dpName")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot fetch department", e);
        }
        return departments;
    }
}
