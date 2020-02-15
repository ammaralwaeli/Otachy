package com.srit.otachy.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;

import com.srit.otachy.R;
import com.srit.otachy.databinding.ActivityOrderBinding;

public class OrderActivity extends AppCompatActivity {


    ActivityOrderBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_order);

    }
}
