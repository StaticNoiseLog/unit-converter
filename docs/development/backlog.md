# Unit Converter — Development Backlog

**Project:** Unit Converter
**Source:** [PRD](../requirements/prd.md), [SAD](../solution_architecture/sad.md), [ADRs](../adr/)

---

## Task Overview

| # | Task | Priority | Complexity | Dependencies | Status |
|---|------|----------|------------|--------------|--------|
| 1 | Project Setup | 1 — Critical | 5 | None | DONE |
| 2 | Core Contracts | 1 — Critical | 3 | Task 1 | DONE |
| 3 | Temperature Converter (TDD) | 1 — Critical | 5 | Task 2 | DONE |
| 4 | Weight Converter (TDD) | 1 — Critical | 3 | Task 2 | DONE |
| 5 | Output Formatter (TDD) | 1 — Critical | 3 | Task 2 | DONE |
| 6 | Temperature ViewModel | 1 — Critical | 8 | Tasks 3, 5 | DONE |
| 7 | Weight ViewModel | 1 — Critical | 5 | Tasks 4, 5 | DONE |
| 8 | Shared UI Components | 1 — Critical | 5 | Task 2 | DONE |
| 9 | App Shell (Registry, Tab Host, Main) | 1 — Critical | 5 | Tasks 6, 7, 8 | TODO |
| 10 | Integration and Manual Testing | 1 — Critical | 3 | Task 9 | TODO |
| 11 | Compose UI Tests | 3 — Nice to have | 5 | Task 9 | TODO |
| 12 | Polish and Edge Cases | 2 — Important | 3 | Task 10 | TODO |
| 13 | README Update | 2 — Important | 1 | Task 10 | TODO |

**Total estimated complexity:** 54 points

---

## Task Details

---

### Task 1 — Project Setup

**Priority:** 1 — Critical
**Complexity:** 5
**Dependencies:** None
**Requirements:** REQ-009, REQ-010, REQ-016, REQ-017
**Architectural Components:** Gradle build, version catalog, Detekt, compiler options

**Scope:**
- Clean up `libs.versions.toml`: remove Ktor/server dependencies not needed; add Compose Multiplatform, compose.desktop.uiTestJUnit4
- Configure `settings.gradle.kts` with plugin management and Compose Multiplatform plugin repository
- Configure `build.gradle.kts`: Kotlin JVM, Compose Desktop plugin, application entry point, dependencies, Detekt, strict compiler options (`allWarningsAsErrors`, `extraWarnings` if supported)
- Configure `gradle.properties` for Compose and JDK 25
- Create the source directory structure per SAD Section 9
- Verify the build compiles and Detekt runs with `./gradlew build`

**Acceptance Criteria:**
- `./gradlew build` succeeds with empty source set
- `./gradlew detekt` runs with default rules
- Kotlin compiler options are strict
- Version catalog contains only needed dependencies with correct versions

---

### Task 2 — Core Contracts

**Priority:** 1 — Critical
**Complexity:** 3
**Dependencies:** Task 1
**Requirements:** REQ-011, REQ-005, REQ-006
**Architectural Components:** ConversionModule, UnitDefinition, ValidationResult, Formatter (interface only)

**Scope:**
- `ConversionModule` interface: name, units, convert, validate, content composable
- `UnitDefinition` interface: name, abbreviation, validators
- `ValidationResult` sealed type: Valid, Invalid(message)
- `Formatter` object stub (implementation in Task 5)

**Acceptance Criteria:**
- Interfaces compile cleanly
- Contracts match SAD Section 4 specification
- No implementation logic yet — pure contracts

---

### Task 3 — Temperature Converter (TDD)

**Priority:** 1 — Critical
**Complexity:** 5
**Dependencies:** Task 2
**Requirements:** REQ-001, REQ-005, REQ-014
**Architectural Components:** TemperatureConverter, TemperatureConverterTest, TemperatureValidationTest

