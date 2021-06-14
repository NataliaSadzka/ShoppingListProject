package com.example.shoppinglist.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.shoppinglist.MainActivity;
import com.example.shoppinglist.R;

public class AboutActivity extends Activity {

    private ImageView imageView;
    private ImageView backImage;
    private TextView appTextView;
    private TextView versionTextView;
    private TextView authorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        imageView = findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        backImage = findViewById(R.id.image_back);
        appTextView = findViewById(R.id.app_text_view);
        versionTextView = findViewById(R.id.version_text_view);
        authorTextView = findViewById(R.id.author_text_view);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
