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
import android.widget.Toast;

import com.srit.otachy.R;
import com.srit.otachy.adapters.ByBindingAdapterKt;
import com.srit.otachy.adapters.LocalDateTimeConverterKt;
import com.srit.otachy.database.local.ShoppingCartRepository;
import com.srit.otachy.database.models.OrderModel;
import com.srit.otachy.database.models.ShoppingCartItemModel;
import com.srit.otachy.databinding.ActivityOrderBinding;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.util.Calendar;

public class OrderActivity extends AppCompatActivity {


    ActivityOrderBinding binding;
    int mYear, mMonth, mDay, mHour, mMinute;
    LocalDate date;
    LocalTime time;
    LocalDateTime dateTime;

    LocalDateTime reciveDate,sendDate;
    public static void newInstance(Context context) {
        Intent in = new Intent(context, OrderActivity.class);
        context.startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order);

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
                        date=LocalDate.of(year,monthOfYear+1,dayOfMonth);
                        Toast.makeText(OrderActivity.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
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
                        time=LocalTime.of(hourOfDay,minute);
                        Toast.makeText(OrderActivity.this, hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                        dateTime=LocalDateTime.of(date,time);
                        if(sendTimeEditText) {
                            ByBindingAdapterKt.setTextFromDate(binding.sendTimeEditText,dateTime);
                            sendDate=LocalDateTime.parse(ByBindingAdapterKt.getTextFromDate(dateTime), LocalDateTimeConverterKt.getDateTimeBackendFormatter());
                            //binding.sendTimeEditText.setText(dateTime.toString());
                        }else{
                            ByBindingAdapterKt.setTextFromDate(binding.receiveTimeEditText,dateTime);
                            reciveDate=LocalDateTime.parse(ByBindingAdapterKt.getTextFromDate(dateTime), LocalDateTimeConverterKt.getDateTimeBackendFormatter());
                            //binding.receiveTimeEditText.setText(dateTime.toString());
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void order(View view) {
        OrderModel orderModel=OrderModel.getInstance();
        orderModel.setDeleverDate(sendDate);
        orderModel.setReceiveDate(reciveDate);
        orderModel.setDescrition(binding.orderDescriptionEditText.getText().toString());

        binding.orderDescriptionEditText.setText(orderModel.toString());

        ShoppingCartRepository repository=new ShoppingCartRepository(this);
        repository.deleteItems(ShoppingCartItemModel.instance);

        //Toast.makeText(this,orderModel.toString(),Toast.LENGTH_LONG).show();
    }
}
