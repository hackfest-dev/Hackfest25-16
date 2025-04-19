package hf25_16.debugging_chickens.mental_health_backend.dto.email;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MassEmailRequestDTO {
    private String subject;
    private String body;
    private List<MultipartFile> files;
}