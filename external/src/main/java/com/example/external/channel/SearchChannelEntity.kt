package com.example.external.channel

import com.example.domain.channel.SearchChannelResponseDomain
import com.google.gson.annotations.SerializedName

data class SearchChannelEntity(

    @SerializedName("items")
    val items: List<Items>

) {
    data class Items(

        @SerializedName("id")
        val id: IdYt,

        @SerializedName("snippet")
        val snippet: SnippetYt

    )

    fun mapSearchChannelEntityListToDomain(entityList: List<Items>): List<SearchChannelResponseDomain.Items> {
        return entityList.map { entity ->
            SearchChannelResponseDomain.Items(
                id = entity.id.mapIdYtToDomain(entity.id),
                snippet = entity.snippet.mapSnippetYtToDomain(entity.snippet)
            )
        }
    }

}