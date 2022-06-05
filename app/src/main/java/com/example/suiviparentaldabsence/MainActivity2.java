package com.example.suiviparentaldabsence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    EditText NomUtilisateur, nom, prenom, dateNaissance, lieuNaissance;
    EditText MotDePasse;
    EditText Confirmation;
    ProgressDialog dialog;
    JSONParser parser=new JSONParser();
    int success  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dateNaissance=findViewById(R.id.tdateNaissance2);

        Date curentDate=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(curentDate);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        dateNaissance.setText(sdf.format(curentDate));

        final Button bOK2=findViewById(R.id.bOK2);
        bOK2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OK2();
            }
        });

        final Button bAnnuler2=findViewById(R.id.bAnnuler2);
        bAnnuler2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Annuler2();
            }
        });



    }




    public void OK2() {

        NomUtilisateur = findViewById(R.id.tNomUtilisateur);
        MotDePasse = findViewById(R.id.tmotdepasse);
        Confirmation = findViewById(R.id.tconfirmation);
        nom=findViewById(R.id.tnom2);
        prenom=findViewById(R.id.tprenom2);
        lieuNaissance=findViewById(R.id.tlieuNaissance);

        if (NomUtilisateur.length() == 0 || MotDePasse.length() == 0 || Confirmation.length() == 0
         || nom.length() == 0 || prenom.length() == 0|| dateNaissance.length() == 0|| lieuNaissance.length() == 0 )
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
            alert.setTitle("Attention !");
            alert.setMessage("Vous devez remplir tous les champs ");
            alert.setPositiveButton("OK", null);

            alert.show();
        }

         else{

               if(!(MotDePasse.getText().toString().equals(Confirmation.getText().toString())))
            {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
                alert.setTitle("Attention  !");
                alert.setMessage("les mots de passe doivent être identiques");
                alert.setNeutralButton("OK", null);
                alert.show();

            }

            else
            {

                new log().execute();

            }
        }
        }

    class log extends AsyncTask<String,String,String>

    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog=new ProgressDialog(MainActivity2.this);
            dialog.setMessage("Patientez SVP");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map=new HashMap<>();

            map.put("nom",nom.getText().toString());
            map.put("prenom",prenom.getText().toString());
            map.put("dateNaissance",dateNaissance.getText().toString());
            map.put("lieuNaissance",lieuNaissance.getText().toString());
            map.put("login",NomUtilisateur.getText().toString());
            map.put("password",MotDePasse.getText().toString());

            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/GererAbsence/Parent/chercheParent.php","GET",map);

            try {
                success=object.getInt("success");
            } catch (JSONException e) {
                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.cancel();

            if (success==1) {
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity2.this);
                alert.setMessage("Utilisateur existe déja .Réessayer");
                alert.setNeutralButton("OK",null);
                alert.show();

            }

            else
            {
                new Add().execute();


            }
        }
    }

    class Add extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(MainActivity2.this);
            dialog.setMessage("Patientez svp");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map=new HashMap<String,String>();
            map.put("nom",nom.getText().toString());
            map.put("prenom",prenom.getText().toString());
            map.put("dateNaissance",dateNaissance.getText().toString());
            map.put("lieuNaissance",lieuNaissance.getText().toString());
            map.put("login",NomUtilisateur.getText().toString());
            map.put("password",MotDePasse.getText().toString());


            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/GererAbsence/Parent/addParent.php","GET",map);

            try {
                success=object.getInt("success");
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


                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity2.this);
                alert.setTitle("Confirmation");
                alert.setMessage("Inscription réussie ! ");
                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent t = new Intent(MainActivity2.this, MainActivity3.class);
                        startActivity(t);
                        finish();
                    }
                });
                alert.show();
            }

            else
            {
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity2.this);
                alert.setTitle("Attention  !");
                alert.setMessage("Echec !!!!!");
                alert.setNeutralButton("OK",null);
                alert.show();
            }

        }
    }



    public void Annuler2(){

        Intent i = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    }


