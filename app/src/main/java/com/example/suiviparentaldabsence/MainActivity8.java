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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainActivity8 extends AppCompatActivity {

    EditText N_inscription,nom,prenom,classe,date;
    Button consul,supp,bdate,bsortir,bretour,bcalender;
    int success ,position;
    Params p=new Params();
    ProgressDialog dialog;
    JSONParser parser=new JSONParser();
    AlertDialog d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        N_inscription=findViewById(R.id.N_inscription8);
        nom=findViewById(R.id.nom8);
        prenom=findViewById(R.id.prenom8);
        classe=findViewById(R.id.classe8);
        date=findViewById(R.id.tDate);

        bdate=findViewById(R.id.bDate);
        consul=findViewById(R.id.bconsulter);
        supp=findViewById(R.id.bsupprimer);
        bsortir=findViewById(R.id.bSortir8);
        bretour=findViewById(R.id.bAnnuler8);
        bcalender=findViewById(R.id.bcalender);


        Date curentDate=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(curentDate);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        date.setText(sdf.format(curentDate));

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            position=extras.getInt("position");

        }

        HashMap<String,String> m=p.values.get(position);
        N_inscription.setText(m.get("N_inscription"));
        nom.setText(m.get("nom"));
        prenom.setText(m.get("prenom"));
        classe.setText(m.get("classe"));





        bcalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Afficher();
            }
        });


        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                consulter();

            }
        });



        consul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MainActivity7.class);
                i.putExtra("N_inscription",N_inscription.getText().toString());
                startActivity(i);
                finish();

            }
        });

        supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity8.this);
                alert.setTitle("Attention !");
                alert.setMessage("Voulez-vous vraiment supprimer cet élève de la liste à consulter");
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        p.values.remove(position);
                        Intent t=new Intent(getApplicationContext(),MainActivity6.class);
                        startActivity(t);
                        finish();
                    }
                });


                alert.setNegativeButton("Non",null);
                alert.show();



            }
        });

        bsortir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        bretour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity8.this, MainActivity6.class);
                startActivity(i);
                finish();

            }
        });

    }


    public void Afficher(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View vpopDate=getLayoutInflater().inflate(R.layout.popcalender,null);
        CalendarView cvDate=vpopDate.findViewById(R.id.calendarView);
        Button bvalider=vpopDate.findViewById(R.id.bvalider);
        cvDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                Date d;
                try {
                    d=sdf.parse(String.valueOf(i)+"-"+String.valueOf(i1+1)+"-"+String.valueOf(i2));
                    date.setText(sdf.format(d));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        bvalider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        builder.setView(vpopDate);
        d=builder.create();
        d.show();

    }

    public void consulter(){

        if (date.length()==0){
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity8.this);
            alert.setTitle("Attention !");
            alert.setMessage("Vous devez taper la date de jour");
            alert.setPositiveButton("OK", null);

            alert.show();
        }
        else {

            new ChercheDate().execute();
        }
    }


    class ChercheDate extends AsyncTask<String,String,String>

    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog=new ProgressDialog(MainActivity8.this);
            dialog.setMessage("Patientez SVP");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map=new HashMap<>();

            map.put("N_inscription",N_inscription.getText().toString());
            map.put("dateAbsence",date.getText().toString());


            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/GererAbsence/Absence/chercheDate.php","GET",map);

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
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity8.this);
                alert.setTitle("Confirmation");
                alert.setMessage("Eleve absent");
                alert.setNeutralButton("ok",null);
                alert.show();
     }

            else
            {
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity8.this);
                alert.setTitle("Confirmation");
                alert.setMessage("Eleve present");
                alert.setNeutralButton("ok",null);
                alert.show();

            }
        }
    }

}