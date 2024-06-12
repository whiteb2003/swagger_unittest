package com.swagger.swagger.repository;

import com.swagger.swagger.dto.SysLogDateDto;
import com.swagger.swagger.dto.SysLogSummaryProjection;
import com.swagger.swagger.entity.SysLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface LogRepository extends JpaRepository<SysLogs, Long> {
    @Query(value = "SELECT FORMAT([CreatedTime], 'yyyy-MM') AS [month]" +
            ", COUNT(*) AS [count2] FROM SysLogs " +
            "WHERE CreatedTime BETWEEN :startDate  and :endDate  " +
            "and (:method IS NULL OR Method = :method) GROUP BY FORMAT([CreatedTime], 'yyyy-MM') " +
            "ORDER BY [month]", nativeQuery = true)
    public List<Object[]> getAll(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("method") String method);

    @Query(value = "SELECT COUNT(*) from SysLogs WHERE CAST(CreatedTime AS DATE) BETWEEN :startDate and :endDate ", nativeQuery = true)
    public Long countSysLogsDelete(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT FORMAT([CreatedTime], 'yyyy-MM') AS [month]" +
            ", COUNT(*) AS [count] FROM SysLogs " +
            "WHERE CAST(CreatedTime AS DATE) BETWEEN :startDate  and :endDate  " +
            "and (:method IS NULL OR Method = :method) " +
            "GROUP BY FORMAT([CreatedTime], 'yyyy-MM') " +
            "ORDER BY [month]", nativeQuery = true)
    public List<Object[]> getAllBySql(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                      @Param("method") String method);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM SysLogs " +
            "WHERE CAST(CreatedTime AS DATE) BETWEEN :startDate  and :endDate ",nativeQuery = true)
    public void deleteSyslogsByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM SysLogs " +
            "WHERE CAST(CreatedTime AS DATE) = :startDate",nativeQuery = true)
    public void deleteSyslogsByDate(@Param("startDate") LocalDate startDate);

    @Query(value = "SELECT FORMAT([CreatedTime], 'yyyy-MM') AS [date]" +
            ", COUNT(*) AS [count] FROM SysLogs " +
            "WHERE CreatedTime BETWEEN :startDate  and :endDate  " +
            " GROUP BY FORMAT([CreatedTime], 'yyyy-MM') " +
            " ORDER BY [date]", nativeQuery = true)
    public List<SysLogSummaryProjection> getByProjection(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(name = "SysLog.countByDateRangeAndMethod", nativeQuery = true)
    public List<SysLogDateDto> getBySetMapping(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
