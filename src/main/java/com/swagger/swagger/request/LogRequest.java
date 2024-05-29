package com.swagger.swagger.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogRequest {
    private String startDate;
    private String endDate;
    private String method;
}
