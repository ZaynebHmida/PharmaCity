package com.example.admin.pharmacity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Messages extends AppCompatActivity {
    static String messagechoisi;
    ListView listV;


    ArrayList<HashMap<String, Object>> messagelist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        listV = (ListView)findViewById(R.id.listView4);
        messagelist = new ArrayList<>();
        getDetailsMessage();
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, Object> map = (HashMap<String, Object>) listV.getItemAtPosition(position);
                String item = listV.getItemAtPosition(position).toString();
                // traitement a faire lrs de clique sur un symptome
                messagechoisi = map.get("ID").toString();
                Intent i = new Intent(Messages.this, AfficheMsg.class);
                startActivity(i);


            }
        });
    }

    private void getDetailsMessage() {
        class PostAsync extends AsyncTask<String, String, JSONObject> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = "http://10.0.3.2/pahrmacie_mobile/messagerecu.php";
            // ici attention loclahost ou bien 127.0.0.1 sa marche pas il faut mettre votre adrese ip  ou bien 10.0.3.2 (@ de genymotion)


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Messages.this);
                pDialog.setMessage("Chargement...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected JSONObject doInBackground(String... args0) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //params.add(new BasicNameValuePair("MOTTT", mot));
                params.add(new BasicNameValuePair("IDD", Authentification.cin_user));
                String result = jsonParser.makeHttpRequest(url_php, "POST", params);
                System.out.println("Response from url: " + result);
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        HashMap<String, Object> mapp = new HashMap<>();
                        mapp.put("NN", json_data.getString("sujet"));
                        mapp.put("AA", json_data.getString("message"));
                        mapp.put("DD", json_data.getString("date"));
                        mapp.put("ID", json_data.getString("id_msg"));

                        messagelist.add(mapp);
                    }
                } catch (final JSONException e) {
                    Log.e("error", "Json parsing error: " + e.getMessage());
                }

                return null;

            }

            protected void onPostExecute(JSONObject json) {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                SimpleAdapter adapter1 = new SimpleAdapter(Messages.this, messagelist,
                        R.layout.item_message, new String[]{"NN","AA","DD"},
                        new int[]{R.id.textView18, R.id.textView20, R.id.textView22});
                //adapter1.setViewBinder(new MyViewBinder());

                listV.setAdapter(adapter1);




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

                        Intent a = new Intent(Messages.this , Profil.class);
                        startActivity(a);

                        break;
                    case R.id.recherce:
                        Intent c = new Intent(Messages.this , Recherche.class);
                        startActivity(c);
                        break;
                    case R.id.symptome:
                        Intent g = new Intent(Messages.this , Symptomes.class);
                        startActivity(g);
                        break;
                    case R.id.panier:
                        Intent h = new Intent(Messages.this , Panier.class);
                        startActivity(h);
                        break;
                    case R.id.favoris:
                        Intent d = new Intent(Messages.this , Favoris.class);
                        startActivity(d);
                        break;
                    case R.id.notif:

                        Intent b = new Intent(Messages.this , Messages.class);
                        startActivity(b);

                        break;




                    case R.id.contact:
                        Intent f = new Intent(Messages.this , Contacter.class);
                        startActivity(f);
                        break;


                    case R.id.stat:
                        Intent i = new Intent(Messages.this , Statistique.class);
                        startActivity(i);
                        break;
                    case R.id.propos:
                        Intent j = new Intent(Messages.this , Apropos.class);
                        startActivity(j);
                        break;
                    case R.id.logout:
                        Intent k = new Intent(Messages.this , Authentification.class);
                        startActivity(k);
                        break;
                }
            }
        }).show();
    }
}
