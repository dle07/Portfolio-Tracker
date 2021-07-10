package com.PortfolioTracker.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    

    

}



