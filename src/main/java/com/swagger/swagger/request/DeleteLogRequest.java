package com.swagger.swagger.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteLogRequest {
    public LocalDate  startDate;
    public LocalDate  endDate;
}
