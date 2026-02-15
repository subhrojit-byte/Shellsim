                                                                     Shell Company Risk Intelligence Simulator

A research-driven financial behavior simulation system designed to model, generate, and analyze shell company patterns using realistic synthetic data.

This project was developed as part of Project-Based Learning (PBL – Semester 4) to explore how suspicious corporate structures and financial transactions can be detected when real investigative datasets are unavailable.

Problem Statement

Detecting shell companies in real financial systems is extremely difficult because:

Real investigation datasets (Panama Papers, Paradise Papers, etc.) are restricted or incomplete

Ethical, legal, and privacy concerns prevent direct experimentation on real entities

Existing academic work focuses on detection models, not data generation or simulation

Proposed Solution

To address this gap, this project builds a realistic simulation engine that:

Generates legitimate and shell-like companies

Creates addresses, directors, and financial transactions

Injects risk-based suspicious patterns

Calculates a risk score using research-inspired heuristics

Provides a dashboard + API to explore the simulated ecosystem

This allows safe experimentation before applying machine learning or forensic analytics on real-world data.

Key Features

Synthetic generation of:

Companies

Directors

Addresses

Transactions

Shell-behavior modeling:

Low employee count with high revenue

Offshore jurisdictions

Repeated directors across entities

Abnormal transaction patterns

Risk scoring engine based on:

Financial anomalies

Structural red flags

Behavioral transaction signals

Full Spring Boot REST backend

Interactive dashboard frontend

PostgreSQL database integration

Cloud deployment ready (Render + Docker)

Tech Stack

Backend

Java

Spring Boot

JDBC Template

PostgreSQL

Frontend

HTML

CSS

JavaScript

Thymeleaf

Deployment

Docker

Render Web Service

Render PostgreSQL

System Architecture
Generators → Database → Risk Calculator → REST API → Dashboard


Simulation engine generates realistic corporate ecosystem

Data stored in PostgreSQL

Risk logic evaluates suspicious indicators

REST APIs expose structured data

Dashboard visualizes companies and shell risk

How the Generator Works

The simulator creates two categories:

Legitimate Companies

Normal employee-to-revenue ratios

Local jurisdictions

Independent directors

Regular transaction distributions

Shell-Like Companies

Minimal employees, inflated revenue

Offshore countries

Shared or nominee directors

Circular or round-value transactions

Each generated entity contributes to a composite risk score.

Running Locally
1. Clone repository
git clone https://github.com/subhrojit-byte/Shellsim.git
cd Shellsim

2. Configure PostgreSQL

Update application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres

3. Run Spring Boot
./mvnw spring-boot:run

4. Open dashboard
http://localhost:8080/

API Endpoints
Endpoint	Description
/api/companies	List all companies
/api/transactions	View transactions
/api/addresses	View addresses
/api/directors	View directors
/simulation/run	Execute data simulation
Research Motivation

Inspired by investigations and academic studies such as:

Panama Papers Investigation

FATF risk indicators

Academic research on shell company detection and financial fraud analytics

This simulator provides a controlled experimental environment for future ML-based detection.

Future Scope

Machine learning–based shell detection

Graph/network analysis of directors and transactions

Integration with real public corporate registries

Advanced visualization dashboards

Automated compliance risk scoring

Author

Subhrojit Mohanty
B.Tech – Semester 4
Project Based Learning (PBL)
