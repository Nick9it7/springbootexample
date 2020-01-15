package com.spring.controller;

import com.spring.dao.EmployeeDAO;
import com.spring.exception.DAOException;
import com.spring.model.Employee;
import com.spring.payload.ApiResponse;
import com.spring.payload.PaginationResponse;
import com.spring.payload.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDAO;

    @ExceptionHandler(DAOException.class)
    public final ResponseEntity<Object> handleDAOException(DAOException exception, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/employee")
    public PaginationResponse show(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "4") Integer limit) {
        return new PaginationResponse(
                employeeDAO.getAll(page, limit),
                employeeDAO.totalCount()
        );
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity fetch(@PathVariable Integer id) {
        Employee employee = employeeDAO.getById(id);

        if (employee == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Employee with id " + id + " not found"),
                    HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(employee);
    }

    @PostMapping("/employee/search")
    public PaginationResponse search(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "4") Integer limit,
                                     @RequestBody SearchRequest searchRequest) {
        return new PaginationResponse(
                employeeDAO.getAllByName(searchRequest.getName(), page, limit),
                employeeDAO.totalCountByName(searchRequest.getName())
        );
    }

    @PostMapping("/employee")
    public ResponseEntity create(@Valid @RequestBody Employee employee) {
        employeeDAO.add(employee);
        return new ResponseEntity<>(new ApiResponse(true, "Employee created successfully"),
                HttpStatus.CREATED);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity update(@PathVariable Integer id, @Valid @RequestBody Employee employee) {
        Employee employeePresent = employeeDAO.getById(id);

        if (employeePresent == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Employee with id " + id + " not found"),
                    HttpStatus.BAD_REQUEST);
        }

        employeePresent.setName(employee.getName());
        employeePresent.setActive(employee.getActive());
        employeePresent.setDepartmentId(employee.getDepartmentId());
        employeeDAO.update(employeePresent);

        return new ResponseEntity<>(new ApiResponse(true, "Employee updated successfully"),
                HttpStatus.OK);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity remove(@PathVariable Integer id) {
        Employee employee = employeeDAO.getById(id);

        if (employee == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Employee with id " + id + " not found"),
                    HttpStatus.BAD_REQUEST);
        }

        employeeDAO.delete(employee);

        return new ResponseEntity<>(new ApiResponse(true, "Employee removed successfully"),
                HttpStatus.OK);
    }
}
