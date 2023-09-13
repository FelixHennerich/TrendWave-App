package event

sealed interface TrendWaveEvent {
    class ChangeLoginErrorMessage(val message: String): TrendWaveEvent
    class ChangeRegisterErrorMessage(val message: String): TrendWaveEvent
}