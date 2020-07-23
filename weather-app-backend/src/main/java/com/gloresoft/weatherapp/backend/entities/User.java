package com.gloresoft.weatherapp.backend.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class User {

    @Id
    @NotNull
    private String username;

    private String password;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

}
