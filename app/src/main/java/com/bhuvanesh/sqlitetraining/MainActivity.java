package com.bhuvanesh.sqlitetraining;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelp;
    Intent intent;
    private EditText etLogin, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelp = new DatabaseHelper(this);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    public void openActivity(View view) {
        switch (view.getId()) {
            case R.id.btnSignUpHere:
                intent = new Intent(getBaseContext(), SignUp.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btnSignIn:
                Cursor cursor = dbHelp.matchWithEmailPassword(etLogin.getText().toString(), etPassword.getText().toString());
                    if (cursor.moveToNext()) {
                        intent = new Intent(getBaseContext(), DisplayUserProfileActivity.class);
                        Log.d("dbcheck", "movetonext true");
                        intent.putExtra("ID_KEY",    ""+cursor.getInt(0));
                        intent.putExtra("NAME_KEY",     cursor.getString(1));
                        intent.putExtra("EMAIL_KEY",    cursor.getString(2));
                        intent.putExtra("PASSWORD_KEY", cursor.getString(3));
                        intent.putExtra("MOBILE_NO_KEY",cursor.getString(4));
                        intent.putExtra("STATE_KEY",    cursor.getString(5));
                        intent.putExtra("DISTRICT_KEY", cursor.getString(6));
                        startActivity(intent);
                    }
                 else {
                    Toast.makeText(getBaseContext(), "Email or Password Incorrect", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
