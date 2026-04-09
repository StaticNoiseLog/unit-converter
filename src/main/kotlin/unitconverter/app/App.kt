// Root composable: renders the tab bar and content area.
// Tabs are generated dynamically from registered modules, sorted alphabetically.

package unitconverter.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import unitconverter.ui.Theme

@Composable
fun App(registry: ModuleRegistry) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            PrimaryTabRow(
                selectedTabIndex = selectedIndex,
                modifier = Modifier.testTag("tab-row"),
            ) {
                registry.modules.forEachIndexed { index, module ->
                    Tab(
                        selected = index == selectedIndex,
                        onClick = { selectedIndex = index },
                        text = {
                            Text(
                                text = module.name,
                                fontSize = Theme.labelFontSize,
                            )
                        },
                        modifier = Modifier.testTag("tab-${module.name}"),
                    )
                }
            }

            registry.modules.getOrNull(selectedIndex)?.content()
        }
    }
}
