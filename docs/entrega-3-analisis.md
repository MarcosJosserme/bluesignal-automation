# Entrega 3 — Implementación de Cucumber y BDD

## Proyecto BlueSignal Automation

### Autor

Marcos Josserme

---

## 1. Introducción

En esta tercera entrega se migró el framework de automatización de BlueSignal desde una ejecución basada directamente en tests JUnit hacia un enfoque BDD utilizando Cucumber y lenguaje Gherkin.

La migración se realizó conservando la arquitectura Page Object Model desarrollada en la entrega anterior. Cucumber no reemplaza los Page Objects, sino que incorpora una capa funcional que permite describir los comportamientos del sistema mediante escenarios legibles para perfiles técnicos y no técnicos.

La arquitectura resultante integra:

* Java.
* Maven.
* Selenium WebDriver.
* JUnit 5.
* Cucumber.
* Gherkin.
* Page Object Model.
* Page Factory.
* Jackson.
* Reportes HTML de Cucumber.

---

## 2. Objetivo de la entrega

El objetivo fue definir y automatizar escenarios BDD sobre las funcionalidades existentes de BlueSignal.

Para ello se implementaron:

* Archivos `.feature`.
* Step Definitions.
* Hooks `@Before` y `@After`.
* Un runner basado en JUnit Platform.
* Escenarios positivos y negativos.
* Uso de `Background`.
* Uso de `Scenario Outline`.
* Parametrización mediante `Examples`.
* Uso de `DataTable`.
* Consumo de datos externos desde JSON.
* Generación de reporte HTML.
* Reutilización de Page Objects y componentes.

---

## 3. Arquitectura del framework

El flujo de ejecución implementado es el siguiente:

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

### Responsabilidad de cada capa

#### Features

Contienen los escenarios funcionales escritos en Gherkin.

#### Step Definitions

Relacionan cada paso `Given`, `When` y `Then` con métodos Java.

#### Page Objects

Representan las páginas principales de la aplicación.

#### Components

Representan secciones específicas y reutilizables de cada página.

#### Hooks

Administran el ciclo de vida del navegador antes y después de cada escenario.

#### DriverContext

Permite compartir la misma instancia de WebDriver entre Hooks y Step Definitions.

#### DriverFactory

Centraliza la creación y configuración de ChromeDriver.

#### BasePage

Centraliza operaciones comunes de Selenium, como esperas, escritura, selección, scroll y validación de visibilidad.

---

## 4. Estructura del proyecto

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

## 5. Organización de los archivos Feature

Se crearon tres archivos `.feature`.

### home.feature

Contiene los escenarios relacionados con la página principal:

* Acceso exitoso a BlueSignal.
* Visualización del mapa principal.
* Selección parametrizada de localidades.

### login.feature

Contiene los escenarios relacionados con la autenticación:

* Visualización de la página de inicio de sesión.
* Visualización de los controles del formulario.
* Inicio de sesión con credenciales inválidas.

### report.feature

Contiene los escenarios relacionados con el formulario de reporte:

* Acceso al formulario.
* Botón de envío deshabilitado con formulario vacío.
* Carga parcial mediante DataTable.
* Carga parcial mediante datos externos JSON.

---

## 6. Uso de Background

Se utilizó `Background` para definir pasos comunes a varios escenarios.

Ejemplo conceptual:

```gherkin
Background:
  Given el visitante no se encuentra autenticado
  When accede a la página principal de BlueSignal
```

El `Background` se ejecuta antes de cada escenario de la Feature.

Esto permite:

* Evitar duplicación.
* Mantener escenarios más breves.
* Representar claramente las precondiciones comunes.
* Conservar la independencia entre escenarios.

Cada escenario continúa creando y cerrando su propio navegador.

---

## 7. Scenario Outline y Examples

La selección de localidades se implementó mediante un `Scenario Outline`.

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

Cucumber genera una ejecución independiente por cada fila de `Examples`.

La Step Definition recibe el valor mediante una expresión parametrizada:

```java
@When("el visitante selecciona la localidad {string}")
public void elVisitanteSeleccionaLaLocalidad(String localidad) {
    // Implementación
}
```

