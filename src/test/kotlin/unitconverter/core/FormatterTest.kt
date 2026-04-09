// Tests for output formatting: rounding, trailing zeros, scientific notation, edge cases.

package unitconverter.core

import kotlin.test.Test
import kotlin.test.assertEquals

class FormatterTest {

    // --- Rounding to max 4 decimal places ---

    @Test
    fun `integer value has no decimals`() {
        assertEquals("212", Formatter.format(212.0))
    }

    @Test
    fun `value with fewer than 4 decimals is not padded`() {
        assertEquals("373.15", Formatter.format(373.15))
    }

    @Test
    fun `value rounded to 4 decimal places`() {
        assertEquals("35.274", Formatter.format(35.27396195))
    }

    @Test
    fun `exact 4 decimal places preserved`() {
        assertEquals("2.2046", Formatter.format(2.20462))
    }

    @Test
    fun `zero formats as zero`() {
        assertEquals("0", Formatter.format(0.0))
    }

    @Test
    fun `negative zero formats as zero`() {
        assertEquals("0", Formatter.format(-0.0))
    }

    @Test
    fun `negative value formatted correctly`() {
        assertEquals("-273.15", Formatter.format(-273.15))
    }

    @Test
    fun `negative value with rounding`() {
        assertEquals("-459.67", Formatter.format(-459.67))
    }

    // --- Trailing zeros stripped ---

    @Test
    fun `trailing zeros removed`() {
        assertEquals("1", Formatter.format(1.00000))
    }

    @Test
    fun `partial trailing zeros removed`() {
        assertEquals("3.5", Formatter.format(3.50000))
    }

    // --- Scientific notation for extreme values ---

    @Test
    fun `very large value uses scientific notation`() {
        val result = Formatter.format(1.23e18)
        assert(result.contains("e", ignoreCase = true)) { "Expected scientific notation, got: $result" }
    }

    @Test
    fun `very small value uses scientific notation`() {
        val result = Formatter.format(1.5e-7)
        assert(result.contains("e", ignoreCase = true)) { "Expected scientific notation, got: $result" }
    }

    @Test
    fun `value just below sci threshold uses decimal`() {
        assertEquals("0.001", Formatter.format(0.001))
    }

    @Test
    fun `value at upper boundary uses decimal`() {
        assertEquals("999999999999999", Formatter.format(999_999_999_999_999.0))
    }

    // --- Edge cases ---

    @Test
    fun `NaN formats as NaN`() {
        assertEquals("NaN", Formatter.format(Double.NaN))
    }

    @Test
    fun `positive infinity formats as Infinity`() {
        assertEquals("Infinity", Formatter.format(Double.POSITIVE_INFINITY))
    }

    @Test
    fun `negative infinity formats as negative Infinity`() {
        assertEquals("-Infinity", Formatter.format(Double.NEGATIVE_INFINITY))
    }
}
