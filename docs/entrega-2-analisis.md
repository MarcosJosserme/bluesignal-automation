# Entrega 2 — Implementación de Automatización con Page Object Model

## 1. Objetivo de la entrega

El objetivo de esta entrega es implementar una estructura de automatización de pruebas utilizando el patrón **Page Object Model (POM)**, aplicando buenas prácticas de diseño, reutilización de código y organización del proyecto.

La automatización fue desarrollada sobre **BlueSignal**, una plataforma digital de ciencia ciudadana orientada al registro de avistajes de fauna marina.

La implementación se realizó utilizando:

- Java
- Maven
- Selenium WebDriver
- JUnit 5
- WebDriverManager
- Page Object Model
- Page Factory

---

## 2. Adaptación de la consigna

La consigna original propone automatizar un flujo de e-commerce compuesto por páginas como:

- LoginPage
- ProductPage
- CartPage
- CheckoutPage

En este proyecto se aplicó el mismo enfoque técnico sobre el dominio funcional de BlueSignal. En lugar de automatizar un flujo de compra, se automatizaron flujos principales de la plataforma:

```
Ingreso a BlueSignal
↓
Carga de Home
↓
Visualización del mapa
↓
Selección de localidad
↓
Acceso al formulario de reporte
↓
Validación del formulario
↓
Acceso y validación del Login
```

### Equivalencia funcional

| Consigna e-commerce         | BlueSignal                        |
|-----------------------------|-----------------------------------|
| HomePage / ProductPage      | HomePage                          |
| Lista de productos          | Mapa y contenido principal        |
| Seleccionar producto        | Seleccionar localidad             |
| Agregar al carrito          | Acceder al formulario de reporte  |
| CartPage                    | ReportPage                        |
| CheckoutPage                | ReportFormComponent               |
| LoginPage                   | LoginPage                         |

El objetivo técnico se mantiene: aplicar Page Object Model, Page Factory, BasePage, reutilización de código y pruebas automatizadas con JUnit 5.

---

## 3. Sitio automatizado

**Sitio seleccionado:** https://bluesignal.org

BlueSignal es una plataforma web orientada al registro colaborativo de avistajes de fauna marina. El sistema permite visualizar información territorial, seleccionar localidades costeras, acceder a un mapa interactivo y reportar observaciones.

Para esta entrega se automatizan flujos seguros, evitando la creación real de avistajes para no alterar información productiva, métricas del sistema o posibles notificaciones reales.

---

## 4. Estructura del proyecto

La estructura del framework fue reorganizada para aplicar Page Object Model con una separación clara entre infraestructura base, componentes reutilizables, páginas principales, componentes internos de páginas y tests automatizados.

```
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
```

---

## 5. Diseño del framework

### Page Object Model

El framework utiliza Page Object Model para separar la lógica de los tests de los detalles internos de Selenium y de la estructura HTML de la aplicación.

Las clases Page Object representan páginas o rutas principales del sistema:

- `HomePage`
- `ReportPage`
- `LoginPage`

Cada una expone métodos funcionales que pueden ser utilizados por los tests sin conocer selectores, esperas explícitas o detalles de implementación.

### Componentización

Además de páginas, el framework utiliza componentes para modelar partes funcionales internas de una página o elementos reutilizables:

- `MapComponent`
- `LocalitySelectorComponent`
- `ReportFormComponent`
- `LoginFormComponent`
- `NavbarComponent`

Esta decisión permite evitar Page Objects demasiado grandes y mejora la mantenibilidad del framework.

Por ejemplo, la Home se modela como una página compuesta por componentes:

```
HomePage
├── MapComponent
├── LocalitySelectorComponent
└── NavbarComponent
```

---

## 6. Implementación de BasePage

`BasePage` centraliza funcionalidades comunes utilizadas por páginas y componentes del framework.

**Responsabilidades principales:**

