// Describes a single unit within a conversion module (e.g., Celsius, Gram).

package unitconverter.core

interface UnitDefinition {
    val name: String
    val abbreviation: String
    fun toBase(value: Double): Double
    fun fromBase(value: Double): Double
    fun validate(value: Double): ValidationResult
}
