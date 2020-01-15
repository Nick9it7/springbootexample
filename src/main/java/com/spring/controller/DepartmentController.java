package com.spring.controller;

import com.spring.dao.DepartmentDAO;
import com.spring.exception.DAOException;
import com.spring.model.Department;
import com.spring.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    @Autowired
    private DepartmentDAO departmentDAO;

    @ExceptionHandler(DAOException.class)
    public final ResponseEntity<Object> handleDAOException(DAOException exception, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/department")
    public ResponseEntity getDepartments() {
        return ResponseEntity.ok(departmentDAO.getAll());
    }

    @PostMapping("/department")
    public ResponseEntity create(@Valid @RequestBody Department Department) {
        departmentDAO.add(Department);
        return new ResponseEntity<>(new ApiResponse(true, "Department created successfully"),
                HttpStatus.CREATED);
    }
}
