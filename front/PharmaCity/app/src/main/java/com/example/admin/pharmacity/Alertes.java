package com.example.admin.pharmacity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class Alertes extends AppCompatActivity {
    static String messagechoisi;
    ListView listV;


    ArrayList<HashMap<String, Object>> messagelist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertes);
    }
}
