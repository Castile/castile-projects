package com.castile.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

/**
 * @author castile
 * @date 2025-02-14 22:09
 */
public class User {
    @NotNull
    private String name;

    @Max(60)
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
