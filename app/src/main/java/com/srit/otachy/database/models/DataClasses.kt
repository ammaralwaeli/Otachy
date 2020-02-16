package com.srit.otachy.database.models

data class LoginModel(
    val mobileNumber:String,
    val password:String
)
data class RegisterModel(
    val username:String,
    val password:String,
    val mobileNumber:String,
    val government:String,
    val district:String
)
data class VerificateionModel(
    val id:Int,
    val code:String
)