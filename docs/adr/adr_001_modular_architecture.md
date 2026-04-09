# ADR-001: Modular Monolith with Module Registry

**Status:** Accepted  
**Date:** 2026-04-09  
**Context:** REQ-011 (Modular Conversion Architecture), REQ-003 (Tab-Based Navigation)

## Context

The application must support multiple conversion categories (Temperature, Weight, and future additions). Adding a new category should not require changes to existing modules or core UI logic. The PRD notes that dynamic runtime loading for paid upgrades is a future consideration, not a current requirement. Compile-time module registration is sufficient.

## Decision

Use a **Modular Monolith** architecture with a **Module Registry** pattern:

- A `ConversionModule` interface defines the contract every module must implement
- A `ModuleRegistry` holds all registered modules and provides them sorted alphabetically
- Modules are registered at compile time in the application's entry point
- The Tab Host reads from the registry to render tabs dynamically

## Alternatives Considered

| Alternative | Pros | Cons |
|-------------|------|------|
| Plugin architecture (runtime JAR loading) | Supports paid upgrades, hot-loading | Over-engineered for current needs; complex classloader management; security concerns |
| Gradle multi-module with separate artifacts | Strong compile-time boundaries | Unnecessary overhead for a small desktop app; complicates the build |
| Flat architecture (no module abstraction) | Simple | Violates OCP; adding a module requires modifying core code |

## Consequences

- Adding a new conversion module requires: (1) implementing `ConversionModule`, (2) registering it in `ModuleRegistry`
- No changes to existing modules or UI code
- If runtime plugin loading is needed later, the `module` packages can be extracted into separate Gradle modules and loaded via `ServiceLoader` or a plugin framework
- The single-module Gradle structure keeps the build simple
