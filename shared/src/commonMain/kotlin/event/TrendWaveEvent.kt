package event

sealed interface TrendWaveEvent {
    object ChangeLoginErrorMessage: TrendWaveEvent
    data class ChangeRegisterErrorMessage(val message: String): TrendWaveEvent
}