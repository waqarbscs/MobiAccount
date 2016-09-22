package com.OneWindow.sol.MobiAccount;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.OneWindow.sol.MobiAccount.Adapter.RecyclerViewAdapter;
import com.OneWindow.sol.MobiAccount.DataBase.DbHelper;
import com.OneWindow.sol.MobiAccount.Models.Item;

import java.util.ArrayList;
import java.util.List;

public class ShowBudget extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Item> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_budget);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String user=getIntent().getStringExtra("user");
        data=new ArrayList<>();

        Cursor cursor= DbHelper.getDbHelper(this).getItems(user);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Item i=new Item();
            i.setInitialValues();
            i.setId(Integer.parseInt(cursor.getString(0)));
            i.setType(Integer.parseInt(cursor.getString(1)));
            i.setSubType(cursor.getString(2));
            i.setAmount(Long.parseLong(cursor.getString(3)));
            i.setDate(cursor.getString(4));
            i.setUserId(cursor.getString(5));
            i.setDescription(cursor.getString(6));
            cursor.moveToNext();

            data.add(i);
        }
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(data,this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.imenu, menu);
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
