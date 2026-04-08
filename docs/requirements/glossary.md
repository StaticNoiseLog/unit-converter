# Unit Converter — Project Glossary

| Term | Definition | Domain |
|------|-----------|--------|
| Absolute Zero | The lowest possible temperature: 0 K, −273.15 °C, −459.67 °F. No valid temperature can be below this value. | Temperature |
| Acceptance Test | A test written in Given-When-Then format that simultaneously serves as requirement, documentation, and automated validation. | QA |
| Conversion Module | A self-contained unit that encapsulates the conversion logic and UI for one category of units (e.g., Temperature, Weight). Modules are compiled into the application and registered dynamically. | Architecture |
| Debounce | A technique that delays execution of a calculation until a specified idle period (1 second in this project) has elapsed since the last input change. | UI |
| Detekt | A static code analysis tool for Kotlin that enforces coding standards and identifies code smells. Used with default rules in this project. | QA |
| End User | A person who uses the Unit Converter application to perform unit conversions on a desktop computer. | User |
| Gradle | The build automation tool used for this project, using Kotlin DSL syntax. | Build |
| Invalid Input | Any value entered into a unit field that cannot be parsed as a numeric value, or that violates domain-specific constraints (e.g., temperature below absolute zero in Kelvin). | Validation |
| JDK 25 | Java Development Kit version 25, the target Java runtime for this project. | Platform |
| Compose Multiplatform | JetBrains' declarative UI framework based on Jetpack Compose, targeting desktop platforms (Windows, macOS, Linux) in this project. | UI Framework |
| Live Update | The behavior where changing a value in one unit field immediately recalculates and displays the corresponding values in all other unit fields on the same tab (subject to debounce). | UI |
| MoSCoW | A prioritization method classifying requirements as Must-have, Should-have, Could-have, or Won't-have. | Requirements |
| Kano Model | A model classifying features as Basic Needs, Performance Needs, or Exciters/Delighters based on customer satisfaction impact. | Requirements |
| Quality Attributes | Measurable qualities of the system such as performance, usability, maintainability, etc. Replaces the outdated term "Non-Functional Requirements". | Requirements |
| Reset Button | A UI control that clears all input and output fields on the currently visible tab only. | UI |
| Scientific Notation | A way of expressing numbers as a coefficient times a power of 10 (e.g., 1.23e+15). Accepted on input; used on output only when the rounded result would otherwise be illegible. | Formatting |
| Tab | A UI element representing one conversion module. Tabs are sorted alphabetically by module name from left to right. | UI |
| TDD | Test-Driven Development — a development methodology where tests are written before the implementation code. | QA |
| Unit Field | A labeled text input displaying a unit name, its abbreviation, and the current numeric value. Fields are stacked vertically within a tab. | UI |
