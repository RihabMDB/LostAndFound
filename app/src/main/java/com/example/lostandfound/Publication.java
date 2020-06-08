package com.example.lostandfound;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Publication extends AppCompatActivity {
    byte[] image = null;
    Uri uri;
    ImageView img;
    EditText title, description,categorie,city;
    Button publish;
    Data db=new Data(this);
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);
        img=findViewById(R.id.pic);
        description=findViewById(R.id.description);
        title=findViewById(R.id.title);
        categorie=findViewById(R.id.cotegorie);
        city=findViewById(R.id.city);
        publish=findViewById(R.id.publish);
        // Get user id
        userid=getIntent().getIntExtra("userId",0);
    }
    public void Publish(View view) {
        if (!img.isClickable())
            Toast.makeText(this, "Enter a picture !", Toast.LENGTH_SHORT).show();
        else if (title.getText().toString().isEmpty())
            Toast.makeText(this, "Enter the title !", Toast.LENGTH_SHORT).show();
        else if (description.getText().toString().isEmpty())
            Toast.makeText(this, "Enter a description !", Toast.LENGTH_SHORT).show();
        else if (categorie.getText().toString().isEmpty())
            Toast.makeText(this, "Enter the categorie !", Toast.LENGTH_SHORT).show();
        else if (city.getText().toString().isEmpty())
            Toast.makeText(this, "Enter the city !", Toast.LENGTH_SHORT).show();
        else {
            boolean add=db.addpub(title.getText().toString(),description.getText().toString(),imageViewToByte(img),
                    categorie.getText().toString(),city.getText().toString(),userid);
            if (add){
                startActivity(new Intent(this,Home.class));
            }
            else Toast.makeText(this, "Publish Failed", Toast.LENGTH_SHORT).show();
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
