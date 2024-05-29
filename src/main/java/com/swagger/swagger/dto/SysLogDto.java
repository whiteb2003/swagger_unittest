package com.swagger.swagger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLogDto {
    private LocalDateTime createdTime;
    private Long sysLogId;
    private String method;
}
