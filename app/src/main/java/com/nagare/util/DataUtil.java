package com.nagare.util;

import com.nagare.model.User;

import java.util.ArrayList;

public class DataUtil {
    private ArrayList<User> users;

    /*** Singleton definition ***/
    private static DataUtil instance = null;
    private DataUtil() {
        users = new ArrayList<>();
    }
    public static DataUtil getInstance() {
        if (instance == null) instance = new DataUtil();
        return instance;
    }

    /*** CRUD section ***/
    public void addUser(User user) {
        users.add(user);
    }

    public User getUser(int idx) {
        return users.get(idx);
    }

    public void deleteUser(int idx) {
        users.remove(idx);
    }

    public void editUser(int idx, User user) {
        users.set(idx, user);
    }

    /*** STUB section ***/
    /**
    public void initUserStub() {
        addUser(new User("dion@email.com", "dion123"));
        addUser(new User("ilham@email.com", "ilham123"));
        addUser(new User("mol@email.com", "mol123"));
    }**/
/**
    public boolean isExistUser(User user) {
        boolean found = false;
        for (User u : users) {
            if (u.getEmail().equals(user.getEmail()) && u.getPassword().equals(user.getPassword())) {
                found = true;
                break;
            }
        }
        return found;
    }*/
}
