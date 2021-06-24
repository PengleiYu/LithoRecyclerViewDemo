package com.utopia.demolithorecyclerview.data

import android.os.Handler
import android.os.Looper
import com.facebook.litho.EventHandler
import com.utopia.demolithorecyclerview.R
import com.utopia.demolithorecyclerview.data.bean.Feed
import com.utopia.demolithorecyclerview.data.bean.FeedData
import com.utopia.demolithorecyclerview.data.bean.FeedModel
import com.utopia.demolithorecyclerview.data.bean.FeedType
import java.util.concurrent.Executor
import kotlin.random.Random

class DataService {
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private val executor = Executor { handler.postDelayed(it, 2000) }

    private var eventHandler: EventHandler<FeedModel>? = null

    fun registerLoadingEvent(handler: EventHandler<FeedModel>) {
        eventHandler = handler
    }

    fun unregisterLoadingEvent() {
        eventHandler = null
    }


    fun fetch(start: Int, count: Int) {
        executor.execute {
            val data = getData(start, count)
            eventHandler?.dispatchEvent(data)
        }
    }

    fun reFetch(start: Int, count: Int) {
        executor.execute {
            val data = getData(start, count)
            eventHandler?.dispatchEvent(data)
        }
    }

    fun getData(start: Int, count: Int): FeedModel {
        val feeds = (start..start + count).map(this::generateFeed)
        return FeedModel(feeds)
    }

    private fun generateFeed(id: Int): Feed {
        val offset = Random.nextInt()
        val type: FeedType = when {
            (id + offset) % 5 == 0 -> FeedType.AD_FEED
            (id + offset) % 3 == 0 -> FeedType.NEWS_FEED
            else -> FeedType.PHOTO_FEED
        }
        return Feed(id, type, generateData(type))
    }

    private fun generateData(type: FeedType): FeedData {
        val title: String
        val description: String
        val photos: List<Int>
        val like = false
        val likeCount: Int
        val commentCount: Int

        when (type) {
            FeedType.NEWS_FEED -> {
                title = TITLES[Random.nextInt(TITLES.size)]
                description =
                    "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu."
                photos = arrayListOf(PHOTOS[Random.nextInt(PHOTOS.size)])
                likeCount = 51
                commentCount = 19
            }
            FeedType.PHOTO_FEED -> {
                title = TITLES[Random.nextInt(TITLES.size)]
                description =
                    "Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu."
                photos = arrayListOf(*PHOTOS)
                likeCount = 32
                commentCount = 27
            }
            FeedType.AD_FEED -> {
                title = "Top Advertisement"
                description = "Buy 1 get 1. Limited period offer. Hurry"
                photos = arrayListOf(R.mipmap.ic_launcher)
                likeCount = 67
                commentCount = 77
            }
        }
        return FeedData(title, description, photos, like, likeCount, commentCount)
    }

    companion object {
        private val PHOTOS = arrayOf(
            R.drawable.sample_feed_img,
            R.drawable.sample_feed_img_2,
            R.drawable.sample_feed_img_3,
            R.drawable.sample_feed_img_4
        )
        private val TITLES = arrayOf(
            "Created upon forth there sea under. Creepeth beast.",
            "Grass grass Moveth sixth from sixth, spirit third to man.",
            "Night said days fly fill saying is. Divided own god.",
            "Replenish bearing Hath beginning he kind night form had, darkness.",
            "Appear night to Above, tree, every greater that from good.",
            "Under herb creeping, brought unto god that great replenish. Whose.",
            "Above green was grass. Kind subdue they're whales meat. You'll.",
            "One own signs without man, us fruit evening his green.",
            "Were fill creeping his heaven waters form, it Fish the.",
            "Two him moveth first man forth Bring their his yielding.",
            "Itself whales spirit third. Bearing forth, fruitful given living creeping.",
            "Them. Him seas you his to, you'll, made darkness darkness.",
            "Living greater form living winged that stars. Shall form for.",
            "Fruitful together his day she'd years our living waters. Lights.",
            "Fifth seed made hath forth thing. Doesn't sixth fill deep.",
            "Created green a greater under second moving brought made that.",
            "Light spirit kind, without also void open in. You they're."
        )
    }
}