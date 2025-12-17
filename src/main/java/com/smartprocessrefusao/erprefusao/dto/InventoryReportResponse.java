package com.smartprocessrefusao.erprefusao.dto;

import java.time.LocalDate;
import java.util.List;

import com.smartprocessrefusao.erprefusao.projections.InventoryReportProjection;

public record InventoryReportResponse(
        String reportName,
        LocalDate startDate,
        LocalDate endDate,
        Long code,
        List<InventoryReportProjection> data
) {}