**Scope:**
- TDD: write tests first, then implement
- `TemperatureConverter`: pure conversion functions using canonical (Kelvin) base unit (ADR-003)
- Temperature units enum/sealed class with `toBase`/`fromBase`
- Domain validation: absolute zero constraint (K ≥ 0)
- Test scenarios: all formula pairs, absolute zero boundary (0 K, −273.15 °C, −459.67 °F), below absolute zero rejection, round-trip accuracy, scientific notation input

**Acceptance Criteria:**
- All conversion formulas match SAD Section 5.3
- Absolute zero boundary tests pass (REQ-014)
- Below-absolute-zero values are rejected with explanatory message
- Tests achieve ≥ 80% coverage on converter logic

---

### Task 4 — Weight Converter (TDD)

**Priority:** 1 — Critical
**Complexity:** 3
**Dependencies:** Task 2
**Requirements:** REQ-002
**Architectural Components:** WeightConverter, WeightConverterTest

**Scope:**
- TDD: write tests first, then implement
- `WeightConverter`: pure conversion functions using canonical (Gram) base unit (ADR-003)
- Weight units enum/sealed class with `toBase`/`fromBase`
- No domain constraints — negative values accepted
- Test scenarios: all formula pairs (Gram, Kilogram, Ounce, Pound), negative values, round-trip accuracy

**Acceptance Criteria:**
- All conversion formulas match SAD Section 5.4
- Negative values are accepted and converted correctly
- Tests achieve ≥ 80% coverage on converter logic

---

### Task 5 — Output Formatter (TDD)

**Priority:** 1 — Critical
**Complexity:** 3
**Dependencies:** Task 2
**Requirements:** REQ-006
**Architectural Components:** Formatter, FormatterTest

**Scope:**
- TDD: write tests first, then implement
- `Formatter.format(value: Double): String`
- Round to maximum 4 decimal places
- Use standard decimal notation by default
- Use scientific notation only when the rounded result would be illegible in decimal form
- Handle edge cases: NaN, Infinity, very large/small values

**Acceptance Criteria:**
- Output rounded to max 4 decimal places
- Scientific notation used only for extreme values
- Edge cases handled gracefully (NaN, Infinity → error message or appropriate display)

---

### Task 6 — Temperature ViewModel

**Priority:** 1 — Critical
**Complexity:** 8
**Dependencies:** Tasks 3, 5
**Requirements:** REQ-001, REQ-005, REQ-007, REQ-008
**Architectural Components:** TemperatureViewModel

**Scope:**
- State management per ADR-002: field values, active field, validation state, debounce state
- Debounce mechanism: coroutine-based, 1-second delay, immediate on blur
- Input validation pipeline: syntax (Double parsing, scientific notation) → domain (absolute zero)
- Conversion trigger: validate → convert all target units → format output
- Reset: clear all fields and validation state
- Field background state: green for active input, red for invalid, white otherwise

**Acceptance Criteria:**
- Debounce triggers conversion after 1 second of inactivity
- Blur triggers immediate conversion
- Invalid input marks field red with tooltip message
- Valid input converts and updates all other fields
- Reset clears all state
- State is preserved across tab switches (ViewModel retained in memory)

---

### Task 7 — Weight ViewModel

**Priority:** 1 — Critical
**Complexity:** 5
**Dependencies:** Tasks 4, 5
**Requirements:** REQ-002, REQ-005, REQ-007, REQ-008
**Architectural Components:** WeightViewModel

**Scope:**
- Same ViewModel pattern as Task 6, adapted for weight units (4 fields instead of 3)
- No domain-specific validation beyond syntax parsing
- Negative values accepted

**Acceptance Criteria:**
- Same behavioral criteria as Task 6, minus absolute zero validation
- All four weight unit fields update correctly

---

### Task 8 — Shared UI Components

**Priority:** 1 — Critical
**Complexity:** 5
**Dependencies:** Task 2
**Requirements:** REQ-005, REQ-007, REQ-008, REQ-012
**Architectural Components:** UnitField, ResetButton, Theme

