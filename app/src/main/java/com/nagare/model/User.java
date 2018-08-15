package com.nagare.model;

public class User {
    private String email, password;

    /**
     * Complete property constructor
     * @param email
     * @param password
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isEqual(User user) {
        return this.email == user.email && this.password == user.password;
    }

    /*** setter - getter area ***/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
