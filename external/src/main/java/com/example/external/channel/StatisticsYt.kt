package com.example.external.channel

import com.example.domain.channel.StatisticsYtDomain
import com.google.gson.annotations.SerializedName

data class StatisticsYt(
    @SerializedName("hiddenSubscriberCount")
    val hiddenSubscriberCount: Boolean,
    @SerializedName("subscriberCount")
    val subscriberCount: String,
    @SerializedName("videoCount")
    val videoCount: String,
    @SerializedName("viewCount")
    val viewCount: String
) {
    fun mapStatisticsYtToDomain(statisticsYt: StatisticsYt): StatisticsYtDomain {
        return StatisticsYtDomain(
            hiddenSubscriberCount = statisticsYt.hiddenSubscriberCount,
            subscriberCount = statisticsYt.subscriberCount,
            videoCount = statisticsYt.videoCount,
            viewCount = statisticsYt.viewCount
        )
    }

}