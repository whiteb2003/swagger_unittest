package com.swagger.swagger.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "SysLogs")
public class SysLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sysLogId;
    private LocalDateTime createdTime;
}
