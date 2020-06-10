package com.tainzhi.android.wanandroid.binding

import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.tainzhi.android.common.util.fromN
import com.tainzhi.android.wanandroid.bean.Article
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/25 下午1:31
 * @description:
 **/

@BindingAdapter("visibleUnless")
fun bindVisibleUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

@BindingAdapter("goneUnless")
fun bindGoneUnless(view: View, gone: Boolean) {
    view.visibility = if (gone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("htmlText")
fun bindHtmlText(view:TextView,html:String){
    view.text = if (fromN()) Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY) else Html.fromHtml(html)
}

@BindingAdapter("srcVectorDrawable")
fun bindImage(imageView: ImageView, srcId: Int) {
    imageView.setImageResource(srcId)
}

@BindingAdapter(
        "imageUrl",
        "imagePlaceholder",
        "circleCropImage",
        "crossFadeImage",
        "overrideImageWidth",
        "overrideImageHeight",
        "imageLoadListener",
        requireAll = false
)
fun bindImage(
        imageView: ImageView,
        imageUrl: String?,
        placeholder: Int? = null,
        circleCrop: Boolean? = false,
        crossFade: Boolean? = false,
        overrideWidth: Int? = null,
        overrideHeight: Int? = null,
        listener: RequestListener<Drawable>?
) {
    if (imageUrl == null) return
    var request = Glide.with(imageView.context).load(imageUrl)
    if (placeholder != null) {
        request = request.placeholder(placeholder)
    }
    if (circleCrop == true) {
        request = request.circleCrop()
    }
    if (crossFade == true) {
        request = request.transition(DrawableTransitionOptions.withCrossFade())
    }
    if (overrideWidth != null && overrideHeight != null) {
        request = request.override(overrideWidth, overrideHeight)
    }
    if (listener != null) {
        request = request.listener(listener)
    }
    request.into(imageView)
}

@BindingAdapter(value = ["afterTextChanged"])
fun EditText.afterTextChanged(action: () -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            action()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

@BindingAdapter(value = ["dateText"])
fun convertDateToString(textView: TextView, date: Date) {
    textView.text = SimpleDateFormat.getInstance().format(date)
}

@BindingAdapter(value = ["articleAuthor"])
fun getArticleAuthor(textView: TextView, article: Article) {
    textView.text = if (article.author.isNullOrBlank()) "分享者：${article.shareUser}" else article.author
}
