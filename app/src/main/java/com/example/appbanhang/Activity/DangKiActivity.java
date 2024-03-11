package com.example.appbanhang.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.Retrofit.ApiBanHang;
import com.example.appbanhang.Retrofit.RetrofitClient;
import com.example.appbanhang.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangKiActivity extends AppCompatActivity {
    EditText userName, userEmail, userPass,userSdt;
    ImageView seeView;
    AppCompatButton dangKi;
    TextView dangNhap;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String email,pass;
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        initView();
        dangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKiwithServer();
                dangKiwithFirebase();
            }
        });

        dangNhap.setOnClickListener(task ->{
           Intent i = new Intent(DangKiActivity.this,DangNhapActivity.class);
           startActivity(i);
        });
        seeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPass.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
    }

    private void dangKiwithServer() {
        email = userEmail.getText().toString().trim();
        pass = userPass.getText().toString().trim();
        String phone = userSdt.getText().toString();
        String name = userName.getText().toString().trim();
    }

    private void dangKiwithFirebase() {
        email = userEmail.getText().toString().trim();
        pass = userPass.getText().toString().trim();
        //Phần này phải đưa vào trong API thì sẽ hợp lí
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
               Intent i = new Intent(DangKiActivity.this,DangNhapActivity.class);
               startActivity(i);
           }else{
               Toast.makeText(this, "Kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
           }
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        userEmail = findViewById(R.id.edt_email_resigter);
        userName = findViewById(R.id.edt_name_resigter);
        userPass = findViewById(R.id.edt_pass_register);
        seeView  = findViewById(R.id.seePass_register);
        userSdt = findViewById(R.id.edt_sdt_resigter);
        dangKi = findViewById(R.id.btndk);
        dangNhap = findViewById(R.id.txt_login_resgiter);
    }
}