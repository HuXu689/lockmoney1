package com.example.ltdd.Model;

public class UserModel {
    private String user_id;
    private String roleId;
    private String username;
    private String fullname;
    private String password;
    private String email;
    private int status;

    public UserModel() {
    }

    public UserModel(String user_id, String username, String fullname, String password, String email, String roleId) {
        this.user_id = user_id;
        this.roleId = roleId;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.email = email;
        this.status = 1;
    }
    public UserModel(String user_id, String username, String fullname, String password, String email, String roleId, int status) {
        this.user_id = user_id;
        this.roleId = roleId;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.email = email;
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
