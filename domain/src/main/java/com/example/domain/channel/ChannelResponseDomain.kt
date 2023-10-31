package com.example.domain.channel

data class ChannelResponseDomain(
    val items: List<Items>? = null
) {
    data class Items(
        val id: String? = null,
        val snippet: SnippetYtDomain? = null,
        val statistic: StatisticsYtDomain? = null
    )
}
