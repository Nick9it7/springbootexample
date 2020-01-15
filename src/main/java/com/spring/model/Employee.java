package com.spring.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Employee {

    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Boolean active;

    @NotNull
    private Integer departmentId;

    private Department department;

    public Employee() {}

    public Employee(String name, Boolean active, Integer departmentId) {
        this.name = name;
        this.active = active;
        this.departmentId = departmentId;
    }

    public Employee(Integer id, String name, Boolean active, Integer departmentId) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.departmentId = departmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
