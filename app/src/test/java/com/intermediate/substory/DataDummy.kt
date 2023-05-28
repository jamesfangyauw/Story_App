package com.dicoding.picodiploma.loginwithanimation

import com.dicoding.picodiploma.loginwithanimation.db.StoryEntity

object DataDummy {

    fun generateDummyQuoteResponse(): List<StoryEntity> {
        val items: MutableList<StoryEntity> = arrayListOf()
        for (i in 0..100) {
            val storyEntity = StoryEntity(
                i.toString(),
                "name + $i",
                "photoUrl$i",
                i.toDouble(),
                i.toDouble(),
            )
            items.add(storyEntity)
        }
        return items
    }
}