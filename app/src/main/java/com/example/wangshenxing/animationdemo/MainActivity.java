package com.example.wangshenxing.animationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private CircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        circleView = findViewById(R.id.circleView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleView.startAnimator();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
