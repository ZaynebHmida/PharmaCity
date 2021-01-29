package com.example.admin.pharmacity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class Panier extends AppCompatActivity {
    ListView lst ;
    SimpleAdapter adapter1 ;
    TextView total ;
    double somme = 0 ;
    String medicament, qte, clt ,  adresse, quantite_saisie  ;
    static Bitmap img_med ;
    String aqte, idmed ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        lst = (ListView) findViewById(R.id.listView5);
        total = (TextView) findViewById(R.id.textView51);
        Button add = (Button) findViewById(R.id.button22);
        Button delete = (Button) findViewById(R.id.button18);
        Button cmd = (Button) findViewById(R.id.button21);
        Button act = (Button) findViewById(R.id.button23);


        chargerList();

        for (int i = 0; i<Menu.panierUser.size(); i++){
            qte =  Menu.panierUser.get(i).get("QTE").toString()  ;
            System.out.println(" quantitee  : " + qte);
        }

        for (int i = 0; i < Menu.panierUser.size(); i++) {
            somme = somme +
                    Double.parseDouble(Menu.panierUser.get(i).get("PRIX").toString())
                    * Integer.parseInt(Menu.panierUser.get(i).get("QTE").toString());
            total.setText(String.valueOf(somme));
        }
        lst.setAdapter(adapter1);
        // action des bouton
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Panier.this, Recherche.class));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Panier.this);
                alert.setIcon(R.drawable.icon_logo);
                alert.setTitle("Suppression de la liste du panier ");
                alert.setMessage("Voulez vous vraiment supprimer  votre liste du panier ! ");
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Menu.panierUser.clear();
                        Intent i = new Intent(Panier.this, Panier.class);
                        startActivity(i);



                    }
                });
                alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();


            }
        });
        act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Panier.this, Panier.class);
                startActivity(i);

            }
        });

        cmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int n= Menu.panierUser.size();
                if(n==0){
                    Toast.makeText(Panier.this, "le panier est vide !", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialogAdresse();

            }
        });


        // action lors  de clique sur un medicament
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, Object> map = (HashMap<String, Object>) lst.getItemAtPosition(position);
               final String item = lst.getItemAtPosition(position).toString();

                aqte = map.get("QTE").toString();
                idmed = map.get("IPROD").toString();
                String tab[] = {"Modifier quantité ", "supprimer de la liste "};
                AlertDialog.Builder alert = new AlertDialog.Builder(Panier.this);
                alert.setItems(tab, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                openDialog();

                                // map.put("QTE", quantite_saisie);
                                chargerList();
                                break;
                            case 1:
                                AlertDialog.Builder alert = new AlertDialog.Builder(Panier.this);
                                alert.setIcon(R.drawable.icon_logo);
                                alert.setTitle("Suppression Panier");
                                alert.setMessage("Voulez vous vraiment supprimer ce médicament de votre panier ? ");
                                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Menu.panierUser.remove(map);
                                        chargerList();
                                        Intent i = new Intent(Panier.this, Panier.class);
                                        startActivity(i);


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
                                Toast.makeText(Panier.this, "Erreur", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.show();

            }
        });
    }




    private void openDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(Panier.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.quantite, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Panier.this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText equantiteaaa = (EditText) mView.findViewById(R.id.editText6);
        equantiteaaa.setText(aqte);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        quantite_saisie = equantiteaaa.getText().toString();
                        System.out.println("quantite_saisie      : " + quantite_saisie);
                        if (quantite_saisie.equals("")) {
                            equantiteaaa.setError("Saisir la quantite");
                            return;
                        }
                        // ajout au panier
                        for (int i = 0; i < Menu.panierUser.size(); i++) {
                            if (Menu.panierUser.get(i).get("IPROD").toString().equals(idmed)) {
                                HashMap<String, Object> map = new HashMap<>();
                                map = Menu.panierUser.get(i);
                                map.put("QTE", quantite_saisie);

                            }
                        }


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


    private void dialogAdresse() {
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(Panier.this);
            View mView = layoutInflaterAndroid.inflate(R.layout.adresse_commande, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Panier.this);
            alertDialogBuilderUserInput.setView(mView);

            final EditText eadr = (EditText) mView.findViewById(R.id.editText6);
            eadr.setText(Authentification.adresse);
            alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {



                            adresse = eadr.getText().toString();
                            if (adresse.equals("")) {
                                Toast.makeText(Panier.this, "SVP saisir l'adresse de livraison", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            for (int i = 0; i < Menu.panierUser.size(); i++) {
                                qte = Menu.panierUser.get(i).get("QTE").toString();
                                medicament = Menu.panierUser.get(i).get("IPROD").toString();

                                clt = Authentification.cin_user;

                                String neeeeeed = Menu.panierUser.get(i).get("ORDO").toString();
                                if (neeeeeed.equals("1")) {
                                    Toast.makeText(Panier.this, "Vous avez commandé un médicament rigoureux ! Vous devez envoyer une ordonnance", Toast.LENGTH_SHORT).show();

                                    Intent o = new Intent(Panier.this, Ordonnance.class);
                                    startActivity(o);

                                } else {
                                    sendCommande(adresse, medicament, clt, qte);
                                }
                            }


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


    private void sendCommande(final String adresse, final String medicament, final String clt ,final String qte) {
        class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = "http://10.0.3.2/pahrmacie_mobile/commander.php";
            // ici attention loclahost ou bien 127.0.0.1 sa marche pas il faut mettre votre adrese ip  ou bien 10.0.3.2 (@ de genymotion)


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Panier.this);
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
                    params.add(new BasicNameValuePair("IDCL", clt));
                    params.add(new BasicNameValuePair("QT",qte));
                    params.add(new BasicNameValuePair("ADR",adresse));
                    params.add(new BasicNameValuePair("IDMED", medicament));
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
                    Menu.panierUser.clear();
                    chargerList();
                    Toast.makeText(Panier.this, "Commande envoyée ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Panier.this, "Erreur d'envoie", Toast.LENGTH_SHORT).show();
                }


            }

        }

        PostAsync la = new PostAsync();
        la.execute();
    }

    private void chargerList() {
          adapter1 = new SimpleAdapter(Panier.this, Menu.panierUser,
                R.layout.item_medicament, new String[]{"IMG", "NPROD", "PRIX","QTE"},
                new int[]{R.id.imageView, R.id.textView7, R.id.textView9, R.id.textView8});
        adapter1.setViewBinder(new MyViewBinder());
    }

    public void openAndroidBottomMenu(View view) {

        new BottomSheet.Builder(this).title(" Menu PharmaCity ").sheet(R.menu.menu_pharmacie).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.profil:

                        Intent a = new Intent(Panier.this , Profil.class);
                        startActivity(a);

                        break;
                    case R.id.recherce:
                        Intent c = new Intent(Panier.this , Recherche.class);
                        startActivity(c);
                        break;
                    case R.id.symptome:
                        Intent g = new Intent(Panier.this , Symptomes.class);
                        startActivity(g);
                        break;
                    case R.id.panier:
                        Intent h = new Intent(Panier.this , Panier.class);
                        startActivity(h);
                        break;
                    case R.id.favoris:
                        Intent d = new Intent(Panier.this , Favoris.class);
                        startActivity(d);
                        break;
                    case R.id.notif:

                        Intent b = new Intent(Panier.this , Messages.class);
                        startActivity(b);

                        break;




                    case R.id.contact:
                        Intent f = new Intent(Panier.this , Contacter.class);
                        startActivity(f);
                        break;


                    case R.id.stat:
                        Intent i = new Intent(Panier.this , Statistique.class);
                        startActivity(i);
                        break;
                    case R.id.propos:
                        Intent j = new Intent(Panier.this , Apropos.class);
                        startActivity(j);
                        break;
                    case R.id.logout:
                        Intent k = new Intent(Panier.this , Authentification.class);
                        startActivity(k);
                        break;
                }
            }
        }).show();
    }


}
