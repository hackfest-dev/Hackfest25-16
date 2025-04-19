package hf25_16.debugging_chickens.mental_health_backend.service;

import hf25_16.debugging_chickens.mental_health_backend.dto.Admin.request.AdminProfileRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Admin.response.AdminProfileSummaryResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.Admin.response.FullAdminProfileResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminService {
    FullAdminProfileResponseDTO createAdminProfile(AdminProfileRequestDTO adminProfileRequestDTO, MultipartFile profilePicture) throws Exception;
    FullAdminProfileResponseDTO getAdminProfile(Integer userId, Integer adminId);
    FullAdminProfileResponseDTO updateAdminProfile(AdminProfileRequestDTO adminProfileRequestDTO, MultipartFile profilePicture) throws Exception;
    List<AdminProfileSummaryResponseDTO> getAllAdmins();
    String deleteAdminProfile(Integer adminId);
}