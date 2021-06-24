package com.utopia.demolithorecyclerview.spec

import android.os.Handler
import com.facebook.litho.StateValue
import com.facebook.litho.annotations.*
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.LoadingEvent
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.SectionLifecycle
import com.facebook.litho.sections.annotations.*
import com.facebook.litho.sections.common.SingleComponentSection
import com.utopia.demolithorecyclerview.data.DataService
import com.utopia.demolithorecyclerview.data.bean.Feed
import com.utopia.demolithorecyclerview.data.bean.FeedModel
import timber.log.Timber

@GroupSectionSpec
class ListSectionSpec {
    companion object {
        @JvmStatic
        @OnCreateInitialState
        fun createInitialState(
            c: SectionContext,
            feeds: StateValue<List<Feed>>
        ) {
            Timber.d("createInitialState")
            val dataService = DataService()
            feeds.set(dataService.getData(0, 10).feeds)
        }

        @JvmStatic
        @OnCreateChildren
        fun onCreateChildren(c: SectionContext, @State feeds: List<Feed>): Children {
            Timber.d("onCreateChildren")
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

        @JvmStatic
        @OnCreateService
        fun onCreateService(c: SectionContext): DataService {
            Timber.d("onCreateService")
            return DataService()
        }

        @JvmStatic
        @OnBindService
        fun onBindService(c: SectionContext, service: DataService) {
            Timber.d("OnBindService")
        }

        @JvmStatic
        @OnUnbindService
        fun onUnbindService(c: SectionContext, service: DataService) {
            Timber.d("OnUnbindService")
        }

        @JvmStatic
        @OnEvent(FeedModel::class)
        fun onDataLoaded(c: SectionContext, @FromEvent feeds: List<Feed>) {
            Timber.d("onDataLoaded")
            ListSection.updateData(c, feeds)
            val state = LoadingEvent.LoadingState.SUCCEEDED
            SectionLifecycle.dispatchLoadingEvent(c, false, state, null)
        }

        @JvmStatic
        @OnUpdateState
        fun updateData(
            feeds: StateValue<List<Feed>>,
            @Param newFeeds: List<Feed>
        ) {
            feeds.set(newFeeds)
        }


        @OnRefresh
        @JvmStatic
        fun onRefresh(c: SectionContext) {
            Timber.d("onRefresh")
            Handler().postDelayed({
                val data = DataService().getData(0, 10)
                ListSection.onDataLoaded(c).dispatchEvent(data)
            }, 1000)
        }

//        fun onDataLoaded(c:SectionContext,@FromEvent feeds:List<Feed>){
//
//        }
    }
}