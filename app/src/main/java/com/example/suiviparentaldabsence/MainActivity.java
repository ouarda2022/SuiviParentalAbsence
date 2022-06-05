package com.example.suiviparentaldabsence;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Button button=findViewById(R.id.button);
        button.setOnClickListener(view -> lancer1());

        final Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(view -> lancer2());

        final Button button3=findViewById(R.id.button3);
        button3.setOnClickListener(view -> sortir());
    }

    public void lancer1() {
        Intent i = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(i);
        finish();
    }
    public void lancer2() {
        Intent i = new Intent(MainActivity.this, MainActivity3.class);
        startActivity(i);
        finish();
    }

    public void sortir(){
    finish();
    }
}