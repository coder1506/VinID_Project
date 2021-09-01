package com.example.vinid_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.vinid_project.adapter.AdapterListOrder;
import com.example.vinid_project.databinding.ActivityShoppingCartBinding;
import com.example.vinid_project.models.ShopItem;
import com.example.vinid_project.sqlite.OrderSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity implements OnClickListener {
    ActivityShoppingCartBinding binding;
    String category;
    List<ShopItem> shopItems = new ArrayList<>();
    int sum = 0;
    AdapterListOrder adapterListOrder;
    OrderSQLiteHelper orderSQLiteHelper;
    String customerName, customerPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart);

        orderSQLiteHelper = new OrderSQLiteHelper(getBaseContext());

        editSpinner();
        initData();

        binding.buttonOrder.setOnClickListener(this);
        binding.buttonBack.setOnClickListener(this);
    }

    private void initData() {
        SharedPreferences sharedPref = getBaseContext().getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
        customerName = sharedPref.getString("customer_name", "x");
        customerPhoneNumber = sharedPref.getString("customer_phone", "x");
        binding.textName.setText(customerName);
        binding.textPhoneNumber.setText(customerPhoneNumber);

        //List
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int count = intent.getIntExtra("count", 1);
        for (int i = 0; i < count; i++){
            if (bundle != null){
                shopItems.add((ShopItem) bundle.get(String.valueOf(i)));
                sum += Integer.parseInt(shopItems.get(i).getPriceItem()) * Integer.parseInt(shopItems.get(i).getCount());
            }
        }
        binding.textSum.setText(sum + " vnđ");
        adapterListOrder = new AdapterListOrder(shopItems, getBaseContext());
        binding.revListItemCart.setAdapter(adapterListOrder);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        binding.revListItemCart.setLayoutManager(manager);
    }

    private void editSpinner() {
        ArrayList<String> spinnerList = new ArrayList<>();
        spinnerList.add("Trực tiếp");
        spinnerList.add("Agribank");
        spinnerList.add("Techcombank");
        spinnerList.add("Vietcombank");
        spinnerList.add("Viettinbank");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerList);

        binding.spinner.setAdapter(arrayAdapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = spinnerList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonOrder:
                order();
                break;
            case R.id.buttonBack:
                finish();
                break;
            default:
                break;
        }
    }

    private void order() {
        if (binding.textLocation.getText().toString().isEmpty()) {
            binding.textLocation.setError("Không được để trống!");
        } else {
            orderSQLiteHelper.insertReceipt(customerName,
                    customerPhoneNumber,
                    category,
                    String.valueOf(sum),
                    binding.textLocation.getText().toString());
            Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}