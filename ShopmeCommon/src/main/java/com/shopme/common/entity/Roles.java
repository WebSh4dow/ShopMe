package com.shopme.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.management.relation.Role;
import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
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

    public Roles(Integer code){
        super();
        this.code = code;
    }
    public Roles(){}

    @Override
    public String toString(){
        return this.name;
    }

}
