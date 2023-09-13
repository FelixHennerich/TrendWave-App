package event

data class TrendWaveState(
    var LoginErrorMessage: String? = null,
    var RegisterErrorMessage: String? = null,
    var isAddPostSheetOpen: Boolean = false,
    var isSettingsSheetOpen: Boolean = false
)
