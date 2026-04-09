// Tests for weight conversion logic using canonical (Gram) base unit.
// Covers all formula pairs, negative values, and round-trip accuracy.

package unitconverter.module.weight

import unitconverter.core.ValidationResult
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

class WeightConverterTest {

    private val epsilon = 1e-4

    private fun assertClose(expected: Double, actual: Double, message: String = "") {
        assertTrue(abs(expected - actual) < epsilon, "$message expected=$expected actual=$actual")
    }

    // --- Gram identity ---

    @Test
    fun `gram toBase is identity`() {
        assertClose(500.0, WeightUnit.Gram.toBase(500.0))
    }

    @Test
    fun `gram fromBase is identity`() {
        assertClose(500.0, WeightUnit.Gram.fromBase(500.0))
    }

    // --- Kilogram ---

    @Test
    fun `kilogram 1 to gram`() {
        assertClose(1000.0, WeightUnit.Kilogram.toBase(1.0))
    }

    @Test
    fun `gram 1000 to kilogram`() {
        assertClose(1.0, WeightUnit.Kilogram.fromBase(1000.0))
    }

    // --- Ounce ---

    @Test
    fun `ounce 1 to gram`() {
        assertClose(28.3495, WeightUnit.Ounce.toBase(1.0))
    }

    @Test
    fun `gram to ounce`() {
        assertClose(1.0, WeightUnit.Ounce.fromBase(28.3495))
    }

    // --- Pound ---

    @Test
    fun `pound 1 to gram`() {
        assertClose(453.592, WeightUnit.Pound.toBase(1.0))
    }

    @Test
    fun `gram to pound`() {
        assertClose(1.0, WeightUnit.Pound.fromBase(453.592))
    }

    // --- PRD acceptance: 1000g -> 1kg, 35.274oz, 2.2046lb ---

    @Test
    fun `prd - 1000 gram to kilogram`() {
        assertClose(1.0, WeightUnit.Kilogram.fromBase(WeightUnit.Gram.toBase(1000.0)))
    }

    @Test
    fun `prd - 1000 gram to ounce`() {
        assertClose(35.274, WeightUnit.Ounce.fromBase(WeightUnit.Gram.toBase(1000.0)))
    }

    @Test
    fun `prd - 1000 gram to pound`() {
        assertClose(2.2046, WeightUnit.Pound.fromBase(WeightUnit.Gram.toBase(1000.0)))
    }

    // --- Negative values accepted ---

    @Test
    fun `negative kilogram converts correctly`() {
        assertClose(-5000.0, WeightUnit.Kilogram.toBase(-5.0))
    }

    @Test
    fun `prd - minus 5 kg to ounce`() {
        assertClose(-176.37, WeightUnit.Ounce.fromBase(WeightUnit.Kilogram.toBase(-5.0)))
    }

    @Test
    fun `prd - minus 5 kg to pound`() {
        assertClose(-11.0231, WeightUnit.Pound.fromBase(WeightUnit.Kilogram.toBase(-5.0)))
    }

    // --- Round-trip accuracy ---

    @Test
    fun `kilogram round-trip through gram`() {
        val original = 3.5
        val roundTripped = WeightUnit.Kilogram.fromBase(WeightUnit.Kilogram.toBase(original))
        assertClose(original, roundTripped)
    }

    @Test
    fun `ounce round-trip through gram`() {
        val original = 16.0
        val roundTripped = WeightUnit.Ounce.fromBase(WeightUnit.Ounce.toBase(original))
        assertClose(original, roundTripped)
    }

    // --- Validation: all weight units accept any value ---

    @Test
    fun `gram validation always valid`() {
        assertIs<ValidationResult.Valid>(WeightUnit.Gram.validate(0.0))
        assertIs<ValidationResult.Valid>(WeightUnit.Gram.validate(-100.0))
        assertIs<ValidationResult.Valid>(WeightUnit.Gram.validate(1e15))
    }

    @Test
    fun `kilogram validation always valid`() {
        assertIs<ValidationResult.Valid>(WeightUnit.Kilogram.validate(-999.0))
    }

    @Test
    fun `ounce validation always valid`() {
        assertIs<ValidationResult.Valid>(WeightUnit.Ounce.validate(-1.0))
    }

    @Test
    fun `pound validation always valid`() {
        assertIs<ValidationResult.Valid>(WeightUnit.Pound.validate(-50.0))
    }
}
