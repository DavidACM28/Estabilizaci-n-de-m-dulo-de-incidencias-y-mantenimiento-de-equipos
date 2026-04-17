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

## 4. Se podían crear incidencias para equipos no permitidos

- **Síntoma observado:** el sistema permitía registrar incidencias sobre equipos que no debían aceptar nuevas atenciones operativas.
- **Regla de negocio afectada:** no se debe permitir registrar incidencias para equipos en estado `DE_BAJA`, `FUERA_SERVICIO` o con `activo = false`.
- **Causa encontrada:** en `IncidenciaService.create()` solo se validaba `activo = false`, pero no se controlaban los estados `FUERA_SERVICIO` ni `DE_BAJA`.
- **Solución aplicada:** se agregó una validación adicional en `IncidenciaService.create()` para rechazar la creación de incidencias cuando el equipo se encuentre en estado `FUERA_SERVICIO` o `DE_BAJA`, además del caso `activo = false`.

## 5. Se permitían incidencias activas duplicadas para el mismo equipo y tipo

- **Síntoma observado:** el sistema permitía registrar una nueva incidencia para el mismo equipo y tipo aunque ya existiera otra incidencia activa en curso.
- **Regla de negocio afectada:** no debe permitirse crear incidencias activas duplicadas para el mismo equipo y el mismo tipo.
- **Causa encontrada:** en `IncidenciaService.create()` la validación de duplicidad solo consideraba algunos estados iniciales del flujo, por lo que dejaba pasar duplicados cuando la incidencia previa seguía activa en estados posteriores.
- **Solución aplicada:** se amplió la validación para considerar como incidencias activas los estados `REGISTRADA`, `EN_REVISION`, `APROBADA` y `EN_ATENCION`, evitando duplicados mientras la incidencia siga abierta en el flujo operativo.

## 6. La fecha límite de atención se calculaba mal para prioridades urgentes

- **Síntoma observado:** las incidencias con prioridad `CRITICA` y `ALTA` generaban una `fechaLimiteAtencion` inconsistente respecto a la urgencia esperada.
- **Regla de negocio afectada:** la `fechaLimiteAtencion` debe calcularse automáticamente según prioridad: `CRITICA` `+4h`, `ALTA` `+8h`, `MEDIA` `+24h`, `BAJA` `+48h`.
- **Causa encontrada:** en `IncidenciaService.calculateDeadline()` los tiempos de `CRITICA` y `ALTA` estaban invertidos.
- **Solución aplicada:** se corrigió el cálculo para que `CRITICA` use `+4h` y `ALTA` use `+8h`, manteniendo `MEDIA` y `BAJA` según la regla del negocio.

## 7. Se podían crear órdenes desde incidencias en estado inválido

- **Síntoma observado:** el sistema permitía generar órdenes de trabajo desde incidencias que aún no estaban listas para pasar a atención técnica.
- **Regla de negocio afectada:** una orden de trabajo solo puede crearse desde una incidencia en estado `APROBADA`.
- **Causa encontrada:** en `OrdenTrabajoService.create()` solo se bloqueaban algunos estados inválidos, pero no se exigía explícitamente que la incidencia estuviera en estado `APROBADA`.
- **Solución aplicada:** se cambió la validación de creación para permitir la orden únicamente cuando la incidencia esté en estado `APROBADA`, devolviendo error en cualquier otro estado.

## 8. Se podían asignar órdenes fuera del estado inicial permitido

- **Síntoma observado:** el sistema permitía asignar una orden de trabajo aunque ya hubiera avanzado en el flujo, siempre que no estuviera finalizada.
- **Regla de negocio afectada:** una orden solo puede asignarse desde el estado `CREADA`.
- **Causa encontrada:** en `OrdenTrabajoService.assign()` solo se bloqueaba el estado `FINALIZADA`, en lugar de exigir explícitamente el estado `CREADA`.
- **Solución aplicada:** se cambió la validación de asignación para permitirla únicamente cuando la orden esté en estado `CREADA`.

