package event

sealed interface TrendWaveEvent {
    object ChangeLoginErrorMessage: TrendWaveEvent
    class ChangeRegisterErrorMessage(val message: String): TrendWaveEvent
}