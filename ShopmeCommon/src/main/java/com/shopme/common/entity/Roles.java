package com.shopme.common.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 300, nullable = false)
    private String description;

    public Roles(String name){
        this.name = name;
    }

    public Roles(String name, String description){
        this.name = name;
        this.description = description;
    }

}