package com.OneWindow.sol.MobiAccount;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.OneWindow.sol.MobiAccount.DataBase.DbHelper;

public class Login extends AppCompatActivity {
    TextView textView,textRemember;
    EditText userText,passText;
    Button login;
    SharedPreferences loginPreference;
    SharedPreferences.Editor loginEdit;
    CheckBox checkRem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView=(TextView)findViewById(R.id.register);
        userText=(EditText)findViewById(R.id.userText);
        passText=(EditText)findViewById(R.id.passText);
        login=(Button)findViewById(R.id.login);
        textRemember=(TextView)findViewById(R.id.remember);
        checkRem=(CheckBox)findViewById(R.id.checkRem);

        loginPreference=getSharedPreferences("login",0);
        loginEdit=loginPreference.edit();

        String u=loginPreference.getString("user",null);
        String p=loginPreference.getString("pass",null);

        if(u!=null&&p!=null){
            userText.setText(u);
            passText.setText(p);
            checkRem.setChecked(true);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(accessGranted()){
                    if(checkRem.isChecked()){
                        loginEdit.putString("user",userText.getText().toString());
                        loginEdit.putString("pass",passText.getText().toString());
                        loginEdit.apply();
                    }
                    Intent intent=new Intent(Login.this,MainActivity.class);
                    intent.putExtra("user",user+pass);
                    startActivity(intent);
                    finish();
                }
            }
        });
        checkRem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    loginEdit.clear();
                    loginEdit.commit();
                }
            }
        });

    }

    String user,pass;
    private boolean accessGranted() {
        user=userText.getText().toString();
        pass=passText.getText().toString();
        //etText.getText().toString().trim().length() == 0

        if(TextUtils.isEmpty(user)) {
            userText.setError("Add UserName");
            return false;
        }
        if(TextUtils.isEmpty(pass)){
            passText.setError("Add PassWord");
            return false;
        }
        if(!TextUtils.isEmpty(user)&&!TextUtils.isEmpty(pass)) {
            if (DbHelper.getDbHelper(Login.this).login_data(userText.getText().toString(), passText.getText().toString())) {
                return true;
            }else{
                Toast.makeText(Login.this, "Wrong Authentication", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
    public void Dialog(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(Login.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(Login.this);
        }
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle("Interent Service Not Enabled");
        builder.setMessage("Please enable Internet Services");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                // Show location settings when the user acknowledges the alert dialog

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Dialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
