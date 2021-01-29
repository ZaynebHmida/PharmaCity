package com.example.admin.pharmacity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.auth.AUTH;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Authentification extends AppCompatActivity {
    EditText log;
    EditText mdp;
    String login, psw ;
    static  String cin_user ,adresse;
    LinearLayout coordinatorLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        coordinatorLayout = (LinearLayout)findViewById(R.id.layouta);
        log = (EditText)findViewById(R.id.editText);
        mdp = (EditText)findViewById(R.id.editText2);
        Button connecter = (Button)findViewById(R.id.button);
        Button nouvcompte = (Button)findViewById(R.id.button13);
        nouvcompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Authentification.this , Inscription.class);
                startActivity(i);
            }
        });
        connecter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // recupration de contenue des champs login et  password
                  login = log.getText().toString();
                  psw = mdp.getText().toString();

                if (login.equals("")) {

                    log.setError("Remplir ce champ !");

                    return;
                }
               if(psw.equals("")){
                   mdp.setError("Remplir ce champ !");
                   return;
               }
               /* if (login.length() < 8) {

                    log.setError("numéro invalid");
                    return;
                }*/

                // verification de l'existance de client dan la base de données
                verifClient();


            }
        });
    }

    private void verifClient() {
        class PostAsync extends AsyncTask<String, String, JSONObject> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = "http://10.0.3.2/pahrmacie_mobile/authentification.php";
            // ici attention loclahost ou bien 127.0.0.1 sa marche pas il faut mettre votre adrese ip  ou bien 10.0.3.2 (@ de genymotion)


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Authentification.this);
                pDialog.setMessage("Connexion...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected JSONObject doInBackground(String... args) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("LOGINNNN", login));
                    params.add(new BasicNameValuePair("PASSSS", psw));
                    String resultt = jsonParser.makeHttpRequest( url_php, "POST", params);
                    JSONArray jArray = new JSONArray(resultt);
                    JSONObject json_data = jArray.getJSONObject(0);
                    if (json_data != null) {
                        Log.d("JSON result", jArray.toString());
                        System.out.println("json_data     : " + json_data.toString());
                        return json_data;
                    }
                } catch (Exception e) {
                    Snackbar snackbar = Snackbar  .make(coordinatorLayout, "SVP verifier vote login et password", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    e.printStackTrace();
               }
                return null;
            }

            protected void onPostExecute(JSONObject json) {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                System.out.println("json_data AAAA    : " + json);
                if (json != null) {
                    try {
                         // recuperation : json.getString("nom de champ de la base de données a recuperer");
                        String nomclt = json.getString("nomCl");
                        cin_user = json.getString("cin_client");
                        adresse=json.getString("ad");
                        Intent o = new Intent(Authentification.this, Menu.class);
                        startActivity(o);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                


            }

        }

        PostAsync la = new PostAsync();
        la.execute();
// NB : n'oublier pas d'ajouter le permission d'internet c'est obligatoire pour que la connexion a la base de données sera effcetuée

    }
}
