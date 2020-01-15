package com.spring.model;

import javax.validation.constraints.NotBlank;

public class Department {

    private Integer id;

    @NotBlank
    private String name;

    public Department() {}

    public Department(String name) {
        this.name = name;
    }

    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
