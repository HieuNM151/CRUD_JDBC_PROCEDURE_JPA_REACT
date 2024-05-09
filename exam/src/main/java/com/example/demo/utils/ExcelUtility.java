package com.example.demo.utils;

import com.example.demo.entity.NhanVien;
import com.example.demo.request.CreateNhanVienRequest;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ExcelUtility {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Tên nhân viên", "Năm sinh", "SĐT", "Giới tính", "Trạng thái", "Email" ,"Địa chỉ" };
    static String SHEET = "NhanVien";
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    public static List<CreateNhanVienRequest> excelToNhanVien(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<CreateNhanVienRequest> nhanVienList = new ArrayList<CreateNhanVienRequest>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                CreateNhanVienRequest nvr = new CreateNhanVienRequest();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            nvr.setName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            nvr.setNamsinh(currentCell.getDateCellValue());
                            break;
                        case 2:
                            nvr.setSdt(currentCell.getStringCellValue());
                            break;
                        case 3:
                            nvr.setGioitinh(currentCell.getStringCellValue() == "Nam" ? true : false);
                            break;
                        case 4:
                            nvr.setTrangthai(currentCell.getStringCellValue() == "Hoạt động" ? true : false);
                            break;
                        case 5:
                            nvr.setEmail(currentCell.getStringCellValue());
                            break;
                        case 6:
                            String uuidString = currentCell.getStringCellValue();
                            nvr.setDiachi(UUID.fromString(uuidString));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                nhanVienList.add(nvr);
            }
            workbook.close();
            return nhanVienList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
