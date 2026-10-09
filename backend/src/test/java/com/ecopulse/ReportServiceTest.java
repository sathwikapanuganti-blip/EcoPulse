package com.ecopulse;

import com.ecopulse.dto.ReportDto;
import com.ecopulse.entity.EnvironmentalReport;
import com.ecopulse.repository.EnvironmentalReportRepository;
import com.ecopulse.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ReportServiceTest {

    private EnvironmentalReportRepository reportRepository;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportRepository = Mockito.mock(EnvironmentalReportRepository.class);
        reportService = new ReportService(reportRepository);
    }

    @Test
    void testCreateReport() {
        ReportDto input = new ReportDto();
        input.setCategory("GARBAGE_DUMPING");
        input.setLocation("Block C Rear Lane");
        input.setSeverity("HIGH");
        input.setDescription("Commercial garbage bags piled on walking trail");
        input.setLatitude(28.6120);
        input.setLongitude(77.2065);

        EnvironmentalReport savedEntity = new EnvironmentalReport(
                1L, "GARBAGE_DUMPING", "Block C Rear Lane", 28.6120, 77.2065, "HIGH",
                "Commercial garbage bags piled on walking trail", null
        );
        savedEntity.setId(101L);

        Mockito.when(reportRepository.save(any(EnvironmentalReport.class))).thenReturn(savedEntity);

        ReportDto result = reportService.createReport(input, 1L);

        assertNotNull(result);
        assertEquals(101L, result.getId());
        assertEquals("GARBAGE_DUMPING", result.getCategory());
        assertEquals("REPORTED", result.getStatus());
    }

    @Test
    void testGetAllReportsRetrieval() {
        EnvironmentalReport r1 = new EnvironmentalReport(1L, "PLASTIC_POLLUTION", "Playground", 28.61, 77.21, "MEDIUM", "Plastic waste", null);
        r1.setId(1L);
        EnvironmentalReport r2 = new EnvironmentalReport(2L, "SMOKE", "Sector 4", 28.62, 77.22, "CRITICAL", "Open leaf burning", null);
        r2.setId(2L);

        Mockito.when(reportRepository.findAllByOrderByCreatedAtDesc()).thenReturn(List.of(r1, r2));

        List<ReportDto> reports = reportService.getAllReports();

        assertNotNull(reports);
        assertEquals(2, reports.size());
        assertEquals("PLASTIC_POLLUTION", reports.get(0).getCategory());
        assertEquals("SMOKE", reports.get(1).getCategory());
    }

    @Test
    void testUpdateReportStatusLifecycle() {
        EnvironmentalReport existing = new EnvironmentalReport(1L, "WATER_WASTAGE", "Main Gate", 28.61, 77.21, "HIGH", "Broken pipe", null);
        existing.setId(50L);
        existing.setStatus("REPORTED");

        Mockito.when(reportRepository.findById(50L)).thenReturn(Optional.of(existing));
        Mockito.when(reportRepository.save(any(EnvironmentalReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReportDto updated = reportService.updateStatus(50L, "IN_PROGRESS", "Plumber team assigned");

        assertNotNull(updated);
        assertEquals("IN_PROGRESS", updated.getStatus());
        assertEquals("Plumber team assigned", updated.getAdminNotes());
    }
}
