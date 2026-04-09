# ADR-004: compose.uiTest with JUnit 4 for Desktop UI Tests

**Status:** Accepted  
**Date:** 2026-04-09  
**Context:** REQ-015 (Compose UI Testing)

## Context

The QA stakeholder expressed a wish for automated UI-level testing, similar to Selenium for web applications. The PRD marks this as Could-have. We need to determine if Compose Multiplatform provides feasible UI testing for desktop targets.

## Research Findings

Compose Multiplatform provides two approaches for UI testing on desktop:

1. **Common test API** (`compose.uiTest`): Uses `runComposeUiTest` function, not JUnit-rule-based. Works across platforms.
2. **JUnit 4 desktop-specific API** (`compose.desktop.uiTestJUnit4`): Uses `createComposeRule()` JUnit 4 rule. Desktop-only.

Both are documented in the official JetBrains Kotlin Multiplatform documentation and are available in current stable releases. The JUnit 4 approach is more familiar to developers coming from Android/Jetpack Compose testing.

Key capabilities:
- `setContent {}` to render composables in a test environment
- Finders: `onNodeWithTag`, `onNodeWithText`
- Actions: `performClick`, `performTextInput`
- Assertions: `assertTextEquals`, `assertIsDisplayed`

## Decision

Use `compose.desktop.uiTestJUnit4` for desktop UI tests:

- Place UI tests in `src/test/kotlin/` (desktop test source set)
- Use `createComposeRule()` with JUnit 4
- Add `testTag` modifiers to key composables for reliable test targeting
- Test scope: tab switching with state preservation, reset button behavior (as specified in REQ-015)

Note: Unit tests for conversion logic continue to use `kotlin-test` + `junit-jupiter` (JUnit 5). The UI tests use JUnit 4 because that is what the Compose testing library requires.

## Alternatives Considered

| Alternative | Pros | Cons |
|-------------|------|------|
| Common `runComposeUiTest` API | Cross-platform tests | Less familiar; no JUnit rule integration |
| Skip UI tests entirely | No risk of tooling issues | Misses the QA wish; less confidence in UI behavior |
| Screenshot testing | Visual regression detection | Complex setup; brittle; doesn't test interaction behavior |

## Consequences

- Two JUnit versions coexist: JUnit 5 for unit tests, JUnit 4 for UI tests (this is a known pattern in the Compose ecosystem)
- UI tests provide automated verification of tab switching and reset behavior
- If compose.uiTest proves unstable or insufficient, the fallback is manual testing (acceptable per REQ-015's Could-have priority)
- Test tags must be added to composables during implementation
