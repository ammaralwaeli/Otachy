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

import com.haytham.coder.otchy.adapters.recyclerAdapter.CategoryRecyclerAdapter;
import com.srit.otachy.R;
import com.srit.otachy.database.api.BackendCallBack;
import com.srit.otachy.database.api.DataService;
import com.srit.otachy.database.models.CategotyModel;
import com.srit.otachy.database.models.UserCategories;
import com.srit.otachy.database.models.VendorShopModel;
import com.srit.otachy.databinding.ActivityCatigoryBinding;
import com.srit.otachy.helpers.BackendHelper;
import com.srit.otachy.ui.Logout;
import com.srit.otachy.ui.widgets.VendorDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;

public class CatigoryActivity extends AppCompatActivity implements CategoryRecyclerAdapter.ItemListener, VendorDialog.VendorListener {


    ActivityCatigoryBinding binding;
    CategoryRecyclerAdapter adapter;
    static String USER_ID_EXTRA="USER_ID_EXTRA";

    int userid;

    public static void newInstance(Context context,int userId) {

        Intent in=new Intent(context, CatigoryActivity.class);
        in.putExtra(USER_ID_EXTRA,userId);
        context.startActivity(in);
    }

    private void loadCategories(){
        DataService service = BackendHelper.INSTANCE.getRetrofitWithAuth()
                .create(DataService.class);

        Intent in=getIntent();

        this.userid=in.getIntExtra(CatigoryActivity.USER_ID_EXTRA,0);


        service.getUserCategories(this.userid)
                .enqueue(new BackendCallBack<List<UserCategories>>() {
                    @Override
                    public void onSuccess(List<UserCategories> result) {
                        adapter = new CategoryRecyclerAdapter(result);
                        if(adapter.getItemCount()==0){
                            binding.errorText.setVisibility(View.VISIBLE);
                            binding.errorText.setText(R.string.noCategories);
                        }else {
                            binding.errorText.setVisibility(View.GONE);
                            binding.errorText.setText("");
                        }
                        adapter.setItemListener(CatigoryActivity.this);
                        binding.recyclerView.setAdapter(adapter);
                        binding.progressIndicator2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        if(code==401){
                            Logout.expireToken(binding.contentLayoutCat,CatigoryActivity.this);
                        }
                        binding.progressIndicator2.setVisibility(View.GONE);
                        binding.errorText.setVisibility(View.VISIBLE);
                        binding.errorText.setText(msg + "  " + code);
                    }


                    @Override
                    public void onFailure(@NotNull Call<List<UserCategories>> call, @NotNull Throwable t) {
                        super.onFailure(call, t);
                        t.printStackTrace();
                        binding.errorText.setVisibility(View.VISIBLE);
                        binding.errorText.setText(getString(R.string.connectionError));
                        binding.progressIndicator2.setVisibility(View.GONE);

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_catigory);
        setSupportActionBar(binding.toolbarPlaceholder.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.categories));
        binding.progressIndicator2.setVisibility(View.VISIBLE);
        loadCategories();
    }

    @Override
    public void onItemClick(@NotNull UserCategories itemModel) {
        CategotyModel.setInstance(itemModel.getCategory());
        ItemActivity.newInstance(this,this.userid,itemModel.getCategory().getId());
    }

    private void shop() {
        VendorDialog vendorDialog=new VendorDialog();
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

    @Override
    public void onFinishEditDialog(VendorShopModel inputText) {
        VendorShopModel.instance=inputText;
        ShoppingCartActivity.newInstance(this);
        Toast.makeText(this, inputText.toString(), Toast.LENGTH_LONG).show();
    }
}
