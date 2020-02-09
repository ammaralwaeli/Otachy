package com.srit.otachy.helpers

import android.content.Context
import android.graphics.Color
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.srit.otachy.R
import com.orhanobut.logger.Logger
import java.nio.charset.StandardCharsets

fun Context.toast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun ProgressBar.hide(){
    visibility= View.GONE
}
fun ProgressBar.show(){
    visibility= View.VISIBLE
}

fun ViewGroup.showSnackBar(msg:String, isError: Boolean= true){
    val snackBar = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
    val snackView: ViewGroup = snackBar.view as ViewGroup
    snackView.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snackView.setBackgroundColor(Color.WHITE)
    (snackView[0] as ViewGroup).forEach {
        Logger.i("type " + it.javaClass.name)
        if (it is TextView) {
            it.setTextColor(if(isError) Color.rgb(150, 0, 0) else Color.rgb(0, 120, 0))
        }
    }
    snackBar.show()
}


fun String.decodeToken(): String {
    val splitString = this.split('.')
//    val base64EncodedHeader = splitString[0]
    val base64EncodedBody = splitString[1]
//    val base64EncodedSignature = splitString[2]
    return base64EncodedBody.base64Decode()
}
private fun String.base64Decode(): String {
    val bytes: ByteArray = Base64.decode(this, Base64.URL_SAFE)
    return String(bytes, StandardCharsets.UTF_8)
}

fun RecyclerView.createGridLayout(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
    val itemMargin = resources.getDimension(R.dimen.item_margin).toInt()
    setPadding(itemMargin, itemMargin, 0, itemMargin)
    layoutManager = GridLayoutManager(context, 2)
    this.adapter = adapter
}