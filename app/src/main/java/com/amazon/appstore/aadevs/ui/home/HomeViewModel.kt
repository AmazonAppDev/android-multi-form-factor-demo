package com.amazon.appstore.aadevs.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.amazon.appstore.aadevs.R
import com.amazon.appstore.aadevs.data.Result
import com.amazon.appstore.aadevs.data.videos.VideosRepository
import com.amazon.appstore.aadevs.utils.ErrorMessage
import com.amazon.appstore.aadevs.utils.YouTubeLauncher
import com.prof.youtubeparser.models.videos.Video
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>
    val searchInput: String

    data class NoVideos(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiState

    data class HasVideos(
        val videosFeed: List<Video>,
        val selectedVideo: Video,
        val isVideoOpen: Boolean,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiState
}

private data class HomeViewModelState(
    val videosFeed: List<Video>? = null,
    val selectedVideoId: String? = null,
    val isVideoDetailsOpened: Boolean = false,
    val favorites: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val searchInput: String = "",
) {

    fun toUiState(): HomeUiState =
        if (videosFeed == null) {
            HomeUiState.NoVideos(
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        } else {
            HomeUiState.HasVideos(
                videosFeed = videosFeed,
                selectedVideo = videosFeed.find {
                    it.videoId == selectedVideoId
                } ?: videosFeed.first(),
                isVideoOpen = isVideoDetailsOpened,
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        }
}

class HomeViewModel(
    private val videosRepository: VideosRepository,
    private val youTubeLauncher: YouTubeLauncher
) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        refreshVideos()
    }

    private fun refreshVideos() {

        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = videosRepository.getVideos()
            viewModelState.update {
                when (result) {
                    is Result.Success -> it.copy(videosFeed = result.data.videos, isLoading = false)
                    is Result.Error -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_error
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }
            }
        }
    }

    fun selectVideo(videoId: String) {
        interactedWithVideoDetails(videoId)
    }

    fun errorShown(errorId: Long) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages.filterNot { it.id == errorId }
            currentUiState.copy(errorMessages = errorMessages)
        }
    }

    fun interactedWithVideoList() {
        viewModelState.update {
            it.copy(isVideoDetailsOpened = false)
        }
    }

    fun interactedWithVideoDetails(videoId: String) {
        viewModelState.update {
            it.copy(
                selectedVideoId = videoId,
                isVideoDetailsOpened = true
            )
        }
    }

    fun onSearchInputChanged(searchInput: String) {
        viewModelState.update {
            it.copy(searchInput = searchInput)
        }
    }

    fun onClickVideo(videoId: String) {
        youTubeLauncher.onClickVideo(videoId)
    }

    companion object {
        fun provideFactory(
            videosRepository: VideosRepository,
            youTubeLauncher: YouTubeLauncher,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(videosRepository, youTubeLauncher) as T
            }
        }
    }
}
