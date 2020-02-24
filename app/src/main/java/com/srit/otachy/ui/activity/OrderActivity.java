package com.srit.otachy.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.srit.otachy.adapters.recyclerAdapter.ServiceRecyclerAdapter;
import com.srit.otachy.R;
import com.srit.otachy.adapters.ByBindingAdapterKt;
import com.srit.otachy.adapters.LocalDateTimeConverterKt;
import com.srit.otachy.database.api.BackendCallBack;
import com.srit.otachy.database.api.DataService;
import com.srit.otachy.database.local.ShoppingCartRepository;
import com.srit.otachy.database.local.VendorShopRepository;
import com.srit.otachy.database.models.OrderModel;
import com.srit.otachy.database.models.ServiceModel;
import com.srit.otachy.database.models.ShoppingCartItemModel;
import com.srit.otachy.database.models.VendorModel;
import com.srit.otachy.database.models.VendorShopModel;
import com.srit.otachy.databinding.ActivityOrderBinding;
import com.srit.otachy.helpers.BackendHelper;
import com.srit.otachy.helpers.ViewExtensionsKt;
import com.srit.otachy.ui.Logout;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

public class OrderActivity extends AppCompatActivity {


    ActivityOrderBinding binding;
    int mYear, mMonth, mDay, mHour, mMinute;
    LocalDate date;
    LocalTime time;
    LocalDateTime dateTime;
    ShoppingCartRepository repository;
    VendorShopRepository vendorShopRepository;
    LocalDateTime reciveDate, sendDate;

    public static void newInstance(Context context) {
        Intent in = new Intent(context, OrderActivity.class);
        context.startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order);

        homeReceive();
        binding.sendTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(true);
            }
        });

        binding.receiveTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(false);
            }
        });

    }


    private void homeReceive() {
        if (VendorShopModel.instance.getHomeRecieve() != 1) {
            binding.recivedId.setVisibility(View.GONE);
            binding.sendTimeEditText.setVisibility(View.GONE);
        } else {
            binding.recivedId.setVisibility(View.VISIBLE);
            binding.sendTimeEditText.setVisibility(View.VISIBLE);
        }
    }

    private void showDatePicker(final boolean sendTimeEditText) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                        showTimePicker(sendTimeEditText);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showTimePicker(final boolean sendTimeEditText) {
// Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        time = LocalTime.of(hourOfDay, minute);
                        dateTime = LocalDateTime.of(date, time);

                        if (sendTimeEditText) {
                            if(dateTime.isBefore(LocalDateTime.now())){
                                ViewExtensionsKt.showSnackBar(binding.layout,getString(R.string.cant_set_old_date),true);
                            }else {
                                ByBindingAdapterKt.setTextFromDate(binding.sendTimeEditText, dateTime);
                                sendDate = dateTime;
                                //binding.sendTimeEditText.setText(dateTime.toString());
                            }
                        } else {
                            ByBindingAdapterKt.setTextFromDate(binding.receiveTimeEditText, dateTime);
                            reciveDate = dateTime;
                            //binding.receiveTimeEditText.setText(dateTime.toString());
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ShoppingCartActivity.newInstance(this);
    }

    public void order(View view) {

        if(sendDate.isAfter(reciveDate)){
            ViewExtensionsKt.showSnackBar(binding.layout,getString(R.string.after_date),true);
            return;
        }
        binding.progressIndicator.setVisibility(View.VISIBLE);
        binding.orderBtn.setText("");
        OrderModel orderModel = OrderModel.getInstance();
        orderModel.setDeleverDate(reciveDate);
        orderModel.setReceiveDate(sendDate);
        orderModel.setDescrition(binding.orderDescriptionEditText.getText().toString());
        sendOrder(orderModel);
    }

    private void sendOrder(OrderModel orderModel) {
        DataService service = BackendHelper.INSTANCE.getRetrofitWithAuth()
                .create(DataService.class);

        repository = new ShoppingCartRepository(this);
        vendorShopRepository = new VendorShopRepository(this);
        service.addOrder(orderModel)
                .enqueue(new BackendCallBack<String>() {
                    @Override
                    public void onSuccess(String result) {
                        binding.progressIndicator.setVisibility(View.GONE);
                        if (result.equals("Order added successfully")) {
                            ViewExtensionsKt.showSnackBar(binding.layout, getString(R.string.order_sent), false);
                            for (ShoppingCartItemModel item : ShoppingCartItemModel.orders) {
                                repository.deleteItems(item);
                            }
                            //new VendorShopRepository(OrderActivity.this).deleteItems(VendorShopModel.instance);
                            vendorShopRepository.deleteItems(VendorShopModel.instance);
                            finish();
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        binding.progressIndicator.setVisibility(View.GONE);
                        if (code == 401) {
                            Logout.expireToken(binding.layout, OrderActivity.this);
                        }
                        binding.orderBtn.setText(getString(R.string.order));
                        binding.progressIndicator.setVisibility(View.GONE);
                        ViewExtensionsKt.showSnackBar(binding.layout, msg + "  " + code, true);
                    }


                    @Override
                    public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                        super.onFailure(call, t);
                        binding.progressIndicator.setVisibility(View.GONE);
                        t.printStackTrace();
                        binding.orderBtn.setText(getString(R.string.order));
                        ViewExtensionsKt.showSnackBar(binding.layout, getString(R.string.connectionError), true);
                        binding.progressIndicator.setVisibility(View.GONE);
                    }
                });
    }

}
