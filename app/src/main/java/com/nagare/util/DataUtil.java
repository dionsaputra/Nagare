package com.nagare.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nagare.fragment.Firebase;
import com.nagare.model.Acara;
import com.nagare.model.GalangDana;
import com.nagare.model.User;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class DataUtil {
    private static final String kelurahanTable = "kelurahans",
            userTable = "users",
            acaraTable = "acaras",
            fasilitasTable = "fasilitas",
            temuLurahTable = "temulurahs",
            laporTable = "lapors",
            galangDanaTable = "galangdanas";

    public static final String USER_KEY = "user_key";

    private static DatabaseReference dbRoot = FirebaseDatabase.getInstance().getReference();
    public static DatabaseReference dbKelurahan = dbRoot.child(kelurahanTable),
            dbUser = dbRoot.child(userTable),
            dbAcara = dbRoot.child(acaraTable),
            dbFasilitas = dbRoot.child(fasilitasTable),
            dbTemuLurah = dbRoot.child(temuLurahTable),
            dbLapor = dbRoot.child(laporTable),
            dbGalangDana = dbRoot.child(galangDanaTable);

}
