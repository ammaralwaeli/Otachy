package com.srit.otachy.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.srit.otachy.R;
import com.srit.otachy.database.api.BackendCallBack;
import com.srit.otachy.database.api.DataService;
import com.srit.otachy.database.models.RegisterModel;
import com.srit.otachy.database.models.VerificateionModel;
import com.srit.otachy.databinding.ActivityRegisterBinding;
import com.srit.otachy.helpers.BackendHelper;
import com.srit.otachy.helpers.ViewExtensionsKt;
import com.tiper.MaterialSpinner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import retrofit2.Call;

import static com.srit.otachy.adapters.ByBindingAdapterKt.setSpinnerList;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private String mGov = "";
    private boolean verificate;
    private int code;

    @Override
    public void onBackPressed() {

        if (verificate && this.code != -1) {
            showRegisterLayout();
            verificate = false;
            this.code = -1;
        }
    }

    private void showRegisterLayout() {
        binding.registerView.setVisibility(View.VISIBLE);
        binding.verificationView.setVisibility(View.GONE);
        binding.titleText.setText(R.string.register);
        binding.registerButton.setVisibility(View.VISIBLE);
        binding.verifyButton.setVisibility(View.GONE);

    }




    private void showVerifyLayout() {
        binding.registerView.setVisibility(View.GONE);
        binding.verificationView.setVisibility(View.VISIBLE);
        binding.titleText.setText(R.string.verifyAccount);
        binding.registerButton.setVisibility(View.GONE);
        binding.verifyButton.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        loadGovs();
        verificate = false;
        code = -1;
        showRegisterLayout();
        binding.governorate.setOnItemSelectedListener(new MaterialSpinner.
                OnItemSelectedListener() {
            @Override
            public void onItemSelected(@NotNull MaterialSpinner materialSpinner,
                                       @Nullable View view, int i, long l) {
                try {
                    materialSpinner.setHint("");
                    mGov = materialSpinner.getSelectedItem().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(@NotNull MaterialSpinner materialSpinner) {
                materialSpinner.setHint(getString(R.string.gov));
            }
        });
    }

    private void loadGovs() {
        ArrayList<String> govs = new ArrayList<>();
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

        setSpinnerList(binding.governorate, govs);

    }

    private boolean isValidPhoneNumber(String phone) {

        if ((phone.startsWith("+96477") ||
                phone.startsWith("+96478") ||
                phone.startsWith("+96479") ||
                phone.startsWith("+96475")) &&
                phone.length() == 14) {
            return true;
        } else {
            return false;
        }
    }

    private Pair<Boolean, String> validataData() {
        int MINMUMPASSWORD = 6;

        if (binding.username.getText().toString().equals("")) {
            return new Pair<>(false, getString(R.string.enterUsername));
        } else if (binding.phoneNumber.getText().toString().equals("")) {
            return new Pair<>(false, getString(R.string.enterPhone));
        } else if (!isValidPhoneNumber(binding.phoneNumber.getText().toString())) {
            return new Pair<>(false, getString(R.string.invalidPhone));
        } else if (mGov.equals("")) {
            return new Pair<>(false, getString(R.string.selectGov));
        } else if (binding.city.getText().toString().equals("")) {
            return new Pair<>(false, getString(R.string.enterCity));
        } else if (binding.pass.getText().toString().equals("")) {
            return new Pair<>(false, getString(R.string.enterPass));
        } else if (binding.pass.getText().toString().length() < MINMUMPASSWORD) {
            return new Pair<>(false, getString(R.string.mustPassMoreSix));
        } else {
            return new Pair<>(true, "");
        }

    }

    public void register(View view) {
        try {

            binding.progressIndicator.setVisibility(View.VISIBLE);
            if (!verificate && this.code == -1) {
                Pair<Boolean, String> pair = validataData();
                if (!pair.first) {
                    ViewExtensionsKt.showSnackBar(binding.contentLayout, pair.second,
                            true);
                    binding.progressIndicator.setVisibility(View.GONE);
                    return;
                }

                DataService service = BackendHelper.INSTANCE.getRetrofit().
                        create(DataService.class);
                service.register(
                        new RegisterModel(binding.username.getText().toString(),
                                binding.pass.getText().toString(),
                                binding.phoneNumber.getText().toString(),
                                mGov,
                                binding.city.getText().toString())
                ).enqueue(new BackendCallBack<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject result) {
                        showVerifyLayout();
                        verificate = true;
                        code = result.get("CodeId").getAsInt();
                        binding.progressIndicator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        binding.progressIndicator.setVisibility(View.GONE);
                        if (msg.equals("\"-20007\"")) {
                            ViewExtensionsKt.showSnackBar(binding.contentLayout, getString(R.string.userIsExist),
                                    true);
                        }
                        /*Toast.makeText(RegisterActivity.this, msg,
                                Toast.LENGTH_LONG).show();*/
                    }

                    @Override
                    public void onFailure(@NotNull Call<JsonObject> call,
                                          @NotNull Throwable t) {
                        super.onFailure(call, t);
                        t.printStackTrace();
                        ViewExtensionsKt.showSnackBar(binding.contentLayout, getString(R.string.connectionError),
                                true);
                        binding.progressIndicator.setVisibility(View.GONE);

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Pair<Boolean, String> validateVerificationCode(String code){
        if(code.length()==0){
            return new Pair<>(false,getString(R.string.mustEnter));
        }else if(code.length()!=6){
            return new Pair<>(false,getString(R.string.mustSixDigits));
        }else{
            return new Pair<>(true,"");
        }
    }

    private void verifyCode(int code) {

        if (binding.verificationCode.getText().toString().length() == 0) {
            ViewExtensionsKt.showSnackBar(binding.contentLayout,
                    getString(R.string.enterVerifyCode), true);
            binding.progressIndicator.setVisibility(View.GONE);
            return;
        }
        DataService service = BackendHelper.INSTANCE.getRetrofit()
                .create(DataService.class);

        service.verify(new VerificateionModel(code,
                binding.verificationCode.getText().toString()))
                .enqueue(new BackendCallBack<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG).show();
                        binding.progressIndicator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        binding.progressIndicator.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, msg + "  " + code,
                                Toast.LENGTH_LONG).show();
                    }


                    @Override
                    public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                        super.onFailure(call, t);
                        t.printStackTrace();
                        ViewExtensionsKt.showSnackBar(binding.contentLayout, getString(R.string.connectionError),
                                true);
                        binding.progressIndicator.setVisibility(View.GONE);

                    }
                });
    }

    public void verify(View view) {

        if (verificate && this.code != -1) {
            verifyCode(this.code);
        }
    }
}
