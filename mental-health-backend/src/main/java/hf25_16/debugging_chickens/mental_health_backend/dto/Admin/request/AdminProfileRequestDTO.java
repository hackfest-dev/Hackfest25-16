package hf25_16.debugging_chickens.mental_health_backend.dto.Admin.request;

import lombok.Data;
@Data
public class AdminProfileRequestDTO {
    private String adminNotes;
    private String fullName;
    private String qualifications;
    private String contactNumber;
    private String email;
}