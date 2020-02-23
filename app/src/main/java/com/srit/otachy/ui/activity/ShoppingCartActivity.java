package com.srit.otachy.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.srit.otachy.adapters.recyclerAdapter.ShoppingCartRecyclerAdapter;
import com.srit.otachy.R;
import com.srit.otachy.adapters.ByBindingAdapterKt;
import com.srit.otachy.database.local.ShoppingCartRepository;
import com.srit.otachy.database.local.VendorShopRepository;
import com.srit.otachy.database.models.OrderItemModel;
import com.srit.otachy.database.models.OrderModel;
import com.srit.otachy.database.models.ServiceModel;
import com.srit.otachy.database.models.ShoppingCartItemModel;
import com.srit.otachy.database.models.UserModel;
import com.srit.otachy.database.models.UserVendorModel;
import com.srit.otachy.database.models.VendorModel;
import com.srit.otachy.database.models.VendorShopModel;
import com.srit.otachy.databinding.ActivityShoppingCartBinding;
import com.srit.otachy.helpers.SharedPrefHelper;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingCartActivity extends AppCompatActivity implements ShoppingCartRecyclerAdapter.ItemListener {

    ActivityShoppingCartBinding binding;
    ShoppingCartRecyclerAdapter adapter;
    ShoppingCartRepository repository;

    public static void newInstance(Context context) {
        Intent in = new Intent(context, ShoppingCartActivity.class);
        context.startActivity(in);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart);
        setSupportActionBar(binding.toolbarPlaceholder.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.shopping_cart));
        repository = new ShoppingCartRepository(this);
        loadItems();


    }

    private void updateAdapter() {
        adapter.updateData(repository.getItems(VendorShopModel.instance.getVendorId()));
        if (adapter.getItemCount() == 0) {
            binding.centeredText.setVisibility(View.VISIBLE);
            binding.centeredText.setText(getString(R.string.empty_sopping_cart));
        }
        ByBindingAdapterKt.setPieces(binding.totalPiecesText, adapter.getItemsCount());
        ByBindingAdapterKt.setFormattedPrice(binding.totalPriceText, adapter.getTotalPrice());

    }


    private void loadItems() {

        adapter = new ShoppingCartRecyclerAdapter();
        adapter.setItemListener(this);
        updateAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemDelete(@NotNull ShoppingCartItemModel itemModel) {
        repository.deleteItems(itemModel);
        updateAdapter();
        if(adapter.getItemCount()==0){
            new VendorShopRepository(this).deleteItems(VendorShopModel.instance);
            finish();
        }
    }

    @Override
    public void onItemOrder(@NotNull ShoppingCartItemModel itemModel) {
        List<ShoppingCartItemModel> orders = new ArrayList<>();
        orders.add(itemModel);
        ShoppingCartItemModel.orders = orders;
        OrderItemModel orderItem = new OrderItemModel(
                Integer.parseInt(itemModel.getItemId()),
                Integer.parseInt(itemModel.getCategoryId()),
                (int) itemModel.getNumberOfItems(),
                (int) (itemModel.getTotalPrice() / itemModel.getNumberOfItems()) + "",
                (int) itemModel.getTotalPrice() + "");

        int userid = Integer.parseInt(UserModel.getInstance(SharedPrefHelper.getInstance().getAccessToken()).getId());
        ArrayList<OrderItemModel> orderItemModels = new ArrayList<>();
        orderItemModels.add(orderItem);
        OrderModel order = new OrderModel(
                userid, Integer.parseInt(itemModel.getVendorId()),
                Integer.parseInt(VendorShopModel.instance.getVendorUserId()),
                LocalDateTime.now(), LocalDateTime.now(),
                (int) itemModel.getTotalPrice() + "", "", orderItemModels
        );



        OrderModel.setInstance(order);

        OrderActivity.newInstance(this);
        finish();

    }

    public void orderAll(View view) {
        ShoppingCartItemModel.orders = adapter.getDataList();
        ArrayList<OrderItemModel> orders = new ArrayList<>();
        int vendorId = 0;
        for (ShoppingCartItemModel itemModel : ShoppingCartItemModel.orders) {
            orders.add(new OrderItemModel(
                    Integer.parseInt(itemModel.getItemId()),
                    Integer.parseInt(itemModel.getCategoryId()),
                    (int) (itemModel.getNumberOfItems()),
                    (int) (itemModel.getTotalPrice() / itemModel.getNumberOfItems()) + "",
                    (int) itemModel.getTotalPrice() + ""
            ));
            vendorId = Integer.parseInt(itemModel.getVendorId());
        }
        int userid = Integer.parseInt(UserModel.getInstance(SharedPrefHelper.getInstance().getAccessToken()).getId());

        OrderModel order = new OrderModel(
                userid, vendorId,
                Integer.parseInt(VendorShopModel.instance.getVendorUserId()),
                LocalDateTime.now(), LocalDateTime.now(),
                (int) adapter.getTotalPrice() + "", "", orders
        );


        OrderModel.setInstance(order);

        OrderActivity.newInstance(this);
        finish();
    }
}
