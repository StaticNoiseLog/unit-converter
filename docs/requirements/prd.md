# Unit Converter — Product Requirements Document (PRD)

**Project Name:** Unit Converter  
**Technical Name / Project Root:** `unit-converter`  
**Purpose:** A desktop GUI application that converts values between units within a category (e.g., temperature, weight), displaying all results simultaneously with live updates.  
**Glossary:** [Project Glossary](glossary.md)

---

## Stakeholders

| Name | Role | Contributed To |
|------|------|---------------|
| End User | Primary user of the application | REQ-001 through REQ-008 |
| Developer | Implements and maintains the application | REQ-009 through REQ-012 |
| Tester/QA | Ensures quality and correctness | REQ-013 through REQ-017 |

---

## Requirements

---

### REQ-001 — Temperature Conversion

**Value:** End Users can convert between Celsius, Fahrenheit, and Kelvin instantly, seeing all three values at once.

**Form:** User Story with Acceptance Tests

As an *End User* I want to enter a temperature in any one of three units (Celsius, Fahrenheit, Kelvin) so that I immediately see the converted values in the other two units.

**Acceptance Criteria:**

```
Given the Temperature tab is active and all fields are empty
When I enter "100" in the Celsius field and the debounce period elapses
Then the Fahrenheit field displays "212" and the Kelvin field displays "373.15"

Given the Temperature tab is active
When I enter "0" in the Kelvin field and the debounce period elapses
Then the Celsius field displays "-273.15" and the Fahrenheit field displays "-459.67"

Given the Temperature tab is active
When I enter "-300" in the Kelvin field and the field loses focus
Then the Kelvin field is marked red with a tooltip: "Temperature below absolute zero (0 K) is not physically possible"
And no conversion is performed

Given the Temperature tab is active
When I enter "-273.15" in the Celsius field and the debounce period elapses
Then the Kelvin field displays "0" and the Fahrenheit field displays "-459.67"
```

**Quality Attributes:**
- Performance: Calculation completes within the 1-second debounce window; perceived as instant.
- Usability: All three unit fields visible simultaneously, stacked vertically, with large readable fonts.

**Constraints:** None beyond global constraints.

**Stakeholders:** End User, Tester/QA  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-005 (Input Validation), REQ-006 (Output Formatting), REQ-007 (Debounce)

---

### REQ-002 — Weight Conversion

**Value:** End Users can convert between Gram, Kilogram, Ounce, and Pound instantly, seeing all four values at once.

**Form:** User Story with Acceptance Tests

As an *End User* I want to enter a weight in any one of four units (Gram, Kilogram, Ounce, Pound) so that I immediately see the converted values in the other three units.

**Acceptance Criteria:**

```
Given the Weight tab is active and all fields are empty
When I enter "1000" in the Gram field and the debounce period elapses
Then the Kilogram field displays "1"
And the Ounce field displays "35.274"
And the Pound field displays "2.2046"

Given the Weight tab is active
When I enter "-5" in the Kilogram field and the debounce period elapses
Then the Gram field displays "-5000"
And the Ounce field displays "-176.37"
And the Pound field displays "-11.0231"
```

**Quality Attributes:**
- Performance: Same as REQ-001.
- Usability: All four unit fields visible simultaneously, stacked vertically.

**Constraints:** None beyond global constraints.

**Stakeholders:** End User  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-005, REQ-006, REQ-007

---

### REQ-003 — Tab-Based Navigation

**Value:** End Users can switch between conversion categories without losing their work.

**Form:** User Story with Acceptance Tests

As an *End User* I want each conversion category on its own tab so that I can switch between them while preserving entered values.

**Acceptance Criteria:**

```
Given I have entered "100" in the Celsius field on the Temperature tab
When I switch to the Weight tab and then back to the Temperature tab
Then the Celsius field still displays "100" and the computed values are preserved

Given the application has Temperature and Weight modules
When the application starts
Then the tabs are displayed in alphabetical order: "Temperature", "Weight"

Given a new conversion module "Length" is added to the application
When the application starts
Then the tabs are displayed in alphabetical order: "Length", "Temperature", "Weight"
```

**Quality Attributes:**
- Usability: Tabs sorted alphabetically, dynamic ordering when modules are added.
- Reliability: State preserved per tab during the session.

**Constraints:** No state persistence between sessions.

**Stakeholders:** End User, Tester/QA  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-008 (Reset), REQ-011 (Modular Architecture)

---

### REQ-004 — Default State

**Value:** Predictable, clean starting point every time the application launches.

**Form:** Acceptance Test

```
Given the application is launched
Then all fields on all tabs are empty
And no errors are displayed
And the first tab in alphabetical order is selected
```

