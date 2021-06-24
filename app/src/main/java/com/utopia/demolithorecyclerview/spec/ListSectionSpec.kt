package com.utopia.demolithorecyclerview.spec

import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.SingleComponentSection
import com.utopia.demolithorecyclerview.data.bean.FeedType

@GroupSectionSpec
class ListSectionSpec {
    companion object {
        @JvmStatic
        @OnCreateChildren
        fun onCreateChildren(c: SectionContext): Children {
            val builder = Children.create()
            (0..10).forEach {
                builder.child(
                    SingleComponentSection.create(c)
                        .key("key$it")
                        .component(
                            CardElement.create(c)
                                .title("Title$it")
                                .desc("Desc$it")
                                .id(it)
                                .type(FeedType.AD_FEED)
                        )
                )
            }
            builder.child(
                SingleComponentSection.create(c)
                    .component(ProgressLayout.create(c))
            )
            return builder.build()
        }
    }
}