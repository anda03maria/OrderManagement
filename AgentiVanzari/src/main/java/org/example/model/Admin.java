package org.example.model;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "Admins")
public class Admin implements Entity<String> {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "password")
    private String password;

    public Admin() {}

    public Admin(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public Admin(String password) {
        this.password = password;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String s) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
