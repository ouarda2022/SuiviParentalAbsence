package com.example.suiviparentaldabsence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity6 extends AppCompatActivity {

    ListView ls;
    String N_inscription,nom, prenom, classe;
    HashMap<String,String> map;
    Params p=new Params();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        ls=findViewById(R.id.lst);



        Bundle extras=getIntent().getExtras();
        if (extras!=null){

            N_inscription=extras.getString("N_inscription");
            nom=extras.getString("nom");
            prenom=extras.getString("prenom");
            classe=extras.getString("classe");

            map=new HashMap<String,String>();
            map.put("N_inscription",N_inscription);
            map.put("nom",nom);
            map.put("prenom",prenom);
            map.put("classe",classe);

            p.values.add(map);

        }

        SimpleAdapter adapter=new SimpleAdapter(MainActivity6.this,p.values,R.layout.item,
                new String[]{"N_inscription","nom","prenom","classe"},
                new int[]{R.id.tN_inscription,R.id.tnom,R.id.tprenom,R.id.tclasse});

        ls.setAdapter(adapter);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent t=new Intent(getApplicationContext(),MainActivity8.class);
                t.putExtra("position",i);
                startActivity(t);
                finish();
            }
        });

        final Button bRetour=findViewById(R.id.bRetour6);
        bRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity6.this, MainActivity5.class);
                startActivity(i);
                finish();
            }
        });

    }
}