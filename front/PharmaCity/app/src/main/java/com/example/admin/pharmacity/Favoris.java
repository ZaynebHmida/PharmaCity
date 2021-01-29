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

import com.cocosw.bottomsheet.BottomSheet;

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

public class Favoris extends AppCompatActivity {
    ListView listV ;
    Bitmap bitmap;
    String prixMed , nomMedicament, quantite_sasie, medicament_choisit, img_med ;
    static String favchoisi , mled_choist, neddOrdo  ;
    static Bitmap img_medfav ;
    ArrayList<HashMap<String, Object>> favorisList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
        listV = (ListView)findViewById(R.id.listView3);
        favorisList = new ArrayList<>();
        getMedicamentByFavoris();
        // action lors de clique sur un item de la liste
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = listV.getItemAtPosition(position).toString();
                final HashMap<String, Object> map = (HashMap<String, Object>) listV.getItemAtPosition(position);
                nomMedicament = map.get("NN").toString();
                prixMed = map.get("PPP").toString();
                favchoisi = map.get("ID").toString();
                mled_choist = map.get("IDM").toString();
                img_medfav = (Bitmap) map.get("IMG");
                neddOrdo = map.get("NEED").toString();
                String tab[] = {"Plus Détails", "Ajouter au panier", " supprimer "};
                AlertDialog.Builder alert = new AlertDialog.Builder(Favoris.this);
                alert.setItems(tab, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                startActivity(new Intent(Favoris.this, DetailsmedFav.class));
                                break;
                            case 1:
                                openDialog();
                                break;
                            case 2:
                                AlertDialog.Builder alert = new AlertDialog.Builder(Favoris.this);
                                alert.setIcon(R.drawable.icon_logo);
                                alert.setTitle("Suppression Favoris");
                                alert.setMessage("Voulez vous vraiment supprimer ce médicament de votre liste favoris ! ");
                                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        supprfav();

                                    }
                                });
                                alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alert.show();

                                break;
                            default:
                                Toast.makeText(Favoris.this, "Erreur", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.show();

            }
        });

    }

    private void openDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(Favoris.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.quantite, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Favoris.this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText equantite = (EditText) mView.findViewById(R.id.editText6);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        quantite_sasie = equantite.getText().toString();
                        if(quantite_sasie.equals("")){
                            equantite.setError("Saisir la quantite");
                            return ;
                        }
                        // ajout au panier
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("IMG", img_medfav);
                        map.put("IPROD", mled_choist);
                        map.put("QTE", quantite_sasie);
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



    private void supprfav() {
        class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = "http://10.0.3.2/pahrmacie_mobile/supprfav.php";
            // ici attention loclahost ou bien 127.0.0.1 sa marche pas il faut mettre votre adrese ip  ou bien 10.0.3.2 (@ de genymotion)


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Favoris.this);
                pDialog.setMessage("Suppression en cours...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {
                String resultt  = null ;
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("IDFAV", favchoisi));
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
                    favorisList.clear();
                    getMedicamentByFavoris();
                    Toast.makeText(Favoris.this, "suppression effectuée ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Favoris.this, "Erreur de suppression", Toast.LENGTH_SHORT).show();
                }


            }

        }

        PostAsync la = new PostAsync();
        la.execute();
    }

    private void getMedicamentByFavoris() {
        class GetMedicament extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private String URL_PHP = "http://10.0.3.2/pahrmacie_mobile/favoris.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Favoris.this);
                pDialog.setMessage("Chargement ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... arg0) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("IDD", Authentification.cin_user));
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
                        mapp.put("ID", json_data.getString("id_favoris"));
                        mapp.put("IDM", json_data.getString("id_med"));
                        mapp.put("NEED", json_data.getString("need_ord"));
                        favorisList.add(mapp);
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
               SimpleAdapter adapter1 = new SimpleAdapter(Favoris.this, favorisList,
                        R.layout.item_medicament, new String[]{"IMG", "NN", "PR","FOR"},
                        new int[]{R.id.imageView, R.id.textView7, R.id.textView9, R.id.textView8});
                adapter1.setViewBinder(new MyViewBinder());
                listV.setAdapter(adapter1);


            }


        }
        GetMedicament e=new GetMedicament();
        e.execute();
    }
    public void openAndroidBottomMenu(View view) {

        new BottomSheet.Builder(this).title(" Menu PharmaCity ").sheet(R.menu.menu_pharmacie).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.profil:

                        Intent a = new Intent(Favoris.this , Profil.class);
                        startActivity(a);

                        break;
                    case R.id.recherce:
                        Intent c = new Intent(Favoris.this , Recherche.class);
                        startActivity(c);
                        break;

                    case R.id.symptome:
                        Intent g = new Intent(Favoris.this , Symptomes.class);
                        startActivity(g);
                        break;
                    case R.id.panier:
                        Intent h = new Intent(Favoris.this , Panier.class);
                        startActivity(h);
                        break;
                    case R.id.favoris:
                        Intent d = new Intent(Favoris.this , Favoris.class);
                        startActivity(d);
                        break;
                    case R.id.notif:

                        Intent b = new Intent(Favoris.this , Messages.class);
                        startActivity(b);

                        break;
                    case R.id.contact:
                        Intent f = new Intent(Favoris.this , Contacter.class);
                        startActivity(f);
                        break;






                    case R.id.stat:
                        Intent i = new Intent(Favoris.this , Statistique.class);
                        startActivity(i);
                        break;
                    case R.id.propos:
                        Intent j = new Intent(Favoris.this , Apropos.class);
                        startActivity(j);
                        break;
                    case R.id.logout:
                        Intent k = new Intent(Favoris.this , Authentification.class);
                        startActivity(k);
                        break;
                }
            }
        }).show();
    }
}
