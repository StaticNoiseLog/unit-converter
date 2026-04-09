// Tests for temperature domain validation — absolute zero boundary (REQ-014).

package unitconverter.module.temperature

import unitconverter.core.ValidationResult
import kotlin.test.Test
import kotlin.test.assertIs

class TemperatureValidationTest {

    // --- Kelvin validation ---

    @Test
    fun `kelvin 0 is valid`() {
        assertIs<ValidationResult.Valid>(TemperatureUnit.Kelvin.validate(0.0))
    }

    @Test
    fun `kelvin positive is valid`() {
        assertIs<ValidationResult.Valid>(TemperatureUnit.Kelvin.validate(300.0))
    }

    @Test
    fun `kelvin negative is invalid`() {
        val result = TemperatureUnit.Kelvin.validate(-0.01)
        assertIs<ValidationResult.Invalid>(result)
    }

    @Test
    fun `kelvin minus 300 is invalid with message`() {
        val result = TemperatureUnit.Kelvin.validate(-300.0)
        assertIs<ValidationResult.Invalid>(result)
        assert(result.message.contains("absolute zero", ignoreCase = true))
    }

    // --- Celsius validation: absolute zero boundary ---

    @Test
    fun `celsius minus 273_15 is valid`() {
        assertIs<ValidationResult.Valid>(TemperatureUnit.Celsius.validate(-273.15))
    }

    @Test
    fun `celsius below absolute zero is invalid`() {
        val result = TemperatureUnit.Celsius.validate(-273.16)
        assertIs<ValidationResult.Invalid>(result)
    }

    @Test
    fun `celsius 100 is valid`() {
        assertIs<ValidationResult.Valid>(TemperatureUnit.Celsius.validate(100.0))
    }

    // --- Fahrenheit validation: absolute zero boundary ---

    @Test
    fun `fahrenheit minus 459_67 is valid`() {
        assertIs<ValidationResult.Valid>(TemperatureUnit.Fahrenheit.validate(-459.67))
    }

    @Test
    fun `fahrenheit below absolute zero is invalid`() {
        val result = TemperatureUnit.Fahrenheit.validate(-459.68)
        assertIs<ValidationResult.Invalid>(result)
    }

    @Test
    fun `fahrenheit 212 is valid`() {
        assertIs<ValidationResult.Valid>(TemperatureUnit.Fahrenheit.validate(212.0))
    }
}
