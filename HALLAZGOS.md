# HALLAZGOS

## 1. Login falla para usuarios con sede asociada

- **Síntoma observado:** los usuarios `tecnico1@andelab.pe` y `sede.lima@andelab.pe` no podían iniciar sesión, mientras que `admin@andelab.pe` y `operaciones@andelab.pe` sí autenticaban correctamente usando la misma contraseña del seed.
- **Regla de negocio afectada:** la autenticación debe seguir funcionando para los usuarios del laboratorio según los datos iniciales cargados por el sistema.
- **Causa encontrada:** después de autenticar, el sistema construía la respuesta accediendo a `user.getSede()` desde una relación `LAZY` fuera de un contexto transaccional. Eso hacía fallar el flujo solo para usuarios con `sede_id`. Además, el controlador de login atrapaba cualquier `RuntimeException` y devolvía un `401` genérico, ocultando el error real.
- **Solución aplicada:** se marcó `AuthService.login()` con `@Transactional(readOnly = true)` para mantener disponible la relación `sede` durante el mapeo de la respuesta, y se eliminó el `catch (RuntimeException)` en `AuthController` para que los errores reales sean manejados por el `GlobalExceptionHandler`.
