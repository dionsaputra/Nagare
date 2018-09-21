package com.nagare.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataUtil {
    private static final String kelurahanTable = "kelurahans",
            userTable = "users",
            acaraTable = "acaras",
            fasilitasTable = "fasilitas",
            temuLurahTable = "temulurahs",
            laporTable = "lapors",
            galangDanaTable = "galangdanas";

    public static String USER_KEY;

    public static DatabaseReference dbRoot = FirebaseDatabase.getInstance().getReference();
    public static DatabaseReference dbKelurahan = dbRoot.child(kelurahanTable);
    public static DatabaseReference dbUser = dbRoot.child(userTable);
    public static DatabaseReference dbAcara = dbRoot.child(acaraTable);
    public static DatabaseReference dbFasilitas = dbRoot.child(fasilitasTable);
    public static DatabaseReference dbTemuLurah = dbRoot.child(temuLurahTable);
    public static DatabaseReference dbLapor = dbRoot.child(laporTable);
    public static DatabaseReference dbGalangDana = dbRoot.child(galangDanaTable);

}