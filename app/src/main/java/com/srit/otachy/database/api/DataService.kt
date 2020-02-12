package com.srit.otachy.database.api

import androidx.annotation.Nullable
import com.google.gson.JsonObject
import com.srit.otachy.database.models.*
import retrofit2.Call
import retrofit2.http.*

interface DataService{

    @POST("api/account/loginUser")
    fun login(
        @Body loginModel: LoginModel
    ): Call<JsonObject>


    @POST("api/account/registerUser")
    fun register(
        @Body registerModel: RegisterModel
    ): Call<JsonObject>


    @POST("api/account/verify")
    fun verify(
        @Body verificateionModel: VerificateionModel
    ): Call<String>

    //@HTTP(method = "GET", path = "vendor/getVendors", hasBody = true)
    @GET("api/vendor/getVendors")
    fun getVendors(
        @Query("government") government:String
    ): Call<List<VendorModel>>


    @GET("api/category/getUserCategories")
    fun getUserCategories(
        @Query("userId") userId:Int
    ): Call<List<UserCategories>>

    @GET("api/items/getUserItems")
    fun getUserItems(
        @Query("UserId") userId:Int,
        @Query("CategoryId") categoryId:Int
    ): Call<List<ServiceModel>>

}

