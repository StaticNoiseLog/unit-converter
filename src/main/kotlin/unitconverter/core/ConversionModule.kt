// Contract that every conversion module must implement.
// Provides the module's identity, units, conversion logic, and UI composable.

package unitconverter.core

import androidx.compose.runtime.Composable

interface ConversionModule {
    val name: String
    val units: List<UnitDefinition>

    fun convert(value: Double, from: UnitDefinition, to: UnitDefinition): Double =
        to.fromBase(from.toBase(value))

    @Composable
    fun content()
}
