package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
Toolbar toolbar;
TextView addpub;
    int userid;
    ListView l;
    Data db=new Data(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Get user id
      userid=getIntent().getIntExtra("userId",0);
      toolbar=findViewById(R.id.toolbar);
      addpub=findViewById(R.id.add);
      l=findViewById(R.id.list);
      // When publish is clicked do :
      addpub.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
           Intent i= new Intent(Home.this,Publication.class);
           i.putExtra("userId",userid);
           startActivity(i);
          }
      });

        ArrayList al=db.getAllPub();
        ArrayAdapter ad= new ArrayAdapter(this,android.R.layout.simple_list_item_1,al);
        l.setAdapter(ad);

    }


}