- Inicializar elementos mediante Page Factory
- Centralizar esperas explícitas
- Realizar clicks seguros
- Escribir en campos de texto
- Validar visibilidad de elementos
- Validar estado habilitado/deshabilitado
- Seleccionar opciones en listas desplegables
- Obtener texto de elementos
- Navegar hacia URLs
- Validar cambios de URL
- Realizar scroll hacia elementos

**Métodos implementados:**

`click()` · `type()` · `getText()` · `isDisplayed()` · `isEnabled()` · `selectByVisibleText()` · `getSelectedOptionText()` · `scrollTo()` · `waitUntilUrlContains()` · `currentUrlContains()` · `getTitle()` · `getCurrentUrl()` · `navigateTo()`

### Page Factory

La inicialización de elementos se centraliza en el constructor de `BasePage`:

```java
PageFactory.initElements(driver, this);
```

Esto permite declarar elementos en páginas y componentes mediante `@FindBy`:

```java
@FindBy(id = "dashboard-map")
private WebElement mapContainer;
```

De esta manera se evita repetir `driver.findElement(...)` dentro de los tests.

---

## 7. Implementación de BaseTest

`BaseTest` define el ciclo de vida común de las pruebas automatizadas.

**Responsabilidades:**

- Crear el WebDriver antes de cada test
- Abrir la URL principal de BlueSignal
- Preparar el estado inicial del navegador
- Cerrar el navegador al finalizar cada test

### Manejo de estado inicial

Durante la automatización se identificó una interferencia relacionada con el banner de instalación PWA. Para evitar que este elemento intercepte acciones durante las pruebas, se configuró el estado del navegador mediante `localStorage`, simulando que el usuario ya descartó el banner.

Esto permite estabilizar los tests sin modificar el comportamiento productivo de la aplicación.

---

## 8. Implementación de DriverFactory

`DriverFactory` centraliza la creación y configuración del WebDriver.

### Modos de ejecución

**Modo automático** — Utiliza WebDriverManager para gestionar ChromeDriver automáticamente:

```bash
mvn clean test
```

**Modo manual** — Utiliza un ChromeDriver local ubicado en `drivers/chromedriver.exe`:

```bash
mvn clean -Ddriver.mode=manual test
```

En PowerShell:

```powershell
mvn clean "-Ddriver.mode=manual" test
```

Esta configuración permite mayor flexibilidad para ejecutar el framework en distintos entornos.

---

## 9. Implementación de componentes comunes

### NavbarComponent

Representa la barra de navegación principal del sistema.

**Elementos modelados:** logo, link Inicio, botón Reportar, botón Perfil/Login, botón de notificaciones, botón de cambio de tema.

**Métodos principales:**

`isVisible()` · `isLogoVisible()` · `isHomeLinkVisible()` · `isReportButtonVisible()` · `isProfileLoginButtonVisible()` · `isPushNotificationButtonVisible()` · `isPushNotificationButtonEnabled()` · `isThemeToggleButtonVisible()` · `goToHome()` · `goToReport()` · `goToLogin()` · `toggleTheme()`

---

## 10. Implementación de páginas y componentes

### HomePage

Representa la pantalla principal de BlueSignal. Componentes asociados: `MapComponent`, `LocalitySelectorComponent`, `NavbarComponent`.

**Validaciones automatizadas:**

- La Home carga correctamente
- El mapa principal se visualiza
- La localidad "Mar del Plata" puede seleccionarse correctamente
- La barra de navegación muestra los elementos principales

### MapComponent

Representa el mapa principal de BlueSignal.

**Elementos principales:** contenedor del mapa, canvas renderizado por MapLibre.

**Validaciones:** el mapa está visible, el canvas del mapa está visible.

### LocalitySelectorComponent

Representa el selector de localidad disponible en la Home.

**Métodos principales:** `open()` · `selectLocality()` · `getSelectedLocality()` · `waitUntilLocalityRouteIsLoaded()`

Permite automatizar la selección de una localidad y validar que el sistema actualice el contexto geográfico.

### ReportPage

Representa la ruta `/reportar/`. Componente asociado: `ReportFormComponent`.

