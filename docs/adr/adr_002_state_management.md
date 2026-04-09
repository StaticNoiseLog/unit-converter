# ADR-002: Unidirectional Data Flow with Per-Tab ViewModel

**Status:** Accepted  
**Date:** 2026-04-09  
**Context:** REQ-003 (Tab-Based Navigation — state preservation), REQ-007 (Debounced Live Calculation), REQ-008 (Reset Button)

## Context

Each tab must maintain its own state independently. Switching tabs must preserve values. The debounce mechanism requires managing timers per field. The Reset button must clear only the current tab.

## Decision

Use **MVVM with unidirectional data flow**, one ViewModel per tab:

- Each `ConversionModule` has an associated ViewModel that holds all state for that tab
- State flows downward: ViewModel → Compose UI
- Events flow upward: User input → ViewModel
- ViewModels are created once and retained for the application's lifetime (no destruction on tab switch)
- Debounce is managed within the ViewModel using Kotlin coroutines (`delay` + `Job` cancellation)

## Alternatives Considered

| Alternative | Pros | Cons |
|-------------|------|------|
| Global state store (Redux-like) | Single source of truth | Over-engineered for isolated tab state; adds boilerplate |
| Compose `remember` / `rememberSaveable` only | Simple, no ViewModel | Debounce logic in composables is messy; harder to test; state may be lost on recomposition |
| SAM (State-Action-Model) | Formal state mutation semantics | Unfamiliar to most Kotlin/Compose developers; adds conceptual overhead for a simple app |

## Consequences

- Tab state is naturally isolated — no risk of cross-tab interference
- Conversion logic is testable independently (ViewModel + Converter, no UI dependency)
- Debounce is cleanly managed via coroutine scope tied to the ViewModel
- Reset is a simple `ViewModel.reset()` call
- No state persistence between sessions (by design, REQ-004)