**Quality Attributes:** Usability.

**Stakeholders:** End User  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-003

---

### REQ-005 — Input Validation

**Value:** Users receive clear, immediate feedback when they enter values that cannot be computed.

**Form:** User Story with Acceptance Tests

As an *End User* I want invalid input to be clearly indicated with an explanation so that I understand what went wrong and can correct it.

**Acceptance Criteria:**

```
Given any unit field on any tab
When I enter "abc" and the field loses focus
Then the field background turns red
And a tooltip displays an error message explaining the input is not a valid number

Given the Kelvin field on the Temperature tab
When I enter "-10" and the field loses focus
Then the field background turns red
And a tooltip displays: "Temperature below absolute zero (0 K) is not physically possible"
And no conversion is performed

Given any unit field
When I enter "1.5e3" (scientific notation) and the debounce period elapses
Then the value is accepted and conversion is performed normally

Given a field contains invalid input (red background)
When I correct the input to a valid number and the debounce period elapses
Then the red background is removed and conversion proceeds normally
```

**Validation Rules:**
- Invalid input: anything that cannot be parsed as a numeric value.
- Domain-specific: negative Kelvin values are rejected with an explanatory message.
- Negative values are accepted for all other units (including weight).
- Scientific notation (e.g., `1.5e3`, `2.7E-4`) is accepted.

**Quality Attributes:**
- Usability: Red field background + tooltip with error explanation. Validation triggers on blur.

**Stakeholders:** End User, Tester/QA  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-001, REQ-002

---

### REQ-006 — Output Formatting

**Value:** Calculated results are displayed in a readable, consistent format.

**Form:** Minimum Requirement

- Calculated output is rounded to a maximum of 4 decimal places.
- Input precision is not limited.
- Scientific notation is used on output only when the rounded result would be completely illegible in standard decimal notation.

**Quality Attributes:** Usability.

**Stakeholders:** End User  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-001, REQ-002

---

### REQ-007 — Debounced Live Calculation

**Value:** Responsive feel without overwhelming the user or triggering calculations on every keystroke.

**Form:** Acceptance Test

```
Given any unit field with focus
When I type a value and stop typing
Then the conversion is triggered after a 1-second debounce delay

Given any unit field with focus and a partially typed value
When the field loses focus before the debounce period elapses
Then the conversion is triggered immediately on blur
```

**Quality Attributes:**
- Performance: 1-second debounce; immediate on blur.
- Usability: The field that was edited gets a light green background; all other fields have a white background.

**Stakeholders:** End User  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-001, REQ-002, REQ-005

---

### REQ-008 — Reset Button

**Value:** Quick way to start fresh on the current tab without affecting other tabs.

**Form:** Acceptance Test

```
Given the Temperature tab is active with values in all fields
When I click the Reset button
Then all fields on the Temperature tab are cleared
And no errors are displayed on the Temperature tab

Given the Temperature tab has values and the Weight tab has values
When I switch to the Weight tab and click Reset
Then all fields on the Weight tab are cleared
And the Temperature tab values remain unchanged
```

**Quality Attributes:** Usability.

**Stakeholders:** End User  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-003

---

### REQ-009 — Desktop Cross-Platform Support

**Value:** The application runs on all major desktop operating systems without separate builds or codebases.

**Form:** Minimum Requirement

- Target platforms: Windows, macOS, Linux.
- Built with Kotlin and Compose Multiplatform for Desktop.
- No mobile or web targets required currently.

**Quality Attributes:**
- Portability: Runs on Windows, macOS, Linux via Compose Multiplatform.

**Constraints:**
- Technical: Kotlin, Compose Multiplatform, Gradle (Kotlin DSL), JDK 25.

**Stakeholders:** Developer  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-010

---

### REQ-010 — Build and Run

**Value:** Simple, standard build and execution workflow for developers.

**Form:** Minimum Requirement

- Gradle build with Kotlin DSL syntax.
- Application is run via a Gradle task (no packaging/installer required currently).
- JDK 25 (current LTS).

**Quality Attributes:**
- Operability: Standard Gradle workflow.

**Constraints:**
- Technical: Gradle, Kotlin DSL, JDK 25.

**Stakeholders:** Developer  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-009

---

### REQ-011 — Modular Conversion Architecture

**Value:** New unit conversion categories can be added with minimal effort, keeping the codebase maintainable and extensible.

**Form:** User Story

As a *Developer* I want each conversion module to be a self-contained component so that adding a new unit category requires only creating a new module without modifying existing code.

**Notes:**
- Compile-time module registration is sufficient for now.
- Tabs are generated dynamically from registered modules and sorted alphabetically.
- Dynamic runtime loading (e.g., for paid upgrades) is a future consideration, not a current requirement.

