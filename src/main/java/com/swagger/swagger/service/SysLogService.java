package com.swagger.swagger.service;

import java.util.List;

import com.swagger.swagger.dto.SysLogDateDto;
import com.swagger.swagger.request.LogRequest;

public interface SysLogService {
    public List<SysLogDateDto> getAll(LogRequest logRequest);
}
