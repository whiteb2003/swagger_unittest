package com.swagger.swagger.service;

import java.time.LocalDate;
import java.util.List;

import com.swagger.swagger.dto.SysLogDateDto;
import com.swagger.swagger.dto.SysLogDetail;
import com.swagger.swagger.dto.SysLogSummaryProjection;
import com.swagger.swagger.request.DeleteLogRequest;
import com.swagger.swagger.request.LogRequest;
import org.springframework.data.domain.Page;

public interface SysLogService {
    public List<SysLogDateDto> getAll(LogRequest logRequest);

    public Long countTheSysLogDeleted(DeleteLogRequest deleteLogRequest);

    public List<Object[]> getAllBySql(LocalDate startDate, LocalDate endDate,String method);

    public Page<SysLogSummaryProjection> getByDateAndElement(String startDate, String endDate, String method, String classes, String httpmethod, int page);

    public Page<SysLogDetail> getByElement(String method, String classes, String httpmethod, int page);

}
