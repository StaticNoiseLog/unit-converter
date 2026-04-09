// Represents the outcome of validating a user-provided input value.

package unitconverter.core

sealed interface ValidationResult {
    data object Valid : ValidationResult
    data class Invalid(val message: String) : ValidationResult
}
