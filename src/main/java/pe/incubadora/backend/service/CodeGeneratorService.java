package pe.incubadora.backend.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class CodeGeneratorService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public String generate(String prefix) {
        int suffix = ThreadLocalRandom.current().nextInt(100, 999);
        return prefix + "-" + LocalDateTime.now().format(FORMATTER) + "-" + suffix;
    }
}
