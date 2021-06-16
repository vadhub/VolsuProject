package com.vad.volsuproject.login.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vad.volsuproject.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;

    private LoginData loginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginData = new LoginData();

        editTextEmail = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);

        editTextEmail.addTextChangedListener(new TextWatcher() {

            private boolean mEditing;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!mEditing){
                    mEditing = true;
                    if(editTextEmail.getText().length()!=0){
                        String str = editable.toString();
                        if(!str.endsWith("@volsu.ru")){
                            str+="@volsu.ru";
                            editTextEmail.setText(str);
                        }
                    }
                    mEditing = false;
                }
            }
        });
    }


    public void onClickLogin(View view) {

    }
}