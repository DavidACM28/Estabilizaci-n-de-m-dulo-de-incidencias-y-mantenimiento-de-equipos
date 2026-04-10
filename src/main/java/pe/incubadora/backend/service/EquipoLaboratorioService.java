package pe.incubadora.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pe.incubadora.backend.dto.equipo.EquipoLaboratorioRequest;
import pe.incubadora.backend.dto.equipo.EquipoLaboratorioResponse;
import pe.incubadora.backend.entity.EquipoLaboratorio;
import pe.incubadora.backend.entity.EstadoEquipo;
import pe.incubadora.backend.entity.Role;
import pe.incubadora.backend.entity.Sede;
import pe.incubadora.backend.exception.ConflictException;
import pe.incubadora.backend.exception.ForbiddenException;
import pe.incubadora.backend.exception.NotFoundException;
import pe.incubadora.backend.mapper.EquipoLaboratorioMapper;
import pe.incubadora.backend.repository.EquipoLaboratorioRepository;
import pe.incubadora.backend.security.SecurityUtils;

@Service
@RequiredArgsConstructor
public class EquipoLaboratorioService {

    private final EquipoLaboratorioRepository equipoRepository;
    private final EquipoLaboratorioMapper equipoMapper;
    private final SedeService sedeService;
    private final SecurityUtils securityUtils;

    @Transactional(readOnly = true)
    public Page<EquipoLaboratorioResponse> findAll(String search,
                                                   Long sedeId,
                                                   EstadoEquipo estado,
                                                   Boolean activo,
                                                   Pageable pageable) {
        Specification<EquipoLaboratorio> spec = (root, query, cb) -> cb.conjunction();
        Long visibleSedeId = resolveVisibleSedeId(sedeId);

        if (StringUtils.hasText(search)) {
            String value = "%" + search.trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("codigoPatrimonial")), value),
                    cb.like(cb.lower(root.get("nombre")), value),
                    cb.like(cb.lower(root.get("marca")), value),
                    cb.like(cb.lower(root.get("modelo")), value)
            ));
        }
        if (visibleSedeId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("sede").get("id"), visibleSedeId));
        }
        if (estado != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("estado"), estado));
        }
        if (activo != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("activo"), activo));
        }

        return equipoRepository.findAll(spec, pageable).map(equipoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public EquipoLaboratorioResponse findById(Long id) {
        return equipoMapper.toResponse(getVisibleEntity(id));
    }

    @Transactional
    public EquipoLaboratorioResponse create(EquipoLaboratorioRequest request) {
        if (equipoRepository.existsByCodigoPatrimonialIgnoreCase(request.codigoPatrimonial().trim())) {
            throw new ConflictException("EQUIPO_CODE_EXISTS", "Ya existe un equipo con el código patrimonial indicado");
        }

        Sede sede = sedeService.getVisibleEntity(request.sedeId());
        EquipoLaboratorio equipo = new EquipoLaboratorio();
        equipoMapper.updateEntity(request, equipo);
        equipo.setSede(sede);
        return equipoMapper.toResponse(equipoRepository.save(equipo));
    }

    @Transactional
    public EquipoLaboratorioResponse update(Long id, EquipoLaboratorioRequest request) {
        EquipoLaboratorio equipo = getVisibleEntity(id);
        if (!equipo.getCodigoPatrimonial().equalsIgnoreCase(request.codigoPatrimonial().trim())
                && equipoRepository.existsByCodigoPatrimonialIgnoreCase(request.codigoPatrimonial().trim())) {
            throw new ConflictException("EQUIPO_CODE_EXISTS", "Ya existe un equipo con el código patrimonial indicado");
        }

        Sede sede = sedeService.getVisibleEntity(request.sedeId());
        equipoMapper.updateEntity(request, equipo);
        equipo.setSede(sede);
        return equipoMapper.toResponse(equipoRepository.save(equipo));
    }

    @Transactional
    public void delete(Long id) {
        equipoRepository.delete(getVisibleEntity(id));
    }

    @Transactional(readOnly = true)
    public EquipoLaboratorio getVisibleEntity(Long id) {
        EquipoLaboratorio equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("EQUIPO_NOT_FOUND", "Equipo de laboratorio no encontrado"));
        validateSedeVisibility(equipo.getSede().getId());
        return equipo;
    }

    private Long resolveVisibleSedeId(Long requestedSedeId) {
        if (securityUtils.hasAnyRole(Role.SEDE)) {
            Long currentSedeId = securityUtils.getCurrentUser().getSedeId();
            if (requestedSedeId != null && !requestedSedeId.equals(currentSedeId)) {
                throw new ForbiddenException("FORBIDDEN", "No puede consultar equipos de otra sede");
            }
            return currentSedeId;
        }
        return requestedSedeId;
    }

    private void validateSedeVisibility(Long sedeId) {
        if (securityUtils.hasAnyRole(Role.SEDE) && !sedeId.equals(securityUtils.getCurrentUser().getSedeId())) {
            throw new ForbiddenException("FORBIDDEN", "No puede acceder a equipos de otra sede");
        }
    }
}
