package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "Agents")
public class Agent implements Entity<String> {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public String getPassword() {
        return password;
    }

    public Agent() {}

    public void setPassword(String password) {
        this.password = password;
    }

    public Agent(String s, String name, String email, String password) {

        this.id = s;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Agent(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Agent(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public Agent(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String s) {
        this.id = id;
    }
}
