// Reusable composable: labeled text input with unit abbreviation,
// background color reflecting field state, and tooltip for errors.

package unitconverter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import unitconverter.core.FieldState

@Composable
fun UnitField(
    label: String,
    abbreviation: String,
    value: String,
    state: FieldState,
    errorMessage: String?,
    onValueChanged: (String) -> Unit,
    onFocusLost: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = when (state) {
        FieldState.Active -> Theme.activeColor
        FieldState.Invalid -> Theme.invalidColor
        FieldState.Default -> Theme.defaultColor
    }

    val tag = remember(label) { "field-$label" }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            label = { Text("$label ($abbreviation)", fontSize = Theme.labelFontSize) },
            isError = state == FieldState.Invalid,
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = Theme.fieldFontSize),
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .testTag(tag)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) onFocusLost()
                },
        )
        if (state == FieldState.Invalid && errorMessage != null) {
            Text(
                text = errorMessage,
                color = androidx.compose.ui.graphics.Color.Red,
                fontSize = Theme.labelFontSize,
                modifier = Modifier
                    .padding(start = 4.dp, top = 2.dp)
                    .testTag("$tag-error"),
            )
        }
    }
}
