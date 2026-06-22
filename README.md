# BlueSignal Automation Framework

Framework de automatización de pruebas funcionales end-to-end para [BlueSignal](https://bluesignal.org), desarrollado con Java, Maven, Selenium WebDriver, Cucumber, JUnit 5 y Page Object Model.

El proyecto automatiza comportamientos críticos de la plataforma mediante escenarios BDD escritos en Gherkin, manteniendo separada la descripción funcional de las pruebas respecto de la implementación técnica de Selenium.

---

## Objetivo

El framework tiene como objetivo validar los principales flujos de BlueSignal mediante una arquitectura:

* Legible para perfiles técnicos y funcionales.
* Reutilizable.
* Mantenible.
* Escalable.
* Basada en separación de responsabilidades.
* Preparada para incorporar nuevos escenarios y fuentes de datos.

---

## Tecnologías utilizadas

* Java 17
* Maven
* Selenium WebDriver
* Cucumber
* Gherkin
* JUnit 5
* JUnit Platform Suite
* Page Object Model
* Page Factory
* WebDriverManager
* Jackson Databind
* ChromeDriver
* Reportes HTML de Cucumber
* Allure Report
* Allure Maven Plugin


---

## Arquitectura

El framework combina BDD con Page Object Model.

```text
Archivo .feature
        ↓
Cucumber
        ↓
Step Definitions
        ↓
Page Objects
        ↓
Components
        ↓
BasePage
        ↓
Selenium WebDriver
        ↓
BlueSignal
```

### Responsabilidades

* **Features:** describen comportamientos en lenguaje Gherkin.
* **Steps:** conectan los pasos funcionales con código Java.
* **Page Objects:** representan las páginas de BlueSignal.
* **Components:** encapsulan regiones específicas de la interfaz.
* **Hooks:** administran el navegador antes y después de cada escenario.
* **DriverFactory:** crea y configura ChromeDriver.
* **DriverContext:** comparte el mismo WebDriver entre Hooks y Steps.
* **BasePage:** centraliza operaciones comunes de Selenium.
* **Data models:** representan datos externos como objetos Java.
* **Utils:** contienen utilidades reutilizables del framework.

---

## Estructura del proyecto

```text
src
├── main
│   └── java
│       └── org
│           └── bluesignal
│               ├── components
│               │   └── NavbarComponent.java
│               │
│               ├── core
│               │   ├── BasePage.java
│               │   ├── DriverContext.java
│               │   └── DriverFactory.java
│               │
│               ├── data
│               │   └── models
│               │       ├── ReportData.java
│               │       └── ReportTestData.java
│               │
│               ├── pages
│               │   ├── home
│               │   │   ├── HomePage.java
│               │   │   └── components
│               │   │       ├── LocalitySelectorComponent.java
│               │   │       └── MapComponent.java
│               │   │
│               │   ├── login
│               │   │   ├── LoginPage.java
│               │   │   └── components
│               │   │       └── LoginFormComponent.java
│               │   │
│               │   └── report
│               │       ├── ReportPage.java
│               │       └── components
│               │           └── ReportFormComponent.java
│               │
│               └── utils
│                   └── JsonDataReader.java
│
└── test
    ├── java
    │   └── org
    │       └── bluesignal
    │           ├── hooks
    │           │   └── Hooks.java
    │           │
    │           ├── runners
    │           │   └── RunCucumberTest.java
    │           │
    │           ├── steps
    │           │   ├── HomeSteps.java
    │           │   ├── LoginSteps.java
    │           │   └── ReportSteps.java
    │           │
    │           └── utils
    │               └── JsonDataReaderTest.java
    │
    └── resources
        ├── data
        │   └── report-data.json
        │
        └── features
            ├── home.feature
            ├── login.feature
            └── report.feature
```

---

## Escenarios automatizados

### Página principal

* Acceso exitoso a la página principal.
* Visualización del mapa principal de avistajes.
* Selección parametrizada de localidades:

  * Mar del Plata.
  * Puerto Madryn.
  * Ushuaia.

### Inicio de sesión

* Visualización de la página de inicio de sesión.
* Visualización de los controles del formulario.
* Inicio de sesión con credenciales inválidas.
* Validación del mensaje de error de autenticación.

### Formulario de reporte

* Acceso exitoso al formulario.
* Botón de envío deshabilitado cuando el formulario está vacío.
* Carga parcial de datos mediante DataTable.
* Carga parcial mediante datos externos JSON.
* Validación de los valores cargados.

Los escenarios del formulario no realizan el envío final, para evitar generar registros automatizados en la plataforma.

---

## Recursos de Gherkin utilizados

El proyecto implementa:

* `Feature`
* `Background`
* `Scenario`
* `Scenario Outline`
* `Examples`
* `Given`
* `When`
* `Then`
* `And`
* Data Tables
* Tags

Ejemplo de Scenario Outline:

```gherkin
Scenario Outline: Selección de una localidad
  When el visitante selecciona la localidad "<localidad>"
  Then la localidad "<localidad>" queda seleccionada

Examples:
  | localidad     |
  | Mar del Plata |
  | Puerto Madryn |
  | Ushuaia       |
```

---

## Datos externos

Los datos externos del formulario se encuentran en:

```text
src/test/resources/data/report-data.json
```

Ejemplo:

```json
{
  "basicReport": {
    "species": "Ballena Franca Austral",
    "quantity": "2",
    "distance": "En el mar: cerca (< 1 km)",
    "comment": "Avistaje automatizado de QA"
  }
}
```

El archivo se procesa mediante:

```text
JsonDataReader
      ↓
Jackson ObjectMapper
      ↓
ReportTestData
      ↓
ReportData
```

La lectura del JSON también cuenta con una prueba unitaria independiente.

---

## Hooks

Cada escenario Cucumber tiene su propio ciclo de navegador:

```text
@Before
   ↓
Background
   ↓
Given / When / Then
   ↓
@After
```

El Hook `@Before`:

* Crea ChromeDriver.
* Registra el navegador en `DriverContext`.

El Hook `@After`:

* Informa el estado del escenario.
* Cierra el navegador.
* Elimina la referencia del `ThreadLocal`.

---

## Requisitos previos

Para ejecutar el proyecto se requiere:

* Java 17 o superior.
* Maven 3.9 o superior.
* Google Chrome.
* Git.
* Acceso a Internet.

Verificar las instalaciones:

```powershell
java -version
mvn -version
git --version
```

---

## Ejecución completa

Desde la raíz del proyecto:

```powershell
mvn clean test
```

Resultado de referencia:

```text
Tests run: 13
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

La ejecución está compuesta por:

```text
12 ejecuciones funcionales Cucumber
+
1 test unitario de JsonDataReader
=
13 pruebas
```

---

## Ejecución mediante tags

### Smoke tests

```powershell
mvn clean test "-Dcucumber.filter.tags=@smoke"
```

### Regression tests

```powershell
mvn clean test "-Dcucumber.filter.tags=@regression"
```

### Escenarios de Home

```powershell
mvn clean test "-Dcucumber.filter.tags=@home"
```

### Escenarios de Login

```powershell
mvn clean test "-Dcucumber.filter.tags=@login"
```

### Escenarios de Reporte

```powershell
mvn clean test "-Dcucumber.filter.tags=@report"
```

### Escenarios negativos

```powershell
mvn clean test "-Dcucumber.filter.tags=@negative"
```

### Escenarios con datos externos

```powershell
mvn clean test "-Dcucumber.filter.tags=@external-data"
```

---

## Reporte HTML de Cucumber

Después de ejecutar la suite, el reporte se genera en:

```text
target/cucumber-report.html
```

Para abrirlo en Windows:

```powershell
Start-Process .\target\cucumber-report.html
```

El reporte incluye:

* Features.
* Escenarios.
* Tags.
* Pasos ejecutados.
* Estado de cada paso.
* Duración.
* Detalle de errores.

La carpeta `target` no se versiona porque contiene archivos generados automáticamente.

---

## Reportes avanzados con Allure Report

A partir de la Entrega 4, el framework incorpora **Allure Report** para generar reportes visuales e interactivos de las ejecuciones automatizadas.

Allure permite enriquecer los resultados de prueba con:

* Agrupación funcional por Epic, Feature y Story.
* Severidad de escenarios.
* Descripciones funcionales.
* Pasos detallados de ejecución.
* Evidencias visuales mediante capturas de pantalla adjuntas.
* Métricas de ejecución.
* Vista Behaviors para analizar el comportamiento validado por el framework.

Los metadatos y evidencias fueron incorporados en los Step Definitions de los siguientes flujos:

* Página principal.
* Inicio de sesión.
* Formulario de reporte.

### Generar resultados de Allure

Desde la raíz del proyecto:

```powershell
mvn clean test
```

Este comando ejecuta la suite automatizada y genera los resultados crudos de Allure en:

```text
allure-results/
```

### Visualizar el reporte Allure

Para levantar el reporte en el navegador:

```powershell
mvn allure:serve
```

También puede generarse el reporte estático con:

```powershell
mvn allure:report
```

El reporte generado por Maven se ubica en:

```text
target/site/allure-maven-plugin/
```

Las carpetas `allure-results`, `.allure` y `target` no se versionan porque contienen archivos generados automáticamente.

### Evidencias incorporadas

El reporte Allure permite visualizar:

* Ejecución general al 100%.
* Escenarios agrupados por módulo funcional.
* Severidad de escenarios críticos.
* Descripciones del objetivo de cada prueba.
* Steps ejecutados durante el flujo.
* Capturas de pantalla adjuntas como evidencia visual.

---

## Gestión de WebDriver

Por defecto, el framework utiliza WebDriverManager:

```powershell
mvn clean test
```

También admite el uso de un ChromeDriver local:

```powershell
mvn clean test "-Ddriver.mode=manual"
```

En modo manual, el ejecutable debe encontrarse en:

```text
drivers/chromedriver.exe
```

---

## Documentación

La documentación de las entregas se encuentra en:

```text
docs/
├── entrega-1-analisis.md
├── entrega-2-analisis.md
├── entrega-3-analisis.md
└── entrega-4-analisis.md
```

Las evidencias se almacenan en:

```text
docs/evidencias/
├── entrega-3/
└── entrega-4/
```

Las evidencias de la Entrega 4 incluyen:

* Ejecución exitosa con `mvn clean test`.
* Overview de Allure Report.
* Vista Behaviors agrupada por BlueSignal.
* Escenarios con Severity, Description y Execution steps.
* Capturas de pantalla adjuntas como attachments.

---

## Estado actual

```text
Arquitectura BDD + POM implementada
Cucumber configurado
Hooks configurados
Scenario Outline implementado
DataTable implementada
Datos externos JSON implementados
Reporte HTML de Cucumber generado
Allure Report configurado
Allure Maven Plugin configurado
Metadatos funcionales incorporados en Step Definitions
Steps de ejecución visibles en Allure
Screenshots adjuntos como evidencia visual
Tests funcionales JUnit migrados a Cucumber
13 pruebas ejecutadas correctamente
```

---

## Autor

**Marcos Josserme**
