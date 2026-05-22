# BlueSignal Automation Framework

Framework de automatización de pruebas end-to-end para BlueSignal, desarrollado con Java, Maven, Selenium WebDriver, JUnit 5, Page Object Model y Page Factory.

El objetivo del proyecto es validar flujos principales de la plataforma BlueSignal mediante una estructura mantenible, reutilizable y escalable.

---

## Tecnologías utilizadas

- Java
- Maven
- Selenium WebDriver
- JUnit 5
- WebDriverManager
- Page Object Model
- Page Factory
- ChromeDriver

---

## Estructura del proyecto

```text
src/test/java/org/bluesignal
│
├── core
│   ├── BasePage.java
│   ├── BaseTest.java
│   └── DriverFactory.java
│
├── components
│   └── NavbarComponent.java
│
├── pages
│   ├── home
│   │   ├── HomePage.java
│   │   └── components
│   │       ├── MapComponent.java
│   │       └── LocalitySelectorComponent.java
│   │
│   ├── report
│   │   ├── ReportPage.java
│   │   └── components
│   │       └── ReportFormComponent.java
│   │
│   └── login
│       ├── LoginPage.java
│       └── components
│           └── LoginFormComponent.java
│
└── tests
    ├── HomeTest.java
    ├── ReportTest.java
    └── LoginTest.java