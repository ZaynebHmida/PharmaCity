package com.example.admin.pharmacity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AfficheMsg extends AppCompatActivity {
    TextView msg,sujet,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_msg);
        msg = (TextView)findViewById(R.id.textView25);
        sujet = (TextView)findViewById(R.id.textView60);
        date = (TextView)findViewById(R.id.textView61);
        getMsg();
    }

    private void getMsg() {
        class PostAsync extends AsyncTask<String, String, JSONObject> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = "http://10.0.3.2/pahrmacie_mobile/detailsmessage.php";
            // ici attention loclahost ou bien 127.0.0.1 sa marche pas il faut mettre votre adrese ip  ou bien 10.0.3.2 (@ de genymotion)


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(AfficheMsg.this);
                pDialog.setMessage("Chargement...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected JSONObject doInBackground(String... args) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("IDM", Messages.messagechoisi));
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
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                System.out.println("json_data AAAA    : " + json);
                if (json != null) {
                    try {
                        // recuperation : json.getString("nom de champ de la base de données a recuperer");
                        msg.setText(json.getString("message"));
                        sujet.setText(json.getString("sujet"));
                        date.setText(json.getString("date"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

        }

        PostAsync la = new PostAsync();
        la.execute();
    }
}
