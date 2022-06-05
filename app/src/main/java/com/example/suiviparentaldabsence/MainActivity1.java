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

import java.util.HashMap;

public class MainActivity1 extends AppCompatActivity {


    EditText N_inscription, id_parent;
    TextView nom, prenom, dateNaissance, lieuNaissance, classe;
    Button But1, But2, But3, bid;
    ProgressDialog dialog;
    JSONParser parser = new JSONParser();
    int success;
    String N_inscriptionE, nomE, prenomE, dateNaissanceE, lieuNaissanceE, classeE;
    HashMap<String,String> map;
    Params p=new Params();
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        N_inscription = findViewById(R.id.N_inscription);
        id_parent = findViewById(R.id.idParent);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        dateNaissance = findViewById(R.id.dateNaissance);
        lieuNaissance = findViewById(R.id.lieuNaissance);
        classe = findViewById(R.id.classe);
        But1 = findViewById(R.id.bOK1);
        But2 = findViewById(R.id.bAnnuler1);
        But3 = findViewById(R.id.Continuer);
        bid = findViewById(R.id.bid_parent);



        Bundle extras=getIntent().getExtras();
        if (extras!=null){

            id=extras.getString("idparent");


            map=new HashMap<String,String>();
            map.put("id_parent",id);


            p.values.add(map);

        }


        bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retourId();
            }
        });



            But1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OK1();


                }
            });


            But2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Annuler1();
                }
            });

            But3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (N_inscription.length() == 0 || id_parent.length() == 0) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);
                        alert.setTitle("Attention !");
                        alert.setMessage("Vous devez taper le numéro d'inscription de votre enfant et l'identifiant du parent ! ");
                        alert.setPositiveButton("OK", null);

                        alert.show();

                    } else {
                        if (nom.length() == 0 && prenom.length() == 0 && dateNaissance.length() == 0
                                && lieuNaissance.length() == 0 && classe.length() == 0) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);
                            alert.setTitle("Attention !");
                            alert.setMessage("Vous devez cliquez le bouton OK , puis continuer ! ");
                            alert.setPositiveButton("OK", null);

                            alert.show();
                        } else {

                            Intent i = new Intent(getApplicationContext(), MainActivity6.class);
                            i.putExtra("N_inscription", N_inscription.getText().toString());
                            i.putExtra("nom", nom.getText().toString());
                            i.putExtra("prenom", prenom.getText().toString());
                            i.putExtra("classe", classe.getText().toString());
                            startActivity(i);
                            finish();
                        }
                    }

                }
            });

        }

        public void retourId () {
            Intent i = new Intent(MainActivity1.this, MainActivity9.class);
            startActivity(i);
            finish();
        }
        public void OK1 () {

            if (N_inscription.length() == 0 || id_parent.length() == 0) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);
                alert.setTitle("Attention !");
                alert.setMessage("Vous devez taper le numéro d'inscription de votre enfant et l'identifiant du parent ! ");
                alert.setPositiveButton("OK", null);

                alert.show();

            } else {

                new Chercher().execute();
            }

        }

        class Chercher extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                dialog = new ProgressDialog(MainActivity1.this);
                dialog.setMessage("Patientez SVP");
                dialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {

                HashMap<String, String> map = new HashMap<String, String>();

                map.put("N_inscription", N_inscription.getText().toString());
                map.put("id_parent", id_parent.getText().toString());


                JSONObject object = parser.makeHttpRequest("http://10.0.2.2/GererAbsence/Eleve/ChercheEleve.php", "GET", map);

                try {
                    success = object.getInt("success");
                    if (success == 1) {

                        JSONArray eleve = object.getJSONArray("eleve");
                        JSONObject o = eleve.getJSONObject(0);
                        N_inscriptionE = o.getString("N_inscription");
                        nomE = o.getString("nom");
                        prenomE = o.getString("prenom");
                        dateNaissanceE = o.getString("dateNaissance");
                        lieuNaissanceE = o.getString("lieuNaissance");
                        classeE = o.getString("classe");


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

                if (success == 1) {

                    nom.setText(nomE);
                    prenom.setText(prenomE);
                    dateNaissance.setText(dateNaissanceE);
                    lieuNaissance.setText(lieuNaissanceE);
                    classe.setText(classeE);

                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);
                    alert.setTitle("Attention  !");
                    alert.setMessage("Elève n'existe pas.Réessayez");
                    alert.setNeutralButton("ok", null);
                    alert.show();
                }


            }
        }

        public void Annuler1 () {
            Intent i = new Intent(MainActivity1.this, MainActivity5.class);
            startActivity(i);
            finish();

        }
    }
