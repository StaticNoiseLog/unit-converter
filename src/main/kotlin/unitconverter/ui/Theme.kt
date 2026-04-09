// Application theme: large fonts, color definitions for field states.

package unitconverter.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

private const val ACTIVE_COLOR_HEX = 0xFFE8F5E9
private const val INVALID_COLOR_HEX = 0xFFFFCDD2
private const val FIELD_FONT_SIZE = 20
private const val LABEL_FONT_SIZE = 16

object Theme {
    val fieldFontSize = FIELD_FONT_SIZE.sp
    val labelFontSize = LABEL_FONT_SIZE.sp

    val activeColor = Color(ACTIVE_COLOR_HEX)
    val invalidColor = Color(INVALID_COLOR_HEX)
    val defaultColor = Color.White
}