**Quality Attributes:**
- Extensibility: Adding a new conversion module should not require changes to existing modules or core UI logic beyond registration.
- Modifiability: Each module is isolated; changes to one module do not affect others.
- Maintainability: Clear module interface/contract.

**Stakeholders:** Developer  
**Priority:** Must-have (MoSCoW) / Performance Need (Kano)  
**Traceability:** REQ-003

---

### REQ-012 — Responsive UI with Large Fonts

**Value:** The application is comfortable to use on various screen sizes and resolutions.

**Form:** Minimum Requirement

- The UI must be responsive to window resizing.
- Use larger-than-default font sizes for excellent readability.
- Unit fields are stacked vertically within each tab.
- No special title bar required.

**Quality Attributes:**
- Usability: Responsive layout, large fonts, vertical field stacking.
- Accessibility: Large fonts improve readability for users with visual impairments. (Note: Full accessibility compliance requires manual testing and is not claimed.)

**Stakeholders:** End User  
**Priority:** Should-have (MoSCoW) / Performance Need (Kano)  
**Traceability:** REQ-001, REQ-002

---

### REQ-013 — Unit Test Coverage

**Value:** Confidence that conversion logic is correct and regressions are caught early.

**Form:** Minimum Requirement

- TDD approach: tests written before implementation.
- Minimum 80% line coverage for business logic (conversion modules).
- Test frameworks: `kotlin-test`, `junit-jupiter`.

**Quality Attributes:**
- Testability: Conversion logic must be testable independently of the UI.
- Reliability: High test coverage reduces regression risk.

**Stakeholders:** Tester/QA, Developer  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-001, REQ-002, REQ-005

---

### REQ-014 — Absolute Zero Boundary Tests

**Value:** Verified correctness at the most critical edge case in temperature conversion.

**Form:** Acceptance Test

```
Given the Temperature tab
When I enter "0" in the Kelvin field
Then Celsius displays "-273.15" and Fahrenheit displays "-459.67"

Given the Temperature tab
When I enter "-273.15" in the Celsius field
Then Kelvin displays "0" and Fahrenheit displays "-459.67"

Given the Temperature tab
When I enter "-459.67" in the Fahrenheit field
Then Celsius displays "-273.15" and Kelvin displays "0"

Given the Temperature tab
When I enter "-0.01" in the Kelvin field
Then the field is marked invalid with tooltip explaining temperature below absolute zero is not possible
```

**Quality Attributes:** Reliability, Testability.

**Stakeholders:** Tester/QA  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-001, REQ-005

---

### REQ-015 — Compose UI Testing

**Value:** Automated verification that the UI behaves correctly, including tab switching and state preservation.

**Form:** Minimum Requirement

- Investigate `compose.uiTest` for Compose Multiplatform Desktop.
- If feasible, implement UI tests covering at minimum: tab switching with state preservation, reset button behavior.
- If not feasible with current tooling, document the finding and skip. This is a wish, not a hard requirement.

**Quality Attributes:**
- Testability: Automated UI-level testing if tooling supports it.

**Stakeholders:** Tester/QA  
**Priority:** Could-have (MoSCoW) / Exciter/Delighter (Kano)  
**Traceability:** REQ-003, REQ-008

---

### REQ-016 — Static Analysis with Detekt

**Value:** Consistent code quality enforced automatically during the build.

**Form:** Minimum Requirement

- Detekt integrated into the Gradle build.
- Default rule set.
- Build fails on detekt violations.

**Quality Attributes:**
- Maintainability: Enforced coding standards.

**Constraints:**
- Technical: Detekt with default configuration.

**Stakeholders:** Tester/QA, Developer  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-010

---

### REQ-017 — Strict Compiler Options

**Value:** Catch potential issues at compile time rather than at runtime.

**Form:** Minimum Requirement

- Kotlin compiler options: `allWarningsAsErrors = true`, `extraWarnings = true` (if supported by the Kotlin version in use; TBD — verify compatibility).

**Quality Attributes:**
- Reliability: Stricter compilation catches more issues early.
- Maintainability: Cleaner codebase.

**Stakeholders:** Tester/QA, Developer  
**Priority:** Must-have (MoSCoW) / Basic Need (Kano)  
**Traceability:** REQ-010

---

## Summary of Priorities

| Priority | Requirements |
|----------|-------------|
| Must-have | REQ-001 through REQ-014, REQ-016, REQ-017 |
| Should-have | REQ-012 |
| Could-have | REQ-015 |
| Won't-have | Runtime dynamic module loading, monetization, mobile/web targets, packaging/installer, session state persistence |
