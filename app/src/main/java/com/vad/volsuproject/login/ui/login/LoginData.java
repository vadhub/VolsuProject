package com.vad.volsuproject.login.ui.login;

public class LoginData {

    private String password;
    private String username;

    public LoginData(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public LoginData() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
