package com.swagger.swagger.service.imp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
        List<String> months = new ArrayList<>();

        LocalDate start = LocalDate.parse(logRequest.getStartDate() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(logRequest.getEndDate() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        while (!start.isAfter(end)) {
            months.add(start.format(formatter));
            start = start.plusMonths(1);
        }

        List<Object[]> rawData = logRepository.getAll(logRequest.getStartDate(), logRequest.getEndDate(),
                logRequest.getMethod());

        Map<String, Long> dataMap = new HashMap<String, Long>();
        for (Object[] result : rawData) {
            dataMap.put((String) result[0], ((Number) result[1]).longValue());
        }
        List<Object[]> results = new ArrayList<>();
        for (String month : months) {
            Long count = dataMap.getOrDefault(month, null);
            results.add(new Object[] { month, count });
        }

        List<SysLogDateDto> list = new ArrayList<>();
        for (Object[] result : results) {
            String month = (String) result[0];
            Long count = (Long) result[1];
            int countValue = (count == null) ? 0 : count.intValue();
            SysLogDateDto sysLogDateDto = new SysLogDateDto(month, countValue);
            list.add(sysLogDateDto);
        }
        return list;
    }

}
