package com.swagger.swagger.controller;

import com.swagger.swagger.request.LogRequest;
import com.swagger.swagger.response.BaseResponse;
import com.swagger.swagger.service.SysLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysLogController {
    @Autowired
    SysLogService sysLogService;

    @GetMapping("/logsAll")
    public ResponseEntity<BaseResponse> getAll(@RequestBody LogRequest logRequest) {

        return ResponseEntity.status(200).body(BaseResponse
                .builder()
                .data(sysLogService.getAll(logRequest)).build());
    }
}
