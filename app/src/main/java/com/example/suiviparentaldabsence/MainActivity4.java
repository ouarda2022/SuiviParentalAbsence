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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.HashMap;

public class MainActivity4 extends AppCompatActivity {


    EditText name, password, confirmation;
    Button bt1,bt2;
    ProgressDialog dialog;
    JSONParser parser=new JSONParser();
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        confirmation = findViewById(R.id.confirmation);
        bt1 = findViewById(R.id.bOK4);
        bt2 = findViewById(R.id.bAnnuler4);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OK4();
            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Annuler4();
            }
        });

    }


        public void OK4 () {


            if (name.length() == 0 || password.length() == 0 || confirmation.length() == 0) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity4.this);
                alert.setTitle("Attention !");
                alert.setMessage("Vous devez remplir tout les champs ");
                alert.setPositiveButton("OK", null);

                alert.show();
            } else {

                if (!(password.getText().toString().equals(confirmation.getText().toString()))) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity4.this);
                    alert.setTitle("Attention  !");
                    alert.setMessage("les mots de passe doivent être identiques");
                    alert.setNeutralButton("ok", null);
                    alert.show();

                } else {

                    new logg().execute();

                }
            }

        }

        class logg extends AsyncTask<String,String,String>

        {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                dialog=new ProgressDialog(MainActivity4.this);
                dialog.setMessage("Patientez SVP");
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {

                HashMap<String,String> map=new HashMap<>();

                map.put("login",name.getText().toString());
                map.put("password",password.getText().toString());

                JSONObject object=parser.makeHttpRequest("http://10.0.2.2/GererAbsence/Parent/login.php","GET",map);

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
                    AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity4.this);
                    alert.setMessage("Utilisateur existe déja .Réessayer");
                    alert.setNeutralButton("ok",null);
                    alert.show();



                }

                else
                {
                    new Modifier().execute();

                    Intent i = new Intent(MainActivity4.this, MainActivity3.class);
                    startActivity(i);
                    finish();


                }
            }
        }





        class Modifier extends AsyncTask<String,String,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                dialog=new ProgressDialog(MainActivity4.this);
                dialog.setMessage("Patientez SVP");
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {

                HashMap<String,String> map=new HashMap<>();

                map.put("login",name.getText().toString());
                map.put("password",password.getText().toString());

                JSONObject object=parser.makeHttpRequest("http://10.0.2.2/GererAbsence/Parent/update.php","GET",map);

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

                if (success==1)
                {
                    Toast.makeText(MainActivity4.this, "La modification a  été enregistrée",
                            Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(MainActivity4.this, "Echec !!!!!", Toast.LENGTH_SHORT).show();
                }

            }
        }


   public void Annuler4(){
       Intent i = new Intent(MainActivity4.this, MainActivity3.class);
       startActivity(i);
       finish();
   }
}

