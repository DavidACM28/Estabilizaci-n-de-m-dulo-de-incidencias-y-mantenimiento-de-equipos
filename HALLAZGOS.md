# HALLAZGOS

## 1. Login falla para usuarios con sede asociada

- **Síntoma observado:** los usuarios `tecnico1@andelab.pe` y `sede.lima@andelab.pe` no podían iniciar sesión, mientras que `admin@andelab.pe` y `operaciones@andelab.pe` sí autenticaban correctamente usando la misma contraseña del seed.
- **Regla de negocio afectada:** la autenticación debe seguir funcionando para los usuarios del laboratorio según los datos iniciales cargados por el sistema.
- **Causa encontrada:** después de autenticar, el sistema construía la respuesta accediendo a `user.getSede()` desde una relación `LAZY` fuera de un contexto transaccional. Eso hacía fallar el flujo solo para usuarios con `sede_id`. Además, el controlador de login atrapaba cualquier `RuntimeException` y devolvía un `401` genérico, ocultando el error real.
- **Solución aplicada:** se marcó `AuthService.login()` con `@Transactional(readOnly = true)` para mantener disponible la relación `sede` durante el mapeo de la respuesta, y se eliminó el `catch (RuntimeException)` en `AuthController` para que los errores reales sean manejados por el `GlobalExceptionHandler`.

## 2. Usuario de sede podía consultar incidencias de otra sede por ID

- **Síntoma observado:** un usuario con rol `SEDE` podía acceder al detalle de una incidencia de otra sede si consultaba directamente el identificador.
- **Regla de negocio afectada:** `SEDE` solo debe ver incidencias asociadas a su propia sede.
- **Causa encontrada:** en `IncidenciaService.findById()` se hacía una excepción para el rol `SEDE` y se usaba `getEntity(id)` en lugar de `getVisibleEntity(id)`, saltándose la validación de visibilidad por sede.
- **Solución aplicada:** se eliminó el bypass para `SEDE` y se dejó `findById()` usando siempre `getVisibleEntity(id)`, de modo que toda consulta por ID pase por la validación de acceso correspondiente.

## 3. Usuario de sede podía consultar órdenes de otra sede por ID

- **Síntoma observado:** un usuario con rol `SEDE` podía acceder al detalle de una orden de trabajo de otra sede si consultaba directamente el identificador.
- **Regla de negocio afectada:** `SEDE` solo debe ver órdenes asociadas a su propia sede.
- **Causa encontrada:** en `OrdenTrabajoService.findById()` se hacía una excepción para el rol `SEDE` y se usaba `getEntity(id)` en lugar de `getVisibleEntity(id)`, evitando la validación de visibilidad por sede.
- **Solución aplicada:** se eliminó el bypass para `SEDE` y se dejó `findById()` usando siempre `getVisibleEntity(id)`, de forma que la validación de acceso se aplique en todas las consultas por identificador.
