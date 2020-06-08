package com.example.lostandfound;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class Data extends SQLiteOpenHelper {
    public Data(Context context) {
        super(context, "lostfound.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user ( id Integer primary key AUTOINCREMENT, picture Blob, firstname text, lastname text, mail text Unique , phone Integer , address text, password text )");
        db.execSQL("create table publication ( id Integer primary key AUTOINCREMENT,  title text, description text, picture Blob, categorie text, city text ,userid Integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public boolean adduser( byte[] pic, String fn, String ln, String m, String ph,String ad, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put("picture", pic);
        contentValues.put("firstname", fn);
        contentValues.put("lastname",ln);
        contentValues.put("mail", m);
        contentValues.put("phone", ph);
        contentValues.put("address", ad);
        contentValues.put("password", pass);

        long result = db.insert("user", null,contentValues);
        if (result == -1) return false;
        else return true;
    }

    public Cursor verifUser(String m, String p) {SQLiteDatabase db = this.getReadableDatabase();

        Cursor res=db.rawQuery("select id from user where mail='"+m+"'"+" and password='"+p+"'",null);
        if (res.moveToFirst())
            return  res;
        else return null;
    }

    public boolean addpub(String title,String  descp, byte[] pic, String cat, String city, int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();


        contentValues.put("title",title );
        contentValues.put("description",descp);
        contentValues.put("picture",pic);
        contentValues.put("categorie", cat);
        contentValues.put("city", city);
        contentValues.put("userid", userid);

        long result = db.insert("publication", null,contentValues);
        if (result == -1) return false;
        else return true;

    }

    public ArrayList getProfile(int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor res=db.rawQuery("select id, firstname, lastname, num_tel, mail  from user where id="+i ,null);
        res.moveToFirst();
        while (res.isAfterLast()==false)
        {
            String t0 = res.getString(0);
            String t1 = res.getString(1);
            String t2 = res.getString(2);
            String t3 = res.getString(3);
          /*  String t4 = res.getString(4);
            String t5 = res.getString(5);*/

            arrayList.add(t0+"\n"+t1+"\n"+t2+"\n"+t3+"\n");
            // arrayList.add(file_string);
            res.moveToNext();
        }

        return arrayList;

    }

    public ArrayList getAllPub() { SQLiteDatabase db = this.getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor res=db.rawQuery("select*from publication" ,null);
        res.moveToFirst();
        while (res.isAfterLast()==false)
        {
            String title = res.getString(1);
            String desc = res.getString(2);
            String cat = res.getString(4);
            String city = res.getString(5);
            int userid = res.getInt(6);
            Cursor user=db.rawQuery("select firstname, lastname, phone, address from user" ,null);
            user.moveToFirst();
            String firstname = user.getString(0);
            String lastname = user.getString(1);
            String phone =user.getString(2);
            String address = user.getString(3);


            arrayList.add(title+"\n"+desc+"\n"+cat+"\n"+city+"\nPublish by "+firstname+" "+lastname+"\n"+phone+"\n"+address);
            res.moveToNext();}

        return arrayList;
    }
}
