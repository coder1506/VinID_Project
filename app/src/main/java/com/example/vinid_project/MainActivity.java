package com.example.vinid_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vinid_project.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    public static final String key_customer = "customer";
    public static final String key_admin = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.buttonAdmin.setOnClickListener(this);
        binding.buttonCustomer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCustomer:
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.putExtra("key",  key_customer);
                startActivity(intent);
                finish();
                break;
            case R.id.buttonAdmin:
                Intent intent2 = new Intent(getBaseContext(), LoginActivity.class);
                intent2.putExtra("key", key_admin);
                startActivity(intent2);
                finish();
                break;
            default:
                break;
        }
    }
}