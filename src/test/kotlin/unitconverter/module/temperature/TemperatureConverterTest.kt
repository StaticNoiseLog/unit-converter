// Tests for temperature conversion logic using canonical (Kelvin) base unit.
// Covers all formula pairs, round-trip accuracy, and edge cases.

package unitconverter.module.temperature

import unitconverter.core.ValidationResult
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TemperatureConverterTest {

    private val epsilon = 1e-9

    private fun assertClose(expected: Double, actual: Double, message: String = "") {
        assertTrue(abs(expected - actual) < epsilon, "$message expected=$expected actual=$actual")
    }

    // --- Celsius to Kelvin ---

    @Test
    fun `celsius 0 to kelvin`() {
        assertClose(273.15, TemperatureUnit.Celsius.toBase(0.0))
    }

    @Test
    fun `celsius 100 to kelvin`() {
        assertClose(373.15, TemperatureUnit.Celsius.toBase(100.0))
    }

    @Test
    fun `celsius minus 273_15 to kelvin`() {
        assertClose(0.0, TemperatureUnit.Celsius.toBase(-273.15))
    }

    // --- Kelvin to Celsius ---

    @Test
    fun `kelvin 0 to celsius`() {
        assertClose(-273.15, TemperatureUnit.Celsius.fromBase(0.0))
    }

    @Test
    fun `kelvin 373_15 to celsius`() {
        assertClose(100.0, TemperatureUnit.Celsius.fromBase(373.15))
    }

    // --- Fahrenheit to Kelvin ---

    @Test
    fun `fahrenheit 32 to kelvin`() {
        assertClose(273.15, TemperatureUnit.Fahrenheit.toBase(32.0))
    }

    @Test
    fun `fahrenheit 212 to kelvin`() {
        assertClose(373.15, TemperatureUnit.Fahrenheit.toBase(212.0))
    }

    @Test
    fun `fahrenheit minus 459_67 to kelvin`() {
        assertClose(0.0, TemperatureUnit.Fahrenheit.toBase(-459.67))
    }

    // --- Kelvin to Fahrenheit ---

    @Test
    fun `kelvin 0 to fahrenheit`() {
        assertClose(-459.67, TemperatureUnit.Fahrenheit.fromBase(0.0))
    }

    @Test
    fun `kelvin 373_15 to fahrenheit`() {
        assertClose(212.0, TemperatureUnit.Fahrenheit.fromBase(373.15))
    }

    // --- Kelvin identity ---

    @Test
    fun `kelvin toBase is identity`() {
        assertClose(300.0, TemperatureUnit.Kelvin.toBase(300.0))
    }

    @Test
    fun `kelvin fromBase is identity`() {
        assertClose(300.0, TemperatureUnit.Kelvin.fromBase(300.0))
    }

    // --- Round-trip accuracy ---

    @Test
    fun `celsius round-trip through kelvin`() {
        val original = 36.6
        val roundTripped = TemperatureUnit.Celsius.fromBase(TemperatureUnit.Celsius.toBase(original))
        assertClose(original, roundTripped)
    }

    @Test
    fun `fahrenheit round-trip through kelvin`() {
        val original = 98.6
        val roundTripped = TemperatureUnit.Fahrenheit.fromBase(TemperatureUnit.Fahrenheit.toBase(original))
        assertClose(original, roundTripped)
    }

    // --- Cross-unit via base: Celsius 100 -> Fahrenheit 212 ---

    @Test
    fun `celsius 100 to fahrenheit via kelvin`() {
        val kelvin = TemperatureUnit.Celsius.toBase(100.0)
        assertClose(212.0, TemperatureUnit.Fahrenheit.fromBase(kelvin))
    }

    // --- PRD acceptance: 0 K -> -273.15 C, -459.67 F ---

    @Test
    fun `prd - kelvin 0 converts to celsius and fahrenheit`() {
        assertClose(-273.15, TemperatureUnit.Celsius.fromBase(0.0))
        assertClose(-459.67, TemperatureUnit.Fahrenheit.fromBase(0.0))
    }
}
