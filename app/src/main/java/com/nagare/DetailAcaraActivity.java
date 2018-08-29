package com.nagare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nagare.util.ViewUtil;

public class DetailAcaraActivity extends AppCompatActivity {

    private ImageView selectedAcaraImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_acara);

        selectedAcaraImage = findViewById(R.id.iv_selected_acara);
        ViewUtil.loadImage(this, selectedAcaraImage, R.drawable.itb);
    }
}