Esto evita crear un método diferente para cada localidad.

---

## 8. Uso de DataTable

Se implementó una DataTable para completar datos básicos del formulario de reporte.

```gherkin
When el visitante completa los datos básicos del reporte
  | especie    | Ballena Franca Austral      |
  | cantidad   | 2                           |
  | distancia  | En el mar: cerca (< 1 km)   |
  | comentario | Avistaje automatizado de QA |
```

La tabla se transforma en Java mediante:

```java
Map<String, String> reportData =
        dataTable.asMap(String.class, String.class);
```

Los datos se envían al Page Object:

```text
DataTable
    ↓
Map<String, String>
    ↓
ReportSteps
    ↓
ReportFormComponent
```

El escenario completa los campos y valida sus valores, pero no envía el formulario. De esta manera se evita crear avistajes de prueba en el ambiente productivo.

---

## 9. Datos externos mediante JSON

Se creó el archivo:

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

Para leer el archivo se utilizó Jackson mediante `ObjectMapper`.

Las clases utilizadas fueron:

* `JsonDataReader`.
* `ReportTestData`.
* `ReportData`.

Flujo:

```text
report-data.json
        ↓
JsonDataReader
        ↓
ReportTestData
        ↓
ReportData
        ↓
ReportSteps
        ↓
ReportFormComponent
```

También se creó un test unitario para verificar que el archivo JSON se convierte correctamente en objetos Java.

---

## 10. Hooks y ciclo de vida del navegador

Se implementaron Hooks de Cucumber:

```java
@Before
public void beforeScenario(Scenario scenario) {
    // Crear y almacenar WebDriver
}
```

```java
@After
public void afterScenario(Scenario scenario) {
    // Cerrar y remover WebDriver
}
```

El ciclo de ejecución es:

```text
@Before
   ↓
Background
   ↓
Given / When / Then
   ↓
@After
```

El Hook `@Before` crea un navegador nuevo para cada escenario.

El Hook `@After` cierra el navegador incluso cuando ocurre una falla.

---

## 11. Gestión de WebDriver

Se implementaron tres clases con responsabilidades separadas.

### DriverFactory

Crea y configura ChromeDriver.

Configuraciones principales:

* WebDriverManager.
* Ventana maximizada.
* Notificaciones deshabilitadas.
* Bloqueo de ventanas emergentes.
* Desactivación del administrador de contraseñas.

### DriverContext

Utiliza `ThreadLocal<WebDriver>` para almacenar el navegador asociado a cada ejecución.

Esto permite que Hooks y Steps compartan la misma instancia del navegador.

### Hooks

Define cuándo crear y cerrar el WebDriver.

---

## 12. Integración con Page Object Model

La migración a Cucumber conserva los Page Objects desarrollados anteriormente.

Ejemplo:

```text
home.feature
    ↓
HomeSteps
    ↓
HomePage
    ↓
MapComponent
    ↓
Selenium
```

Los Step Definitions no contienen selectores CSS, XPath ni identificadores HTML.

Los selectores permanecen encapsulados en:

* `NavbarComponent`.
* `MapComponent`.
* `LocalitySelectorComponent`.
* `LoginFormComponent`.
* `ReportFormComponent`.

Esto mantiene el framework reutilizable y reduce el acoplamiento entre los escenarios funcionales y la implementación técnica.

---

## 13. Runner de Cucumber

Se creó la clase:

```text
RunCucumberTest.java
```

El runner configura:

* Motor Cucumber.
* Ubicación de Features.
* Glue de Steps y Hooks.
* Salida legible en consola.
* Reporte HTML.

Configuración conceptual:

```java
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
```

El glue utilizado es:

```text
org.bluesignal
```

Esto permite detectar:

```text
org.bluesignal.steps
org.bluesignal.hooks
```

---

## 14. Tags

Se utilizaron tags para clasificar escenarios:

```text
@smoke
@regression
@home
@map
@locality
@login
@negative
@report
@data-table
@external-data
```

Ejemplo de ejecución filtrada:

