// Formats conversion output values for display.
// Rounds to a maximum of 4 decimal places; uses scientific notation only
// when the rounded result would be illegible in standard decimal form.

package unitconverter.core

import kotlin.math.abs

object Formatter {

    private const val MAX_DECIMALS = 4
    private const val SCI_UPPER = 1e15
    private const val SCI_LOWER = 1e-4

    fun format(value: Double): String = when {
        value.isNaN() || value.isInfinite() -> value.toString()
        else -> formatFinite(value)
    }

    private fun formatFinite(value: Double): String {
        val absValue = abs(value)
        return when {
            absValue == 0.0 -> "0"
            absValue >= SCI_UPPER || absValue < SCI_LOWER -> buildScientific(value)
            else -> stripTrailingZeros("%.${MAX_DECIMALS}f".format(value))
        }
    }

    private fun buildScientific(value: Double): String = "%.${MAX_DECIMALS}e".format(value)

    private fun stripTrailingZeros(text: String): String {
        if ('.' !in text) return text
        return text.trimEnd('0').trimEnd('.')
    }
}
