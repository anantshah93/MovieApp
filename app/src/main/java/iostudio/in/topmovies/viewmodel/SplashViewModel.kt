package iostudio.`in`.topmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iostudio.`in`.topmovies.model.Splash
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SCREEN_DELAY = 2000L

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private var uiModelLiveData: MutableLiveData<Splash> = MutableLiveData()

    fun getSplashLiveData() = uiModelLiveData

    init {
        viewModelScope.launch {
            delay(SCREEN_DELAY)
            uiModelLiveData.postValue(Splash(showPopularMovieScreen = true))
        }
    }
}