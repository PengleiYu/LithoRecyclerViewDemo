package com.utopia.demolithorecyclerview.spec

import android.graphics.Color
import com.facebook.litho.StateValue
import com.facebook.litho.annotations.*
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.LoadingEvent
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.SectionLifecycle
import com.facebook.litho.sections.annotations.*
import com.facebook.litho.sections.common.SingleComponentSection
import com.facebook.litho.widget.Card
import com.utopia.demolithorecyclerview.data.DataService
import com.utopia.demolithorecyclerview.data.bean.Feed
import com.utopia.demolithorecyclerview.data.bean.FeedModel
import timber.log.Timber

@GroupSectionSpec
class ListSectionSpec {
    companion object {
        private const val COUNT_LOAD = 10

// ==================生命周期===============

        @JvmStatic
        @OnCreateInitialState
        fun createInitialState(
            c: SectionContext,
            feeds: StateValue<List<Feed>>,
            start: StateValue<Int>,
        ) {
            Timber.d("createInitialState")
            val dataService = DataService()
            val data = dataService.getData(0, 10)
            feeds.set(data.feeds)
            start.set(0)
        }

        @JvmStatic
        @OnCreateChildren
        fun onCreateChildren(
            c: SectionContext,
            @State feeds: List<Feed>,
        ): Children {
            Timber.d("onCreateChildren")
            val builder = Children.create()
            feeds.forEach {
                builder.child(
                    SingleComponentSection.create(c)
                        .key("${it.id}")
                        .component(
                            Card.create(c)
                                .content(
                                    CardElement.create(c)
                                        .title(it.data.title)
                                        .desc(it.data.description)
                                        .id(it.id)
                                        .photos(it.data.photos)
                                        .type(it.type)
                                )
                                .elevationDip(6f)
                                .cardBackgroundColor(Color.WHITE)
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
        fun onCreateService(c: SectionContext, @State start: Int): DataService {
            Timber.d("onCreateService")
            return DataService()
        }

        @JvmStatic
        @OnBindService
        fun onBindService(c: SectionContext, service: DataService) {
            Timber.d("OnBindService")
            service.registerLoadingEvent(ListSection.onDataLoaded(c))
        }

        @JvmStatic
        @OnUnbindService
        fun onUnbindService(c: SectionContext, service: DataService) {
            Timber.d("OnUnbindService")
            service.unregisterLoadingEvent()
        }

// ==================UI操作===============

        @JvmStatic
        @OnViewportChanged
        fun onViewportChanged(
            c: SectionContext,
            firstVisiblePosition: Int,
            lastVisiblePosition: Int,
            totalCount: Int,
            firstFullyVisibleIndex: Int,
            lastFullyVisibleIndex: Int,
            service: DataService,
            @State feeds: List<Feed>,
            @State loading: Boolean,
        ) {
            Timber.d("onViewportChanged")
            Timber.d(
                """
                firstVisiblePosition=$firstVisiblePosition,lastVisiblePosition=$lastVisiblePosition,totalCount=$totalCount,firstFullyVisibleIndex=$firstFullyVisibleIndex,lastFullyVisibleIndex=$lastFullyVisibleIndex
                loading=$loading
            """.trimIndent()
            )
            if (feeds.size == lastFullyVisibleIndex && !loading) {
                Timber.d("scroll to the bottom, load new data")
                service.fetch(feeds.size, COUNT_LOAD)
                ListSection.updateStartParam(c, feeds.size)
                ListSection.updateLoadingParam(c, true)
            }
        }

        @OnRefresh
        @JvmStatic
        fun onRefresh(
            c: SectionContext,
            service: DataService,
        ) {
            Timber.d("onRefresh")
            service.reFetch(0, COUNT_LOAD)
            ListSection.updateStartParam(c, 0)
            ListSection.updateLoadingParam(c, true)
        }

// ==================事件监听===============

        @JvmStatic
        @OnEvent(FeedModel::class)
        fun onDataLoaded(c: SectionContext, @FromEvent feeds: List<Feed>) {
            Timber.d("onDataLoaded")
            ListSection.updateFeedsParam(c, feeds)
            ListSection.updateLoadingParam(c, false)
            val state = LoadingEvent.LoadingState.SUCCEEDED
            SectionLifecycle.dispatchLoadingEvent(c, false, state, null)
        }

// ==================更新state===============

        @JvmStatic
        @OnUpdateState
        fun updateStartParam(start: StateValue<Int>, @Param newStart: Int) {
            Timber.d("updateStartParam")
            start.set(newStart)
        }

        @JvmStatic
        @OnUpdateState
        fun updateLoadingParam(loading: StateValue<Boolean>, @Param newLoading: Boolean) {
            Timber.d("updateLoadingParam")
            loading.set(newLoading)
        }

        @JvmStatic
        @OnUpdateState
        fun updateFeedsParam(
            start: StateValue<Int>,
            feeds: StateValue<List<Feed>>,
            @Param newFeeds: List<Feed>,
        ) {
            Timber.d("updateFeedsParam:startValue=${start.get()}")
            val oldSize = feeds.get()?.size ?: 0
            if (start.get() == 0) {
                feeds.set(newFeeds)
            } else {
                val oldList = feeds.get() ?: listOf()
                val result = mutableListOf<Feed>()
                result.addAll(oldList)
                result.addAll(newFeeds)
                feeds.set(result)
            }
            val newSize = feeds.get()?.size ?: 0
            Timber.d("size: $oldSize -> $newSize")
        }
    }
}