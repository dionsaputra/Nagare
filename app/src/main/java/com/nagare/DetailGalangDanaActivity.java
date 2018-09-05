package com.nagare;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
        getSupportActionBar().setTitle("Galang Dana");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
