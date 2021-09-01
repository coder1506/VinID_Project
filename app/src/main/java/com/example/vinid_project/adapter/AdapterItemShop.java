package com.example.vinid_project.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vinid_project.R;
import com.example.vinid_project.databinding.ItemShopBinding;
import com.example.vinid_project.models.ShopItem;

import java.util.List;

public class AdapterItemShop extends RecyclerView.Adapter<AdapterItemShop.ShopHolder> {
    private iClickRecycleView iClickRecycleView;
    private Context context;
    private List<ShopItem> shopItemList;

    public void setiAdapterItemShop(iClickRecycleView iAdapterItemShop) {
        this.iClickRecycleView = iAdapterItemShop;
    }

    public AdapterItemShop(Context context, List<ShopItem> shopItemList) {
        this.context = context;
        this.shopItemList = shopItemList;
    }

    @NonNull
    @Override
    public ShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemShopBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_shop, parent, false);
        return new ShopHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopHolder holder, int position) {
        ShopItem shopItem = shopItemList.get(position);

        Glide.with(context).load(R.drawable.vinid_logo).into(holder.binding.imgItemShop);
        holder.binding.textNameItem.setText(shopItem.getNameItem());
        holder.binding.textPrice.setText(shopItem.getPriceItem() + " đ");
        holder.binding.textCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                shopItem.setCount(holder.binding.textCount.getText().toString());
            }
        });
        shopItem.setCount(holder.binding.textCount.getText().toString());
        holder.binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickRecycleView.clickButtonBuy(shopItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopItemList.size();
    }

    public class ShopHolder extends RecyclerView.ViewHolder {
        ItemShopBinding binding;
        public ShopHolder(@NonNull ItemShopBinding binding1) {
            super(binding1.getRoot());
            binding = binding1;
        }
    }
}
