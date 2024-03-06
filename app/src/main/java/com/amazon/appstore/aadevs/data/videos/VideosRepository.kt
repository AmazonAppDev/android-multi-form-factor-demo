package com.amazon.appstore.aadevs.data.videos

import com.amazon.appstore.aadevs.data.Result
import com.prof.youtubeparser.core.ParsingResult

interface VideosRepository {
    suspend fun getVideos(): Result<ParsingResult>
}
