package com.example.ltdd.Model;

public class UserModel {
    private String id;
    private String roleId;
    private String username;
    private String fullname;
    private String password;
    private String email;

    public UserModel() {
    }

    public UserModel(String id, String username, String fullname, String password, String email, String roleId) {
        this.id = id;
        this.roleId = roleId;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
