package com.swagger.swagger.repository;

import com.swagger.swagger.dto.SysLogDateDto;
import com.swagger.swagger.dto.SysLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class SysLogRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SysLogDto> getAll() {
        String sql = "  select s.CreatedTime, s.SysLogId, s.Method from SysLogs s where s.CreatedTime = '2024-05-02 01:45:58.810'";
        return jdbcTemplate.query(sql, new MyRowMapper());
    }

    public List<SysLogDateDto> getSysLogDate(String startTime, String endTime, String method) {
        String sql = "SELECT CONCAT(YEAR([CreatedTime]), '/', MONTH([CreatedTime])) AS [month], COUNT(*) AS [count] FROM SysLogs WHERE CreatedTime >= ? + '-01 00:00:00.000' and CreatedTime <= ? + '-01 00:00:00.000' and Method = ? GROUP BY CONCAT(YEAR([CreatedTime]), '/', MONTH([CreatedTime])) ORDER BY [month]";
        return jdbcTemplate.query(sql, new Object[] { startTime, endTime, method }, new MyRowMapper2());
    }

    private static class MyRowMapper implements RowMapper<SysLogDto> {

        @Override
        public SysLogDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            SysLogDto log = new SysLogDto();
            Timestamp timestamp = rs.getTimestamp("created_time");
            if (timestamp != null) {
                log.setCreatedTime(timestamp.toLocalDateTime());
            }
            log.setSysLogId(rs.getLong("sys_log_id"));
            log.setMethod(rs.getString("method"));
            return log;
        }
    }

    private static class MyRowMapper2 implements RowMapper<SysLogDateDto> {

        @Override
        public SysLogDateDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            SysLogDateDto log = new SysLogDateDto();
            log.setDate(rs.getString("month"));
            log.setCount(rs.getInt("count"));
            return log;
        }
    }
}
