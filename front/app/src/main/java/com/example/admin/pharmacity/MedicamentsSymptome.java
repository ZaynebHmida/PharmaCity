package com.example.admin.pharmacity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MedicamentsSymptome extends AppCompatActivity {
    ListView listV ;
   static String med_choisi; ;
    Bitmap bitmap;
    static Bitmap bitmapMed;
    String quantite_saisie;
    String prixMed, nomMedicament, neddOrdo ;
    ArrayList<HashMap<String, Object>> medicamentList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicaments_symptome);
        listV = (ListView)findViewById(R.id.listView2);
        medicamentList = new ArrayList<>();
        getMedicamentBySymptome();
        // action lors de clique sur un item de la liste
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> map = (HashMap<String, Object>)listV.getItemAtPosition(position);
                // traitement a faire lrs de clique sur un symptome
                med_choisi = map.get("ID").toString();
                bitmapMed = (Bitmap)map.get("IMG");
                nomMedicament = map.get("NN").toString();
                prixMed = map.get("PPP").toString();
                neddOrdo = map.get("NEED").toString();
                String tab[] ={"Plus Détails","Ajouter au panier","Ajouter au favoris"};
                AlertDialog.Builder alert = new AlertDialog.Builder(MedicamentsSymptome.this);
                alert.setItems(tab, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position){
                            case 0 :  startActivity(new Intent(MedicamentsSymptome.this, Detailsmeddiag.class));  break ;
                            case 1 :  openDialog() ; break ;
                            case 2 :  addFavoris() ; break ;
                            default:
                                Toast.makeText(MedicamentsSymptome.this, "Erreur", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.show();

            }
        });

    }



    private void openDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MedicamentsSymptome.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.quantite, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MedicamentsSymptome.this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText equantite = (EditText) mView.findViewById(R.id.editText6);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        quantite_saisie = equantite.getText().toString();
                        if(quantite_saisie.equals("")){
                            equantite.setError("Saisir la quantite");
                            return ;
                        }
                        // ajout au panier
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("IMG", bitmapMed);
                        map.put("IPROD", med_choisi);
                        map.put("QTE", quantite_saisie);
                        map.put("NPROD", nomMedicament);
                        map.put("PRIX", prixMed);
                        map.put("ORDO", neddOrdo);
                        Menu.panierUser.add(map);
                    }
                })

                .setNegativeButton("Fermer",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

    }


    private void addFavoris() {
        class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = "http://10.0.3.2/pahrmacie_mobile/addFavoris.php";
            // ici attention loclahost ou bien 127.0.0.1 sa marche pas il faut mettre votre adrese ip  ou bien 10.0.3.2 (@ de genymotion)


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(MedicamentsSymptome.this);
                pDialog.setMessage("Ajout favoris...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {
                String resultt  = null ;
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("IDD", Authentification.cin_user));
                    params.add(new BasicNameValuePair("MEDDD",med_choisi));
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
                    Toast.makeText(MedicamentsSymptome.this, "Favoris ajoutée", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MedicamentsSymptome.this, "Erreur d'ajout favoris", Toast.LENGTH_SHORT).show();
                }


            }

        }

        PostAsync la = new PostAsync();
        la.execute();
    }

    private void getMedicamentBySymptome() {
        class GetMedicament extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private String URL_PHP = "http://10.0.3.2/pahrmacie_mobile/medicamentSymptome.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(MedicamentsSymptome.this);
                pDialog.setMessage("Loading ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... arg0) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("IDD", Symptomes.symptome_choisit));
                String result = jsonParser.makeHttpRequest(URL_PHP, "POST", params);
                System.out.println("Response from url: " + result);
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                       String nom_photo = json_data.getString("image");
                        String url = "http://10.0.3.2/pahrmacie_mobile/images/"+nom_photo;
                        Log.e("url", url);
                        try {
                               bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        HashMap<String, Object> mapp = new HashMap<>();
                        mapp.put("IMG", bitmap);
                        mapp.put("NN", json_data.getString("nom_med"));
                        mapp.put("PR", json_data.getString("prix")+" Dt");
                        mapp.put("PPP", json_data.getString("prix"));
                        mapp.put("FOR", json_data.getString("forme"));
                        mapp.put("ID", json_data.getString("id_med"));
                        mapp.put("NEED", json_data.getString("need_ord"));
                       medicamentList.add(mapp);
                    }
                } catch (final JSONException e) {
                    Log.e("error", "Json parsing error: " + e.getMessage());
                }

                return null;
            }

            protected void onPostExecute(String result) {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                SimpleAdapter adapter1 = new SimpleAdapter(MedicamentsSymptome.this, medicamentList,
                        R.layout.item_medicament, new String[]{"IMG", "NN", "PR","FOR"},
                        new int[]{R.id.imageView, R.id.textView7, R.id.textView9, R.id.textView8});
                adapter1.setViewBinder(new MyViewBinder());
                listV.setAdapter(adapter1);


            }


        }
        GetMedicament e=new GetMedicament();
        e.execute();
    }
}
