package com.example.suiviparentaldabsence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity5 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);


        final Button bNouveau=findViewById(R.id.bNouveau);
        bNouveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity5.this, MainActivity1.class);
                startActivity(i);
                finish();
            }
        });

       final Button bAncien=findViewById(R.id.bAncien);
       bAncien.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(MainActivity5.this, MainActivity6.class);
               startActivity(i);
               finish();
           }
       });

       final Button bSortir=findViewById(R.id.bSortir);
       bSortir.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });

    }

}