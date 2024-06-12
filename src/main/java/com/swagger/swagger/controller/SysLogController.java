package com.swagger.swagger.controller;

import com.swagger.swagger.dto.SysLogDateDto;
import com.swagger.swagger.dto.SysLogDetail;
import com.swagger.swagger.dto.SysLogSummaryProjection;
import com.swagger.swagger.mapper.SyslogMapperStruct;
import com.swagger.swagger.repository.LogRepository;
import com.swagger.swagger.request.DeleteLogRequest;
import com.swagger.swagger.request.LogRequest;
import com.swagger.swagger.response.BaseResponse;
import com.swagger.swagger.service.ConverDataService;
import com.swagger.swagger.service.SysLogService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin("*")
@RestController
public class SysLogController {
    @Autowired
    SysLogService sysLogService;
    @Autowired
    ConverDataService converDataService;
    @Autowired
    LogRepository logRepository;
    @Autowired
    ModelMapper modelMapper;
    private final SyslogMapperStruct syslogMapperStruct;

    public SysLogController(SyslogMapperStruct syslogMapperStruct) {
        this.syslogMapperStruct = syslogMapperStruct;
    }

    @GetMapping("/logsAll")
    public ResponseEntity<BaseResponse> getAll(@RequestBody LogRequest logRequest) {
        return ResponseEntity.status(200).body(BaseResponse
                .builder()
                .data(sysLogService.getAll(logRequest)).build());
    }
    @DeleteMapping("/deleteSyslog")
    public ResponseEntity<BaseResponse> deleteSyslog(@RequestBody DeleteLogRequest deleteLogRequest) {

        return ResponseEntity.status(200).body(BaseResponse
                .builder()
                .data("You have deleted " + sysLogService.countTheSysLogDeleted(deleteLogRequest)).build());
    }

    @GetMapping("/downloadCSV")
    public ResponseEntity<InputStreamResource> downloadFileCSV(@RequestParam("startDate") LocalDate startDate,
                                                            @RequestParam("endDate") LocalDate endDate,
                                                            @RequestParam(value = "method", required = false) String method) throws IOException {
        List<Object[]> data = sysLogService.getAllBySql(startDate, endDate, method);
        ByteArrayInputStream byteArrayInputStream = converDataService.convertToCSV(data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=syslogs.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping("/downloadPDF")
    public ResponseEntity<InputStreamResource> downloadFilePDF(@RequestParam("startDate") LocalDate startDate,
                                                            @RequestParam("endDate") LocalDate endDate,
                                                            @RequestParam(value = "method", required = false) String method) throws IOException, PrinterException {
        List<Object[]> data = sysLogService.getAllBySql(startDate, endDate, method);
        ByteArrayInputStream byteArrayInputStream = converDataService.convertToPDF(data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=syslogs.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping("/download/{type}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("type") String type, @RequestParam("startDate") LocalDate startDate,
                                                               @RequestParam("endDate") LocalDate endDate,
                                                               @RequestParam(value = "method", required = false) String method) throws IOException, PrinterException {
        List<Object[]> data = sysLogService.getAllBySql(startDate, endDate, method);
        ByteArrayInputStream byteArrayInputStream;

        String fileName;
        MediaType mediaType;

        if (type.equalsIgnoreCase("PDF")) {
            byteArrayInputStream = converDataService.convertToPDF(data);
            fileName = "syslogs.pdf";
            mediaType = MediaType.APPLICATION_PDF;
        } else if (type.equalsIgnoreCase("CSV")) {
            byteArrayInputStream = converDataService.convertToCSV(data);
            fileName = "syslogs.csv";
            mediaType = MediaType.parseMediaType("text/csv");
        } else {
            return ResponseEntity.badRequest().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(mediaType)
                .body(new InputStreamResource(byteArrayInputStream));
    }
    @GetMapping("/logs")
    public ResponseEntity<BaseResponse> get(@RequestParam String startDate, @RequestParam String endDate) {
        List<SysLogSummaryProjection> data = logRepository.getByProjection(startDate,endDate);

//        return ResponseEntity.status(200).body(BaseResponse
//                .builder()
//                .data(data.stream().map(item -> syslogMapperStruct.toSysLogDateDto(item))
//                        .collect(Collectors.toList())).build());
        return ResponseEntity.status(200).body(BaseResponse
                .builder()
                .data(data.stream().map(item -> modelMapper.map(item, SysLogDateDto.class))
                        .collect(Collectors.toList())).build());
    }

    @GetMapping("/logsBySetMapping")
    public ResponseEntity<BaseResponse> getLogsBySetMapping(@RequestBody DeleteLogRequest logRequest) {
        return ResponseEntity.status(200).body(BaseResponse
                .builder()
                .data(logRepository.getBySetMapping(logRequest.startDate,logRequest.endDate)).build());
    }

    @GetMapping("/logs/page/{pageNumber}")
    public ResponseEntity<BaseResponse> getLogsByPage(@PathVariable("pageNumber") int currentPage,@RequestParam String startDate, @RequestParam String endDate,
                                                      @RequestParam(required = false) String method,@RequestParam(required = false) String classes, @RequestParam(required = false) String httpmethod) {
        Page<SysLogSummaryProjection> page = sysLogService.getByDateAndElement(startDate,endDate,method,classes,httpmethod,currentPage);
        List<SysLogSummaryProjection> data = page.getContent();
        return ResponseEntity.status(200).body(BaseResponse
                .builder()
                .data(page).build());
    }

    @GetMapping("/logs/pageElement/{pageNumber}")
    public ResponseEntity<BaseResponse> getLogsByPageElement(@PathVariable("pageNumber") int currentPage,@RequestParam String method,@RequestParam String classes, @RequestParam String httpmethod) {
        Page<SysLogDetail> page = sysLogService.getByElement(method,classes,httpmethod,currentPage);
        List<SysLogDetail> data = page.getContent();
        return ResponseEntity.status(200).body(BaseResponse
                .builder()
                .data(page).build());
    }
    
}
