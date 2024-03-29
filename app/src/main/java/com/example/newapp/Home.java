package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Home extends AppCompatActivity{
    ImageView logout;
    Button bill, print_meter, duplicate_bill, location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logout=findViewById(R.id.logout);
        bill=findViewById(R.id.btn_bill);
        print_meter=findViewById(R.id.btn_meter);
        duplicate_bill=findViewById(R.id.btn_duplicate);
        location=findViewById(R.id.btn_location);

        //Logout Button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //Bill Button
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Details.class);
                startActivity(intent);
            }
        });
    }

    //Alert Box(Are you sure u want to Exit?)
    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this).setMessage("Are you sure you want to exit?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("No",null).show();
    }
}