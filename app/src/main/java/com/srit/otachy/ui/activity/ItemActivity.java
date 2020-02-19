package com.srit.otachy.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.srit.otachy.database.models.CategotyModel;
import com.srit.otachy.database.models.VendorModel;
import com.srit.otachy.database.models.VendorShopModel;
import com.srit.otachy.helpers.ViewExtensionsKt;
import com.srit.otachy.ui.Logout;
import com.srit.otachy.ui.widgets.OrderBottomSheet;

import com.srit.otachy.adapters.recyclerAdapter.ServiceRecyclerAdapter;
import com.srit.otachy.R;
import com.srit.otachy.database.api.BackendCallBack;
import com.srit.otachy.database.api.DataService;
import com.srit.otachy.database.models.ServiceModel;
import com.srit.otachy.databinding.ActivityItemBinding;
import com.srit.otachy.helpers.BackendHelper;
import com.srit.otachy.ui.widgets.VendorDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;

public class ItemActivity extends AppCompatActivity implements ServiceRecyclerAdapter.ItemListener, VendorDialog.VendorListener {

    ActivityItemBinding binding;
    ServiceRecyclerAdapter adapter;


    int catID,userid;


    public static void newInstance(Context context) {

        Intent in = new Intent(context, ItemActivity.class);
        context.startActivity(in);
    }

    private void shop() {
        VendorDialog vendorDialog=VendorDialog.newInstance(this);
        if(vendorDialog.getAdapterItemCount()==0){
            ViewExtensionsKt.showSnackBar(binding.contentLayoutItem,getString(R.string.noVendorItem),true);
            return;
        }
        vendorDialog.setListener(this);
        vendorDialog.show(getSupportFragmentManager(),"");
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shoppingCartMenuItem1:
                shop();
                break;
            case R.id.filter:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop, menu);
        return super.onCreateOptionsMenu(menu);
    }



    private void loadItems() {
        DataService service = BackendHelper.INSTANCE.getRetrofitWithAuth()
                .create(DataService.class);



        service.getUserItems(this.userid,this.catID)
                .enqueue(new BackendCallBack<List<ServiceModel>>() {
                    @Override
                    public void onSuccess(List<ServiceModel> result) {
                        adapter = new ServiceRecyclerAdapter(result);
                        if(adapter.getItemCount()==0){
                            binding.centeredText.setVisibility(View.VISIBLE);
                            binding.centeredText.setText(R.string.no_services);
                        }else {
                            binding.centeredText.setVisibility(View.GONE);
                            binding.centeredText.setText("");
                            adapter.setItemListener(ItemActivity.this);
                        }
                        binding.recyclerView.setAdapter(adapter);
                        binding.progressIndicator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        if(code==401){
                            Logout.expireToken(binding.contentLayoutItem,ItemActivity.this);
                        }
                        binding.progressIndicator.setVisibility(View.GONE);
                        binding.centeredText.setVisibility(View.VISIBLE);
                        binding.centeredText.setText(msg + "  " + code);
                    }


                    @Override
                    public void onFailure(@NotNull Call<List<ServiceModel>> call, @NotNull Throwable t) {
                        super.onFailure(call, t);
                        t.printStackTrace();
                        binding.centeredText.setVisibility(View.VISIBLE);
                        binding.centeredText.setText(getString(R.string.connectionError));
                        binding.progressIndicator.setVisibility(View.GONE);
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item);
        setSupportActionBar(binding.toolbarPlaceholder.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.services));
        binding.progressIndicator.setVisibility(View.VISIBLE);
        this.catID= CategotyModel.getInstance().getId();
        userid= VendorModel.getInstance().getUser().getId();
        loadItems();
    }

    @Override
    public void onItemClick(@NotNull ServiceModel itemModel) {


        ServiceModel.setInstance(itemModel);
        OrderBottomSheet.Factory.newInstance().show(getSupportFragmentManager(),"");

    }

    @Override
    public void onFinishEditDialog(VendorShopModel inputText) {
        VendorShopModel.instance=inputText;
        ShoppingCartActivity.newInstance(this);
        Toast.makeText(this, "Ammar", Toast.LENGTH_LONG).show();
    }
}
