package com.srit.otachy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonObject;
import com.srit.otachy.R;
import com.srit.otachy.database.api.BackendCallBack;
import com.srit.otachy.database.api.DataService;
import com.srit.otachy.database.models.LoginModel;
import com.srit.otachy.database.models.RegisterModel;
import com.srit.otachy.database.models.VerificateionModel;
import com.srit.otachy.databinding.ActivityRegisterBinding;
import com.srit.otachy.helpers.BackendHelper;
import com.srit.otachy.helpers.SharedPrefHelper;
import com.srit.otachy.helpers.SmsBroadcastReceiver;
import com.srit.otachy.helpers.ViewExtensionsKt;
import com.srit.otachy.ui.SharedUI;
import com.tiper.MaterialSpinner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

import static com.srit.otachy.adapters.ByBindingAdapterKt.setSpinnerList;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private String mGov = "",phone,password;
    private boolean verificate;
    private int code;
    private static final int REQ_USER_CONSENT = 200;
    private static final String PHONE_NUMBER = "+14088984604";
    SmsBroadcastReceiver smsBroadcastReceiver;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOtpFromMessage(message);
            }
        }
    }

    private void getOtpFromMessage(String message) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            //Toast.makeText(this,matcher.group(0),Toast.LENGTH_LONG).show();
            String code=matcher.group(0);
            binding.verificationCode.setText(code);
            verifyCode(Integer.decode(code));
        }
    }


    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(PHONE_NUMBER).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, REQ_USER_CONSENT);
                    }
                    @Override
                    public void onFailure() {
                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }
    @Override
    protected void onResume() {
        super.onResume();
        startSmsUserConsent();
    }



    @Override
    public void onBackPressed() {

        if (verificate && this.code != -1) {
            showRegisterLayout();
            verificate = false;
            this.code = -1;
        }
/*
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);*/
    }


    public static void newInstance(Context context){
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    private void showRegisterLayout() {
        binding.registerView.setVisibility(View.VISIBLE);
        binding.verificationView.setVisibility(View.GONE);
        binding.titleText.setText(R.string.register);
        binding.registerButton.setText(R.string.register);
        binding.registerButton.setVisibility(View.VISIBLE);
        binding.verifyButton.setVisibility(View.GONE);
        binding.haveAccount.setVisibility(View.VISIBLE);
    }




    private void showVerifyLayout() {
        binding.registerView.setVisibility(View.GONE);
        binding.verificationView.setVisibility(View.VISIBLE);
        binding.titleText.setText(R.string.verifyAccount);
        binding.registerButton.setVisibility(View.GONE);
        binding.verifyButton.setVisibility(View.VISIBLE);
        binding.haveAccount.setVisibility(View.GONE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        loadGovs();
        verificate = false;
        code = -1;
        if(SharedPrefHelper.getInstance().getIsVerification()){
            showVerifyLayout();
        }else {
            showRegisterLayout();
        }
        //startSmsUserConsent();
        binding.newRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.Factory.newInstance(RegisterActivity.this);
                SharedPrefHelper.getInstance().setIsRegister(false);
                finish();
            }
        });
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


    private Pair<Boolean, String> validataData() {
        int MINMUMPASSWORD = 6;

        if (binding.name.getText().toString().equals("")) {
            return new Pair<>(false, getString(R.string.enterUsername));
        } else if (binding.phoneNumber.getText().toString().equals("")) {
            return new Pair<>(false, getString(R.string.enterPhone));
        } else if (!SharedUI.isValidPhoneNumber(binding.phoneNumber.getText().toString())) {
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
            binding.registerButton.setText("");
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
                        new RegisterModel(binding.name.getText().toString(),
                                binding.pass.getText().toString(),
                                binding.phoneNumber.getText().toString(),
                                mGov,
                                binding.city.getText().toString())
                ).enqueue(new BackendCallBack<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject result) {
                        showVerifyLayout();
                        SharedPrefHelper.getInstance().setIsVerification(true);
                        startSmsUserConsent();
                        RegisterActivity.this.phone=binding.phoneNumber.getText().toString();
                        RegisterActivity.this.password=binding.pass.getText().toString();
                        verificate = true;
                        RegisterActivity.this.code = result.get("CodeId").getAsInt();
                        binding.progressIndicator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        binding.progressIndicator.setVisibility(View.GONE);
                        if (msg.equals("\"-20007\"")) {
                            ViewExtensionsKt.showSnackBar(binding.contentLayout, getString(R.string.userIsExist),
                                    true);
                        }
                        binding.registerButton.setText(getString(R.string.register));

                    }

                    @Override
                    public void onFailure(@NotNull Call<JsonObject> call,
                                          @NotNull Throwable t) {
                        super.onFailure(call, t);
                        t.printStackTrace();
                        ViewExtensionsKt.showSnackBar(binding.contentLayout, getString(R.string.connectionError),
                                true);
                        binding.progressIndicator.setVisibility(View.GONE);
                        binding.registerButton.setText(getString(R.string.register));
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

    private void login(){

        DataService service = BackendHelper.INSTANCE.getRetrofit()
                .create(DataService.class);

        service.login(new LoginModel(this.phone,this.password)
        )
                .enqueue(new BackendCallBack<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject result) {
                        //Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG).show();
                        binding.progressIndicator.setVisibility(View.GONE);
                        SharedPrefHelper.getInstance().setIsVerification(false);
                        String access=result.get("jwt").getAsString();
                        SharedPrefHelper.getInstance().setAccessToken(access);
                        HomeActivity.newInstance(RegisterActivity.this);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        binding.progressIndicator.setVisibility(View.GONE);
                        ViewExtensionsKt.showSnackBar(binding.contentLayout, msg,
                                true);
                        binding.verifyButton.setText(getString(R.string.verify));
                    }


                    @Override
                    public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                        super.onFailure(call, t);
                        t.printStackTrace();
                        ViewExtensionsKt.showSnackBar(binding.contentLayout, t.getMessage(),
                                true);
                        binding.progressIndicator.setVisibility(View.GONE);
                        binding.verifyButton.setText(getString(R.string.verify));

                    }
                });
    }

    private void verifyCode(int code) {

        binding.verifyButton.setText("");
        if (binding.verificationCode.getText().toString().length() == 0) {
            ViewExtensionsKt.showSnackBar(binding.contentLayout,
                    getString(R.string.enterVerifyCode), true);

            binding.progressIndicator.setVisibility(View.GONE);
            return;
        }
        DataService service = BackendHelper.INSTANCE.getRetrofit()
                .create(DataService.class);

        service.verify(new VerificateionModel(this.code,
                binding.verificationCode.getText().toString()))
                .enqueue(new BackendCallBack<String>() {
                    @Override
                    public void onSuccess(String result) {
                        //Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG).show();
                        binding.progressIndicator.setVisibility(View.GONE);
                        login();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        binding.progressIndicator.setVisibility(View.GONE);
                        binding.verifyButton.setText(getString(R.string.verify));
                    }


                    @Override
                    public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                        super.onFailure(call, t);
                        t.printStackTrace();
                        ViewExtensionsKt.showSnackBar(binding.contentLayout, getString(R.string.connectionError),
                                true);
                        binding.progressIndicator.setVisibility(View.GONE);
                        binding.verifyButton.setText(getString(R.string.verify));

                    }
                });
    }

    public void verify(View view) {

        if (verificate && this.code != -1) {
            verifyCode(this.code);
        }
    }
}
