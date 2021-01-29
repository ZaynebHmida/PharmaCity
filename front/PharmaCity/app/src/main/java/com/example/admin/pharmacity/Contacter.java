package com.example.admin.pharmacity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Contacter extends AppCompatActivity {
    Spinner sp ;
    EditText msg ;
    String sujet , message ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacter);
          sp = (Spinner)findViewById(R.id.spinner);
          msg = (EditText)findViewById(R.id.editText9);
         Button send = (Button)findViewById(R.id.button3);
        String tab []= {"Avis","Comment commander","Expédition","Ordonnance ","Produits" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tab);
        sp.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // recuperation
                sujet = sp.getSelectedItem().toString();
                message = msg.getText().toString();
                // verification si champs de message est vide
                if(message.equals("")){
                    msg.setError("SVP saisir votre message");
                    return ;
                }
                // insertion de mssage dans la base de données
                 sendMessage();
            }
        });
    }

    private void sendMessage() {
        class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = "http://10.0.3.2/pahrmacie_mobile/contact.php";
            // ici attention loclahost ou bien 127.0.0.1 sa marche pas il faut mettre votre adrese ip  ou bien 10.0.3.2 (@ de genymotion)


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Contacter.this);
                pDialog.setMessage("Envoi en cours...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {
                String resultt  = null ;
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("T", Authentification.cin_user));
                    params.add(new BasicNameValuePair("N",sujet));
                    params.add(new BasicNameValuePair("P",message));
                    resultt = jsonParser.makeHttpRequest(url_php, "POST", params);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return resultt;
            }

            protected void onPostExecute(String res) {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                System.out.println("json_data AAAA    : " + res);
                if (res.contains("ok")) {
                    Toast.makeText(Contacter.this, "Message envoyée avec succées", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Contacter.this, "Erreur d'envoie", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(Contacter.this, Menu.class);
                startActivity(i);


            }

        }

        PostAsync la = new PostAsync();
        la.execute();
    }
    public void openAndroidBottomMenu(View view) {

        new BottomSheet.Builder(this).title(" Menu PharmaCity ").sheet(R.menu.menu_pharmacie).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.profil:

                        Intent a = new Intent(Contacter.this , Profil.class);
                        startActivity(a);

                        break;
                    case R.id.recherce:
                        Intent c = new Intent(Contacter.this , Recherche.class);
                        startActivity(c);
                        break;
                    case R.id.symptome:
                        Intent g = new Intent(Contacter.this , Symptomes.class);
                        startActivity(g);
                        break;
                    case R.id.panier:
                        Intent h = new Intent(Contacter.this , Panier.class);
                        startActivity(h);
                        break;
                    case R.id.favoris:
                        Intent d = new Intent(Contacter.this , Favoris.class);
                        startActivity(d);
                        break;
                    case R.id.notif:

                        Intent b = new Intent(Contacter.this , Messages.class);
                        startActivity(b);

                        break;




                    case R.id.contact:
                        Intent f = new Intent(Contacter.this , Contacter.class);
                        startActivity(f);
                        break;


                    case R.id.stat:
                        Intent i = new Intent(Contacter.this , Statistique.class);
                        startActivity(i);
                        break;
                    case R.id.propos:
                        Intent j = new Intent(Contacter.this , Apropos.class);
                        startActivity(j);
                        break;
                    case R.id.logout:
                        Intent k = new Intent(Contacter.this , Authentification.class);
                        startActivity(k);
                        break;
                }
            }
        }).show();
    }
}
