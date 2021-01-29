package com.example.admin.pharmacity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profil extends AppCompatActivity {
    EditText enom , eprenom , etel , elogin , epassword , ecin ,econfirmpwd, eadresse, email  ;
    String nom , cin , prenom , tel , mail , login , password ,confirmpwd, adresse ;
    ImageView imgClt ;
    private static final int RESULT_SELECT_IMAGE = 1;
    String SERVER = "http://10.0.3.2/pahrmacie_mobile/editPhotoProfil.php", timestamp, filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        enom = (EditText)findViewById(R.id.EditText3);
        eprenom = (EditText)findViewById(R.id.EditText4);
        ecin = (EditText)findViewById(R.id.editText5);
        etel = (EditText)findViewById(R.id.EditText6);
        email = (EditText)findViewById(R.id.EditText5);
        eadresse = (EditText)findViewById(R.id.EditText7);
        elogin = (EditText)findViewById(R.id.editText3);
        epassword = (EditText)findViewById(R.id.editText4);
        econfirmpwd = (EditText)findViewById(R.id.editText18);
        Button edit = (Button)findViewById(R.id.button2);
        Button choose = (Button)findViewById(R.id.button20);
        imgClt = (ImageView)findViewById(R.id.imageView6);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();

            }
        });
        // recuperation de information de client connecté
        getInfoClient();
        // action de bouton modifier
        edit.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(Profil.this, "SVP remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return ;
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
                // modification dans la base de données
                editProfilClient();
                Bitmap image = ((BitmapDrawable) imgClt.getDrawable()).getBitmap();
                //execute the async task and upload the image to server
                if (filename != null) {
                    new Upload(image, filename).execute();
                } else {
                    Toast.makeText(Profil.this, "Chosoir une image SVP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void editProfilClient() {
        class PostAsync extends AsyncTask<String, String, String> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = "http://10.0.3.2/pahrmacie_mobile/editProfil.php";
            // ici attention loclahost ou bien 127.0.0.1 sa marche pas il faut mettre votre adrese ip  ou bien 10.0.3.2 (@ de genymotion)


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Profil.this);
                pDialog.setMessage("Modification...");
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
                    Toast.makeText(Profil.this, "Modification effectuée avec succées", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Profil.this, "Erreur de modification", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(Profil.this, Menu.class);
                startActivity(i);


            }

        }

        PostAsync la = new PostAsync();
        la.execute();
    }

    private void getInfoClient() {
        class PostAsync extends AsyncTask<String, String, JSONObject> {
            JSONParser jsonParser = new JSONParser();
            private ProgressDialog pDialog;
            private  String url_php = "http://10.0.3.2/pahrmacie_mobile/profil.php";
            // ici attention loclahost ou bien 127.0.0.1 sa marche pas il faut mettre votre adrese ip  ou bien 10.0.3.2 (@ de genymotion)


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Profil.this);
                pDialog.setMessage("Chargement...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected JSONObject doInBackground(String... args) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("IDD", Authentification.cin_user));
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
                        enom.setText(json.getString("nomCl"));
                        eprenom.setText(json.getString("prenomCl"));
                        etel.setText(json.getString("tel"));
                        ecin.setText(json.getString("CIN"));
                        elogin.setText(json.getString("login"));
                        epassword.setText(json.getString("pw"));
                        eadresse.setText(json.getString("ad"));
                        email.setText(json.getString("mail"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

        }

        PostAsync la = new PostAsync();
        la.execute();
    }




    //function to select a image
    private void selectImage() {
        //open album to select image
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallaryIntent, RESULT_SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            //set the selected image to image variable
            Uri image = data.getData();
            String filePath = getPath(image);
            filename = filePath.substring(filePath.lastIndexOf("/") + 1);
            //Toast.makeText(getActivity(), filePath+"\n hello : "+filename, Toast.LENGTH_SHORT).show();
            imgClt.setImageURI(image);

            //get the current timeStamp and strore that in the time Variable
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp = tsLong.toString();

            // Toast.makeText(getActivity(), timestamp, Toast.LENGTH_SHORT).show();
        }
    }

    private String hashMapToUrl(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private String getPath(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(Profil.this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    //async task to upload image
    private class Upload extends AsyncTask<Void, Void, String> {
        private Bitmap image;
        private String name;

        public Upload(Bitmap image, String name) {
            this.image = image;
            this.name = name;
        }

        @Override
        protected String doInBackground(Void... params) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //compress the image to jpg format
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            /*
            * encode image to base64 so that it can be picked by saveImage.php file
            * */
            String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            //generate hashMap to store encodedImage and the name
            HashMap<String, String> detail = new HashMap<>();

            detail.put("name", name);
            detail.put("ID", Authentification.cin_user);
            detail.put("image", encodeImage);

            try {
                //convert this HashMap to encodedUrl to send to php file
                String dataToSend = hashMapToUrl(detail);
                //make a Http request and send data to saveImage.php file
                String response = Request.post(SERVER, dataToSend);

                //return the response
                return response;

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Hello : ", "ERROR  " + e);
                return null;
            }
        }


        @Override
        protected void onPostExecute(String s) {
            //show image uploaded
            Toast.makeText(Profil.this, "Photo de profil changé ", Toast.LENGTH_SHORT).show();
        }
    }























    public void openAndroidBottomMenu(View view) {

        new BottomSheet.Builder(this).title(" Menu PharmaCity ").sheet(R.menu.menu_pharmacie).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.profil:

                        Intent a = new Intent(Profil.this , Profil.class);
                        startActivity(a);

                        break;
                    case R.id.recherce:
                        Intent c = new Intent(Profil.this , Recherche.class);
                        startActivity(c);
                        break;
                    case R.id.symptome:
                        Intent g = new Intent(Profil.this , Symptomes.class);
                        startActivity(g);
                        break;
                    case R.id.panier:
                        Intent h = new Intent(Profil.this , Panier.class);
                        startActivity(h);
                        break;
                    case R.id.favoris:
                        Intent d = new Intent(Profil.this , Favoris.class);
                        startActivity(d);
                        break;

                    case R.id.notif:

                        Intent b = new Intent(Profil.this , Messages.class);
                        startActivity(b);

                        break;



                    case R.id.contact:
                        Intent f = new Intent(Profil.this , Contacter.class);
                        startActivity(f);
                        break;


                    case R.id.stat:
                        Intent i = new Intent(Profil.this , Statistique.class);
                        startActivity(i);
                        break;
                    case R.id.propos:
                        Intent j = new Intent(Profil.this , Apropos.class);
                        startActivity(j);
                        break;
                    case R.id.logout:
                        Intent k = new Intent(Profil.this , Authentification.class);
                        startActivity(k);
                        break;
                }
            }
        }).show();
    }
}
