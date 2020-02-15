package com.srit.otachy.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haytham.coder.otchy.adapters.recyclerAdapter.ShoppingCartRecyclerAdapter;
import com.srit.otachy.R;
import com.srit.otachy.adapters.ByBindingAdapterKt;
import com.srit.otachy.database.local.ShoppingCartRepository;
import com.srit.otachy.database.local.VendorShopRepository;
import com.srit.otachy.database.models.ShoppingCartItemModel;
import com.srit.otachy.database.models.VendorShopModel;
import com.srit.otachy.databinding.ActivityShoppingCartBinding;

import org.jetbrains.annotations.NotNull;

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
        binding= DataBindingUtil.setContentView(this,R.layout.activity_shopping_cart);
        setSupportActionBar(binding.toolbarPlaceholder.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.shopping_cart));
        repository=new ShoppingCartRepository(this);
        loadItems();


    }

    private void updateAdapter(){
        adapter.updateData(repository.getItems(VendorShopModel.instance.getVendorId()));
        if(adapter.getItemCount()==0){
            binding.centeredText.setVisibility(View.VISIBLE);
            binding.centeredText.setText(getString(R.string.empty_sopping_cart));
        }
        ByBindingAdapterKt.setPieces(binding.totalPiecesText,adapter.getItemsCount());
        ByBindingAdapterKt.setFormattedPrice(binding.totalPriceText,adapter.getTotalPrice());

    }


    private void loadItems(){

        adapter=new ShoppingCartRecyclerAdapter();
        adapter.setItemListener(this);
        updateAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemDelete(@NotNull ShoppingCartItemModel itemModel) {
        repository.deleteItems(itemModel);
        updateAdapter();
    }

    @Override
    public void onItemOrder(@NotNull ShoppingCartItemModel itemModel) {

    }

    public void orderAll(View view) {
        
    }
}
