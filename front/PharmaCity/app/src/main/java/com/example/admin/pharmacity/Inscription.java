package com.example.admin.pharmacity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Inscription extends AppCompatActivity {
    EditText enom , eprenom , etel , elogin , epassword ,econfirmpwd, ecin , eadresse, email  ;
    String nom , cin , prenom , tel , mail , login , password, confirmpwd , adresse ;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        enom = (EditText)findViewById(R.id.editText8);
        eprenom = (EditText)findViewById(R.id.editText12);
        ecin = (EditText)findViewById(R.id.editText10);
        etel = (EditText)findViewById(R.id.editText14);
        email = (EditText)findViewById(R.id.editText11);
        eadresse = (EditText)findViewById(R.id.editText13);
        elogin = (EditText)findViewById(R.id.editText16);
        epassword = (EditText)findViewById(R.id.editText15);
        econfirmpwd = (EditText)findViewById(R.id.editText17);
        Button valider = (Button)findViewById(R.id.button15);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cin = ecin.getText().toString();
                nom = enom.getText().toString();
                prenom = eprenom.getText().toString();
                tel = etel.getText().toString();
                mail = email.getText().toString();
                adresse = eadresse.getText().toString();
                login = elogin.getText().toString();
                password = epassword.getText().toString();
                confirmpwd = econfirmpwd.getText().toString();

                if(cin.equals("") || nom.equals("") || prenom.equals("") || tel.equals("") || mail.equals("") || adresse.equals("") || login.equals("") || password.equals("")){
                    Toast.makeText(Inscription.this, "SVP remplir tous les champs !", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (cin.length() < 8 ) {

                    ecin.setError("Vérifiez votre numéro CIN !");
                    return;
                }
                if(!(mail.contains("@") && (mail.contains(".")))){
                  email.setError("Vérifiez votre Email !");
                    return;
                }
                if(!(password.equals(confirmpwd))){
                    epassword.setError(" les deux champs du mot de passe doivent être  identiques");
                    econfirmpwd.setError(" les deux champs du mot de passe doivent être  identiques");
                    return ;

                }
                ajoutProfilClient();

            }
        });
    }

    private void ajoutProfilClient() {
        class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = "http://10.0.3.2/pahrmacie_mobile/inscription.php";
            // ici attention loclahost ou bien 127.0.0.1 sa marche pas il faut mettre votre adrese ip  ou bien 10.0.3.2 (@ de genymotion)


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Inscription.this);
                pDialog.setMessage("Inscription en cours...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {
                String resultt  = null ;
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("IDD",cin));
                    params.add(new BasicNameValuePair("N",nom));
                    params.add(new BasicNameValuePair("P",prenom));
                    params.add(new BasicNameValuePair("T",tel));
                    params.add(new BasicNameValuePair("M",mail));
                    params.add(new BasicNameValuePair("A",adresse));
                    params.add(new BasicNameValuePair("L",login));
                    params.add(new BasicNameValuePair("PA",password));
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
                    Toast.makeText(Inscription.this, "Inscription effectuée avec succées", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Inscription.this, Authentification.class);
                    startActivity(i);
                }else {
                    Toast.makeText(Inscription.this, "Vérifiez votre n° CIN ou votre identifiant ", Toast.LENGTH_SHORT).show();
                    return ;
                }


            }

        }

        PostAsync la = new PostAsync();
        la.execute();
    }
}
