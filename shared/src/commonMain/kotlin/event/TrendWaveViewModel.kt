package event


import account.image.ImageDataSource
import account.image.ImageStorage
import account.image.StorageImageDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import di.AppModule
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

    fun onEvent(event: TrendWaveEvent){
        when(event){
            is TrendWaveEvent.ChangeRegisterErrorMessage -> {
                _state.update {it.copy(
                    RegisterErrorMessage = "Error while creating account"
                )
                }
            }
            is TrendWaveEvent.ChangeLoginErrorMessage -> {
                _state.update {it.copy(
                    LoginErrorMessage = "Error while logging in"
                )
                }
            }
            else -> {}
        }
    }
}