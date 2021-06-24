package com.utopia.demolithorecyclerview.spec

import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.SingleComponentSection

@GroupSectionSpec
class ListSectionSpec {
    companion object {
        @JvmStatic
        @OnCreateChildren
        fun onCreateChildren(c: SectionContext): Children {
            val builder = Children.create()
            builder.child(
                SingleComponentSection.create(c)
                    .component(ProgressLayout.create(c))
            )
            return builder.build()
        }
    }
}