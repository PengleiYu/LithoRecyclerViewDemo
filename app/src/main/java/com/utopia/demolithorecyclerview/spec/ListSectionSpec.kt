package com.utopia.demolithorecyclerview.spec

import com.facebook.litho.StateValue
import com.facebook.litho.annotations.OnCreateInitialState
import com.facebook.litho.annotations.State
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.SingleComponentSection
import com.utopia.demolithorecyclerview.data.DataService
import com.utopia.demolithorecyclerview.data.bean.Feed

@GroupSectionSpec
class ListSectionSpec {
    companion object {
        @JvmStatic
        @OnCreateInitialState
        fun createInitialState(
            c: SectionContext,
            feeds: StateValue<List<Feed>>
        ) {
            val dataService = DataService()
            feeds.set(dataService.getData(0, 10).feeds)
        }

        @JvmStatic
        @OnCreateChildren
        fun onCreateChildren(c: SectionContext, @State feeds: List<Feed>): Children {
            val builder = Children.create()
            feeds.forEach {
                builder.child(
                    SingleComponentSection.create(c)
                        .key("${it.id}")
                        .component(
                            CardElement.create(c)
                                .title(it.data.title)
                                .desc(it.data.description)
                                .id(it.id)
                                .type(it.type)
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