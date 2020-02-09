package com.srit.otachy.database.api

import androidx.annotation.Nullable
import com.google.gson.JsonObject
import com.srit.otachy.database.models.*
import retrofit2.Call
import retrofit2.http.*

interface DataService{

    @POST("account/loginUser")
    fun login(@Body loginModel: LoginModel
    ): Call<JsonObject>


    @POST("account/registerUser")
    fun register(
        @Body registerModel: RegisterModel
    ): Call<JsonObject>


    @POST("account/verify")
    fun verify(
        @Body verificateionModel: VerificateionModel
    ): Call<String>

    //@HTTP(method = "GET", path = "vendor/getVendors", hasBody = true)
    @GET("vendor/getVendors")
    fun getVendors(
        @Body  government:Governments
    ): Call<List<VendorModel>>


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

