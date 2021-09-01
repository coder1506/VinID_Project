package com.example.vinid_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vinid_project.adapter.AdapterItemShop;
import com.example.vinid_project.adapter.iClickRecycleView;
import com.example.vinid_project.databinding.ActivityDetailBinding;
import com.example.vinid_project.models.ShopItem;
import com.example.vinid_project.sqlite.ItemSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class DetailCustomerActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    AdapterItemShop adapterItemShop;
    List<ShopItem> shopItemsPut = new ArrayList<>();
    ItemSQLiteHelper itemSQLiteHelper;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        itemSQLiteHelper = new ItemSQLiteHelper(this);
        setDataToList();

        binding.buttonShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ShoppingCartActivity.class);
                for (int i = 0; i < shopItemsPut.size(); i++){
                    intent.putExtra(String.valueOf(i), shopItemsPut.get(i));
                    count++;
                }
                intent.putExtra("count", count);
                startActivity(intent);
            }
        });
    }

    private void setDataToList() {
        if (itemSQLiteHelper.getCount() == 0) {
            itemSQLiteHelper.insertItems("Rau cải bắp", "10000", "1");
            itemSQLiteHelper.insertItems("Đậu bắp", "15000", "1");
            itemSQLiteHelper.insertItems("Cải thảo", "12000", "1");
        }
        adapterItemShop = new AdapterItemShop(this, itemSQLiteHelper.getAllItemsAdvanced());
        binding.revItems.setAdapter(adapterItemShop);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        binding.revItems.setLayoutManager(manager);
        adapterItemShop.setiAdapterItemShop(new iClickRecycleView() {
            @Override
            public void clickButtonBuy(ShopItem item) {
                shopItemsPut.add(item);
            }
        });
    }
}