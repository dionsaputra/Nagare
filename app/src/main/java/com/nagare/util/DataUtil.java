package com.nagare.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nagare.model.Acara;
import com.nagare.model.GalangDana;
import com.nagare.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataUtil {

    public static DatabaseReference dbRoot = FirebaseDatabase.getInstance().getReference();
    public static DatabaseReference dbUser = dbRoot.child("users");
    public static DatabaseReference dbGalangDana = dbRoot.child("galang-danas");
    public static DatabaseReference dbAcara = dbRoot.child("acaras");

    public static void addGalangDana(GalangDana galangDana) {
        Map<String, Object> map = new HashMap<>();
        map.put(galangDana.title, galangDana);
        dbGalangDana.push().updateChildren(map);
    }

    public static void addAcara(Acara acara) {
        Map<String, Object> map = new HashMap<>();
        map.put(acara.title, acara);
        dbAcara.push().updateChildren(map);
    }

//    /*** Singleton definition ***/
//    private static DataUtil instance = null;
//    private DataUtil() {
//        users = new ArrayList<>();
//    }
//    public static DataUtil getInstance() {
//        if (instance == null) instance = new DataUtil();
//        return instance;
//    }
//
//    /*** CRUD section ***/
//    public void addUser(User user) {
//        users.add(user);
//    }
//
//    public User getUser(int idx) {
//        return users.get(idx);
//    }
//
//    public void deleteUser(int idx) {
//        users.remove(idx);
//    }
//
//    public void editUser(int idx, User user) {
//        users.set(idx, user);
//    }

    //
//    /*** STUB section ***/
//    /**
//    public void initUserStub() {
//        addUser(new User("dion@email.com", "dion123"));
//        addUser(new User("ilham@email.com", "ilham123"));
//        addUser(new User("mol@email.com", "mol123"));
//    }**/
///**
//    public boolean isExistUser(User user) {
//        boolean found = false;
//        for (User u : users) {
//            if (u.getEmail().equals(user.getEmail()) && u.getPassword().equals(user.getPassword())) {
//                found = true;
//                break;
//            }
//        }
//        return found;
//    }*/
}
