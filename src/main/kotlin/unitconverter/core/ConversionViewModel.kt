// ViewModel for a single conversion tab. Manages field values, validation state,
// debounce, and conversion triggering per ADR-002 (unidirectional data flow).
// Reusable across all conversion modules.

package unitconverter.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DEBOUNCE_MILLIS = 1000L
private const val INVALID_NUMBER_MESSAGE = "Not a valid number"

enum class FieldState { Default, Active, Invalid }

data class FieldData(
    val text: String = "",
    val state: FieldState = FieldState.Default,
    val errorMessage: String? = null,
)

class ConversionViewModel(
    val units: List<UnitDefinition>,
    private val scope: CoroutineScope,
) {

    var fields: Map<UnitDefinition, FieldData> by mutableStateOf(
        units.associateWith { FieldData() }
    )
        private set

    private var debounceJob: Job? = null

    fun onValueChanged(unit: UnitDefinition, text: String) {
        fields = fields.toMutableMap().apply {
            this[unit] = FieldData(text = text, state = FieldState.Active)
            units.filter { it != unit }.forEach { other ->
                val current = this[other]
                if (current != null) {
                    this[other] = current.copy(state = FieldState.Default, errorMessage = null)
                }
            }
        }
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(DEBOUNCE_MILLIS)
            triggerConversion(unit, text)
        }
    }

    fun onFocusLost(unit: UnitDefinition) {
        val text = fields[unit]?.text ?: return
        debounceJob?.cancel()
        if (text.isNotBlank()) {
            triggerConversion(unit, text)
        }
    }

    fun reset() {
        debounceJob?.cancel()
        fields = units.associateWith { FieldData() }
    }

    private fun triggerConversion(sourceUnit: UnitDefinition, text: String) {
        val parsed = text.toDoubleOrNull()
        if (parsed == null) {
            markInvalid(sourceUnit, INVALID_NUMBER_MESSAGE)
            return
        }

        val validation = sourceUnit.validate(parsed)
        if (validation is ValidationResult.Invalid) {
            markInvalid(sourceUnit, validation.message)
            return
        }

        fields = fields.toMutableMap().apply {
            this[sourceUnit] = FieldData(text = text, state = FieldState.Active)
            units.filter { it != sourceUnit }.forEach { target ->
                val converted = target.fromBase(sourceUnit.toBase(parsed))
                this[target] = FieldData(
                    text = Formatter.format(converted),
                    state = FieldState.Default,
                )
            }
        }
    }

    private fun markInvalid(unit: UnitDefinition, message: String) {
        fields = fields.toMutableMap().apply {
            val current = this[unit]
            this[unit] = FieldData(
                text = current?.text.orEmpty(),
                state = FieldState.Invalid,
                errorMessage = message,
            )
        }
    }
}
