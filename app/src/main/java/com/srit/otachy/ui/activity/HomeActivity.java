package com.srit.otachy.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haytham.coder.otchy.adapters.recyclerAdapter.VendorRecyclerAdapter;
import com.srit.otachy.R;
import com.srit.otachy.database.api.BackendCallBack;
import com.srit.otachy.database.api.DataService;
import com.srit.otachy.database.models.Governments;
import com.srit.otachy.database.models.UserModel;
import com.srit.otachy.database.models.VendorModel;
import com.srit.otachy.database.models.VerificateionModel;
import com.srit.otachy.databinding.ActivityHomeBinding;
import com.srit.otachy.helpers.BackendHelper;
import com.srit.otachy.helpers.ViewExtensionsKt;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    VendorRecyclerAdapter adapter;


    public static void newInstance(Context context){
        context.startActivity(new Intent(context, HomeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        loadVendors();



    }

    private void loadVendors() {
        DataService service = BackendHelper.INSTANCE.getRetrofit()
                .create(DataService.class);

        service.getVendors(new Governments("بغداد"))
                .enqueue(new BackendCallBack<List<VendorModel>>() {
                    @Override
                    public void onSuccess(List<VendorModel> result) {
                        adapter=new VendorRecyclerAdapter(result);

                        ViewExtensionsKt.createGridLayout(binding.homeRecyclerView,adapter);

                    }

                    @Override
                    public void onError(int code, String msg) {
                        binding.progressIndicator2.setVisibility(View.GONE);

                        binding.errorText.setText( msg + "  " + code);
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


}
