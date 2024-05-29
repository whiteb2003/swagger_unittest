package com.swagger.swagger.repository;

import com.swagger.swagger.entity.SysLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogRepository extends JpaRepository<SysLogs, Long> {
    @Query(value = "SELECT CONCAT(YEAR([CreatedTime]), '/', MONTH([CreatedTime])) AS [month], COUNT(*) AS [count] FROM SysLogs WHERE CreatedTime >= :startDate + '-01 00:00:00.000' and CreatedTime <= :endDate + '-01 00:00:00.000' and Method = :method GROUP BY CONCAT(YEAR([CreatedTime]), '/', MONTH([CreatedTime])) ORDER BY [month]", nativeQuery = true)
    public List<Object[]> getAll(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("method") String method);
}
