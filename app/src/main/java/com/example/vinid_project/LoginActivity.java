package com.example.vinid_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.vinid_project.databinding.ActivityLoginBinding;
import com.example.vinid_project.sqlite.CustomerSQLiteHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityLoginBinding binding;
    String key;
    private static final String account = "admin";
    private static final String password = "admin@173";
    CustomerSQLiteHelper customerSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        customerSQLiteHelper = new CustomerSQLiteHelper(this);

        typeLogin();
        
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });
    }
    
    private void typeLogin() {
        key = getIntent().getStringExtra("key");
        if (key.equals(MainActivity.key_admin)) {
            binding.layoutAdmin.setVisibility(View.VISIBLE);
            binding.layoutCustomer.setVisibility(View.GONE);
            binding.buttonLogin.setOnClickListener(this);
        } else {
            binding.layoutCustomer.setVisibility(View.VISIBLE);
            binding.layoutAdmin.setVisibility(View.GONE);
            binding.buttonAccept.setOnClickListener(this);
        }
    }

    private void loginWithAdmin() {
        if (binding.textAccount.getText().toString().equals(account)
                && binding.textPassword.getText().toString().equals(password)) {
            startActivity(new Intent(getBaseContext(), AdminActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Sai thông tin đăng nhập!!", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void loginWithCustomer() {
        if (binding.textName.getText().toString().isEmpty()) {
            binding.textName.setError("Không được để trống!");
        } else if (binding.textPhoneNumber.getText().toString().isEmpty()) {
            binding.textPhoneNumber.setError("Không được để trống!");
        } else {
            SharedPreferences sharedPref = getBaseContext().getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("customer_name", binding.textName.getText().toString());
            editor.putString("customer_phone", binding.textPhoneNumber.getText().toString());
            editor.apply();
            customerSQLiteHelper.insertCustomers(binding.textName.getText().toString(), binding.textPhoneNumber.getText().toString());
            startActivity(new Intent(getBaseContext(), DetailCustomerActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                loginWithAdmin();
                break;
            case R.id.buttonAccept:
                loginWithCustomer();
                break;
            default:
                break;
        }
    }
}