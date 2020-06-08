package com.example.lostandfound;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SignUp extends AppCompatActivity {
    byte[] image = null;
    Uri uri;
    ImageView img;
    EditText firstname, lastname,mail, phone,address, password,confirmpass;
    Button signup;
    Data db= new Data(this);
    TextView cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        mail=findViewById(R.id.mail);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.address);
        password=findViewById(R.id.password);
        confirmpass=findViewById(R.id.confirmpassword);
        signup=findViewById(R.id.signup);
        cancel=findViewById(R.id.cancel);
        img=findViewById(R.id.img);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,SignIn.class));
            }
        });
    }

    // Sign up treatment
    public void Signup(View view) {
       //test if the input is empty(vide)
        // if (img.isEnabled())
            //Toast.makeText(this, "Enter your picture !", Toast.LENGTH_SHORT).show();
         if (firstname.getText().toString().isEmpty())
            Toast.makeText(this, "Enter your first name !", Toast.LENGTH_SHORT).show();
        else if (lastname.getText().toString().isEmpty())
                Toast.makeText(this, "Enter your last name !", Toast.LENGTH_SHORT).show();
        else if (mail.getText().toString().isEmpty())
            Toast.makeText(this, "Enter your Mail !", Toast.LENGTH_SHORT).show();
        else if (phone.getText().toString().isEmpty())
            Toast.makeText(this, "Enter your phone number !", Toast.LENGTH_SHORT).show();
        else if (address.getText().toString().isEmpty())
            Toast.makeText(this, "Enter your address !", Toast.LENGTH_SHORT).show();
        else if (password.getText().toString().isEmpty())
            Toast.makeText(this, "Enter your password !", Toast.LENGTH_SHORT).show();
        else if (confirmpass.getText().toString().isEmpty())
            Toast.makeText(this, "Re-Enter your password !", Toast.LENGTH_SHORT).show();
       //test if the password and confirm password are not equal
        else if (!password.getText().toString().equals(confirmpass.getText().toString()))
            Toast.makeText(this, "Check your password !", Toast.LENGTH_SHORT).show();
        else {
            boolean add=db.adduser(imageViewToByte(img),firstname.getText().toString(),lastname.getText().toString(),mail.getText().toString(),phone.getText().toString(),address.getText().toString(),password.getText().toString());
               if (add)
               {Toast.makeText(this, "Sign Up Succes", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(this,SignIn.class));
               }
               else Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
            }
    }


    // ouvrir gallery pour choisir la photo
    public void openGallery(View v) {
        Intent intgallery=new Intent(Intent.ACTION_GET_CONTENT);
        intgallery.setType("image/");
        startActivityForResult(intgallery,100);
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    //changer ImageView par la photo choisi apatrir de galerie
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==100) {
            uri=data.getData();
            try{ InputStream inputStream=  getContentResolver().openInputStream(uri);
                Bitmap decodestream= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(decodestream);
                image = getBitmapAsByteArray(decodestream);}
            catch (Exception ex){
                Log.e("ex",ex.getMessage());}
        }}

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

  }
