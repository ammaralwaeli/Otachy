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
import com.srit.otachy.R
import com.srit.otachy.database.api.BackendCallBack
import com.srit.otachy.database.api.DataService
import com.srit.otachy.database.models.LoginModel
import com.srit.otachy.database.models.MeatServiceItemModel
import com.srit.otachy.databinding.ActivityLoginBinding
import com.srit.otachy.helpers.BackendHelper
import com.srit.otachy.helpers.SharedPrefHelper
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback


class LoginActivity : AppCompatActivity(){
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login)

        Glide
            .with(this)
            .load(R.drawable.ic_launcher)
            .into(binding.logoImageView)

        animateAlpha()
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

    fun login(view: View) {
         val service= BackendHelper.retrofit.create(DataService::class.java)

        showLoading()
        service.login(LoginModel(binding.roomEditText.text.toString(),
            binding.passwordEditText.text.toString()))
            .enqueue(object : BackendCallBack<JsonObject>(){
                override fun onSuccess(result: JsonObject?) {

                }

                override fun onError(code: Int, msg: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    super.onFailure(call, t)
                }

            })

    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        binding.logoImageView.transitionName = ""
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
