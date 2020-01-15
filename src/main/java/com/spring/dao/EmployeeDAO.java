package com.spring.dao;

import com.spring.model.Employee;

import java.util.List;

public interface EmployeeDAO {

    void add(Employee employee);

    List<Employee> getAll(Integer page, Integer limit);

    List<Employee> getAllByName(String name, Integer page, Integer limit);

    Employee getById(Integer id);

    void update(Employee employee);

    void delete(Employee employee);

    Integer totalCount();

    Integer totalCountByName(String name);
}
