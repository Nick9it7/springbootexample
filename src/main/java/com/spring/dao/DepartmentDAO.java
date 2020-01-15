package com.spring.dao;

import com.spring.exception.DAOException;
import com.spring.model.Department;

import java.util.List;

public interface DepartmentDAO {
    void add(Department department) throws DAOException;

    List<Department> getAll() throws DAOException;
}
