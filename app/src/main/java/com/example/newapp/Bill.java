package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Bill extends AppCompatActivity implements View.OnClickListener {
    TextView tv_meter_no, tv_ca_no, tv_name;
    String meter, ca, name;
    EditText meter_reading;
    Button btn_generate;
    ImageView back_icon;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        tv_meter_no=findViewById(R.id.tv_meter_no);
        tv_ca_no=findViewById(R.id.tv_cano_no);
        tv_name=findViewById(R.id.tv_name);
        meter_reading=findViewById(R.id.curr_reading);
        btn_generate=findViewById(R.id.btn_generate);
        back_icon=findViewById(R.id.back_icon);

        //Back icon
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Bill.this, Details.class);
                startActivity(i);
            }
        });

        // Creating database and table
        db = openOrCreateDatabase("BillDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS customers(meter_no VARCHAR, name VARCHAR, ca_no VARCHAR, curr_meter_reading INTEGER);");

        //Displaying Details (meter no., cano., name)
        Intent i=getIntent();
        meter=i.getStringExtra("meter_key");
        ca=i.getStringExtra("ca_key");
        name=i.getStringExtra("name_key");

        tv_meter_no.setText(meter);
        tv_ca_no.setText(ca);
        tv_name.setText(name);

        btn_generate.setOnClickListener(this);
    }
    //Inserting/updating current meter reading
    public void onClick(View view){
        if (view == btn_generate) {
            // Checking for empty roll number
            if (meter.trim().length() == 0) {
                showMessage("Error", "Please enter Value");
                return;
            }
            Cursor c = db.rawQuery("SELECT * FROM customers WHERE meter_no='" + meter + "'", null);
            if (c.moveToFirst()) {
                db.execSQL("UPDATE customers SET curr_meter_reading='" + meter_reading.getText());
                //showMessage("Success", "Record Modified");

                //Calculation part



                Intent i=new Intent(Bill.this,Home.class);
                startActivity(i);
            } else {
                showMessage("Error", "Invalid value");
            }
            clearText();
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
        meter_reading.setText("");
    }
}