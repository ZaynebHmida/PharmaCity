package com.example.admin.pharmacity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cocosw.bottomsheet.BottomSheet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Menu extends AppCompatActivity {
    static ArrayList<HashMap<String, Object>> panierUser = new ArrayList<>() ;
    int jour ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button profil = (Button)findViewById(R.id.button5);
        Button rech = (Button)findViewById(R.id.button6);
        Button fav = (Button)findViewById(R.id.button8);
        Button contact = (Button)findViewById(R.id.button9);
        Button symp = (Button)findViewById(R.id.button7);
        Button apropos=(Button)findViewById(R.id.button10);
        Button statistique=(Button)findViewById(R.id.button12);
        Button notif=(Button)findViewById(R.id.button17);
        Button panier=(Button)findViewById(R.id.button19);


        Calendar cal = Calendar.getInstance();
        jour = cal.get(Calendar.DAY_OF_MONTH);
        verifAlert();

        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(Menu.this , Panier.class);
                startActivity(o);
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(Menu.this , Profil.class);
                startActivity(o);
            }
        });



        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(Menu.this , Messages.class);
                startActivity(o);
            }
        });

        rech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(Menu.this , Recherche.class);
                startActivity(o);
            }
        });


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(Menu.this , Favoris.class);
                startActivity(o);
            }
        });


        symp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(Menu.this , Symptomes.class);
                startActivity(o);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(Menu.this , Contacter.class);
                startActivity(o);
            }
        });
        apropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(Menu.this, Apropos.class);
                startActivity(o);
            }
        });
        statistique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(Menu.this, Statistique.class);
                startActivity(o);
            }
        });

    }

    private void verifAlert() {
        class PostAsync extends AsyncTask<String, String, JSONObject> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = "http://10.0.3.2/pahrmacie_mobile/verification.php";
            // ici attention loclahost ou bien 127.0.0.1 sa marche pas il faut mettre votre adrese ip  ou bien 10.0.3.2 (@ de genymotion)


            @Override
            protected void onPreExecute() {
            }

            @Override
            protected JSONObject doInBackground(String... args) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("IDC", Authentification.cin_user));
                    String resultt = jsonParser.makeHttpRequest( url_php, "POST", params);
                    JSONArray jArray = new JSONArray(resultt);
                    JSONObject json_data = jArray.getJSONObject(0);
                    if (json_data != null) {
                        Log.d("JSON result", jArray.toString());
                        System.out.println("json_data     : " + json_data.toString());
                        return json_data;
                    }
                } catch (Exception e) {
                 e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(JSONObject json) {

                System.out.println("json_data AAAA    : " + json);
                if (json != null) {
                    try {
                        int nombre = json.getInt("nbr");
                        if(nombre>0){
                            Intent intent = new Intent();
                            PendingIntent pIntent = PendingIntent.getActivity(Menu.this, 0, intent, 0);
                            Notification noti = new Notification.Builder(Menu.this)
                                    .setTicker("PharmaCity")
                                    .setContentTitle("PharmaCity")
                                    .setContentText("Vous avez un médicament ponctuel ! Tu dois le commander !")
                                    .setSmallIcon(R.drawable.img_notif)
                                    .setContentIntent(pIntent).getNotification();
                            noti.flags=Notification.FLAG_AUTO_CANCEL;
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(0, noti);
                        }
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
