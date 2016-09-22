package com.OneWindow.sol.MobiAccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.OneWindow.sol.MobiAccount.DataBase.DbHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText txtBusiness,txtPhone,txtEmail,txtUserName,txtPass,txtCPass;
    AutoCompleteTextView spinCategory;

    String business,phone,Email,username,pass,cpass,category;

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtBusiness=(EditText)findViewById(R.id.txtBusiness);
        txtPhone=(EditText) findViewById(R.id.txtPhone);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPass=(EditText) findViewById(R.id.txtPassword);
        txtCPass=(EditText) findViewById(R.id.confirmPassword);
        spinCategory=(AutoCompleteTextView) findViewById(R.id.spinCategory);
        btnRegister=(Button) findViewById(R.id.button);

          final String[] COUNTRIES = new String[] {
                "Artists, Writers", "Auto Accessories",
                "Accountants", "Acupuncture",
                "Agents & Brokers", "Animal Care & Supplies",
                "Animal Hospital", "Antiques & Collectibles",
                "Apartment & Home Rental",
                "Architects, Landscape Architects, Engineers & Surveyors",
                "Assisted Living & Home Health Care",
                "Attorneys", "Audiologist",
                "Auto Dealers – New", "Auto Dealers – Used",
            "Barber & Beauty Salons",
            "Beauty Supplies",
            "Blasting & Demolition",
            "Building Materials & Supplies",
            "Cards & Gifts",
            "Chiropractic", "Cleaning Clinics & Medical Centers",
            "Clothing & Accessories",
            "Computer Programming & Support",
            "Construction Companies",
            "Consultants",
            "Consumer Electronics & Accessories",
            "Crafts, Hobbies & Sports",
            "Dental",
            "Department Stores, Sporting Goods",
            "Desserts, Catering & Supplies",
            "Detail & Carwash",
            "Diet I& Nutrition",
             "Distribution",
            "Dry Cleaners & Laundromats",
            "Early Childhood Education",
            "Education",
            "Educational Resources",
            "Electricians",
            "Employment Agency",
            "Engineer, Survey",
            "Entertainment",
            "Environmental Assessments",
            "Event Planners & Supplies",
            "Exercise & Fitness",
            "Fast Food & Carry Out",
            "Financial Institutions",
            "Financial Services",
            "Flower Shops",
            "Food & Dining",
            "Funeral Service Providers & Cemetaries",
            "Gas Stations",
            "General",
            "Golf Courses",
            "Grocery, Beverage & Tobacco",
            "Health & Medicine",
            "Home & Garden",
            "Home Furnishings",
            "Home Goods",
            "Home Improvements & Repairs",
            "Import/Export",
            "Inspectors", "Insurance",
            "Jewelry",
            "Laboratory, Imaging & Diagnostic",
            "Landscape & Lawn Service",
            "Legal & Financial",
            "Manufacturing",
            "Manufacturing",
            "Marketing & Communications",
            "Massage & Body Works",
            "Massage Therapy",
            "Mental Health",
            "Miscellaneous", "Miscellaneous",
            "Mortgage Broker & Lender",
            "Motorcycle Sales & Repair", "Movies",
            "Moving & Storage",
            "Nail Salons",
            "Nurse", "Office Supplies",
            "Optical", "Other Educational",
            "Other Legal",
            "Packaging & Shipping",
            "Personal Care & Services",
            "Pest Control",
            "Pharmacy, Drug & Vitamin Stores",
            "Physical Therapy",
            "Physicians & Assistants",
            "Plaster & Concrete",
            "Plumbers", "Podiatry",
            "Pool Supplies & Service",
            "Printing & Publishing",
            "Productions",
            "Property Management",
            "Real Estate Agencies & Brokerage",
            "Rental & Leasing",
            "Restaurants", "Roofers",
            "Security System & Services",
            "Service, Repair & Parts",
            "Shoe Repairs", "Shoes",
            "Social Worker", "Tailors",
            "Title Company",
            "Towing", "Transportation",
            "Travel & Tourism",
            "Travel & Transportation Hotel",
            "Utility Companies",
            "Veterinary & Animal Surgeons", "Wholesale"
        };


        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
         //       R.array.spinner_array, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // spinCategory.setAdapter(adapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(adapter);


        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinCategory.setText(adapterView.getItemAtPosition(i).toString());
                category=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(shouldRegister()){

                    DbHelper.getDbHelper(Register.this).saveUSER(username,pass,business,category,Email,phone);
                    finish();
                }
            }
        });

    }

    private boolean shouldRegister() {
        business=txtBusiness.getText().toString();
        phone=txtPhone.getText().toString();
        Email=txtEmail.getText().toString();
        username=txtUserName.getText().toString();
        pass=txtPass.getText().toString();
        cpass=txtCPass.getText().toString();

        if(TextUtils.isEmpty(business)){
            txtBusiness.setError("Business Empty");
            return false;
        }
        if(TextUtils.isEmpty(phone)){
            txtPhone.setError("Phone Empty");
            return false;
        }
        if(TextUtils.isEmpty(Email)){
            txtEmail.setError("Email Empty");
            return false;
        }
        if(TextUtils.isEmpty(username)){
            txtUserName.setError("UserName Empty");
            return false;
        }
        if(TextUtils.isEmpty(pass)){
            txtPass.setError("password Empty");
            return false;
        }
        if(TextUtils.isEmpty(cpass)){
            txtCPass.setError("password Empty");
            return false;
        }
        if(!isEmailValid(Email)){
            txtEmail.setError("Not Valid Email");
            return false;
        }
        if(!pass.equals(cpass)){
            txtPass.setError("password not matching");
            return false;
        }
        return true;
    }

        public static boolean isEmailValid(String email) {
            boolean isValid = false;

            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;

            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                isValid = true;
            }
            return isValid;
        }

}
