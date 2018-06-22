package com.codeforireland.vacanthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    protected int SECOND_ACTIVITY_RESULT_CODE = 0;
    protected static boolean message = false;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = (Button) findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
//                Intent i = new Intent(MainActivity.this, DataEntry.class);
//                startActivityForResult(i, SECOND_ACTIVITY_RESULT_CODE);
                startActivity(new Intent(MainActivity.this, MainActivityNew.class));

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                String returnString = data.getStringExtra("keyName");

                // set text view with string
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void onResume() {
        super.onResume();
        b.getBackground().setAlpha(255);
    }
}
