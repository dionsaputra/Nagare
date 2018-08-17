package com.nagare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagare.activity.Acara;
import com.nagare.activity.GalangDana;
import com.nagare.activity.Keliling;
import com.nagare.activity.Lapor;
import com.nagare.activity.TemuLurah;
import com.nagare.util.ViewUtil;

public class MainActivity extends AppCompatActivity {

    private Context context = this;

    private ImageView nagareLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();

        prepareOtherActivity(R.id.tv_temu_lurah, TemuLurah.class);
        prepareOtherActivity(R.id.tv_keliling, Keliling.class);
        prepareOtherActivity(R.id.tv_lapor, Lapor.class);
        prepareOtherActivity(R.id.tv_acara, Acara.class);
        prepareOtherActivity(R.id.tv_galang_dana, GalangDana.class);
        prepareOtherActivity(R.id.tv_stub_1, MapsActivity.class);

    }

    private void initComponent() {
        nagareLogo = findViewById(R.id.iv_nagare_logo);
        ViewUtil.loadImage(context, nagareLogo, R.drawable.nagare_logo);
    }

    private void prepareOtherActivity(int textViewResId, final Class intentClass) {
        TextView textView = findViewById(textViewResId);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, intentClass);
                startActivity(intent);
            }
        });
    }
}
