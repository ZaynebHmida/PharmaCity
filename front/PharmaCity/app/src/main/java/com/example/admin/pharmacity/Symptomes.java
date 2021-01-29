package com.example.admin.pharmacity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class Symptomes extends AppCompatActivity {
    ListView listV ;
    static String symptome_choisit ;
    ArrayList<HashMap<String, Object>> symptomeList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptomes);
        listV = (ListView)findViewById(R.id.listView2);
        symptomeList = new ArrayList<>();
        getAllSymptome();
        // action lors de clique sur un item de la liste
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, Object> map = (HashMap<String, Object>) listV.getItemAtPosition(position);
                String item = listV.getItemAtPosition(position).toString();
                // traitement a faire lrs de clique sur un symptome
                symptome_choisit = map.get("ID").toString();
                Intent i = new Intent(Symptomes.this, MedicamentsSymptome.class);
                startActivity(i);
            }
        });

    }

    private void getAllSymptome() {
        class GetSymptomes extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private String URL_PHP = "http://10.0.3.2/pahrmacie_mobile/AllSymptomes.php";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Symptomes.this);
                pDialog.setMessage("Loading ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... arg0) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //params.add(new BasicNameValuePair("MOTTT", mot));

                String result = jsonParser.makeHttpRequest(URL_PHP, "POST", params);
                System.out.println("Response from url: " + result);
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                       /* String nom_photo = json_data.getString("image");
                        String url = "http://10.0.3.2/pahrmacie_mobile/images/"+nom_photo;
                        Log.e("url", url);
                        try {
                            bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                        HashMap<String, Object> mapp = new HashMap<>();
                       // mapp.put("IMG", bitmap);
                        mapp.put("NN", json_data.getString("nom"));
                        mapp.put("ID", json_data.getString("id_sym"));

                        symptomeList.add(mapp);
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
                SimpleAdapter adapter1 = new SimpleAdapter(Symptomes.this, symptomeList,
                        R.layout.item_symptome, new String[]{"NN"},
                        new int[]{R.id.textView10});
                //adapter1.setViewBinder(new MyViewBinder());
                listV.setAdapter(adapter1);


            }


        }
        GetSymptomes e=new GetSymptomes();
        e.execute();
    }
    public void openAndroidBottomMenu(View view) {

        new BottomSheet.Builder(this).title(" Menu PharmaCity ").sheet(R.menu.menu_pharmacie).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.profil:

                        Intent a = new Intent(Symptomes.this , Profil.class);
                        startActivity(a);

                        break;
                    case R.id.recherce:
                        Intent c = new Intent(Symptomes.this , Recherche.class);
                        startActivity(c);
                        break;
                    case R.id.symptome:
                        Intent g = new Intent(Symptomes.this , Symptomes.class);
                        startActivity(g);
                        break;
                    case R.id.panier:
                        Intent h = new Intent(Symptomes.this , Panier.class);
                        startActivity(h);
                        break;
                    case R.id.favoris:
                        Intent d = new Intent(Symptomes.this , Favoris.class);
                        startActivity(d);
                        break;
                    case R.id.notif:

                        Intent b = new Intent(Symptomes.this , Messages.class);
                        startActivity(b);

                        break;




                    case R.id.contact:
                        Intent f = new Intent(Symptomes.this , Contacter.class);
                        startActivity(f);
                        break;


                    case R.id.stat:
                        Intent i = new Intent(Symptomes.this , Statistique.class);
                        startActivity(i);
                        break;
                    case R.id.propos:
                        Intent j = new Intent(Symptomes.this , Apropos.class);
                        startActivity(j);
                        break;
                    case R.id.logout:
                        Intent k = new Intent(Symptomes.this , Authentification.class);
                        startActivity(k);
                        break;
                }
            }
        }).show();
    }
}
