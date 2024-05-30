package com.swagger.swagger.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swagger.swagger.dto.SysLogDateDto;
import com.swagger.swagger.repository.LogRepository;
import com.swagger.swagger.request.LogRequest;
import com.swagger.swagger.service.SysLogService;

@Service
public class SysLogImp implements SysLogService {
    @Autowired
    LogRepository logRepository;

    @Override
    public List<SysLogDateDto> getAll(LogRequest logRequest) {
        List<Object[]> results = logRepository.getAll(logRequest.getStartDate(), logRequest.getEndDate(),
                logRequest.getMethod());
        List<SysLogDateDto> list = new ArrayList<>();
        for (Object[] result : results) {
            String month = (String) result[0];
            int count = (int) result[1];
            SysLogDateDto sysLogDateDto = new SysLogDateDto(month, count);
            list.add(sysLogDateDto);
        }
        return list;
    }

}
