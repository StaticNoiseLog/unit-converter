// Formats conversion output values for display.
// Rounds to a maximum of 4 decimal places; uses scientific notation only
// when the rounded result would be illegible in standard decimal form.

package unitconverter.core

import kotlin.math.abs
import kotlin.math.roundToLong

object Formatter {

    private const val MAX_DECIMALS = 4
    private const val SCALE = 10_000.0
    private const val SCI_UPPER = 1e15
    private const val SCI_LOWER = 1e-4

    fun format(value: Double): String = when {
        value.isNaN() || value.isInfinite() -> value.toString()
        else -> formatFinite(value)
    }

    private fun formatFinite(value: Double): String {
        val rounded = (value * SCALE).roundToLong() / SCALE
        return when {
            rounded != 0.0 && (abs(rounded) >= SCI_UPPER || abs(rounded) < SCI_LOWER) -> buildScientific(value)
            else -> stripTrailingZeros(rounded)
        }
    }

    private fun buildScientific(value: Double): String {
        val formatted = "%.${MAX_DECIMALS}e".format(value)
        return formatted
    }

    private fun stripTrailingZeros(value: Double): String {
        val text = "%.${MAX_DECIMALS}f".format(value)
        if ('.' !in text) return text
        return text.trimEnd('0').trimEnd('.')
    }
}
