package com.example.suiviparentaldabsence;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity3 extends AppCompatActivity {

    EditText login,pass;
    Button b, b1,b2;
    ProgressDialog dialog;
    JSONParser parser=new JSONParser();
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        login=findViewById(R.id.login);
        pass=findViewById(R.id.pass);
        b=findViewById(R.id.bOK3);
        b1=findViewById(R.id.bAnnuler3);
        b2=findViewById(R.id.bOublier);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  OK3();

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Annuler3();

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Oublier();
            }
        });

    }


    public void OK3(){


        if (login.length()==0|pass.length()==0)
        {
            AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity3.this) ;
            alert.setTitle("Attention !");
            alert.setMessage("Vous devez remplir tout les champs ");
            alert.setPositiveButton("OK", null);

            alert.show();

        }
        else {
            new log().execute();
        }

    }

    class log extends AsyncTask<String,String,String>

    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog=new ProgressDialog(MainActivity3.this);
            dialog.setMessage("Patientez SVP");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map=new HashMap<>();

            map.put("login",login.getText().toString());
            map.put("password",pass.getText().toString());

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

                Intent i = new Intent(MainActivity3.this, MainActivity5.class);
                startActivity(i);
                finish();
            }

            else
            {
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity3.this);
                alert.setMessage("Utilisateur n'existe pas");
                alert.setNeutralButton("ok",null);
                alert.show();

            }
        }
    }


    public void Oublier(){
        Intent i = new Intent(MainActivity3.this, MainActivity4.class);
        startActivity(i);
        finish();


    }
    public void Annuler3(){


        Intent i = new Intent(MainActivity3.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}