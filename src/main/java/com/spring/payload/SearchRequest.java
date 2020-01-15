package com.spring.payload;

import javax.validation.constraints.NotBlank;

public class SearchRequest {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
