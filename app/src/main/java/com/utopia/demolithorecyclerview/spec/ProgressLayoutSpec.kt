package com.utopia.demolithorecyclerview.spec

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.widget.Progress
import com.facebook.yoga.YogaAlign

@LayoutSpec
class ProgressLayoutSpec {
    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(c: ComponentContext): Component {
            return Column.create(c)
                .child(
                    Progress.create(c)
                        .widthDip(40f)
                        .heightDip(40f)
                        .alignSelf(YogaAlign.CENTER)
                )
                .build()
        }
    }
}