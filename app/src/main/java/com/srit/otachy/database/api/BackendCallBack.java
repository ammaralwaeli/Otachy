package com.srit.otachy.database.api;

import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract public class BackendCallBack<T> implements Callback<T> {

    @Override
    public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            String errorBody="";
            try {
                errorBody = Objects.requireNonNull(response.errorBody()).string();
                Logger.e("<<<onResponse error>>>\nCode: %s\nMessage: %s\nBody: %s",
                        response.code(), response.message(), errorBody);


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                onError(response.code(), errorBody);

            }
        }
    }

    @Override
    public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
        Logger.e("<<<onFailure error>>>\nMessage: %s", t.getMessage());
    }

    abstract public void onSuccess(T result);

    abstract public void onError(int code, String msg);
}
