package com.srit.otachy.adapters

import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.srit.otachy.R
import com.tiper.MaterialSpinner
import java.text.DecimalFormat


@BindingAdapter("android:price")
fun setFormattedPrice(textView: TextView, price: Double) {
    val completePrice = price * 1000
    val formattedPrice = DecimalFormat("#,###,###").format(completePrice)
    textView.text = formattedPrice
}

@BindingAdapter("android:pieces")
fun setPieces(textView: TextView, pieces: Double) {
    textView.text = "$pieces ${textView.context.resources.getString(R.string.pieces)}"
}




@BindingAdapter("android:priceTxt")
fun setFormattedPriceTxt(textView: TextView, price: Double) {


    if((price-price.toInt())==0.0) {
        textView.text = "${price.toInt()} ${textView.context.resources.getString(R.string.thousand)}"

    }else{
        textView.text = "$price ${textView.context.resources.getString(R.string.thousand)}"
    }

}

@BindingAdapter("android:srcBitmap")
fun loadImage(iv: ImageView, bitmap: Bitmap?) {
    iv.setImageBitmap(bitmap)
}

@BindingAdapter("android:srcUrl")
fun loadImageUrl(iv: ImageView, url: String) {
    Glide
        .with(iv.context)
        .load(url)
        .into(iv)
}


@BindingAdapter("android:setList")
fun <T>setSpinnerList(spinner: MaterialSpinner, list:List<T>) {
    ArrayAdapter<T>(
        spinner.context,
        android.R.layout.simple_spinner_item, list
    ).apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter= this
    }


}
