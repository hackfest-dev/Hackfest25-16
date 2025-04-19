package hf25_16.debugging_chickens.mental_health_backend.service;
import hf25_16.debugging_chickens.mental_health_backend.dto.Listener.response.ListenerDetailsResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.listenerApplication.request.ListenerApplicationRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.listenerApplication.response.ListenerApplicationResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.listenerApplication.response.ListenerApplicationSummaryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ListenerApplicationService {
    ListenerApplicationResponseDTO submitApplication(ListenerApplicationRequestDTO applicationRequestDTO, MultipartFile certificate) throws Exception;
    ListenerApplicationResponseDTO getApplicationById(Integer applicationId);
    void deleteApplication(Integer applicationId);
    ListenerApplicationResponseDTO updateApplication(Integer applicationId, ListenerApplicationRequestDTO applicationRequestDTO, MultipartFile certificate) throws Exception;
    ListenerDetailsResponseDTO updateApplicationStatus(Integer applicationId, String status);
    ListenerApplicationResponseDTO getApplicationsByUserId(Integer userId);
    Page<ListenerApplicationSummaryResponseDTO> getApplications(String status, Pageable pageable);

}