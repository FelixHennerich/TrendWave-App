import androidx.compose.ui.window.ComposeUIViewController
import di.AppModule


fun MainViewController() = ComposeUIViewController {
    App(
        appModule = AppModule()
    )
}