package com.example.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vaadin.ui.ComboBox;

public class ExcelReportGenerator {
	Workbook workbook;
	Sheet sheet;
	int rowCount;
	FileInputStream file;

	public ExcelReportGenerator(ComboBox month) {
		try {
			FileInputStream file =new FileInputStream(new File("C:\\Users\\kishan.j\\Desktop\\report.xlsx"));
		String reportMonth = (String) month.getValue();
		workbook = new XSSFWorkbook(file);
		sheet = workbook.createSheet(reportMonth+" Feedback");
		rowCount = 0;
		generateTitle(reportMonth);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateTitle(String reportMonth) {
		Row row = sheet.createRow(++rowCount);
		Cell cell = row.createCell(1);
		cell.setCellValue("Feedback for the month :" + reportMonth);
		Row emptyRow1 = sheet.createRow(++rowCount);
		Row emptyRow2 = sheet.createRow(++rowCount);
	}

	public void addRow(String qualityName, String satisfactionIndicator, float percentage) {
		int cellCount = 0;
		Row row = sheet.createRow(++rowCount);
		Cell qualityNameCell = row.createCell(++cellCount);
		qualityNameCell.setCellValue(qualityName);
		Cell satisfactionIndicatorCell = row.createCell(++cellCount);
		satisfactionIndicatorCell.setCellValue(satisfactionIndicator);
		Cell percentageCell = row.createCell(++cellCount);
		percentageCell.setCellValue(percentage);
	}

	public void generateReport() {
		try (FileOutputStream outputStream = new FileOutputStream(("C:\\Users\\kishan.j\\Desktop\\report.xlsx"))) {
			System.out.println("After write");
			workbook.write(outputStream);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}