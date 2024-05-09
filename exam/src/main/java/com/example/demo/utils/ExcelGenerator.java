package com.example.demo.utils;

import com.example.demo.entity.NhanVien;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ExcelGenerator {
    private List<NhanVien> nhanVienList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List < NhanVien > nhanVienList) {
        this.nhanVienList = nhanVienList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("NhanVien");
        Row row = sheet.createRow(4);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "STT", style); // Add "STT" column
        createCell(row, 1, "ID", style);
        createCell(row, 2, "Tên nhân viên", style);
        createCell(row, 3, "Năm sinh", style);
        createCell(row, 4, "SĐT", style);
        createCell(row, 5, "Giới tính", style);
        createCell(row, 6, "Trạng thái", style);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Boolean) {
            cell.setCellValue((Boolean) valueOfCell);
        } else if (valueOfCell instanceof UUID) {
            cell.setCellValue(valueOfCell.toString());
        }else if (valueOfCell instanceof Date) {
            cell.setCellValue(new SimpleDateFormat("dd/MM/yyyy").format((Date) valueOfCell));
        }
        cell.setCellStyle(style);
    }

    private void write() {
        int rowCount = 5;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (NhanVien record: nhanVienList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, rowCount - 5, style); // Add row number
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getName(), style);
            createCell(row, columnCount++, record.getNamsinh(), style);
            createCell(row, columnCount++, record.getSdt(), style);
            createCell(row, columnCount++, record.getGioitinh() == true ? "Nam" : "Nữ", style);
            createCell(row, columnCount++, record.getTrangthai() == true ? "Hoạt động" : "Nghỉ làm", style);
        }
    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}