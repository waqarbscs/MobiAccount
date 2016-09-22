package com.OneWindow.sol.MobiAccount;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.OneWindow.sol.MobiAccount.Adapter.RecyclerStatusAdapter;
import com.OneWindow.sol.MobiAccount.DataBase.DbHelper;
import com.OneWindow.sol.MobiAccount.Models.Item_Subtype;

import java.util.ArrayList;
import java.util.List;

public class Status extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Item_Subtype> data;
    Spinner spinner;
    int type=0;
    RecyclerStatusAdapter recyclerStatusAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data=new ArrayList<>();

        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        recyclerStatusAdapter=new RecyclerStatusAdapter(data,this);
        recyclerView.setAdapter(recyclerStatusAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spinner=(Spinner)findViewById(R.id.status_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("Expense")){
                    type=1;
                    getsubCategory(1);

                }else{
                    type=0;
                    getsubCategory(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        }
    public void getsubCategory(int type){
        data.clear();
        Cursor cursor=DbHelper.getDbHelper(this).getSubType(type);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Item_Subtype i=new Item_Subtype();
            i.setInitialValues();
            i.setType(Integer.parseInt(cursor.getString(1)));
            //String z=cursor.getColumnName(0);
            i.setSubType(cursor.getString(2));
            //s.setResult(cursor.getString(7));
            cursor.moveToNext();
            data.add(i);
        }
        recyclerStatusAdapter.notifyDataSetChanged();
    }
    public void insertType(int type,String subType){
        Item_Subtype i=new Item_Subtype();
        i.setInitialValues();
        i.setType(type);
        i.setSubType(subType);
        data.add(i);
        recyclerStatusAdapter.notifyDataSetChanged();
        DbHelper.getDbHelper(Status.this).insertItemSubType(type,subType);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.smenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_save:
                showDialog1(type);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void showDialog1(final int type){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Status.this);
        alertDialog.setTitle("Add AccountType");


        final EditText input = new EditText(Status.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        //alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        insertType(type,input.getText().toString());
                    }
                });

        alertDialog.setNegativeButton("Discard",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();

}
}
