// Weight conversion module — registers Gram, Kilogram, Ounce, Pound.

package unitconverter.module.weight

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import unitconverter.core.ConversionModule
import unitconverter.core.ConversionViewModel
import unitconverter.core.UnitDefinition
import unitconverter.ui.ConversionTab

class WeightModule : ConversionModule {
    override val name: String = "Weight"
    override val units: List<UnitDefinition> = WeightUnit.entries

    @Composable
    override fun content() {
        val scope = rememberCoroutineScope()
        val viewModel = remember { ConversionViewModel(units, scope) }
        ConversionTab(viewModel)
    }
}
