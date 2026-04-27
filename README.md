# BlueSignal Automation

Framework de automatización de pruebas funcionales para https://bluesignal.org.

## Objetivo

Automatizar flujos críticos de la plataforma BlueSignal utilizando Selenium WebDriver y Java.

## Stack tecnológico

- Java 17
- Maven
- Selenium WebDriver
- JUnit 5
- WebDriverManager

## Arquitectura inicial

```text
src/test/java/org/bluesignal/

core/
→ configuración base y manejo de drivers

tests/
→ casos de prueba automatizados