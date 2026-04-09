// Application theme: large fonts, color definitions for field and tab states.

package unitconverter.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

private const val ACTIVE_COLOR_HEX = 0xFFE8F5E9
private const val INVALID_COLOR_HEX = 0xFFFFCDD2
private const val FIELD_FONT_SIZE = 20
private const val LABEL_FONT_SIZE = 16

private const val TAB_ACTIVE_HEX = 0xFFE3F2FD
private const val TAB_INACTIVE_HEX = 0xFFF5F5F5
private const val TAB_ACTIVE_HOVER_HEX = 0xFFBBDEFB
private const val TAB_INACTIVE_HOVER_HEX = 0xFFE0E0E0

object Theme {
    val fieldFontSize = FIELD_FONT_SIZE.sp
    val labelFontSize = LABEL_FONT_SIZE.sp

    val activeColor = Color(ACTIVE_COLOR_HEX)
    val invalidColor = Color(INVALID_COLOR_HEX)
    val defaultColor = Color.White

    val tabActiveColor = Color(TAB_ACTIVE_HEX)
    val tabInactiveColor = Color(TAB_INACTIVE_HEX)
    val tabActiveHoverColor = Color(TAB_ACTIVE_HOVER_HEX)
    val tabInactiveHoverColor = Color(TAB_INACTIVE_HOVER_HEX)
}
