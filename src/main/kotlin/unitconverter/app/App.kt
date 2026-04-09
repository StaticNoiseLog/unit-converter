// Root composable: renders the tab bar and content area.
// Tabs are generated dynamically from registered modules, sorted alphabetically.
// All tabs remain in the composition tree to preserve state (ADR-002).
// Hidden tabs are non-interactive to prevent focus stealing.

package unitconverter.app

import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import unitconverter.ui.Theme

private val TAB_HEIGHT = 48.dp
private val DIVIDER_THICKNESS = 1.dp
private val OFF_SCREEN_OFFSET = 10_000.dp

@Composable
fun App(registry: ModuleRegistry) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TabBar(
                modules = registry.modules,
                selectedIndex = selectedIndex,
                onTabSelected = { selectedIndex = it },
            )
            HorizontalDivider(thickness = DIVIDER_THICKNESS)
            TabContent(
                modules = registry.modules,
                selectedIndex = selectedIndex,
            )
        }
    }
}

@Composable
private fun TabBar(
    modules: List<unitconverter.core.ConversionModule>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("tab-row"),
    ) {
        modules.forEachIndexed { index, module ->
            val selected = index == selectedIndex
            val interactionSource = remember { MutableInteractionSource() }
            val isHovered by interactionSource.collectIsHoveredAsState()

            val backgroundColor = when {
                selected && isHovered -> Theme.tabActiveHoverColor
                selected -> Theme.tabActiveColor
                isHovered -> Theme.tabInactiveHoverColor
                else -> Theme.tabInactiveColor
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(TAB_HEIGHT)
                    .background(backgroundColor)
                    .hoverable(interactionSource)
                    .pointerHoverIcon(PointerIcon.Hand)
                    .selectable(
                        selected = selected,
                        onClick = { onTabSelected(index) },
                        role = Role.Tab,
                    )
                    .testTag("tab-${module.name}"),
            ) {
                Text(
                    text = module.name,
                    fontSize = Theme.labelFontSize,
                    color = if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                )
            }
        }
    }
}

@Composable
private fun TabContent(
    modules: List<unitconverter.core.ConversionModule>,
    selectedIndex: Int,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        modules.forEachIndexed { index, module ->
            val visible = index == selectedIndex
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .then(
                        if (visible) Modifier
                        else Modifier.absoluteOffset(x = OFF_SCREEN_OFFSET)
                    ),
            ) {
                module.content()
            }
        }
    }
}
