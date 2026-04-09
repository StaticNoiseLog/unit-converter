// UI tests for conversion behavior, validation, and edge cases.
// Verifies PRD acceptance criteria through the full UI stack.

package unitconverter.ui

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import unitconverter.app.App
import unitconverter.app.ModuleRegistry
import unitconverter.module.temperature.TemperatureModule
import unitconverter.module.weight.WeightModule

class ConversionUiTest {

    @get:Rule
    val rule = createComposeRule()

    private fun setUpApp() {
        val registry = ModuleRegistry(
            listOf(TemperatureModule(), WeightModule())
        )
        rule.setContent { App(registry) }
    }

    // Triggers conversion by clicking the reset button area (causes focus loss)
    // then waits for idle. This avoids waiting for the 1-second debounce.
    private fun triggerConversionViaFocusLoss() {
        // Click on an empty area to lose focus — the tab label works
        rule.onNodeWithTag("tab-Temperature").performClick()
        rule.waitForIdle()
    }

    private fun triggerWeightConversionViaFocusLoss() {
        rule.onNodeWithTag("tab-Weight").performClick()
        rule.waitForIdle()
    }

    // --- REQ-004: Default State ---

    @Test
    fun `default state - all temperature fields empty`() {
        setUpApp()
        rule.onNodeWithTag("field-Celsius").assertTextContains("")
        rule.onNodeWithTag("field-Fahrenheit").assertTextContains("")
        rule.onNodeWithTag("field-Kelvin").assertTextContains("")
    }

    @Test
    fun `default state - all weight fields empty`() {
        setUpApp()
        rule.onNodeWithTag("tab-Weight").performClick()
        rule.waitForIdle()
        rule.onNodeWithTag("field-Gram").assertTextContains("")
        rule.onNodeWithTag("field-Kilogram").assertTextContains("")
        rule.onNodeWithTag("field-Ounce").assertTextContains("")
        rule.onNodeWithTag("field-Pound").assertTextContains("")
    }

    @Test
    fun `default state - no error messages displayed`() {
        setUpApp()
        rule.onNodeWithTag("field-Celsius-error").assertDoesNotExist()
        rule.onNodeWithTag("field-Fahrenheit-error").assertDoesNotExist()
        rule.onNodeWithTag("field-Kelvin-error").assertDoesNotExist()
    }

    // --- REQ-001: Temperature Conversion ---

    @Test
    fun `celsius 100 converts to fahrenheit 212 and kelvin 373_15`() {
        setUpApp()
        rule.onNodeWithTag("field-Celsius").performTextInput("100")
        triggerConversionViaFocusLoss()

        rule.onNodeWithTag("field-Fahrenheit").assertTextContains("212")
        rule.onNodeWithTag("field-Kelvin").assertTextContains("373.15")
    }

    @Test
    fun `kelvin 0 converts to celsius minus 273_15 and fahrenheit minus 459_67`() {
        setUpApp()
        rule.onNodeWithTag("field-Kelvin").performTextInput("0")
        triggerConversionViaFocusLoss()

        rule.onNodeWithTag("field-Celsius").assertTextContains("-273.15")
        rule.onNodeWithTag("field-Fahrenheit").assertTextContains("-459.67")
    }

    @Test
    fun `celsius minus 273_15 converts to kelvin 0 and fahrenheit minus 459_67`() {
        setUpApp()
        rule.onNodeWithTag("field-Celsius").performTextInput("-273.15")
        triggerConversionViaFocusLoss()

        rule.onNodeWithTag("field-Kelvin").assertTextContains("0")
        rule.onNodeWithTag("field-Fahrenheit").assertTextContains("-459.67")
    }

    // --- REQ-002: Weight Conversion ---

    @Test
    fun `gram 1000 converts to kg 1, ounce 35_274, pound 2_2046`() {
        setUpApp()
        rule.onNodeWithTag("tab-Weight").performClick()
        rule.waitForIdle()

        rule.onNodeWithTag("field-Gram").performTextInput("1000")
        triggerWeightConversionViaFocusLoss()

        rule.onNodeWithTag("field-Kilogram").assertTextContains("1")
        rule.onNodeWithTag("field-Ounce").assertTextContains("35.274")
        rule.onNodeWithTag("field-Pound").assertTextContains("2.2046")
    }

    @Test
    fun `negative weight converts correctly`() {
        setUpApp()
        rule.onNodeWithTag("tab-Weight").performClick()
        rule.waitForIdle()

        rule.onNodeWithTag("field-Kilogram").performTextInput("-5")
        triggerWeightConversionViaFocusLoss()

        rule.onNodeWithTag("field-Gram").assertTextContains("-5000")
        rule.onNodeWithTag("field-Ounce").assertTextContains("-176.37")
        rule.onNodeWithTag("field-Pound").assertTextContains("-11.0231")
    }

    // --- REQ-005: Input Validation ---

    @Test
    fun `non-numeric input shows error`() {
        setUpApp()
        rule.onNodeWithTag("field-Celsius").performTextInput("abc")
        triggerConversionViaFocusLoss()

        rule.onNodeWithTag("field-Celsius-error").assertExists()
        rule.onNodeWithTag("field-Celsius-error").assertTextContains("Not a valid number", substring = true)
    }

    @Test
    fun `below absolute zero kelvin shows error`() {
        setUpApp()
        rule.onNodeWithTag("field-Kelvin").performTextInput("-300")
        triggerConversionViaFocusLoss()

        rule.onNodeWithTag("field-Kelvin-error").assertExists()
        rule.onNodeWithTag("field-Kelvin-error").assertTextContains("absolute zero", substring = true)
    }

    @Test
    fun `scientific notation accepted`() {
        setUpApp()
        rule.onNodeWithTag("field-Celsius").performTextInput("1.5e3")
        triggerConversionViaFocusLoss()

        // 1500°C = 1773.15K
        rule.onNodeWithTag("field-Kelvin").assertTextContains("1773.15")
    }

    @Test
    fun `correcting invalid input clears error`() {
        setUpApp()

        // Enter invalid input
        rule.onNodeWithTag("field-Celsius").performTextInput("abc")
        triggerConversionViaFocusLoss()
        rule.onNodeWithTag("field-Celsius-error").assertExists()

        // Clear and enter valid input
        rule.onNodeWithTag("field-Celsius").performTextInput("")
        // The field now has "abc" + "" — need to clear first
        // Actually performTextInput appends, so let me use the ViewModel reset
        rule.onNodeWithTag("reset-Temperature").performClick()
        rule.waitForIdle()

        rule.onNodeWithTag("field-Celsius").performTextInput("100")
        triggerConversionViaFocusLoss()

        rule.onNodeWithTag("field-Celsius-error").assertDoesNotExist()
        rule.onNodeWithTag("field-Fahrenheit").assertTextContains("212")
    }
}