**Validaciones:** la página de reporte se abre correctamente, el formulario de reporte se visualiza correctamente.

### ReportFormComponent

Representa el formulario de reporte de avistajes.

**Elementos principales:** formulario, botón de envío.

**Validaciones:** el formulario está visible, el botón de envío permanece deshabilitado cuando el formulario está vacío. Esta validación permite probar reglas básicas del formulario sin generar reportes reales en producción.

### LoginPage

Representa la ruta `/login/`. Componente asociado: `LoginFormComponent`.

**Validaciones:** la página de login se abre correctamente, el formulario de login se visualiza correctamente.

### LoginFormComponent

Representa el formulario de inicio de sesión.

**Elementos modelados:** campo email, campo contraseña, botón Iniciar sesión, link Crear perfil, link Olvidé mi contraseña, mensaje de error.

**Validaciones:**

- Los campos principales están visibles
- El sistema muestra un mensaje de error ante credenciales inválidas
- El usuario permanece en la página de login cuando las credenciales no son válidas

---

## 11. Tests automatizados implementados

### HomeTest

| Test | Descripción |
|------|-------------|
| `shouldOpenBlueSignalHomePage` | Valida que la Home de BlueSignal cargue correctamente |
| `shouldDisplayMainMap` | Valida que el mapa principal y su canvas estén visibles |
| `shouldSelectLocality` | Valida que el usuario pueda seleccionar la localidad "Mar del Plata" |
| `shouldDisplayNavbarElements` | Valida que la navegación principal muestre sus elementos principales |

### ReportTest

| Test | Descripción |
|------|-------------|
| `shouldOpenReportFormDesktop` | Valida que el usuario pueda llegar al formulario de reporte desde la Home luego de seleccionar una localidad |
| `shouldKeepSubmitButtonDisabledWhenFormIsEmpty` | Valida que el botón de envío permanezca deshabilitado cuando el formulario está vacío |

### LoginTest

| Test | Descripción |
|------|-------------|
| `shouldOpenLoginPage` | Valida que la ruta `/login/` se abra correctamente y que el formulario esté visible |
| `shouldDisplayLoginFormFields` | Valida que los campos y links principales del formulario estén visibles |
| `shouldShowErrorMessageWithInvalidCredentials` | Valida que el sistema muestre un mensaje de error ante credenciales inválidas |

---

## 12. Resultado de ejecución

La suite completa fue ejecutada correctamente:

```
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
```

---

## 13. Comandos de ejecución

```bash
# Ejecutar toda la suite
mvn clean test

# Ejecutar un test específico
mvn clean -Dtest=HomeTest test
mvn clean -Dtest=ReportTest test
mvn clean -Dtest=LoginTest test

# Ejecutar varios tests específicos
mvn clean -Dtest=HomeTest,ReportTest,LoginTest test

# Ejecutar con ChromeDriver local
mvn clean -Ddriver.mode=manual test
```

---

## 14. Buenas prácticas aplicadas

- Separación entre infraestructura, páginas, componentes y tests
- Uso de Page Object Model
- Uso de Page Factory con `@FindBy`
- Encapsulamiento de selectores dentro de Page Objects y Components
- Eliminación de selectores directos en los tests
- Uso de esperas explícitas mediante `WebDriverWait`
- Centralización de acciones comunes en `BasePage`
- Configuración centralizada del navegador en `DriverFactory`
- Tests escritos en lenguaje funcional
- Evitar la creación de datos reales en producción
- Validación de flujos seguros
- Soporte para ejecución automática y manual del driver

---

## 15. Consideraciones sobre ambiente productivo

Las pruebas se ejecutan sobre el sitio productivo de BlueSignal. Por este motivo, se decidió no automatizar acciones que creen datos reales, como el envío efectivo de un avistaje.

Las pruebas actuales se limitan a:

- Navegación
- Visualización de componentes
- Selección de localidad
- Validación de formularios
- Validación de login inválido

Esto reduce el riesgo de contaminar datos productivos, métricas del sistema o activar notificaciones reales.

---