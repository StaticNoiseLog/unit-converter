// Temperature units with canonical (Kelvin) base unit conversion.
// Domain constraint: temperatures below absolute zero are rejected.

package unitconverter.module.temperature

import unitconverter.core.UnitDefinition
import unitconverter.core.ValidationResult

private const val ABSOLUTE_ZERO_MESSAGE = "Temperature below absolute zero (0 K) is not physically possible"
private const val CELSIUS_OFFSET = 273.15
private const val FAHRENHEIT_OFFSET = 459.67
private const val FAHRENHEIT_SCALE = 5.0 / 9.0
private const val FAHRENHEIT_INVERSE_SCALE = 9.0 / 5.0
private const val ABSOLUTE_ZERO_TOLERANCE = -1e-9

enum class TemperatureUnit(
    override val abbreviation: String,
    override val unitName: String,
    private val convertToBase: (Double) -> Double,
    private val convertFromBase: (Double) -> Double,
) : UnitDefinition {

    Celsius(
        abbreviation = "°C",
        unitName = "Celsius",
        convertToBase = { it + CELSIUS_OFFSET },
        convertFromBase = { it - CELSIUS_OFFSET },
    ),

    Fahrenheit(
        abbreviation = "°F",
        unitName = "Fahrenheit",
        convertToBase = { (it + FAHRENHEIT_OFFSET) * FAHRENHEIT_SCALE },
        convertFromBase = { it * FAHRENHEIT_INVERSE_SCALE - FAHRENHEIT_OFFSET },
    ),

    Kelvin(
        abbreviation = "K",
        unitName = "Kelvin",
        convertToBase = { it },
        convertFromBase = { it },
    );

    override fun toBase(value: Double): Double = convertToBase(value)

    override fun fromBase(value: Double): Double = convertFromBase(value)

    override fun validate(value: Double): ValidationResult {
        val kelvin = toBase(value)
        return if (kelvin < ABSOLUTE_ZERO_TOLERANCE) ValidationResult.Invalid(ABSOLUTE_ZERO_MESSAGE)
        else ValidationResult.Valid
    }
}
