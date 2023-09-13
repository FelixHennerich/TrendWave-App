package event


import account.image.ImageDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import utilities.CommonLogger

class TrendWaveViewModel(
    private val imageDataSource: ImageDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(TrendWaveState())

    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), TrendWaveState())

    /**
     * Will watch every event
     *
     * @param event -> Eventlist of TrendWaveEvent
     */
    fun onEvent(event: TrendWaveEvent){
        when(event){
            is TrendWaveEvent.ChangeRegisterErrorMessage -> {
                _state.update {it.copy(
                    RegisterErrorMessage = event.message
                )
                }
            }
            is TrendWaveEvent.ChangeLoginErrorMessage -> {
                _state.update {it.copy(
                    LoginErrorMessage = event.message
                )
                }
            }
            else -> {}
        }
    }
}