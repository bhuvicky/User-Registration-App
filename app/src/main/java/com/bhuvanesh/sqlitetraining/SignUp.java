package com.bhuvanesh.sqlitetraining;

import android.app.FragmentManager;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.security.Policy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText etName, etEmail, etPassword, etMobileNo;
    private Spinner spinState, spinDistrict;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        spinState = (Spinner) findViewById(R.id.spinState);
        spinDistrict = (Spinner) findViewById(R.id.spinDistrict);


        etName.addTextChangedListener(this.new MyTextWatcher(etName));
        etEmail.addTextChangedListener(this.new MyTextWatcher(etEmail));
        etPassword.addTextChangedListener(this.new MyTextWatcher(etPassword));
        spinState.setOnItemSelectedListener(this);

        dbHelper = new DatabaseHelper(this);
    }

    public void storeDataInDatabase(View view) {
        Cursor cursor = dbHelper.matchWithEmail(etEmail.getText().toString());
        if (!(cursor.moveToNext())) {
            ArrayList<String> userInformation = new ArrayList();
            userInformation.add(etName.getText().toString());
            userInformation.add(etEmail.getText().toString());
            userInformation.add(etPassword.getText().toString());
            userInformation.add(etMobileNo.getText().toString());
            userInformation.add(spinState.getSelectedItem().toString());
            userInformation.add(spinDistrict.getSelectedItem().toString());
            dbHelper.addUserRecord(userInformation);
            Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_LONG).show();
        } else
            etEmail.setError("Email already Exists");
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                ArrayAdapter adapterTamilNadu = ArrayAdapter.createFromResource(getBaseContext(), R.array.district_in_tami_nadu, android.R.layout.simple_list_item_1);
                spinDistrict.setAdapter(adapterTamilNadu);
                break;
            case 1:
                ArrayAdapter adapterKarnataka = ArrayAdapter.createFromResource(getBaseContext(), R.array.district_in_karnataka, android.R.layout.simple_list_item_1);
                spinDistrict.setAdapter(adapterKarnataka);
                break;
            case 2:
                ArrayAdapter adapterKerala = ArrayAdapter.createFromResource(getBaseContext(), R.array.district_in_kerala, android.R.layout.simple_list_item_1);
                spinDistrict.setAdapter(adapterKerala);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}



    class MyTextWatcher implements TextWatcher {

        View view;

        MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.etName:
                    if (!(s.toString().matches("[a-zA-Z]+")))
                        etName.setError("only alphabets are allowed");
                    break;
                case R.id.etEmail:
                    if (!(isValidEmail(s)))
                        etEmail.setError("invalid email");
                    break;
                case R.id.etPassword:
                    if (pswdLength(s) == 8) {
                        Toast toast = Toast.makeText(getBaseContext(), "strength: Good", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (pswdLength(s) < 8)
                        etPassword.setError("strength: Too Short");
                    break;
            }
        }

        public int pswdLength(Editable s) {
            return s.toString().length();
        }

        public boolean isValidEmail(Editable s) {
            String regex = "[^.][[a-z][A-Z] \\d [._@]]{5,}";
            return Pattern.matches(regex, s.toString());
        }
    }
}

