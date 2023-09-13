package event

sealed interface TrendWaveEvent {
    class ChangeLoginErrorMessage(val message: String): TrendWaveEvent
    class ChangeRegisterErrorMessage(val message: String): TrendWaveEvent
    object ClickPostButton: TrendWaveEvent
    object ClickSettingsScreen: TrendWaveEvent
    object TestHomeButton: TrendWaveEvent
    object ClickClosePostButton: TrendWaveEvent
    object ClickCloseSettingsScreen: TrendWaveEvent
}