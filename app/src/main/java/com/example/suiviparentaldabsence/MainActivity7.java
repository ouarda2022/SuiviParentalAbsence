package com.example.suiviparentaldabsence;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity7 extends AppCompatActivity {


    ListView ls;
    Button bt1,bt2;

    int success;
    Params p = new Params();
    String N_inscription;

    ProgressDialog dialog;
    JSONParser parser = new JSONParser();

    ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        ls=findViewById(R.id.listAbsence);
        bt1=findViewById(R.id.button10);
        bt2=findViewById(R.id.button11);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity7.this, MainActivity5.class);
                startActivity(i);
                finish();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Bundle extras=getIntent().getExtras();
        if (extras!=null)
        {
            N_inscription=extras.getString("N_inscription");
        }
        

        new SelectOne().execute();



    }

    class SelectOne extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(MainActivity7.this);
            dialog.setMessage("Patientez SVP");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {



            HashMap<String, String> map = new HashMap<String, String>();

            map.put("N_inscription", N_inscription);


            JSONObject object = parser.makeHttpRequest("http://10.0.2.2/GererAbsence/Absence/afficherHistorique.php", "GET", map);

            try {
                success = object.getInt("success");
                if (success == 1) {
                    JSONArray absences = object.getJSONArray("absences");
                    for (int i = 0; i < absences.length(); i++) {
                        JSONObject absence = absences.getJSONObject(i);
                        HashMap<String, String> map1 = new HashMap<String, String>();
                        map1.put("dateAbsence", absence.getString("dateAbsence"));
                        map1.put("nbreHeures", absence.getString("nbreHeures"));
                        map1.put("lesMatieres", absence.getString("lesMatieres"));
                        map1.put("dateDebutAbsence", absence.getString("dateDebutAbsence"));
                        map1.put("nbreJours", absence.getString("nbreJours"));
                        map1.put("alerte1", absence.getString("alerte1"));
                        map1.put("alerte2", absence.getString("alerte2"));
                        map1.put("alerte3", absence.getString("alerte3"));
                        map1.put("dateFinAbsence", absence.getString("dateFinAbsence"));
                        map1.put("motif", absence.getString("motif"));
                        map1.put("sousResponsabiliteDe", absence.getString("sousResponsabiliteDe"));
                        map1.put("observation", absence.getString("observation"));

                        values.add(map1);

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.cancel();

            SimpleAdapter adapter = new SimpleAdapter(MainActivity7.this, values, R.layout.item2,
                    new String[]{"dateAbsence", "nbreHeures","lesMatieres" ,"dateDebutAbsence", "nbreJours",
                            "alerte1", "alerte2", "alerte3", "dateFinAbsence", "motif", "sousResponsabiliteDe",
                            "observation"}, new int[]{R.id.t1, R.id.t2, R.id.t3, R.id.t4, R.id.t5, R.id.t6,
                    R.id.t7, R.id.t8, R.id.t9, R.id.t10, R.id.t11,R.id.t12});

            ls.setAdapter(adapter);

        }
    }




}