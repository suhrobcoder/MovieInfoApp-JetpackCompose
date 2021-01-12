package uz.suhrob.movieinfoapp.other

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AmbientContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@Composable
fun loadImage(
    url: String,
    @DrawableRes defaultImage: Int
): MutableState<Bitmap?> {
    val image: MutableState<Bitmap?> = mutableStateOf(null)
    Glide.with(AmbientContext.current)
        .asBitmap()
        .load(defaultImage)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                image.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    Glide.with(AmbientContext.current)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                image.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    return image
}