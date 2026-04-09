// Reusable composable: clears all fields on the current tab.

package unitconverter.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun ResetButton(
    onClick: () -> Unit,
    testTag: String = "reset-button",
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(16.dp)
            .testTag(testTag),
    ) {
        Text("Reset", fontSize = Theme.fieldFontSize)
    }
}
