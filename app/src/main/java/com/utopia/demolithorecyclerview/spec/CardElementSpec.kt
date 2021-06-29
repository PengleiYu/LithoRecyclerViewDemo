package com.utopia.demolithorecyclerview.spec

import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.*
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.common.DataDiffSection
import com.facebook.litho.sections.common.RenderEvent
import com.facebook.litho.sections.widget.ListRecyclerConfiguration
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.Image
import com.facebook.litho.widget.RenderInfo
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

            return Column.create(c)
                .child(titleComp)
                .child(
                    if (type == FeedType.PHOTO_FEED)
                        getRecyclerComp(c, photos)
                    else getImageComp(c, photos[0])
                )
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

        private fun getRecyclerComp(c: ComponentContext, imageResList: List<Int>): Component {
            return RecyclerCollectionComponent.create(c)
                .heightDip(200f)
                .recyclerConfiguration(
                    ListRecyclerConfiguration.create()
                        .orientation(LinearLayoutManager.HORIZONTAL)
                        .build()
                )
                .section(
                    DataDiffSection.create<Int>(SectionContext(c))
                        .data(imageResList)
                        .renderEventHandler(CardElement.onRenderImage(c))
                )
                .build()
        }

        @JvmStatic
        @OnEvent(RenderEvent::class)
        fun onRenderImage(c: ComponentContext, @FromEvent model: Int): RenderInfo {
            return ComponentRenderInfo.create()
                .component(getImageComp(c, model))
                .build()
        }
    }
}