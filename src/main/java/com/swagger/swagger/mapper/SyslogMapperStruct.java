package com.swagger.swagger.mapper;

import com.swagger.swagger.dto.SysLogDateDto;
import com.swagger.swagger.dto.SysLogSummaryProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Mapper(componentModel="spring")
@Service
public interface SyslogMapperStruct {

    SysLogDateDto toSysLogDateDto(SysLogSummaryProjection projection);
}
