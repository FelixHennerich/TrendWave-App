package event


import dev.icerock.moko.mvvm.viewmodel.ViewModel
import event.TrendWaveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import event.TrendWaveState
import io.ktor.util.logging.Logger
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utilities.CommonLogger

class TrendWaveViewModel(

): ViewModel() {
    private val _state = MutableStateFlow(TrendWaveState())

    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), TrendWaveState())

    fun onEvent(event: TrendWaveEvent){
        when(event){
            is TrendWaveEvent.ChangeRegisterErrorMessage -> {
                _state.update {it.copy(
                    RegisterErrorMessage = "Error while creating account"
                )
                }
            }
            is TrendWaveEvent.ChangeLoginErrorMessage -> {
                val logger = CommonLogger()
                logger.log("ViewModel 1")

                _state.update {it.copy(
                    LoginErrorMessage = "Error while logging in"
                )
                }

            }
            else -> {}
        }
    }
}