package com.fashare.mydemo.data

import com.fashare.mydemo.R
import com.fashare.no_view_holder.annotation.BindImageView
import com.fashare.no_view_holder.annotation.BindImageViews
import com.fashare.no_view_holder.annotation.BindRecyclerView
import com.fashare.no_view_holder.annotation.BindTextView
import java.util.*

/**
 * Created by apple on 17-5-31.
 */
class HomeInfo(){
    @BindRecyclerView(id = R.id.rv_story, layout = R.layout.item_story)
    val stories: List<Story> = Collections.emptyList()

    inner class Story{
        @BindTextView(id = R.id.tv_title)
        val title: String = ""

        @BindImageViews(
                BindImageView(id = R.id.iv_image, placeHolder = R.drawable.ic_launcher)
        )
        val images: List<String> = Collections.emptyList()

        override fun toString(): String {
            return title
        }
    }
}