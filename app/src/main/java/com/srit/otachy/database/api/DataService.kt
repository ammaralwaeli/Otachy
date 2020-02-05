package com.srit.otachy.database.api

import com.google.gson.JsonObject
import com.srit.otachy.database.models.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface DataService{
    @FormUrlEncoded
    @POST("account/loginUser")
    fun login(loginModel: LoginModel
    ): Call<JsonObject>


    @POST("account/registerUser")
    fun register(
        @Body registerModel: RegisterModel
    ): Call<JsonObject>


    @POST("account/verify")
    fun verify(
        @Body verificateionModel: VerificateionModel
    ): Call<String>


    @FormUrlEncoded
    @POST(".")
    fun getServicesByCategory(
        @Field("action") action: String="get-items",
        @Field("category") categoryId: String
    ): Call<List<ServiceItemModel>>


    // TODO: complete
    @FormUrlEncoded
    @POST(".")
    fun getMeatServicesByCategory(
        @Field("action") action: String="butcher-get-items",
        @Field("category") categoryId: String
    ): Call<List<MeatServiceItemModel>>

    @FormUrlEncoded
    @POST(".")
    fun sendOrder(
        @Field("action") action: String="bill",

        @Field("date_r") date_r: String,
        @Field("phone") phone: String,
        @Field("description") description: String,
        @Field("items[]") items: List<String>,
        @Field("counts[]") counts: List<String>,
        @Field("price[]") prices: List<String>,
        @Field("category[]") categories: List<String>

    ): Call<Void>

}

