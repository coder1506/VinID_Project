package com.example.vinid_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.vinid_project.databinding.ActivityAdminBinding;
import com.example.vinid_project.models.Customer;
import com.example.vinid_project.models.Receipt;
import com.example.vinid_project.models.ShopItem;
import com.example.vinid_project.sqlite.CustomerSQLiteHelper;
import com.example.vinid_project.sqlite.ItemSQLiteHelper;
import com.example.vinid_project.sqlite.OrderSQLiteHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminActivity extends AppCompatActivity implements OnClickListener {
    ActivityAdminBinding binding;
    CustomerSQLiteHelper customerSQLiteHelper;
    ItemSQLiteHelper itemSQLiteHelper;
    OrderSQLiteHelper orderSQLiteHelper;

    ArrayAdapter<Customer> customerAdapter;
    List<Customer> customerList = new ArrayList<>();

    ArrayAdapter<ShopItem> itemsAdapter;
    List<ShopItem> itemsList = new ArrayList<>();

    ArrayAdapter<Receipt> receiptAdapter;
    List<Receipt> receiptList = new ArrayList<>();

    String receipt, category;
    int idCustomer, idItems, idReceipt;
    int viTriCustomer, viTriItems, viTriReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin);

        customerSQLiteHelper = new CustomerSQLiteHelper(this);
        itemSQLiteHelper = new ItemSQLiteHelper(this);
        orderSQLiteHelper = new OrderSQLiteHelper(this);

        initDataCustomer();
        initDataItems();
        initDataReceipt();
        editSpinner();

        optionMenu();

        binding.buttonAddCustomers.setOnClickListener(this);
        binding.buttonEditCustomers.setOnClickListener(this);
    }

    private void initDataCustomer() {
        customerList = customerSQLiteHelper.getAllCustomersAdvanced();

        binding.textCodeCustomer.setText("KH"+UUID.randomUUID());
        customerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customerList);
        binding.lvCustomer.setAdapter(customerAdapter);

        // Lấy id khách hàng
        binding.lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.textCodeCustomer.setText(customerList.get(position).getCode());
                binding.textNameCustomer.setText(customerList.get(position).getName());
                binding.textEmailCustomer.setText(customerList.get(position).getEmail());
                binding.textPhoneNumberCustomer.setText(customerList.get(position).getPhoneNumber());
                binding.textAddressCustomer.setText(customerList.get(position).getAddress());
                viTriCustomer = position;
                idCustomer = customerList.get(position).getId();
                Log.d("IdCustomer", String.valueOf(id));
            }
        });

        // Xóa dữ liệu
        binding.lvCustomer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                customerSQLiteHelper.deleteCustomers(customerList.get(position).getId());
                customerList.remove(position);
                customerAdapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Xóa dữ liệu thành công", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void initDataItems() {
        itemsList = itemSQLiteHelper.getAllItemsAdvanced();

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemsList);
        binding.lvItems.setAdapter(itemsAdapter);

        // Lấy id khách hàng
        binding.lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.textNameItems.setText(itemsList.get(position).getNameItem());
                binding.textPriceItems.setText(itemsList.get(position).getPriceItem());
                viTriItems = position;
                idItems = itemsList.get(position).getId();
            }
        });

        // Xóa dữ liệu
        binding.lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemSQLiteHelper.deleteItems(itemsList.get(position).getId());
                itemsList.remove(position);
                itemsAdapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Xóa dữ liệu thành công", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void initDataReceipt() {
        receiptList = orderSQLiteHelper.getAllReceiptAdvanced();

        receiptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, receiptList);
        binding.lvReceipt.setAdapter(receiptAdapter);

        // Lấy id khách hàng
        binding.lvReceipt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.textNameReceipt.setText(receiptList.get(position).getName());
                binding.textPhoneNumberReceipt.setText(receiptList.get(position).getPhoneNumber());
                binding.textMoney.setText(receiptList.get(position).getMoney());
                binding.textLocationReceipt.setText(receiptList.get(position).getLocation());
                viTriReceipt = position;
                idReceipt = receiptList.get(position).getId();
            }
        });

        // Xóa dữ liệu
        binding.lvReceipt.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                orderSQLiteHelper.deleteReceipt(receiptList.get(position).getId());
                receiptList.remove(position);
                receiptAdapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Xóa dữ liệu thành công", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void editSpinner() {
        // Spinner loaị thanh toán
        ArrayList<String> spinnerList = new ArrayList<>();
        spinnerList.add("Trực tiếp");
        spinnerList.add("Agribank");
        spinnerList.add("Techcombank");
        spinnerList.add("Vietcombank");
        spinnerList.add("Viettinbank");
        ArrayAdapter receiptAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerList);

        binding.spinnerReceipt.setAdapter(receiptAdapter);

        binding.spinnerReceipt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                receipt = spinnerList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner đơn vị đo mặt hàng
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("kg");
        arrayList.add("lốc");
        arrayList.add("mét");
        arrayList.add("cái");
        arrayList.add("chiếc");
        ArrayAdapter itemAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList);

        binding.spinnerCategoryItems.setAdapter(itemAdapter);

        binding.spinnerCategoryItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = arrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void optionMenu() {
        binding.buttonMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(getBaseContext(), v);
                menu.getMenuInflater().inflate(R.menu.item_option_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.buttonCustomers:
                                customers();
                                break;
                            case R.id.buttonItems:
                                items();
                                break;
                            case R.id.buttonReceipt:
                                receipts();
                                break;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });
    }

    private void receipts() {
        binding.textSelection.setText(R.string.receipt);
        binding.layoutCustomerSelection.setVisibility(View.GONE);
        binding.layoutItemsSelection.setVisibility(View.GONE);
        binding.layoutReceiptSelection.setVisibility(View.VISIBLE);

        binding.buttonAddReceipt.setOnClickListener(this);
        binding.buttonEditReceipt.setOnClickListener(this);
    }

    private void items() {
        binding.textSelection.setText(R.string.items);
        binding.layoutCustomerSelection.setVisibility(View.GONE);
        binding.layoutItemsSelection.setVisibility(View.VISIBLE);
        binding.layoutReceiptSelection.setVisibility(View.GONE);

        binding.buttonAddItems.setOnClickListener(this);
        binding.buttonEditItems.setOnClickListener(this);
    }

    private void customers() {
        binding.textSelection.setText(R.string.customers);
        binding.layoutCustomerSelection.setVisibility(View.VISIBLE);
        binding.layoutItemsSelection.setVisibility(View.GONE);
        binding.layoutReceiptSelection.setVisibility(View.GONE);

        binding.buttonAddCustomers.setOnClickListener(this);
        binding.buttonEditCustomers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddCustomers:
                addCustomers();
                break;
            case R.id.buttonEditCustomers:
                editCustomers();
                break;
            case R.id.buttonAddItems:
                addItems();
                break;
            case R.id.buttonEditItems:
                editItems();
                break;
            case R.id.buttonAddReceipt:
                addReceipt();
                break;
            case R.id.buttonEditReceipt:
                editReceipt();
                break;
            default:
                break;
        }
    }

    private void editReceipt() {
        receiptList.set(viTriReceipt, new Receipt(binding.textNameReceipt.getText().toString(),
                binding.textPhoneNumberReceipt.getText().toString(),
                receipt,
                binding.textMoney.getText().toString(),
                binding.textLocationReceipt.getText().toString()));
        receiptAdapter.notifyDataSetChanged();
        orderSQLiteHelper.updateReceipt(idReceipt,
                binding.textNameReceipt.getText().toString(),
                binding.textPhoneNumberReceipt.getText().toString(),
                receipt,
                binding.textMoney.getText().toString(),
                binding.textLocationReceipt.getText().toString());
        Toast.makeText(getBaseContext(), "Cập nhật dữ liệu thành công!", Toast.LENGTH_SHORT).show();
    }

    private void addReceipt() {
        if (binding.textNameReceipt.getText().toString().isEmpty()) {
            binding.textNameReceipt.setError("Không được để trống!");
        } else if (binding.textPhoneNumberReceipt.getText().toString().isEmpty()) {
            binding.textPhoneNumberReceipt.setError("Không được để trống!");
        } else if (binding.textMoney.getText().toString().isEmpty()) {
            binding.textMoney.setError("Không được để trống!");
        } else if (binding.textLocationReceipt.getText().toString().isEmpty()) {
            binding.textLocationReceipt.setError("Không được để trống!");
        } else {
            receiptList.add(new Receipt(binding.textNameReceipt.getText().toString(),
                    binding.textPhoneNumberReceipt.getText().toString(),
                    receipt,
                    binding.textMoney.getText().toString(),
                    binding.textLocationReceipt.getText().toString()));
            receiptAdapter.notifyDataSetChanged();

            orderSQLiteHelper.insertReceipt(binding.textNameReceipt.getText().toString(),
                    binding.textPhoneNumberReceipt.getText().toString(),
                    receipt,
                    binding.textMoney.getText().toString(),
                    binding.textLocationReceipt.getText().toString());
            Toast.makeText(getBaseContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private void editItems() {
        itemsList.set(viTriItems, new ShopItem(binding.textNameItems.getText().toString(),
                binding.textPriceItems.getText().toString(),
                "1"));
        itemsAdapter.notifyDataSetChanged();
        itemSQLiteHelper.updateItems(idItems,
                binding.textNameItems.getText().toString(),
                binding.textPriceItems.getText().toString(),
                "1");
        Toast.makeText(getBaseContext(), "Cập nhật dữ liệu thành công!", Toast.LENGTH_SHORT).show();
    }

    private void addItems() {
        if (binding.textNameItems.getText().toString().isEmpty()) {
            binding.textNameItems.setError("Không được để trống!");
        } else if (binding.textPriceItems.getText().toString().isEmpty()) {
            binding.textPriceItems.setError("Không được để trống!");
        } else {
            itemsList.add(new ShopItem(binding.textNameItems.getText().toString(),
                    binding.textPriceItems.getText().toString(), "1"));
            itemsAdapter.notifyDataSetChanged();

            itemSQLiteHelper.insertItems(binding.textNameItems.getText().toString(),
                    binding.textPriceItems.getText().toString(), "1");
            Toast.makeText(getBaseContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private void editCustomers() {
        customerList.set(viTriCustomer, new Customer(
                binding.textCodeCustomer.getText().toString(),
                binding.textNameCustomer.getText().toString(),
                binding.textEmailCustomer.getText().toString(),
                binding.textPhoneNumberCustomer.getText().toString(),
                binding.textAddressCustomer.getText().toString()
        ));
        customerAdapter.notifyDataSetChanged();
        customerSQLiteHelper.updateCustomers(idCustomer,
                binding.textNameCustomer.getText().toString(),
                binding.textPhoneNumberCustomer.getText().toString());
        Toast.makeText(getBaseContext(), "Cập nhật dữ liệu thành công!", Toast.LENGTH_SHORT).show();
    }

    private void addCustomers() {
        if (binding.textNameCustomer.getText().toString().isEmpty()) {
            binding.textNameCustomer.setError("Không được để trống!");
        } else if (binding.textPhoneNumberCustomer.getText().toString().isEmpty()) {
            binding.textPhoneNumberCustomer.setError("Không được để trống!");
        } else {
            customerList.add(new Customer(
                    binding.textCodeCustomer.getText().toString(),
                    binding.textNameCustomer.getText().toString(),
                    binding.textEmailCustomer.getText().toString(),
                    binding.textPhoneNumberCustomer.getText().toString(),
                    binding.textAddressCustomer.getText().toString()
            ));
            customerAdapter.notifyDataSetChanged();

            customerSQLiteHelper.insertCustomers(
                    binding.textCodeCustomer.getText().toString(),
                    binding.textNameCustomer.getText().toString(),
                    binding.textEmailCustomer.getText().toString(),
                    binding.textPhoneNumberCustomer.getText().toString(),
                    binding.textAddressCustomer.getText().toString()
            );
            Toast.makeText(getBaseContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
            binding.textCodeCustomer.setText(UUID.randomUUID().toString());
        }
    }
}