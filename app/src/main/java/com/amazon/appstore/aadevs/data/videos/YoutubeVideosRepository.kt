package com.amazon.appstore.aadevs.data.videos

import com.amazon.appstore.aadevs.BuildConfig
import com.amazon.appstore.aadevs.data.Result
import com.prof.youtubeparser.Parser
import com.prof.youtubeparser.core.ParsingResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YoutubeVideosRepository : VideosRepository {

    private val channelID = "UCT9ApARFgQJOeqD-ygmxnJQ"

    override suspend fun getVideos(): Result<ParsingResult> {
        val parser = Parser()

        return withContext(Dispatchers.IO) {

            val requestUrl = parser.generateRequest(
                channelID = channelID,
                maxResult = 20,
                orderType = Parser.ORDER_DATE,
                key = gcloud_api_key
            )
            try {
                val result = parser.getVideos(requestUrl)
                Result.Success(result)

            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    companion object {
        const val gcloud_api_key = BuildConfig.GOOGLE_CLOUD_API_KEY
    }
}
