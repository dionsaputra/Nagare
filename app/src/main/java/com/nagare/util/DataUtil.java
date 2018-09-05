package com.nagare.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nagare.model.Acara;
import com.nagare.model.GalangDana;
import com.nagare.model.User;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class DataUtil {
    public DatabaseReference dbRoot = FirebaseDatabase.getInstance().getReference();
    public DatabaseReference dbGalangDana = dbRoot.child("galang-danas");

    public ArrayList<User> users;
    public ArrayList<GalangDana> galangDanas;

    /*** Singleton definition ***/
    private static DataUtil instance = null;
    private DataUtil() {
       users = new ArrayList<>();
       galangDanas = new ArrayList<>();
    }

    public static DataUtil getInstance() {
        if (instance == null) instance = new DataUtil();
        return instance;
    }

    public ArrayList<GalangDana> generateGalangDana() {
        ArrayList<GalangDana> galangDanas = new ArrayList<>();
        for (int i=0; i<8; i++) {
            galangDanas.add(new GalangDana(
                    randomStr(8),
                    randomStr(20),
                    randomStr(4),
                    10L,
                    10L,
                    10L
                    )
            );
        }
        return galangDanas;
    }

    public String randomStr(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

    public void setGalangDanas(ArrayList<GalangDana> newGalangDanas) {
        this.galangDanas = newGalangDanas;
    }
}
