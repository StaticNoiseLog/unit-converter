// Shared composable for rendering a conversion tab's content.
// Displays unit fields vertically with a reset button at the bottom.

package unitconverter.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import unitconverter.core.ConversionViewModel

@Composable
fun ConversionTab(viewModel: ConversionViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        viewModel.units.forEach { unit ->
            val field = viewModel.fields[unit]
            UnitField(
                label = unit.unitName,
                abbreviation = unit.abbreviation,
                value = field?.text.orEmpty(),
                state = field?.state ?: unitconverter.core.FieldState.Default,
                errorMessage = field?.errorMessage,
                onValueChanged = { viewModel.onValueChanged(unit, it) },
                onFocusLost = { viewModel.onFocusLost(unit) },
            )
        }
        ResetButton(onClick = { viewModel.reset() })
    }
}
