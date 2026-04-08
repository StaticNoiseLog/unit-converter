# Unit Converter

A demo project exploring the use of playbooks in agentic software development.

## What Is This?

This project uses a **Requirements Engineering Playbook** to guide an AI coding agent through a structured requirements elicitation process. The goal is to demonstrate how playbooks can steer agentic development toward producing high-quality, well-documented software artifacts — starting from stakeholder interviews all the way to a formal Product Requirements Document (PRD).

The application itself is a desktop unit converter built with Kotlin and Compose Multiplatform.

## Project Structure

- `docs/requirements/prd.md` — Product Requirements Document
- `docs/requirements/glossary.md` — Project glossary (ubiquitous language)

## How It Works

1. A playbook (steering file) defines the methodology, standards, and process the agent must follow.
2. The agent conducts stakeholder interviews, one at a time, to elicit and document requirements.
3. Requirements are captured in standardized forms (user stories, acceptance tests, etc.) and prioritized using MoSCoW and the Kano model.
4. The result is a PRD that can drive implementation.
