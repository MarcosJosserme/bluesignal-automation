Feature: Página principal de BlueSignal
  Como visitante de la plataforma
  Quiero acceder a la página principal
  Para consultar información sobre avistajes de fauna marina

    @smoke
    @home
    Scenario: Acceso exitoso a la página principal
        Given el visitante no se encuentra autenticado
        When accede a la página principal de BlueSignal
        Then la página principal se muestra correctamente