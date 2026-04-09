// Temperature conversion module — registers Celsius, Fahrenheit, Kelvin.

package unitconverter.module.temperature

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import unitconverter.core.ConversionModule
import unitconverter.core.ConversionViewModel
import unitconverter.core.UnitDefinition
import unitconverter.ui.ConversionTab

class TemperatureModule : ConversionModule {
    override val name: String = "Temperature"
    override val units: List<UnitDefinition> = TemperatureUnit.entries

    @Composable
    override fun content() {
        val scope = rememberCoroutineScope()
        val viewModel = remember { ConversionViewModel(units, scope) }
        ConversionTab(viewModel)
    }
}
