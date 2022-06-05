package com.example.suiviparentaldabsence;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainActivity9 extends AppCompatActivity {


    EditText nom,prenom,dateN;
    TextView t99,t999;
    Button b,bb;
    ProgressDialog dialog;
    String id_parent;
    JSONParser parser=new JSONParser();
    int success  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);

        nom=findViewById(R.id.tnom9);
        prenom=findViewById(R.id.tprenom9);
        dateN=findViewById(R.id.tdate9);
        t99=findViewById(R.id.t99);
        t999=findViewById(R.id.t999);
        b=findViewById(R.id.button9);
        bb=findViewById(R.id.button99);

        Date curentDate=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(curentDate);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        dateN.setText(sdf.format(curentDate));

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new afficherId().execute();
            }
        });
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retour9();
            }
        });

    }
    class afficherId extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(MainActivity9.this);
            dialog.setMessage("Patientez SVP");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String, String> map = new HashMap<String, String>();

            map.put("nom", nom.getText().toString());
            map.put("prenom", prenom.getText().toString());
            map.put("dateNaissance", dateN.getText().toString());



            JSONObject object = parser.makeHttpRequest("http://10.0.2.2/GererAbsence/Parent/AfficherId.php", "GET", map);

            try {
                success = object.getInt("success");
                if(success==1){

                    JSONArray user=object.getJSONArray("user");
                    JSONObject o=user.getJSONObject(0);
                    id_parent=o.getString("id_parent");

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

            if(success==1) {

                t999.setText(id_parent);


            }

            else
            {
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity9.this);
                alert.setTitle("Attention  !");
                alert.setMessage("Echec !!!!!");
                alert.setNeutralButton("ok",null);
                alert.show();
            }


        }
    }
    public void Retour9(){
        Intent i = new Intent(MainActivity9.this, MainActivity1.class);
        startActivity(i);
        finish();
    }

}