package com.utopia.demolithorecyclerview.spec

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Text
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
        ): Component {
            val titleComp = Text.create(c, 0, R.style.TextAppearance_AppCompat_Title)
                .text(title)
                .build()
            val descComp = Text.create(c)
                .text(desc)
                .build()
            return Column.create(c)
                .child(titleComp)
                .child(descComp)
                .build()
        }
    }
}