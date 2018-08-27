package com.nagare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nagare.util.ViewUtil;

public class DetailFasilitasActivity extends AppCompatActivity {

    private ImageView selectedFasilitasImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fasilitas);

        selectedFasilitasImage = findViewById(R.id.iv_selected_fasilitas);
        ViewUtil.loadImage(this, selectedFasilitasImage, R.drawable.itb);
    }
}
