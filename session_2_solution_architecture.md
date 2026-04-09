Settings
--------

- Claude Opus 4.6
- Autopilot

Session Dialog
--------------

### User

#solution_architecture_playbook.md 

Let's define the solution architecture for the unit-converter project!

### Kiro

I've created the initial solution architecture. Here's what's in place:

- sad.md — the main Solution Architecture Document covering system context, component architecture, module contract, conversion engine design, state management, UI architecture, input handling, project structure, testing strategy, technology stack, cross-cutting concerns, risk register, deployment, implementation roadmap, and full traceability to the PRD.

Four Architecture Decision Records in docs/adr/:

- ADR-001: Modular Monolith with Module Registry — extensibility without plugin overhead
- ADR-002: Unidirectional Data Flow with per-tab ViewModel — isolated tab state, clean debounce
- ADR-003: Canonical (base) unit conversion strategy — each unit only needs toBase/fromBase
- ADR-004: compose.uiTest with JUnit 4 for desktop UI tests — confirmed feasible with current tooling

A few things worth discussing:

1. I chose Kelvin as the base unit for temperature and Gram for weight. Kelvin makes sense because it's the SI unit and the domain constraint (absolute zero = 0) is cleanest there. Gram keeps weight conversions straightforward. Any thoughts on those choices?
2. The project structure is a single Gradle module with package-level separation rather than Gradle multi-module. This keeps the build simple while the ConversionModule interface enforces the boundaries. If you ever need runtime plugin loading, the packages can be extracted into separate modules.
3. For UI testing, compose.desktop.uiTestJUnit4 is available and documented. It uses JUnit 4 (not 5), so the project will have both JUnit versions — JUnit 5 for unit tests, JUnit 4 for Compose UI tests. This is a known pattern in the Compose ecosystem.

Take a look and let me know what you'd like to adjust or dig deeper into.

### User

Great!

1. Kelvin and Gram as base units are fine.
2. I agree that we do not need build-time modularization currently. A single Gradle module will do for now.
3. Thanks for pointing out why we need JUnit 4, too.

Please add a definition for MVVM to the glossary. Not all maintainers may understand the pattern.

### Kiro

Done — added MVVM to the glossary between MoSCoW and Kano Model, with a definition that explains the three layers and how the pattern is used in this project.