**Scope:**
- `UnitField` composable: labeled text input with unit abbreviation, background color (green/red/white), tooltip for errors, `testTag` for UI testing
- `ResetButton` composable: clears current tab, `testTag` for UI testing
- `Theme`: large fonts, color definitions (light green, red, white), responsive layout helpers
- All composables use `Modifier.fillMaxWidth` and vertical stacking

**Acceptance Criteria:**
- UnitField displays label, abbreviation, and input
- Background color reflects field state (active/invalid/default)
- Tooltip shows error message on invalid input
- ResetButton triggers ViewModel reset
- Large, readable fonts
- Responsive to window resizing

---

### Task 9 — App Shell (Registry, Tab Host, Main)

**Priority:** 1 — Critical
**Complexity:** 5
**Dependencies:** Tasks 6, 7, 8
**Requirements:** REQ-003, REQ-004, REQ-009, REQ-011
**Architectural Components:** Main.kt, App.kt, ModuleRegistry

**Scope:**
- `ModuleRegistry`: holds registered modules, provides them sorted alphabetically
- `App.kt`: root composable with tab bar and content area; reads modules from registry; manages tab selection
- `Main.kt`: bootstraps registry with TemperatureModule and WeightModule, launches Compose window
- Default state: all fields empty, no errors, first tab selected (REQ-004)
- Tab switching preserves per-tab state (ViewModels retained)

**Acceptance Criteria:**
- Application launches with `./gradlew run`
- Tabs displayed in alphabetical order: "Temperature", "Weight"
- Tab switching preserves entered values
- Default state is clean (empty fields, no errors, first tab selected)

---

### Task 10 — Integration and Manual Testing

**Priority:** 1 — Critical
**Complexity:** 3
**Dependencies:** Task 9
**Requirements:** REQ-001 through REQ-008, REQ-013
**Architectural Components:** All

**Scope:**
- Verify all acceptance criteria from REQ-001 through REQ-008 manually
- Run full test suite: `./gradlew test`
- Run Detekt: `./gradlew detekt`
- Verify test coverage ≥ 80% on business logic
- Fix any issues found during integration

**Acceptance Criteria:**
- All PRD acceptance criteria pass
- `./gradlew build` succeeds (compile + test + detekt)
- No detekt violations
- Test coverage ≥ 80% on conversion logic

---

### Task 11 — Compose UI Tests

**Priority:** 3 — Nice to have
**Complexity:** 5
**Dependencies:** Task 9
**Requirements:** REQ-015
**Architectural Components:** TabSwitchingTest (compose.desktop.uiTestJUnit4)

**Scope:**
- Per ADR-004: use `compose.desktop.uiTestJUnit4` with `createComposeRule()` and JUnit 4
- Test tab switching with state preservation
- Test reset button behavior
- If tooling proves unstable, document the finding and skip (REQ-015 is Could-have)

**Acceptance Criteria:**
- UI tests pass for tab switching and reset, OR
- Documented finding that tooling is not feasible (acceptable per REQ-015)

---

### Task 12 — Polish and Edge Cases

**Priority:** 2 — Important
**Complexity:** 3
**Dependencies:** Task 10
**Requirements:** REQ-006, REQ-012
**Architectural Components:** Formatter, Theme, all UI components

**Scope:**
- Verify output formatting edge cases (very large/small numbers, scientific notation threshold)
- Verify responsive layout at various window sizes
- Verify arithmetic edge cases (overflow, NaN, Infinity)
- Fine-tune font sizes and spacing for readability

**Acceptance Criteria:**
- No crashes or unexpected behavior on edge case inputs
- Layout remains usable at small and large window sizes
- Output formatting is consistent and readable

---

### Task 13 — README Update

**Priority:** 2 — Important
**Complexity:** 1
**Dependencies:** Task 10
**Requirements:** Playbook (project documentation)
**Architectural Components:** README.md

**Scope:**
- Update README.md with: project description, prerequisites (JDK 25), how to build (`./gradlew build`), how to run (`./gradlew run`), how to test (`./gradlew test`), project structure overview

**Acceptance Criteria:**
- README accurately describes the project and how to use it
- A new developer can set up and run the project by following the README
