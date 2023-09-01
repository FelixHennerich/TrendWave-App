package event

sealed interface TrendWaveEvent {
    object ChangeLoginErrorMessage: TrendWaveEvent
    object ChangeRegisterErrorMessage: TrendWaveEvent
}