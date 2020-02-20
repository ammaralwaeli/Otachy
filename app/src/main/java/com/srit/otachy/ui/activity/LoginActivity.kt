package com.srit.otachy.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat.getFont
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import com.orhanobut.logger.Logger
import com.srit.otachy.R
import com.srit.otachy.database.api.BackendCallBack
import com.srit.otachy.database.api.DataService
import com.srit.otachy.database.models.LoginModel
import com.srit.otachy.databinding.ActivityLoginBinding
import com.srit.otachy.helpers.BackendHelper
import com.srit.otachy.helpers.SharedPrefHelper
import com.srit.otachy.ui.SharedUI
import retrofit2.Call


class LoginActivity : AppCompatActivity(){
    lateinit var binding: ActivityLoginBinding





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login)

        if(SharedPrefHelper.getInstance().accessToken!=null){
            HomeActivity.newInstance(this)
            finish()
        }else if(SharedPrefHelper.getInstance().isRegister){
            RegisterActivity.newInstance(this)
            finish()
        }

        Glide
            .with(this)
            .load(R.drawable.ic_launcher)
            .into(binding.logoImageView)

        animateAlpha()
        binding.newLogin.setOnClickListener {
            RegisterActivity.newInstance(this)
            SharedPrefHelper.getInstance().isRegister=true
        }



    }



    companion object Factory{

        fun newInstance(context: Context){
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }

    private fun animateAlpha() {
        binding.contentLayout.animate().alpha(1.0F).setDuration(1500).start()
    }

    private fun validateData():Pair<Boolean,String>{

        if(!SharedUI.isValidPhoneNumber(binding.phoneEditText.text.toString())){
            return Pair(false, getString(R.string.invalidPhone))
        }else if(binding.phoneEditText.text.toString() == ""){
            return Pair(false, getString(R.string.enterPhone))
        }else if(binding.passwordEditText.text.toString()==""){
            return Pair(false, getString(R.string.enterPass))
        }else return Pair(true,"")


    }



    fun login(view: View) {
         val service= BackendHelper.retrofit.create(DataService::class.java)

        val value=validateData()
        if(!value.first){
            showErrorSnackBar(value.second)
            return
        }
        showLoading()
        service.login(LoginModel(binding.phoneEditText.text.toString(),
            binding.passwordEditText.text.toString()))
            .enqueue(object : BackendCallBack<JsonObject>(){
                override fun onSuccess(result: JsonObject?) {
                    val access=result?.get("jwt")?.asString
                    SharedPrefHelper.getInstance().accessToken=access
                    HomeActivity.newInstance(this@LoginActivity)
                }

                override fun onError(code: Int, msg: String?) {
                    if (msg == "\"-20004\"") {
                        showErrorSnackBar(getString(R.string.invalidLoginData))

                    }else if (msg == "\"-20003\""){
                        showErrorSnackBar(getString(R.string.userIsNotExist))
                    }
                    hideLoading()

                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    super.onFailure(call, t)
                    showErrorSnackBar("${t.message}")
                    hideLoading()
                }

            })

    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        binding.layout.transitionName = ""


    }


    private fun showErrorSnackBar(msg:String) {
        val snackBar = Snackbar.make(binding.layout, msg, Snackbar.LENGTH_LONG)
        val snackView: ViewGroup = snackBar.view as ViewGroup
        snackView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        snackView.setBackgroundColor(Color.WHITE)
        (snackView[0] as ViewGroup).forEach {
            Logger.i("type " + it.javaClass.name)
            if (it is TextView) {
                it.setTextColor(Color.RED)
                it.typeface = getFont(this, R.font.bahij_the_sans_arabic_plain)
            }
        }
        snackBar.show()
    }

    private fun showLoading(){
        binding.progressIndicator.visibility= View.VISIBLE
        binding.loginButton.isEnabled= false
        binding.loginButton.text= ""
    }

    private fun hideLoading(){
        binding.progressIndicator.visibility= View.INVISIBLE
        binding.loginButton.isEnabled= true
        binding.loginButton.text= getString(R.string.login)
    }
}
