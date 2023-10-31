package com.example.external.channel

import com.example.domain.channel.ChannelResponseDomain
import com.google.gson.annotations.SerializedName

data class ChannelEntity(

    @SerializedName("items")
    val items: List<Items>

) {
    data class Items(

        @SerializedName("id")
        val id: String,

        @SerializedName("snippet")
        val snippet: SnippetYt,

        @SerializedName("statistics")
        val statistics: StatisticsYt
    )

    fun mapChannelEntityListToDomain(entityList: List<Items>): List<ChannelResponseDomain.Items> {
        return entityList.map { entity ->
            ChannelResponseDomain.Items(
                id = entity.id,
                snippet = entity.snippet.mapSnippetYtToDomain(entity.snippet),
                statistic = entity.statistics.mapStatisticsYtToDomain(entity.statistics)
            )
        }
    }

}