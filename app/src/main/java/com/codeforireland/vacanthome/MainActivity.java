package com.codeforireland.vacanthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    protected static boolean message = false;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivity(new Intent(MainActivity.this, MainActivityNew.class));

            }
        });
    }

    protected void onResume() {
        super.onResume();
        b.getBackground().setAlpha(255);
    }
}
