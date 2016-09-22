package com.OneWindow.sol.MobiAccount;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.OneWindow.sol.MobiAccount.DataBase.DbHelper;

import java.util.Calendar;

public class ItemDetails extends AppCompatActivity {
    EditText editType,editDate,editDescription,editAmount;
    String date,description,amount;
    TextView sign_view;
    String subType;
    int type,_id;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sign_view=(TextView)findViewById(R.id.sign_view);
        editType=(EditText)findViewById(R.id.sub_type_text);
        editDate=(EditText)findViewById(R.id.date_text);
        editDescription=(EditText)findViewById(R.id.details_edit_text);
        editAmount=(EditText)findViewById(R.id.amount_edit_text);

        subType=editType.getText().toString();
        date=editDate.getText().toString();

        user = getIntent().getStringExtra("user");


        if(getIntent().getIntExtra("_id",0)!=0) {
            _id=getIntent().getIntExtra("_id",0);
            type = getIntent().getIntExtra("_type",0);
            subType = getIntent().getStringExtra("_sub");
            amount = String.valueOf(getIntent().getLongExtra("_amount",0));
            date = getIntent().getStringExtra("_date");
            description = getIntent().getStringExtra("_description");
            editType.setText(subType);
            editAmount.setText(amount);
            editDate.setText(date);
            editDescription.setText(description);
            if(type==0) {
                getSupportActionBar().setTitle("New Income");
                sign_view.setText("+");
            }
            else {
                getSupportActionBar().setTitle("New Expense");
                sign_view.setText("-");
            }
        }else {

            final int intent = getIntent().getIntExtra("type", 0);


            type = intent;
            if (intent == 0) {
                getSupportActionBar().setTitle("New Income");
                sign_view.setText("+");
            } else {
                getSupportActionBar().setTitle("New Expense");
                sign_view.setText("-");
            }


        }



        editType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog1(type);
            }
        });
        Calendar calendar=Calendar.getInstance();
        final int date=calendar.get(Calendar.DATE);
        final int month=calendar.get(Calendar.MONTH);
        final int year=calendar.get(Calendar.YEAR);
        int mo=month+1;
        if(mo<10&date<10){
            editDate.setText(year+"-0"+mo+"-0"+date);
        }else if(mo<10&&date>=10){
            editDate.setText(year+"-0"+mo+"-"+date);
        }else if(mo>=10&&date<10){
            editDate.setText(year+"-"+mo+"-0"+date);
        }else {
            editDate.setText(year+"-"+mo+"-"+date);
        }

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(ItemDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int m=i1+1;
                        if(m<10&i2<10){
                            editDate.setText(i+"-0"+m+"-0"+i2);
                        }else if(m<10&&i2>=10){
                            editDate.setText(i+"-0"+m+"-"+i2);
                        }else if(m>=10&&i2<10){
                            editDate.setText(i+"-"+m+"-0"+i2);
                        }else {
                            editDate.setText(i+"-"+m+"-"+i2);
                        }
                    }
                },year,month,date);
                datePickerDialog.show();
            }
        });


    }

    public void showDialog1(int type){
        int k=0;
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ItemDetails.this);
        //builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Pick Income Type");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                ItemDetails.this,
                android.R.layout.select_dialog_item);
        Cursor cursor=DbHelper.getDbHelper(this).getSubType(type);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
           arrayAdapter.add(cursor.getString(2));
            k++;
            cursor.moveToNext();
        }

        builderSingle.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editType.setText(arrayAdapter.getItem(which));
                    /*
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                ItemDetails.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builderInner.show();
                        */
                    }
                });
        if(k==0){
            Toast.makeText(ItemDetails.this, "Please Update Account Head First", Toast.LENGTH_SHORT).show();
        }else {
            builderSingle.show();
        }
    }
    public boolean insertItem(int t, String st, long a, String d, String des, String userId){
        boolean f=DbHelper.getDbHelper(ItemDetails.this).insertItem(t,st,a,d,des,userId);
        return f;
    }
    public void updateItem(int id, int t, String st, long a, String d, String des, String userId){
        DbHelper.getDbHelper(ItemDetails.this).update_item(id,t,st,a,d,des,userId);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.imenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_save:
                subType=editType.getText().toString();
                date=editDate.getText().toString();
                description=editDescription.getText().toString();
                amount=editAmount.getText().toString();
                if(amount.equals("")){
                    Toast.makeText(ItemDetails.this, "Add amount", Toast.LENGTH_SHORT).show();
                }else {
                    if (_id == 0) {
                        if (shouldInsert(subType)) {
                            if (insertItem(type, subType, Long.parseLong(amount), date, description, user)) {
                                Toast.makeText(ItemDetails.this, "Save  account", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    } else {
                        updateItem(_id, type, subType, Long.parseLong(amount), date, description, user);
                        Intent i = new Intent(ItemDetails.this, ShowBudget.class);
                        i.putExtra("user", user);
                        startActivity(i);
                        finish();

                    }
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean shouldInsert(String t) {
        if(t.equals("")){
            Toast.makeText(ItemDetails.this, "Please pick the type", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
