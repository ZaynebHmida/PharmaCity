package com.example.admin.pharmacity;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.cocosw.bottomsheet.BottomSheet;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Ordonnance extends AppCompatActivity {
    Button choose, upload;
    private static final int RESULT_SELECT_IMAGE = 1;
    ImageView imageview;
    String SERVER = "http://10.0.3.2/pahrmacie_mobile/upload.php", timestamp, filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordonnance);
        choose = (Button) findViewById(R.id.button4);
        upload = (Button) findViewById(R.id.button16);
        imageview = (ImageView) findViewById(R.id.imageView5);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap image = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
                //execute the async task and upload the image to server

                    if (filename != null) {
                        new Upload(image, filename).execute();
                    }

                    else {
                        Toast.makeText(Ordonnance.this, "Choisir une image SVP", Toast.LENGTH_SHORT).show();

                }

            }
        });
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
            imageview.setImageURI(image);

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
        CursorLoader loader = new CursorLoader(Ordonnance.this, contentUri, proj, null, null, null);
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
            Toast.makeText(Ordonnance.this, "Ordonnance envoyée", Toast.LENGTH_SHORT).show();
        }
    }

    public void openAndroidBottomMenu(View view) {

        new BottomSheet.Builder(this).title(" Menu PharmaCity ").sheet(R.menu.menu_pharmacie).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.profil:

                        Intent a = new Intent(Ordonnance.this, Profil.class);
                        startActivity(a);

                        break;
                    case R.id.recherce:
                        Intent c = new Intent(Ordonnance.this, Recherche.class);
                        startActivity(c);
                        break;
                    case R.id.symptome:
                        Intent g = new Intent(Ordonnance.this, Symptomes.class);
                        startActivity(g);
                        break;
                    case R.id.panier:
                        Intent h = new Intent(Ordonnance.this, Panier.class);
                        startActivity(h);
                        break;
                    case R.id.favoris:
                        Intent d = new Intent(Ordonnance.this, Favoris.class);
                        startActivity(d);
                        break;
                    case R.id.notif:

                        Intent b = new Intent(Ordonnance.this, Messages.class);
                        startActivity(b);

                        break;




                    case R.id.contact:
                        Intent f = new Intent(Ordonnance.this, Contacter.class);
                        startActivity(f);
                        break;


                    case R.id.stat:
                        Intent i = new Intent(Ordonnance.this, Statistique.class);
                        startActivity(i);
                        break;
                    case R.id.propos:

                    case R.id.logout:
                        Intent k = new Intent(Ordonnance.this, Authentification.class);
                        startActivity(k);
                        break;
                }
            }
        }).show();
    }
}
