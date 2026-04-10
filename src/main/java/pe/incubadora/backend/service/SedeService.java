package pe.incubadora.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pe.incubadora.backend.dto.sede.SedeRequest;
import pe.incubadora.backend.dto.sede.SedeResponse;
import pe.incubadora.backend.entity.Role;
import pe.incubadora.backend.entity.Sede;
import pe.incubadora.backend.exception.ConflictException;
import pe.incubadora.backend.exception.ForbiddenException;
import pe.incubadora.backend.exception.NotFoundException;
import pe.incubadora.backend.mapper.SedeMapper;
import pe.incubadora.backend.repository.SedeRepository;
import pe.incubadora.backend.security.SecurityUtils;

@Service
@RequiredArgsConstructor
public class SedeService {

    private final SedeRepository sedeRepository;
    private final SedeMapper sedeMapper;
    private final SecurityUtils securityUtils;

    @Transactional(readOnly = true)
    public Page<SedeResponse> findAll(String search, Boolean activa, Pageable pageable) {
        Specification<Sede> spec = (root, query, cb) -> cb.conjunction();

        if (securityUtils.hasAnyRole(Role.SEDE)) {
            Long sedeId = securityUtils.getCurrentUser().getSedeId();
            spec = spec.and((root, query, cb) -> cb.equal(root.get("id"), sedeId));
        }
        if (StringUtils.hasText(search)) {
            String value = "%" + search.trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("codigo")), value),
                    cb.like(cb.lower(root.get("nombre")), value),
                    cb.like(cb.lower(root.get("ciudad")), value)
            ));
        }
        if (activa != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("activa"), activa));
        }

        return sedeRepository.findAll(spec, pageable).map(sedeMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public SedeResponse findById(Long id) {
        return sedeMapper.toResponse(getVisibleEntity(id));
    }

    @Transactional
    public SedeResponse create(SedeRequest request) {
        if (sedeRepository.existsByCodigoIgnoreCase(request.codigo().trim())) {
            throw new ConflictException("SEDE_CODE_EXISTS", "Ya existe una sede con el código indicado");
        }
        Sede sede = new Sede();
        sedeMapper.updateEntity(request, sede);
        return sedeMapper.toResponse(sedeRepository.save(sede));
    }

    @Transactional
    public SedeResponse update(Long id, SedeRequest request) {
        Sede sede = getVisibleEntity(id);
        if (!sede.getCodigo().equalsIgnoreCase(request.codigo().trim())
                && sedeRepository.existsByCodigoIgnoreCase(request.codigo().trim())) {
            throw new ConflictException("SEDE_CODE_EXISTS", "Ya existe una sede con el código indicado");
        }
        sedeMapper.updateEntity(request, sede);
        return sedeMapper.toResponse(sedeRepository.save(sede));
    }

    @Transactional
    public void delete(Long id) {
        Sede sede = getVisibleEntity(id);
        sedeRepository.delete(sede);
    }

    @Transactional(readOnly = true)
    public Sede getVisibleEntity(Long id) {
        Sede sede = sedeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("SEDE_NOT_FOUND", "Sede no encontrada"));

        if (securityUtils.hasAnyRole(Role.SEDE) && !id.equals(securityUtils.getCurrentUser().getSedeId())) {
            throw new ForbiddenException("FORBIDDEN", "No puede acceder a una sede distinta a la suya");
        }
        return sede;
    }
}
