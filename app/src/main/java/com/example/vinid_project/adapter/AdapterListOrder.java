package com.example.vinid_project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vinid_project.R;
import com.example.vinid_project.databinding.ItemOrderBinding;
import com.example.vinid_project.models.ShopItem;

import java.util.List;

public class AdapterListOrder extends RecyclerView.Adapter<AdapterListOrder.OrderHolder> {
    private List<ShopItem> shopItems;
    private Context context;

    public AdapterListOrder(List<ShopItem> shopItems, Context context) {
        this.shopItems = shopItems;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterListOrder.OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemOrderBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_order, parent, false);
        return new AdapterListOrder.OrderHolder(binding);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        ShopItem shopItem = shopItems.get(position);
        Glide.with(context)
                .load(R.drawable.vinid_logo)
                .centerCrop()
                .into(holder.binding.imgOrder);
        holder.binding.textNameOrder.setText(shopItem.getNameItem());
        holder.binding.textPriceOrder.setText(shopItem.getPriceItem() + " đ");
        holder.binding.textCountOrder.setText(String.valueOf(shopItem.getCount()));
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        ItemOrderBinding binding;
        public OrderHolder(@NonNull ItemOrderBinding binding1) {
            super(binding1.getRoot());
            binding = binding1;
        }
    }

    private void removeItem(int position) {
        shopItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, shopItems.size());
    }
}
