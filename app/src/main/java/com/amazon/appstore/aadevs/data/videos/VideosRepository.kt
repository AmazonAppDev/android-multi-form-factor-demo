// Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0


package com.amazon.appstore.aadevs.data.videos

import com.amazon.appstore.aadevs.data.Result
import com.prof.youtubeparser.core.ParsingResult

interface VideosRepository {
    suspend fun getVideos(): Result<ParsingResult>
}
