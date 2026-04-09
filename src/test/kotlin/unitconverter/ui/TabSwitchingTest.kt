// Compose UI tests for tab switching with state preservation and reset behavior.
// Uses compose.desktop.uiTestJUnit4 with JUnit 4 (ADR-004).

package unitconverter.ui

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import unitconverter.app.App
import unitconverter.app.ModuleRegistry
import unitconverter.module.temperature.TemperatureModule
import unitconverter.module.weight.WeightModule

class TabSwitchingTest {

    @get:Rule
    val rule = createComposeRule()

    private fun setUpApp() {
        val registry = ModuleRegistry(
            listOf(TemperatureModule(), WeightModule())
        )
        rule.setContent { App(registry) }
    }

    @Test
    fun `temperature tab is selected by default`() {
        setUpApp()
        rule.onNodeWithTag("field-Celsius").assertExists()
    }

    @Test
    fun `switching to weight tab shows weight fields`() {
        setUpApp()
        rule.onNodeWithTag("tab-Weight").performClick()
        rule.waitForIdle()
        rule.onNodeWithTag("field-Gram").assertExists()
    }

    @Test
    fun `state preserved when switching tabs and back`() {
        setUpApp()

        // Type a value in the Celsius field
        rule.onNodeWithTag("field-Celsius").performTextInput("100")
        rule.waitForIdle()

        // Switch to Weight tab
        rule.onNodeWithTag("tab-Weight").performClick()
        rule.waitForIdle()

        // Switch back to Temperature tab
        rule.onNodeWithTag("tab-Temperature").performClick()
        rule.waitForIdle()

        // Celsius field should still contain "100"
        rule.onNodeWithTag("field-Celsius").assertTextContains("100")
    }

    @Test
    fun `reset clears fields on current tab only`() {
        setUpApp()

        // Type a value in the Celsius field
        rule.onNodeWithTag("field-Celsius").performTextInput("50")
        rule.waitForIdle()

        // Switch to Weight, type a value
        rule.onNodeWithTag("tab-Weight").performClick()
        rule.waitForIdle()
        rule.onNodeWithTag("field-Gram").performTextInput("1000")
        rule.waitForIdle()

        // Reset on Weight tab
        rule.onNodeWithTag("reset-Weight").performClick()
        rule.waitForIdle()

        // Weight fields should be empty
        rule.onNodeWithTag("field-Gram").assertTextContains("")

        // Switch back to Temperature — values should be preserved
        rule.onNodeWithTag("tab-Temperature").performClick()
        rule.waitForIdle()
        rule.onNodeWithTag("field-Celsius").assertTextContains("50")
    }

    @Test
    fun `input goes to visible tab only`() {
        setUpApp()

        // Type on Temperature tab
        rule.onNodeWithTag("field-Celsius").performTextInput("42")
        rule.waitForIdle()
        rule.onNodeWithTag("field-Celsius").assertTextContains("42")

        // Switch to Weight tab
        rule.onNodeWithTag("tab-Weight").performClick()
        rule.waitForIdle()

        // Gram field should be empty (input did not leak)
        rule.onNodeWithTag("field-Gram").assertTextContains("")

        // Type on Weight tab
        rule.onNodeWithTag("field-Gram").performTextInput("500")
        rule.waitForIdle()
        rule.onNodeWithTag("field-Gram").assertTextContains("500")

        // Switch back — Temperature should still have "42"
        rule.onNodeWithTag("tab-Temperature").performClick()
        rule.waitForIdle()
        rule.onNodeWithTag("field-Celsius").assertTextContains("42")
    }
}
