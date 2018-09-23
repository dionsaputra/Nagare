package com.nagare;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagare.util.ViewUtil;

public class DetailAcaraActivity extends AppCompatActivity {

    private ImageView selectedAcaraImage;
    private ActionBar actionBar;
    private TextView title;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_acara);
        selectedAcaraImage = findViewById(R.id.iv_selected_acara);
        ViewUtil.loadImage(this, selectedAcaraImage, R.drawable.itb);
        getSupportActionBar().setTitle("Kalender");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = findViewById(R.id.tv_acara_name);
        desc = findViewById(R.id.tv_acara_address);
        String[] s = getIntent().getStringArrayExtra("TEST");
        title.setText(s[1]);
        desc.setText(s[0]);
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
