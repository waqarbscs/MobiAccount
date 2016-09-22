package com.OneWindow.sol.MobiAccount;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.OneWindow.sol.MobiAccount.DataBase.DbHelper;
import com.OneWindow.sol.MobiAccount.Util.PieChartUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Account extends AppCompatActivity {
    TextView from_date_text,to_date_text,income_amount_text,
            expense_amount_text,margin_text,mNoChartDataTextView;
    String user;
    PieChartView pieChartView;
    PieChartData pieChartData;

    private static final int TAB_POSITION_INCOME = 0;
    private static final int TAB_POSITION_EXPENSE = 1;

    private int mSelectedTabPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user=getIntent().getStringExtra("user");

        from_date_text=(TextView)findViewById(R.id.from_date_text);
        to_date_text=(TextView)findViewById(R.id.to_date_text);
        income_amount_text=(TextView)findViewById(R.id.income_amount_text);
        expense_amount_text=(TextView)findViewById(R.id.expense_amount_text);
        margin_text=(TextView)findViewById(R.id.margin_text);

        pieChartView=(PieChartView)findViewById(R.id.chart);
        mNoChartDataTextView=(TextView)findViewById(R.id.no_chart_data_text);
        pieChartView.setOnValueTouchListener(new ValueTouchListener());


        Calendar calendar=Calendar.getInstance();
        final int date=calendar.get(Calendar.DATE);
        final int month=calendar.get(Calendar.MONTH);
        final int year=calendar.get(Calendar.YEAR);
        int mo=month+1;
        if(mo<10&date<10){
            from_date_text.setText(year+"-0"+mo+"-0"+date);
            to_date_text.setText(year+"-0"+mo+"-0"+date);
        }else if(mo<10&&date>=10){
            from_date_text.setText(year+"-0"+mo+"-"+date);
            to_date_text.setText(year+"-0"+mo+"-"+date);
        }else if(mo>=10&&date<10){
            from_date_text.setText(year+"-"+mo+"-0"+date);
            to_date_text.setText(year+"-"+mo+"-0"+date);
        }else {
            from_date_text.setText(year+"-"+mo+"-"+date);
            to_date_text.setText(year+"-"+mo+"-"+date);
        }
        setupTabLayout(user,from_date_text.getText().toString(),to_date_text.getText().toString());
        initColors();
        PieChartUtil.initChart(pieChartView);
        getData();
        from_date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Account.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int m = i1 + 1;
                        if (m < 10 & date < 10) {
                            from_date_text.setText(i + "-0" + m + "-0" + i2);
                        } else if (m < 10 && date >= 10) {
                            from_date_text.setText(i + "-0" + m + "-" + i2);
                        } else if (m >= 10 && date < 10) {
                            from_date_text.setText(i + "-" + m + "-0" + i2);
                        } else {
                            from_date_text.setText(i + "-" + m + "-" + i2);
                        }
                        getData();
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });
        to_date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Account.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int m = i1 + 1;
                        if (m < 10 & date < 10) {
                            to_date_text.setText(i + "-0" + m + "-0" + i2);
                        } else if (m < 10 && date >= 10) {
                            to_date_text.setText(i + "-0" + m + "-" + i2);
                        } else if (m >= 10 && date < 10) {
                            to_date_text.setText(i + "-" + m + "-0" + i2);
                        } else {
                            to_date_text.setText(i + "-" + m + "-" + i2);
                        }
                        getData();
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });


    }
    private int[] mExpenseColors;
    private int[] mIncomeColors;

    private int mPrimaryColor;
    private int mAccentColor;
    private int mSecondaryTextColor;
    private void initColors() {
        mPrimaryColor = ContextCompat.getColor(Account.this, R.color.primary_color);
        mAccentColor = ContextCompat.getColor(Account.this, R.color.accent_color);
        mSecondaryTextColor = ContextCompat.getColor(Account.this, R.color.text_secondary);

        mExpenseColors = new int[]{
                ContextCompat.getColor(this, R.color.expense_graph_color_1),
                ContextCompat.getColor(this, R.color.expense_graph_color_2),
                ContextCompat.getColor(this, R.color.expense_graph_color_3),
                ContextCompat.getColor(this, R.color.expense_graph_color_4),
                ContextCompat.getColor(this, R.color.expense_graph_color_5)
        };

        mIncomeColors = new int[]{
                ContextCompat.getColor(this, R.color.income_graph_color_1),
                ContextCompat.getColor(this, R.color.income_graph_color_2),
                ContextCompat.getColor(this, R.color.income_graph_color_3),
                ContextCompat.getColor(this, R.color.income_graph_color_4),
                ContextCompat.getColor(this, R.color.income_graph_color_5)
        };
    }
    private void displayChart(String user,String from, String to) {
        if (mSelectedTabPosition == TAB_POSITION_EXPENSE) {
            String noExpenseString = getResources().getString(R.string.no_expenses_in_this_period);

            displayChart( getCharData(user,1,from,to), mExpenseColors, noExpenseString);
        } else {
            String noIncomeString = getResources().getString(R.string.no_income_in_this_period);
            displayChart(getCharData(user,0,from,to), mIncomeColors, noIncomeString);
        }
    }
    private void displayChart(Map<String, Long> values, int[] colors, String noValueString) {
        if (values.size() == 0) {
            mNoChartDataTextView.setText(noValueString);
            pieChartView.setVisibility(View.GONE);
            mNoChartDataTextView.setVisibility(View.VISIBLE);
        } else {
            PieChartUtil.setData(pieChartView, values, colors);
            mNoChartDataTextView.setVisibility(View.GONE);
            pieChartView.setVisibility(View.VISIBLE);
        }
    }

    public void getData(){
        long in=income(user,0,from_date_text.getText().toString(),to_date_text.getText().toString());
        long ex=income(user,1,from_date_text.getText().toString(),to_date_text.getText().toString());

        String mg1;
        long mg;
        if(ex>in){
            mg=ex-in;
            mg1=String.valueOf("-"+mg);
        }else {
            mg=in-ex;
            mg1=String.valueOf("+"+mg);
        }

        String in1= String.valueOf(in);
        String ex1=String.valueOf(ex);


        income_amount_text.setText(in1);
        expense_amount_text.setText(ex1);
        margin_text.setText(mg1);
        displayChart(user,from_date_text.getText().toString(),to_date_text.getText().toString());
        //setupTabLayout(user,from_date_text.getText().toString(),to_date_text.getText().toString(),in,ex);

    }

    public long income(String user,int type,String f,String t) {
        long sum=0;
        Cursor cursor = DbHelper.getDbHelper(this).getIncome(user,type,f,t);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            sum+=Long.parseLong(cursor.getString(3));
            cursor.moveToNext();
        }
        cursor.close();
        return sum;
    }
    public HashMap<String, Long> getCharData(String user,int type,String f,String t) {
        HashMap<String, Long> myMap = new HashMap<String, Long>();
        List<String> cat=getsubCategory(type);
        long sum=0;
        for(int i=0;i<cat.size();i++) {
            Cursor cursor = DbHelper.getDbHelper(this).getIncome(user, type, f, t,cat.get(i));
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                sum += Long.parseLong(cursor.getString(3));
                cursor.moveToNext();
            }
            if(sum>0)
                myMap.put(cat.get(i),sum);
            sum=0;
        }
        return myMap;
    }
    public List<String> getsubCategory(int type){
        List<String> data=new ArrayList<>();
        Cursor cursor=DbHelper.getDbHelper(this).getSubType(type);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            data.add(cursor.getString(2));
            cursor.moveToNext();
        }
        return data;
    }
    private void setupTabLayout(final String user,final String s, final String toString) {
        final TabLayout tabLayout = (TabLayout) findViewById( R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.income_chart)),
                TAB_POSITION_INCOME);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.expense_chart)),
                TAB_POSITION_EXPENSE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == TAB_POSITION_EXPENSE) {
                    mSelectedTabPosition = TAB_POSITION_EXPENSE;
                    //tabLayout.setSelectedTabIndicatorColor(mAccentColor);
                } else {
                    mSelectedTabPosition = TAB_POSITION_INCOME;
                    //tabLayout.setSelectedTabIndicatorColor(mPrimaryColor);
                }
                displayChart(user,s,toString);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(Account.this, "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.smenu, menu);
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
