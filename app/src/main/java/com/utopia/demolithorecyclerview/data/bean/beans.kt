package com.utopia.demolithorecyclerview.data.bean

import com.facebook.litho.annotations.Event

data class FeedData(
    val title: String,
    val description: String,
    val photos: List<Int>,
    val like: Boolean,
    val likeCount: Int,
    val commentCount: Int,
)

enum class FeedType {
    NEWS_FEED, PHOTO_FEED, AD_FEED,
}

data class Feed(
    val id: Int,
    val type: FeedType,
    val data: FeedData,
)

@Event
class FeedModel(
    @JvmField
    val feeds: List<Feed>,
)