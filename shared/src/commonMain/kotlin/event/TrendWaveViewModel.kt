package event


import dev.icerock.moko.mvvm.viewmodel.ViewModel
import event.TrendWaveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import event.TrendWaveState
import io.ktor.util.logging.Logger
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utilities.CommonLogger

class TrendWaveViewModel(

): ViewModel() {
    private  val _state = MutableStateFlow(TrendWaveState(
        LoginErrorMessage = null,
        RegisterErrorMessage = null
    ))



    fun onEvent(event: TrendWaveEvent){
        when(event){
            is TrendWaveEvent.ChangeRegisterErrorMessage -> {

                viewModelScope.launch {
                    _state.update { it.copy(
                        LoginErrorMessage = null,
                        RegisterErrorMessage = "abcd"
                    )
                    }
                }
            }
            is TrendWaveEvent.ChangeLoginErrorMessage -> {
                val logger = CommonLogger()
                logger.log("ViewModel 1")
                viewModelScope.launch {
                    _state.update { it.copy(
                        LoginErrorMessage = "ahewduehudw",
                        RegisterErrorMessage = null
                    )
                    }
                }
            }
            else -> {}
        }
    }
}