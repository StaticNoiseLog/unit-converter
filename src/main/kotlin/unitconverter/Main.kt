// Application entry point for the Unit Converter desktop application.
// Bootstraps the module registry and launches the Compose window.

package unitconverter

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import unitconverter.app.App
import unitconverter.app.ModuleRegistry
import unitconverter.module.temperature.TemperatureModule
import unitconverter.module.weight.WeightModule

private const val WINDOW_WIDTH = 500
private const val WINDOW_HEIGHT = 600

fun main() = application {
    val registry = ModuleRegistry(
        listOf(
            TemperatureModule(),
            WeightModule(),
        )
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "Unit Converter",
        state = rememberWindowState(width = WINDOW_WIDTH.dp, height = WINDOW_HEIGHT.dp),
    ) {
        App(registry)
    }
}
