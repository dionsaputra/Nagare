package com.nagare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nagare.util.ViewUtil;

public class DetailGalangDanaActivity extends AppCompatActivity {

    private ImageView selectedGalangDana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_galang_dana);

        selectedGalangDana = findViewById(R.id.iv_selected_galang_dana);
        ViewUtil.loadImage(this, selectedGalangDana, R.drawable.itb);
    }
}
