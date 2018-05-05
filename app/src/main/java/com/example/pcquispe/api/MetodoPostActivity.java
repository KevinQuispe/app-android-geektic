package com.example.pcquispe.api;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pcquispe.api.entidades.Post;
import com.example.pcquispe.api.http.Api;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

public class MetodoPostActivity extends AppCompatActivity {

    EditText txt_titulo, txt_body;
    ProgressDialog progreso, progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_post);
    }
}

