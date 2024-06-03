package com.swagger.swagger.service;

import java.time.LocalDate;
import java.util.List;

import com.swagger.swagger.dto.SysLogDateDto;
import com.swagger.swagger.request.DeleteLogRequest;
import com.swagger.swagger.request.LogRequest;

public interface SysLogService {
    public List<SysLogDateDto> getAll(LogRequest logRequest);

    public Long countTheSysLogDeleted(DeleteLogRequest deleteLogRequest);

    public List<Object[]> getAllBySql(LocalDate startDate, LocalDate endDate,String method);
}
