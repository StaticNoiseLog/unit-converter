# Unit Converter

A demo project exploring the use of playbooks in agentic software development with [Kiro](https://kiro.dev).

## What Is This?

This project demonstrates how **playbooks** (steering files) can guide an AI coding agent through the entire software development lifecycle — from stakeholder interviews to a running application. The goal is to show how structured methodology produces high-quality, well-documented software artifacts at every stage.

The application itself is a desktop unit converter built with Kotlin and Compose Multiplatform.

## The Three Playbooks

This project uses three playbooks, each governing a distinct phase of the development process:

### 1. Requirements Engineering Playbook

Guides the agent through structured requirements elicitation:

- Identifies and interviews stakeholders one at a time (End User, Developer, Tester/QA)
- Captures requirements in standardized forms (user stories, acceptance tests, minimum requirements)
- Prioritizes using MoSCoW and the Kano model
- Maintains a project glossary with ubiquitous language (DDD)
- Produces a formal Product Requirements Document (PRD)

### 2. Solution Architecture Playbook

Transforms the PRD into a technical architecture:

- Defines system context and boundaries
- Selects and justifies architectural patterns
- Decomposes the system into modular components with defined interfaces
- Addresses quality attributes and cross-cutting concerns (security, performance, resilience)
- Produces a Solution Architecture Document (SAD), Architecture Decision Records (ADRs), and diagrams (Mermaid, draw.io)

### 3. Software Development Playbook

Translates requirements and architecture into code:

- Follows coding standards, SOLID principles, and clean architecture
- Applies TDD with Red-Green-Refactor
- Manages a task backlog with prioritized, estimated work items
- Enforces static analysis (detekt), strict compiler options, and 80% test coverage
- Handles build, deployment, security, and performance concerns

## How It Works

Each playbook is loaded as a Kiro steering file. The agent follows the playbook's process, asks the right questions, and produces the prescribed artifacts. The human stays in control — reviewing, approving, and course-correcting at each step.

1. **Requirements phase:** The agent conducts stakeholder interviews, elicits requirements, and writes the PRD and glossary.
2. **Architecture phase:** The agent studies the PRD, designs the system architecture, documents decisions in ADRs, and produces the SAD with diagrams.
3. **Development phase:** The agent breaks requirements into tasks, implements them using TDD, enforces quality gates, and keeps documentation current.

## Project Structure

```
unit-converter/
├── docs/
│   ├── requirements/
│   │   ├── prd.md              # Product Requirements Document
│   │   └── glossary.md         # Project glossary (ubiquitous language)
│   ├── solution_architecture/
│   │   └── sad.md              # Solution Architecture Document
│   ├── adr/                    # Architecture Decision Records
│   └── development/
│       └── backlog.md          # Task backlog
├── session_1_requirements_engineering.md  # Transcript of the RE session
└── README.md
```

## Technology Stack

| Concern | Choice |
|---------|--------|
| Language | Kotlin (current stable) |
| UI Framework | Compose Multiplatform for Desktop |
| Build System | Gradle with Kotlin DSL |
| JDK | 25 (current LTS) |
| Static Analysis | detekt (default rules) |
| Testing | kotlin-test, junit-jupiter |
| Platforms | Windows, macOS, Linux |

## Session Transcripts

Each phase of the project is captured in a session transcript so you can see exactly how the agent and human collaborated:

- `session_1_requirements_engineering.md` — Stakeholder interviews producing the PRD

## Getting Started

Prerequisites: JDK 25 installed.

```bash
# Clone the repository
git clone <repository-url>
cd unit-converter

# Run the application
./gradlew run

# Run tests
./gradlew test
```

## License

This project is released into the public domain under [The Unlicense](LICENSE).
