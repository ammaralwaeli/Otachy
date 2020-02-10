package com.srit.otachy.ui.widgets;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.haytham.coder.otchy.adapters.recyclerAdapter.GovernmentRecyclerAdapter;
import com.srit.otachy.R;
import com.srit.otachy.databinding.GovernmentDialogBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.srit.otachy.adapters.ByBindingAdapterKt.setSpinnerList;

public class GovernmentDialog extends DialogFragment implements GovernmentRecyclerAdapter.ItemListener {


    public interface GovernmentListener {
        void onFinishEditDialog(String inputText);
    }

    private GovernmentListener listener;

    GovernmentDialogBinding binding;
    GovernmentRecyclerAdapter adapter;



    public static GovernmentDialog newInstance() {
        GovernmentDialog frag = new GovernmentDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }



    private void loadGovs() {
        ArrayList<String> govs = new ArrayList<>();
        govs.add("جميع المحافظات");
        govs.add("بغداد");
        govs.add("اربيل");
        govs.add("السليمانية");
        govs.add("دهوك");
        govs.add("كركوك");
        govs.add("سامراء");
        govs.add("نينوى");
        govs.add("الانبار");
        govs.add("صلاح الدين");
        govs.add("ديالى");
        govs.add("الحلة");
        govs.add("كربلاء");
        govs.add("النجف");
        govs.add("القادسية");
        govs.add("المثنى");
        govs.add("واسط");
        govs.add("ذي قار");
        govs.add("ميسان");
        govs.add("البصرة");

        adapter=new GovernmentRecyclerAdapter(govs);


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


        binding = GovernmentDialogBinding.inflate(inflater,container,false);
        loadGovs();
        binding.govRecyclerView.setAdapter(adapter);

        adapter.setItemListener(this);
        return binding.getRoot();
    }

    public void setListener(GovernmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(String itemModel) {

        this.listener.onFinishEditDialog(itemModel);
        dismiss();
    }
}
