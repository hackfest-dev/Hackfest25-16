package hf25_16.debugging_chickens.mental_health_backend.service;

import hf25_16.debugging_chickens.mental_health_backend.dto.SessionReport.request.SessionReportRequestDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.SessionReport.response.SessionReportResponseDTO;
import hf25_16.debugging_chickens.mental_health_backend.dto.SessionReport.response.SessionReportSummaryResponseDTO;

import java.util.List;

public interface SessionReportService {
    SessionReportResponseDTO createReport(SessionReportRequestDTO requestDTO);
    List<SessionReportResponseDTO> getReportBySessionId(Integer sessionId);
    SessionReportResponseDTO getReportById(Integer reportId);
    List<SessionReportResponseDTO> getAllUserReports(Integer userId);
    SessionReportSummaryResponseDTO getReportSummary();
}