```powershell
mvn clean test "-Dcucumber.filter.tags=@smoke"
```

Esto permite crear suites específicas sin modificar los archivos `.feature`.

---

## 15. Reporte HTML

El runner genera automáticamente:

```text
target/cucumber-report.html
```

El reporte contiene:

* Features.
* Escenarios.
* Tags.
* Pasos Given, When y Then.
* Estado de cada paso.
* Duración de ejecución.
* Errores en caso de falla.

El reporte se genera con:

```powershell
mvn clean test
```

Para abrirlo en Windows:

```powershell
Start-Process .\target\cucumber-report.html
```

La carpeta `target` no se versiona porque contiene artefactos generados automáticamente.

---

## 16. Resultado de ejecución

La ejecución final se realizó mediante:

```powershell
mvn clean test
```

Resultado:

```text
Tests run: 13
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

La suite está compuesta por:

```text
12 ejecuciones funcionales Cucumber
+
1 test unitario de JsonDataReader
=
13 pruebas exitosas
```

---

## 17. Migración desde JUnit

Los tests funcionales anteriores fueron migrados a Cucumber.

Se eliminaron:

```text
HomeTest.java
LoginTest.java
ReportTest.java
BaseTest.java
```

Las pruebas funcionales de interfaz ahora se ejecutan mediante:

```text
Feature
→ Steps
→ Page Objects
→ Selenium
```

JUnit se conserva para:

* Aserciones.
* JUnit Platform.
* Test unitario del lector JSON.

Esto evita ejecutar dos veces el mismo comportamiento y reduce la duplicación de mantenimiento.

---

## 18. Escenarios implementados

### Home

* Acceso exitoso a la página principal.
* Visualización del mapa.
* Selección de Mar del Plata.
* Selección de Puerto Madryn.
* Selección de Ushuaia.

### Login

* Visualización de la página de login.
* Visualización de controles.
* Credenciales inválidas.

### Reporte

* Acceso al formulario.
* Botón deshabilitado con formulario vacío.
* Datos cargados mediante DataTable.
* Datos cargados desde JSON.

Total de ejecuciones funcionales:

```text
12 escenarios Cucumber
```

---

## 19. Decisiones técnicas

### Mantener Page Object Model

Se decidió conservar Page Object Model porque Cucumber representa el comportamiento funcional, mientras que POM encapsula la interacción técnica con la interfaz.

### No enviar formularios reales

Los escenarios de reporte completan y validan campos, pero no presionan el botón de envío.

Esto evita contaminar la base de datos con registros automatizados.

### JSON como fuente externa

Se utilizó JSON para desacoplar los datos de prueba del código y facilitar su mantenimiento.

### Componentes reutilizables

Los formularios, navbar, mapa y selector de localidad se modelaron como componentes independientes.

### Eliminación de tests duplicados

Los tests JUnit de interfaz se eliminaron después de validar sus equivalentes Cucumber.

---

## 20. Evidencias

Las capturas se encuentran en:

```text
docs/evidencias/entrega-3/
```

Evidencias previstas:

1. Estructura completa del proyecto.
2. Dependencias Maven.
3. Archivos `.feature`.
4. Step Definitions.
5. Runner Cucumber.
6. Hooks.
7. Uso de DataTable.
8. Archivo JSON y lector.
9. Resultado de ejecución.
10. Reporte HTML.

---

## 21. Conclusión

La Entrega 3 permitió transformar el proyecto BlueSignal Automation en un framework BDD basado en Cucumber.

Los escenarios funcionales ahora se encuentran expresados en un lenguaje legible mediante Gherkin y están conectados con Selenium a través de Step Definitions.

La solución conserva Page Object Model, incorpora Hooks, parametrización, Data Tables, fuentes externas JSON, filtrado mediante tags y reportes HTML.

La arquitectura resultante mejora:

* La legibilidad.
* La trazabilidad.
* La reutilización.
* La mantenibilidad.
* La separación de responsabilidades.
* La comunicación entre perfiles técnicos y funcionales.

La ejecución final confirma el correcto funcionamiento del framework:

```text
Tests run: 13
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```
