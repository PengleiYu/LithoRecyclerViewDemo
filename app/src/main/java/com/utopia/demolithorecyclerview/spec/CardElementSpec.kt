package com.utopia.demolithorecyclerview.spec

import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.widget.ImageView
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Image
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge.*
import com.utopia.demolithorecyclerview.R
import com.utopia.demolithorecyclerview.data.bean.FeedType

@LayoutSpec
class CardElementSpec {
    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(
            c: ComponentContext,
            @Prop id: Int,
            @Prop type: FeedType,
            @Prop title: String,
            @Prop desc: String,
            @Prop photos: List<Int>,
        ): Component {
            val titleComp = Text.create(c, 0, R.style.TextAppearance_AppCompat_Title)
                .marginDip(TOP, 16f)
                .marginDip(BOTTOM, 8f)
                .marginDip(HORIZONTAL, 8f)
                .typeface(Typeface.DEFAULT_BOLD)
                .textColor(Color.BLACK)
                .text(title)
                .build()
            val descComp = Text.create(c)
                .text(desc)
                .marginDip(VERTICAL, 16f)
                .marginDip(HORIZONTAL, 8f)
                .paddingDip(BOTTOM, 8f)
                .textSizeSp(17f)
                .ellipsize(TextUtils.TruncateAt.END)
                .maxLines(4)
                .build()
            val drawable = if (photos.isEmpty()) R.mipmap.ic_launcher else photos[0]
            return Column.create(c)
                .child(titleComp)
                .child(getImageComp(c, drawable))
                .child(descComp)
                .build()
        }

        private fun getImageComp(c: ComponentContext, imageRes: Int): Image {
            return Image.create(c)
                .widthPercent(100f)
                .heightDip(200f)
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .drawableRes(imageRes)
                .build()
        }
    }
}