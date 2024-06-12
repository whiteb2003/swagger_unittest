package com.swagger.swagger.repository;

import com.swagger.swagger.dto.SysLogDetail;
import com.swagger.swagger.dto.SysLogSummaryProjection;
import com.swagger.swagger.entity.SysLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogRepositoryPaging extends PagingAndSortingRepository<SysLogs,Long> {

    @Query(value = "SELECT FORMAT([CreatedTime], 'yyyy-MM') AS [date]" +
            ", COUNT(*) AS [count] FROM SysLogs " +
            "WHERE CreatedTime BETWEEN :startDate  and :endDate  " +
            "and (:method IS NULL OR Method = :method)"+
            "and (:classes IS NULL OR Class = :classes)"+
            "and (:httpmethod IS NULL OR HttpMethod = :httpmethod)"+
            " GROUP BY FORMAT([CreatedTime], 'yyyy-MM') " +
            " ORDER BY [date]", nativeQuery = true)
    public Page<SysLogSummaryProjection> getByDateAndElement(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("method") String method,
                                                             @Param("classes") String classes, @Param("httpmethod") String httpmethod, Pageable pageable);

    @Query(value = "SELECT FORMAT([CreatedTime], 'yyyy-MM') AS [date]" +
            ", COUNT(*) AS [count], " +
            " Method as [method], Class as [classes], HttpMethod as[httpmethod] FROM SysLogs "+
            " WHERE (:method IS NULL OR Method = :method)"+
            " and (:classes IS NULL OR Class = :classes)"+
            " and (:httpmethod IS NULL OR HttpMethod = :httpmethod)"+
            " GROUP BY FORMAT([CreatedTime], 'yyyy-MM'), Method, Class,HttpMethod " +
            " ORDER BY [date]", nativeQuery = true)
    public Page<SysLogDetail> getByElement(@Param("method") String method,@Param("classes") String classes, @Param("httpmethod") String httpmethod, Pageable pageable);
}
