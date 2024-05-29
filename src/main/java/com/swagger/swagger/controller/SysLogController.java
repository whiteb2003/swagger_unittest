package com.swagger.swagger.controller;

import com.swagger.swagger.dto.SysLogDateDto;
import com.swagger.swagger.repository.LogRepository;
import com.swagger.swagger.repository.SysLogRepository;
import com.swagger.swagger.request.LogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SysLogController {
    @Autowired
    SysLogRepository sysLogRepository;
    @Autowired
    LogRepository logRepository;

    @GetMapping("/logs")
    public ResponseEntity<?> getLogs() {
        return ResponseEntity.ok(sysLogRepository.getAll());
    }

    @GetMapping("/logsDetail")
    public ResponseEntity<?> getLogsDetail(@RequestBody LogRequest logRequest) {
        return ResponseEntity.ok(sysLogRepository.getSysLogDate(logRequest.getStartDate(), logRequest.getEndDate(),
                logRequest.getMethod()));
    }

    @GetMapping("/logsAll")
    public ResponseEntity<?> getAll(@RequestBody LogRequest logRequest) {
        List<Object[]> results = logRepository.getAll(logRequest.getStartDate(), logRequest.getEndDate(),
                logRequest.getMethod());
        List<SysLogDateDto> list = new ArrayList<>();
        for (Object[] result : results) {
            String month = (String) result[0];
            int count = (int) result[1];
            SysLogDateDto sysLogDateDto = new SysLogDateDto(month, count);
            list.add(sysLogDateDto);
        }
        return ResponseEntity.ok(list);
    }
}
