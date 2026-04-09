# ADR-003: Canonical (Base) Unit Conversion Strategy

**Status:** Accepted  
**Date:** 2026-04-09  
**Context:** REQ-001 (Temperature Conversion), REQ-002 (Weight Conversion), REQ-011 (Modular Architecture)

## Context

Each conversion module must convert between N units. A naive approach requires N×(N−1) conversion functions. We need a strategy that scales well as units are added and keeps the conversion logic simple and testable.

## Decision

Use a **canonical (base) unit** strategy:

- Each module designates one unit as the base unit
- Every unit implements `toBase(value: Double): Double` and `fromBase(value: Double): Double`
- Converting from unit A to unit B: `B.fromBase(A.toBase(value))`
- Temperature base unit: Kelvin
- Weight base unit: Gram

## Alternatives Considered

| Alternative | Pros | Cons |
|-------------|------|------|
| Direct conversion matrix (N×N lookup table) | No intermediate step; potentially more precise for specific pairs | O(N²) functions to maintain; error-prone; doesn't scale |
| Conversion factor table (multiply/divide) | Simple for linear conversions | Doesn't handle non-linear conversions (e.g., Celsius ↔ Fahrenheit involves offset) |

## Consequences

- Adding a new unit to a module requires only two functions (`toBase`, `fromBase`)
- Conversion between any two units always goes through the base unit (two function calls)
- For temperature, this introduces a negligible intermediate step but keeps the code uniform
- Floating-point precision: the two-step conversion may introduce minor rounding differences compared to direct formulas, but this is within acceptable tolerance given the 4-decimal-place output rounding
- Each conversion function is independently testable
