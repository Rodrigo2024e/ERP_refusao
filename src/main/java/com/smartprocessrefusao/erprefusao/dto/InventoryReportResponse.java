package com.smartprocessrefusao.erprefusao.dto;

import java.time.LocalDate;
import java.util.List;

import com.smartprocessrefusao.erprefusao.projections.ReportInventoryProjection;

public record InventoryReportResponse(
        String reportName,
        LocalDate startDate,
        LocalDate endDate,
        Long code,
        List<ReportInventoryProjection> data
) {}
