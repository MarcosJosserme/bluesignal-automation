# Entrega 4 - Allure Report avanzado

## Objetivo

Se enriqueció el framework de automatización de BlueSignal incorporando reportes avanzados con Allure Report. El objetivo fue mejorar la trazabilidad de las pruebas automatizadas, facilitar el análisis de resultados y agregar evidencia visual dentro del reporte.

## Cambios realizados

- Se configuró Allure Report para generar y visualizar reportes de ejecución.
- Se incorporaron metadatos funcionales en Step Definitions mediante Allure.
- Se agregaron agrupaciones por Epic, Feature y Story.
- Se incorporaron severidades, descripciones y pasos de ejecución.
- Se agregaron capturas de pantalla como evidencia visual dentro del reporte.

## Flujos enriquecidos

- Página principal
- Inicio de sesión
- Reporte de avistajes

## Validación

Comando ejecutado:

```bash
mvn clean test