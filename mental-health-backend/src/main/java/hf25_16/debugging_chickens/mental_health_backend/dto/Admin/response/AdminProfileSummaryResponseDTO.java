package hf25_16.debugging_chickens.mental_health_backend.dto.Admin.response;
import lombok.Data;

@Data
public class AdminProfileSummaryResponseDTO {
    private Integer adminId;
    private String fullName;
    private String adminNotes;
    private String contactNumber;
}