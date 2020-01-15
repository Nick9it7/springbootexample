package com.spring.dao.impl;

import com.spring.dao.EmployeeDAO;
import com.spring.exception.DAOException;
import com.spring.model.Department;
import com.spring.model.Employee;
import com.spring.util.DatabaseUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeDAOImpl extends DatabaseUtil implements EmployeeDAO {
    @Override
    public void add(Employee employee) {
        String sql = "INSERT INTO tblEmployees (empName, empActive, emp_dpID) VALUES (?, ?, ?)";
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.getName());
            statement.setBoolean(2, employee.getActive());
            statement.setInt(3, employee.getDepartmentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot create employee", e);
        }
    }

    @Override
    public List<Employee> getAll(Integer page, Integer limit) {
        List<Employee> employees;
        String sql = "SELECT * FROM tblEmployees INNER JOIN tblDepartments ON tblEmployees.emp_dpID = " +
                "tblDepartments.dpID LIMIT ? OFFSET ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            statement.setInt(2, (page - 1) * limit);

            employees = getEmployees(statement);
        } catch (SQLException e) {
            throw new DAOException("Cannot fetch employees", e);
        }
        return employees;
    }

    @Override
    public List<Employee> getAllByName(String name, Integer page, Integer limit) {
        List<Employee> employees;
        String sql = "SELECT * FROM tblEmployees INNER JOIN tblDepartments ON tblEmployees.emp_dpID = " +
                "tblDepartments.dpID WHERE tblEmployees.empName LIKE ? LIMIT ? OFFSET ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name + "%");
            statement.setInt(2, limit);
            statement.setInt(3, (page - 1) * limit);

            employees = getEmployees(statement);
        } catch (SQLException e) {
            throw new DAOException("Cannot fetch employees", e);
        }
        return employees;
    }

    @Override
    public Employee getById(Integer id) {
        Employee employee = null;
        String sql = "SELECT * FROM tblEmployees INNER JOIN tblDepartments ON tblEmployees.emp_dpID = " +
                " tblDepartments.dpID WHERE empID = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    employee = new Employee();
                    Department department = new Department();
                    department.setId(resultSet.getInt("dpID"));
                    department.setName(resultSet.getString("dpName"));

                    employee.setId(resultSet.getInt("empID"));
                    employee.setName(resultSet.getString("empName"));
                    employee.setActive(resultSet.getBoolean("empActive"));
                    employee.setDepartmentId(resultSet.getInt("emp_dpID"));
                    employee.setDepartment(department);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot fetch employee", e);
        }
        return employee;
    }

    @Override
    public void update(Employee employee) {
        String sql = "UPDATE tblEmployees SET empName=?, empActive=?, emp_dpID=? WHERE empID = ?";
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employee.getName());
            statement.setBoolean(2, employee.getActive());
            statement.setInt(3, employee.getDepartmentId());
            statement.setInt(4, employee.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot update employees", e);
        }
    }

    @Override
    public void delete(Employee employee) {
        String sql = "DELETE FROM tblEmployees WHERE empID = ?";
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employee.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot delete employees", e);
        }
    }

    @Override
    public Integer totalCount() {
        Integer count = 0;
        String sql = "SELECT COUNT(*) as 'count' FROM tblEmployees";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot get the number of employees", e);
        }
        return count;
    }

    @Override
    public Integer totalCountByName(String name) {
        Integer count = 0;
        String sql = "SELECT COUNT(*) as 'count' FROM tblEmployees WHERE tblEmployees.empName LIKE ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot get the number of employees", e);
        }
        return count;
    }

    private List<Employee> getEmployees(PreparedStatement statement) {
        List<Employee> employees = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("dpID"));
                department.setName(resultSet.getString("dpName"));

                Employee employee = new Employee(
                        resultSet.getInt("empID"),
                        resultSet.getString("empName"),
                        resultSet.getBoolean("empActive"),
                        resultSet.getInt("emp_dpID")
                );
                employee.setDepartment(department);

                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot fetch employees", e);
        }

        return employees;
    }
}
