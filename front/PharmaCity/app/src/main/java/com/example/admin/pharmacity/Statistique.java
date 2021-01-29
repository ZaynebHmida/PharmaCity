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

public class Statistique extends AppCompatActivity {
    ListView listV ;
    Bitmap bitmap;
    String quantite_saisie;
    String adresse_saisie;
    static String favchoisi ;
    static Bitmap img_medfav ;
    ArrayList<HashMap<String, Object>> medList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);
        listV = (ListView)findViewById(R.id.listView3);
        medList = new ArrayList<>();
        getTopMedicaments();


    }





    private void getTopMedicaments() {
        class GetMedicament extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private String URL_PHP = "http://10.0.3.2/pahrmacie_mobile/statistiques.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Statistique.this);
                pDialog.setMessage("Chargement ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... arg0) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
               //params.add(new BasicNameValuePair("IDD", Authentification.cin_user));
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
                        mapp.put("NN", json_data.getString("nom_med")+"("+json_data.getString("nbr")+")");
                        mapp.put("PR", json_data.getString("prix")+" Dt");
                        mapp.put("FOR", json_data.getString("forme"));

                        medList.add(mapp);
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
                SimpleAdapter adapter1 = new SimpleAdapter(Statistique.this, medList,
                        R.layout.item_statistique, new String[]{"IMG", "NN", "PR","FOR"},
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

                        Intent a = new Intent(Statistique.this , Profil.class);
                        startActivity(a);

                        break;
                    case R.id.recherce:
                        Intent b = new Intent(Statistique.this , Recherche.class);
                        startActivity(b);
                        break;
                    case R.id.symptome:
                        Intent f = new Intent(Statistique.this , Symptomes.class);
                        startActivity(f);
                        break;
                    case R.id.panier:
                        Intent l = new Intent(Statistique.this , Panier.class);
                        startActivity(l);
                        break;
                    case R.id.favoris:
                        Intent c = new Intent(Statistique.this , Favoris.class);
                        startActivity(c);
                        break;
                    case R.id.notif:

                        Intent k = new Intent(Statistique.this , Messages.class);
                        startActivity(k);

                        break;




                    case R.id.contact:
                        Intent e = new Intent(Statistique.this , Contacter.class);
                        startActivity(e);
                        break;


                    case R.id.stat:
                        Intent j = new Intent(Statistique.this , Statistique.class);
                        startActivity(j);
                        break;
                    case R.id.propos:
                        Intent g = new Intent(Statistique.this , Apropos.class);
                        startActivity(g);
                        break;
                    case R.id.logout:
                        Intent h = new Intent(Statistique.this , Authentification.class);
                        startActivity(h);
                        break;
                }
            }
        }).show();
    }
}
