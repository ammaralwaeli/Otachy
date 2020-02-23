package com.srit.otachy.adapters

import android.graphics.Bitmap
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import com.srit.otachy.R
import com.srit.otachy.helpers.BackendHelper
import com.tiper.MaterialSpinner
import org.threeten.bp.LocalDateTime
import java.lang.Exception
import java.text.DecimalFormat
import java.util.*


@BindingAdapter("android:price")
fun setFormattedPrice(textView: TextView, price: Int) {
    val formattedPrice = DecimalFormat("#,###").format(price)
    textView.text = formattedPrice
}

@BindingAdapter("android:pieces")
fun setPieces(textView: TextView, pieces: Int) {
    textView.text = "$pieces ${textView.context.resources.getString(R.string.pieces)}"
}


@BindingAdapter("mine:setDate")
fun setTextFromDate(textView: TextView, date: LocalDateTime?) {
    try {
        textView.text = date?.format(dateTimeFormatter)
    } catch (e: Exception) {
        Logger.e("binding error: ${e.message}")
    }

}
@BindingAdapter("mine:setEnglishPrice")
fun setPriceEnglish(textView: TextView, price: Int) {
    textView.text=String.format(Locale.ENGLISH, "%s", DecimalFormat("#,###").format(price))
}

@BindingAdapter("android:priceTxt")
fun setFormattedPriceTxt(textView: TextView, price: Int) {

        val p=String.format(Locale.ENGLISH, "%s", DecimalFormat("#,###").format(price))
        textView.text = "$p ${textView.context.resources.getString(R.string.thousand)}"


}

@BindingAdapter("android:srcBitmap")
fun loadImage(iv: ImageView, bitmap: Bitmap?) {
    iv.setImageBitmap(bitmap)
}

@BindingAdapter("android:srcUrl")
fun loadImageUrl(iv: ImageView, url: String) {
    Glide
        .with(iv.context)
        .load(BackendHelper.API_BASE_URL + url)
        .into(iv)
}


@BindingAdapter("android:setList")
fun <T> setSpinnerList(spinner: MaterialSpinner, list: List<T>) {
    ArrayAdapter<T>(
        spinner.context,
        android.R.layout.simple_spinner_item, list
    ).apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = this
    }


}
