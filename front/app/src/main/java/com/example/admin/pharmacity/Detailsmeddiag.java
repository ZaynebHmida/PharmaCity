package com.example.admin.pharmacity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Detailsmeddiag extends AppCompatActivity {
    TextView ref, nom , prix , lib , form ;

    ImageView img ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsmeddiag);
        ref = (TextView)findViewById(R.id.textView30);
        nom = (TextView)findViewById(R.id.textView31);
        prix = (TextView)findViewById(R.id.textView32);
        lib = (TextView)findViewById(R.id.textView33);
        form = (TextView)findViewById(R.id.textView34);
        img = (ImageView)findViewById(R.id.imageView4);
       img.setImageBitmap(MedicamentsSymptome.bitmapMed);
        getDetailsMedicament();
    }

    private void getDetailsMedicament() {
        class PostAsync extends AsyncTask<String, String, JSONObject> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = "http://10.0.3.2/pahrmacie_mobile/detailsMedicament.php";
            // ici attention loclahost ou bien 127.0.0.1 sa marche pas il faut mettre votre adrese ip  ou bien 10.0.3.2 (@ de genymotion)


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Detailsmeddiag.this);
                pDialog.setMessage("Chargement...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected JSONObject doInBackground(String... args) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("IDD", MedicamentsSymptome.med_choisi));
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
                        ref.setText(json.getString("ref"));
                        nom.setText(json.getString("nom_med"));
                        lib.setText(json.getString("lib"));
                        prix.setText(json.getString("prix")+" Dt");
                        form.setText(json.getString("forme"));

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
