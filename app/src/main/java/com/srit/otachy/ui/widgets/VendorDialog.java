package com.srit.otachy.ui.widgets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import com.haytham.coder.otchy.adapters.recyclerAdapter.GovernmentRecyclerAdapter;
import com.haytham.coder.otchy.adapters.recyclerAdapter.VendorRecyclerAdapter;
import com.haytham.coder.otchy.adapters.recyclerAdapter.VendorShopRecyclerAdapter;
import com.srit.otachy.database.local.VendorShopRepository;
import com.srit.otachy.database.models.VendorModel;
import com.srit.otachy.database.models.VendorShopModel;
import com.srit.otachy.databinding.GovernmentDialogBinding;
import com.srit.otachy.databinding.VendorDialogBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class VendorDialog extends DialogFragment implements VendorShopRecyclerAdapter.ItemListener {


    public interface VendorListener {
        void onFinishEditDialog(VendorShopModel inputText);
    }

    private VendorListener listener;

    VendorDialogBinding binding;
    VendorShopRecyclerAdapter adapter;



    public static VendorDialog newInstance() {
        VendorDialog frag = new VendorDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }



    private void loadVendors() {

        Context context=getContext();
        VendorShopRepository repository=new VendorShopRepository(context);

        List<VendorShopModel> vendorShopModel=repository.getItems();
        adapter=new VendorShopRecyclerAdapter(vendorShopModel);

        binding.vendorRecyclerView.setAdapter(adapter);

    }



    @Override
    public void onStart() {
        super.onStart();
        int width=(int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = VendorDialogBinding.inflate(inflater,container,false);
        loadVendors();

        adapter.setItemListener(this);
        return binding.getRoot();
    }

    public void setListener(VendorListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(VendorShopModel itemModel) {
        this.listener.onFinishEditDialog(itemModel);
        dismiss();
    }
}
