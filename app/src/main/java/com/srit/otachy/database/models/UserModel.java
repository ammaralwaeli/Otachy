package com.srit.otachy.database.models;

import com.google.gson.Gson;
import com.srit.otachy.helpers.BackendHelper;
import com.srit.otachy.helpers.ViewExtensionsKt;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class UserModel implements Serializable {

    private String id;
    private String username;
    private String mobileNo;
    private String role;
    private String Government;


    private static UserModel instance;

    public static UserModel getInstance(String access) {
        if (instance == null) {
            instance = fromToken(access);
            return instance;
        }
        return instance;
    }


    private static UserModel fromToken(String accessToken) {
        String body = ViewExtensionsKt.decodeToken(accessToken);
        Gson gson = BackendHelper.INSTANCE.getGson();
        return gson.fromJson(body, UserModel.class);

    }

    public UserModel(String username, String mobileNo, String role, String government) {
        this.username = username;
        this.mobileNo = mobileNo;
        this.role = role;
        Government = government;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getRole() {
        return role;
    }

    public String getGovernment() {
        return Government;
    }


    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", role='" + role + '\'' +
                ", Government='" + Government + '\'' +
                '}';
    }
}