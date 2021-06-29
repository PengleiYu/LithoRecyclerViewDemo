package com.utopia.demolithorecyclerview.spec

import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import timber.log.Timber

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
            return Column.create(c)
                .child(createTitleComp(c, title))
                .child(createImagesComp(c, type, photos))
                .child(createDescComp(c, desc))
                .build()
        }

        private fun createImagesComp(c: ComponentContext, type: FeedType, photos: List<Int>) =
            if (type == FeedType.PHOTO_FEED)
                CardHorizontalRecycler.create(c)
                    .imageResList(photos)
                    .build()
            else
                CardInnerImage.create(c)
                    .imageRes(photos[0])
                    .build()

        private fun createDescComp(c: ComponentContext, desc: String) =
            Text.create(c)
                .text(desc)
                .marginDip(VERTICAL, 16f)
                .marginDip(HORIZONTAL, 8f)
                .paddingDip(BOTTOM, 8f)
                .textSizeSp(17f)
                .ellipsize(TextUtils.TruncateAt.END)
                .maxLines(4)
                .build()

        private fun createTitleComp(c: ComponentContext, title: String) =
            Text.create(c, 0, R.style.TextAppearance_AppCompat_Title)
                .marginDip(TOP, 16f)
                .marginDip(BOTTOM, 8f)
                .marginDip(HORIZONTAL, 8f)
                .typeface(Typeface.DEFAULT_BOLD)
                .textColor(Color.BLACK)
                .text(title)
                .build()
    }
}

@LayoutSpec
class CardHorizontalRecyclerSpec {
    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(
            c: ComponentContext,
            @Prop imageResList: List<Int>,
        ): Component {
            return RecyclerCollectionComponent.create(c)
                .heightDip(200f)
                .recyclerConfiguration(
                    ListRecyclerConfiguration.create()
                        .orientation(LinearLayoutManager.HORIZONTAL)
                        .build()
                )
                .itemDecoration(CardInnerImageDecoration())
                .section(
                    DataDiffSection.create<Int>(SectionContext(c))
                        .data(imageResList)
                        .renderEventHandler(CardHorizontalRecycler.onRenderImage(c))
                )
                .build()
        }

        @JvmStatic
        @OnEvent(RenderEvent::class)
        fun onRenderImage(c: ComponentContext, @FromEvent model: Int): RenderInfo {
            return ComponentRenderInfo.create()
                .component(
                    CardInnerImage.create(c)
                        .imageRes(model)
                )
                .build()
        }
    }
}

@LayoutSpec
class CardInnerImageSpec {
    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(
            c: ComponentContext,
            @Prop imageRes: Int,
        ): Component {
            return Image.create(c)
                .widthPercent(100f)
                .heightDip(200f)
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .drawableRes(imageRes)
                .build()
        }
    }
}

class CardInnerImageDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager ?: return
        val childCount = layoutManager.itemCount
        val position = layoutManager.getPosition(view)
        val isLast = position == childCount - 1
        val width = if (isLast) 0 else 40
        outRect.set(0, 0, width, view.height)
        Timber.d("getItemOffsets:view=${view.hashCode()},index=$position,idLast=$isLast,rect=$outRect,childCount=$childCount")
    }
}