package com.shopme.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    @Column(length = 150, nullable = false, unique = true)
    private String email;

    @Column(length = 64,nullable = false)
    private String password;

    @Column(name = "first_name",length = 150, nullable = false, unique = true)
    private String firstName;

    @Column(name = "last_name",length = 150, nullable = false, unique = true)
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "users_roles", joinColumns = @JoinColumn(name = "user_code"),
            inverseJoinColumns = @JoinColumn(name = "role_code")
    )
    private Set<Roles> roles = new HashSet<>();

    @Column(length = 64)
    private String photos;

    private boolean enabled;

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(){}

    public void addRole(Roles roles){
        this.roles.add(roles);
    }

}