## 9. Se podían iniciar órdenes fuera del estado asignado

- **Síntoma observado:** el sistema permitía iniciar una orden de trabajo aunque no hubiera sido asignada previamente, siempre que no estuviera finalizada.
- **Regla de negocio afectada:** una orden solo puede iniciarse desde el estado `ASIGNADA`.
- **Causa encontrada:** en `OrdenTrabajoService.start()` solo se bloqueaba el estado `FINALIZADA`, en lugar de exigir explícitamente el estado `ASIGNADA`.
- **Solución aplicada:** se cambió la validación de inicio para permitirla únicamente cuando la orden esté en estado `ASIGNADA`.

## 10. Se podían finalizar órdenes fuera del estado en proceso

- **Síntoma observado:** el sistema permitía finalizar una orden aunque no hubiera llegado formalmente al estado `EN_PROCESO`.
- **Regla de negocio afectada:** una orden solo puede finalizarse desde el estado `EN_PROCESO`.
- **Causa encontrada:** en `OrdenTrabajoService.finalizeOrder()` solo se bloqueaban los estados `CREADA` y `ASIGNADA`, pero no se exigía explícitamente el estado `EN_PROCESO`.
- **Solución aplicada:** se cambió la validación de finalización para permitirla únicamente cuando la orden esté en estado `EN_PROCESO`.

## 11. La finalización de órdenes podía dejar stock inconsistente por fallos parciales

- **Síntoma observado:** si ocurría un error durante la finalización de una orden, existía riesgo de que parte del stock de repuestos quedara descontado antes de completar todo el proceso.
- **Regla de negocio afectada:** al finalizar una orden, el descuento de repuestos debe ser consistente y el stock no debe quedar en estado parcial si una parte de la operación falla.
- **Causa encontrada:** `OrdenTrabajoService.finalizeOrder()` no estaba marcado como transaccional y además hacía `saveAndFlush()` sobre repuestos dentro del bucle de consumo, forzando persistencia parcial antes de completar toda la operación.
- **Solución aplicada:** se marcó `finalizeOrder()` con `@Transactional` y se reemplazó `saveAndFlush()` por `save()`, dejando que todo el proceso se confirme o se revierta como una sola transacción.

## 12. Los conflictos concurrentes de stock se devolvían como error inesperado

- **Síntoma observado:** cuando dos operaciones competían por actualizar el stock del mismo repuesto, la API podía responder con un error genérico en lugar de un conflicto controlado.
- **Regla de negocio afectada:** si existe conflicto concurrente de stock, la API debe responder con error controlado.
- **Causa encontrada:** aunque `Repuesto` ya usaba control optimista con `@Version`, el manejador global no traducía las excepciones de optimistic locking a una respuesta funcional de conflicto.
- **Solución aplicada:** se agregó manejo explícito para `ObjectOptimisticLockingFailureException` y `OptimisticLockException`, devolviendo `409` con el contrato uniforme `{ code, message }` usando el código `STOCK_CONFLICT`.

## 13. El detalle de órdenes devolvía errores con un contrato inconsistente

- **Síntoma observado:** el endpoint `GET /ordenes/{id}` capturaba cualquier `RuntimeException` y respondía con un JSON distinto al contrato estándar, usando campos como `status` y `detail`.
- **Regla de negocio afectada:** la API debe responder errores en formato uniforme con la estructura `{ code, message }`.
- **Causa encontrada:** `OrdenTrabajoController.findById()` manejaba errores manualmente en el controlador en vez de delegarlos al `GlobalExceptionHandler`.
- **Solución aplicada:** se eliminó el `try/catch` manual y el endpoint quedó devolviendo directamente `OrdenTrabajoResponse`, dejando que los errores reales se traduzcan mediante el manejador global con el contrato uniforme.
