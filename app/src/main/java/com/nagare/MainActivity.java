package com.nagare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nagare.activity.Acara;
import com.nagare.activity.Fasilitas;
import com.nagare.activity.GalangDana;
import com.nagare.activity.Keliling;
import com.nagare.activity.Lapor;
import com.nagare.activity.StubOne;
import com.nagare.activity.TemuLurah;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareOtherActivity(R.id.tv_temu_lurah, TemuLurah.class);
        prepareOtherActivity(R.id.tv_keliling, Keliling.class);
        prepareOtherActivity(R.id.tv_lapor, Lapor.class);
        prepareOtherActivity(R.id.tv_acara, Acara.class);
        prepareOtherActivity(R.id.tv_fasilitas, Fasilitas.class);
        prepareOtherActivity(R.id.tv_galang_dana, GalangDana.class);
        prepareOtherActivity(R.id.tv_stub_1, StubOne.class);

    }

    private void prepareOtherActivity(int textViewResId, final Class intentClass) {
        TextView textView = (TextView) findViewById(textViewResId);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, intentClass);
                startActivity(intent);
            }
        });
    }
}
