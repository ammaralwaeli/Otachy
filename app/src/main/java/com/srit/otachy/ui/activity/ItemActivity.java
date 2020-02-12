package com.srit.otachy.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.srit.otachy.ui.widgets.OrderBottomSheet;

import com.haytham.coder.otchy.adapters.recyclerAdapter.ServiceRecyclerAdapter;
import com.srit.otachy.R;
import com.srit.otachy.database.api.BackendCallBack;
import com.srit.otachy.database.api.DataService;
import com.srit.otachy.database.models.ServiceModel;
import com.srit.otachy.database.models.VendorModel;
import com.srit.otachy.databinding.ActivityItemBinding;
import com.srit.otachy.helpers.BackendHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;

public class ItemActivity extends AppCompatActivity implements ServiceRecyclerAdapter.ItemListener {

    ActivityItemBinding binding;
    ServiceRecyclerAdapter adapter;

    static String USER_ID_EXTRA = "USER_ID_EXTRA";
    static String CAT_ID_EXTRA = "CAT_ID_EXTRA";

    int catID,userid;

    public static void newInstance(Context context, int userId, int catId) {

        Intent in = new Intent(context, ItemActivity.class);
        in.putExtra(USER_ID_EXTRA, userId);
        in.putExtra(CAT_ID_EXTRA, catId);
        context.startActivity(in);
    }


    private void loadItems() {
        DataService service = BackendHelper.INSTANCE.getRetrofitWithAuth()
                .create(DataService.class);

        Intent in = getIntent();

        this.userid = in.getIntExtra(ItemActivity.USER_ID_EXTRA, 0);
        this.catID = in.getIntExtra(ItemActivity.CAT_ID_EXTRA, 0);

        service.getUserItems(this.userid,catID)
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
                        binding.progressIndicator.setVisibility(View.GONE);
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
        binding.progressIndicator.setVisibility(View.VISIBLE);
        loadItems();
    }

    @Override
    public void onItemClick(@NotNull ServiceModel itemModel) {


        ServiceModel.setInstance(itemModel);
        OrderBottomSheet.Factory.newInstance().show(getSupportFragmentManager(),"");

    }
}
