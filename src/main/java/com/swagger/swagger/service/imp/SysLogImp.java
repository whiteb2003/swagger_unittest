package com.swagger.swagger.service.imp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.swagger.swagger.request.DeleteLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swagger.swagger.dto.SysLogDateDto;
import com.swagger.swagger.repository.LogRepository;
import com.swagger.swagger.request.LogRequest;
import com.swagger.swagger.service.SysLogService;

@Service
public class SysLogImp implements SysLogService {
    @Autowired
    LogRepository logRepository;

    @Override
    public List<SysLogDateDto> getAll(LogRequest logRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        List<String> months = new ArrayList<>();

        LocalDate start = LocalDate.parse(logRequest.getStartDate() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(logRequest.getEndDate() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        while (!start.isAfter(end)) {
            months.add(start.format(formatter));
            start = start.plusMonths(1);
        }

        String startDateTime = logRequest.getStartDate() + "-01 00:00:00.000";
        LocalDate endLocalDate = LocalDate.parse(logRequest.getEndDate() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDateTime = endLocalDate.with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 23:59:59.999";

        List<Object[]> rawData = logRepository.getAll(startDateTime, endDateTime, logRequest.getMethod());


        Map<String, Long> dataMap = rawData.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> ((Number) row[1]).longValue()));


        List<Object[]> results = new ArrayList<>();
        for (String month : months) {
            Long count = dataMap.getOrDefault(month, null);
            results.add(new Object[] { month, count});
        }

        List<SysLogDateDto> list = new ArrayList<>();
        for (Object[] result : results) {
            String month = (String) result[0];
            Long count = (Long) result[1];
            long countValue = (count == null) ? 0 : count.intValue();
            SysLogDateDto sysLogDateDto = new SysLogDateDto(month, countValue);
            list.add(sysLogDateDto);
        }

        return list;
    }

    @Override
    public Long countTheSysLogDeleted(DeleteLogRequest deleteLogRequest) {
        Long count =  logRepository.countSysLogsDelete(deleteLogRequest.startDate,deleteLogRequest.endDate);
        if(deleteLogRequest.endDate == null){
            logRepository.deleteSyslogsByDate(deleteLogRequest.startDate);
        }else{
            logRepository.deleteSyslogsByDateRange(deleteLogRequest.startDate,deleteLogRequest.endDate);
        }
        return count;
    }

    @Override
    public List<Object[]> getAllBySql(LocalDate startDate, LocalDate endDate,String method) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        List<Object[]> rawData =logRepository.getAllBySql(startDate, endDate, method);
        List<String> months = new ArrayList<>();
        while (!startDate.isAfter(endDate)) {
            months.add(startDate.format(formatter));
            startDate = startDate.plusMonths(1);
        }
        System.out.println(months);
        Map<String, Long> dataMap = rawData.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> ((Number) row[1]).longValue()));
        System.out.println(dataMap);
        List<Object[]> results = new ArrayList<>();
        for (String month : months) {
            Long count = dataMap.getOrDefault(month, null);
            System.out.println("Month: " + month + ", count: " + count);
            results.add(new Object[] { month, count });
        }
        return results;
    }

}
