package com.srit.otachy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.haytham.coder.otchy.adapters.recyclerAdapter.VendorRecyclerAdapter;
import com.srit.otachy.R;
import com.srit.otachy.database.api.BackendCallBack;
import com.srit.otachy.database.api.DataService;
import com.srit.otachy.database.models.UserModel;
import com.srit.otachy.database.models.VendorModel;
import com.srit.otachy.databinding.ActivityHomeBinding;
import com.srit.otachy.helpers.BackendHelper;
import com.srit.otachy.helpers.SharedPrefHelper;
import com.srit.otachy.helpers.ViewExtensionsKt;
import com.srit.otachy.ui.widgets.GovernmentDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;

public class HomeActivity extends AppCompatActivity implements GovernmentDialog.GovernmentListener,VendorRecyclerAdapter.ItemListener {

    ActivityHomeBinding binding;
    VendorRecyclerAdapter adapter;

    String selectedGov;


    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.progressIndicator2.setVisibility(View.VISIBLE);
        loadVendors(false);
        setSupportActionBar(binding.toolbar.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.home));

        binding.searchCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
                binding.homeRecyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.progressIndicator2.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void shop() {

    }


    private void filter() {
        GovernmentDialog governmentDialog = GovernmentDialog.newInstance();
        governmentDialog.setListener(this);
        governmentDialog.show(getSupportFragmentManager(), "");

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shoppingCartMenuItem:
                shop();
                break;
            case R.id.filter:
                filter();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadVendors(boolean filter) {
        DataService service = BackendHelper.INSTANCE.getRetrofitWithAuth()
                .create(DataService.class);

        String gov = UserModel.getInstance(SharedPrefHelper.getInstance().getAccessToken()).getGovernment();
        if(filter){
            gov=this.selectedGov;
        }

        service.getVendors(gov)
                .enqueue(new BackendCallBack<List<VendorModel>>() {
                    @Override
                    public void onSuccess(List<VendorModel> result) {
                        adapter = new VendorRecyclerAdapter(result);
                        if(adapter.getItemCount()==0){
                            binding.errorText.setVisibility(View.VISIBLE);
                            binding.errorText.setText(R.string.noVendors);
                        }
                        else {
                            binding.errorText.setVisibility(View.GONE);
                            binding.errorText.setText("");
                        }
                        adapter.setItemListener(HomeActivity.this);
                        ViewExtensionsKt.createGridLayout(binding.homeRecyclerView, adapter);
                        binding.progressIndicator2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        binding.progressIndicator2.setVisibility(View.GONE);
                        binding.errorText.setText(msg + "  " + code);
                    }


                    @Override
                    public void onFailure(@NotNull Call<List<VendorModel>> call, @NotNull Throwable t) {
                        super.onFailure(call, t);
                        t.printStackTrace();
                        binding.errorText.setVisibility(View.VISIBLE);
                        binding.errorText.setText(getString(R.string.connectionError));
                        binding.progressIndicator2.setVisibility(View.GONE);

                    }
                });
    }


    @Override
    public void onFinishEditDialog(String inputText) {
        //Toast.makeText(this, inputText, Toast.LENGTH_LONG).show();
        if(inputText.equals("جميع المحافظات")){
            this.selectedGov=null;

        }else {
            this.selectedGov = inputText;
        }
        binding.progressIndicator2.setVisibility(View.VISIBLE);
        loadVendors(true);

    }

    @Override
    public void onItemClick(@NotNull VendorModel itemModel) {

        VendorModel.setInstance(itemModel);
        Toast.makeText(this, itemModel.getUser().getId()+"", Toast.LENGTH_LONG).show();
        CatigoryActivity.newInstance(this,itemModel.getUser().getId());
    }
}
