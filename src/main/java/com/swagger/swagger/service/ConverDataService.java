package com.swagger.swagger.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.print.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@Service
public class ConverDataService {
    public ByteArrayInputStream convertToCSV(List<Object[]> data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);
        writer.println("date,count");
        for (Object[] row : data) {
            writer.println(row[0] + "," + row[1]);
        }

        writer.flush();
        return new ByteArrayInputStream(out.toByteArray());
    }
    public ByteArrayInputStream convertToPDF(List<Object[]> data) throws IOException, PrinterException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("SysLog Data"));

        Table table = new Table(2);
        table.addCell(new Cell().add(new Paragraph("Date")));
        table.addCell(new Cell().add(new Paragraph("Count")));

        for (Object[] row : data) {
            table.addCell(new Cell().add(new Paragraph(row[0].toString())));
            table.addCell(new Cell().add(new Paragraph(row[1].toString())));
        }

        document.add(table);
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
