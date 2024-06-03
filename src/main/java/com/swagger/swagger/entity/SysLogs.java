package com.swagger.swagger.entity;

import com.swagger.swagger.dto.SysLogDateDto;
import com.swagger.swagger.dto.SysLogSummaryProjection;
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
@NamedNativeQuery(
        name = "SysLog.countByDateRangeAndMethod",
        query = """
        SELECT FORMAT([CreatedTime], 'yyyy-MM') AS [date] 
        , COUNT(*) AS [count] FROM SysLogs 
        WHERE CreatedTime BETWEEN :startDate  and :endDate  
        GROUP BY FORMAT([CreatedTime], 'yyyy-MM') 
        ORDER BY [date]
""",
        resultSetMapping = "SysLogFilterResponseMapping"
)
@SqlResultSetMapping(
        name = "SysLogFilterResponseMapping",
        classes = @ConstructorResult(
                targetClass = SysLogDateDto.class,
                columns = {
                        @ColumnResult(name="date", type = String.class),
                        @ColumnResult(name = "count",type = Long.class)
                }
        )
)
public class SysLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sysLogId;
    private LocalDateTime createdTime;
}
