package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity implements View.OnClickListener{
    EditText et_meter_no, et_name;
    Button btn_submit;
    ImageView back_icon;
    String meter, ca, name;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        et_meter_no=findViewById(R.id.meter);
        et_name=findViewById(R.id.name);
        btn_submit=findViewById(R.id.btn_submit);
        back_icon=findViewById(R.id.back_icon);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        // Creating database and table
        db = openOrCreateDatabase("BillDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS customers(meter_no VARCHAR, name VARCHAR, ca_no VARCHAR, curr_meter_reading INTEGER);");

        btn_submit.setOnClickListener(this);
    }
    public void onClick(View view){
        // Validate a record from the Customer table
        if (view == btn_submit) {

            Cursor c = db.rawQuery("SELECT * FROM customers WHERE meter_no='" + et_meter_no.getText().toString().trim() + "'", null);
            Cursor c2 = db.rawQuery("SELECT * FROM customers WHERE name='" + et_name.getText().toString().trim() + "'", null);
            if (c.moveToFirst() && c2.moveToFirst()) {
                Toast.makeText(getApplicationContext(),"Authentication Successfull", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Details.this, Bill.class);
                meter=c.getString(0);
                name=c.getString(1);
                ca=c.getString(2);
                intent.putExtra("meter_key",meter);
                intent.putExtra("ca_key",ca);
                intent.putExtra("name_key",name);
                startActivity(intent);
                clearText();


            }
            else {
                //showMessage("Error", "Invalid Meter Number or Name");
                clearText();
            }
            if (et_name.getText().toString().isEmpty() && c.moveToFirst()){
                Toast.makeText(getApplicationContext(),"Authentication Successfull", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Details.this, Bill.class);
                meter=c.getString(0);
                name=c.getString(1);
                ca=c.getString(2);
                intent.putExtra("meter_key",meter);
                intent.putExtra("ca_key",ca);
                intent.putExtra("name_key",name);
                startActivity(intent);
                clearText();
            }
            else {
                showMessage("Error", "Invalid meter number");
                clearText();
            }
            /*if (c2.moveToFirst() && et_meter_no.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Authentication Successfull", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Details.this, Bill.class);
                meter=c.getString(0);
                name=c.getString(1);
                ca=c.getString(2);
                i.putExtra("meter_key",meter);
                i.putExtra("ca_key",ca);
                i.putExtra("name_key",name);
                startActivity(i);
                //clearText();
            }
            else {
                showMessage("Error", "Invalid Meter Number or Name");
                clearText();
            }*/
        }
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
        et_meter_no.setText("");
        et_name.setText("");
        et_meter_no.requestFocus();
    }
}