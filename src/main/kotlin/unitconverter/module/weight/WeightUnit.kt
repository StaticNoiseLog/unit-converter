// Weight units with canonical (Gram) base unit conversion.
// No domain constraints — negative values are accepted.

package unitconverter.module.weight

import unitconverter.core.UnitDefinition
import unitconverter.core.ValidationResult

private const val GRAMS_PER_KILOGRAM = 1000.0
private const val GRAMS_PER_OUNCE = 28.3495
private const val GRAMS_PER_POUND = 453.592

enum class WeightUnit(
    override val abbreviation: String,
    override val unitName: String,
    private val factor: Double,
) : UnitDefinition {

    Gram("g", "Gram", 1.0),
    Kilogram("kg", "Kilogram", GRAMS_PER_KILOGRAM),
    Ounce("oz", "Ounce", GRAMS_PER_OUNCE),
    Pound("lb", "Pound", GRAMS_PER_POUND);

    override fun toBase(value: Double): Double = value * factor

    override fun fromBase(value: Double): Double = value / factor

    override fun validate(value: Double): ValidationResult = ValidationResult.Valid
}
