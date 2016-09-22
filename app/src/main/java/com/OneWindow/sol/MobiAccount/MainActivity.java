package com.OneWindow.sol.MobiAccount;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button income,expense,view,status,account,about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        income=(Button)findViewById(R.id.btnIncome);
        expense=(Button)findViewById(R.id.btnExpense);
        view=(Button)findViewById(R.id.btnView);
        status=(Button)findViewById(R.id.btnStatus);
        account=(Button)findViewById(R.id.btnAccount);
        about=(Button)findViewById(R.id.btnAbout);

        final String user=getIntent().getStringExtra("user");

        final SharedPreferences p=getPreferences(0);
        SharedPreferences.Editor e=p.edit();
        e.putString("user",user);
        e.commit();

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,ItemDetails.class);
                intent.putExtra("type",0);
                intent.putExtra("user",p.getString("user",null));
                startActivity(intent);
            }
        });
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ItemDetails.class);
                intent.putExtra("type",1);
                intent.putExtra("user",p.getString("user",null));
                startActivity(intent);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ShowBudget.class);
                intent.putExtra("user",p.getString("user",null));
                startActivity(intent);
            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Status.class);
                startActivity(intent);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Account.class);
                intent.putExtra("user",p.getString("user",null));
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,About.class);
                startActivity(intent);
            }
        });
    }
}
