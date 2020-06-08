package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
EditText mail,password;
Button login;
TextView t;
Cursor id;
Data db=new Data(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        t= findViewById(R.id.text);

        //Go to Sign Up Activity
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SignIn.this,SignUp.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=db.verifUser(mail.getText().toString(),password.getText().toString());
                if (id!=null)
                {
                    Intent i = new Intent(SignIn.this, Home.class);
                    i.putExtra("userId",id.getInt(0));
                    startActivity(i);}

                else   Toast.makeText(SignIn.this,"echec",Toast.LENGTH_SHORT).show();
            }
        });



    }
}
