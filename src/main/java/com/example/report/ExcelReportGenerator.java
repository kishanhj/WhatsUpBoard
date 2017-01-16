package com.example.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;

import com.example.Helpers.PropertyUtils;
import com.vaadin.ui.ComboBox;

public class ExcelReportGenerator {
	Workbook workbook;
	Sheet sheet;
	int rowCount;
	String reportMonth;
	CTTable cttable;
	String areaName;
	int areaRowCount;

	/**
	 * Constructor
	 * @param month
	 */
	public ExcelReportGenerator(ComboBox month) {
		reportMonth = (String) month.getValue();
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet(reportMonth + " Feedback");
		rowCount = 0;
		generateTitle(reportMonth);
	}

	/**
	 * Generate area/quality Wise Sheets
	 * @param areaName
	 */
   public void GenerateAreaSheet(String areaName){
		this.areaName = areaName;
		sheet = workbook.createSheet(areaName + " Feedback");
		areaRowCount = 0;
		generateAreaTitle(areaName);
	}

   /**
    * Generate title page
    * @param reportMonth
    */
	@SuppressWarnings("unused")
	private void generateTitle(String reportMonth) {
		Row row = sheet.createRow(++rowCount);
		Cell cell = row.createCell(1);
		cell.setCellValue("Feedback for the month :" + reportMonth);
		Row emptyRow1 = sheet.createRow(++rowCount);
		Row emptyRow2 = sheet.createRow(++rowCount);
		generateHeader();
	}

	/**
	 * Generate Area Sheets Title
	 * @param AreaName
	 */
	@SuppressWarnings("unused")
	private void generateAreaTitle(String areaName) {
		Row row = sheet.createRow(++areaRowCount);
		Cell cell = row.createCell(1);
		cell.setCellValue("Feedback for the Area :" + areaName);
		Row emptyRow1 = sheet.createRow(++areaRowCount);
		Row emptyRow2 = sheet.createRow(++areaRowCount);
		generateAreaHeader();
	}

	/**
	 * Generates Area Header
	 */
	private void generateAreaHeader() {
		int cellCount = 0;
		Row row = sheet.createRow(++areaRowCount);

	    XSSFFont font= (XSSFFont) workbook.createFont();
	    font.setFontHeightInPoints((short)10);
	    font.setFontName("Arial");
	    font.setBold(true);
	    font.setItalic(false);

		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);

		Cell nameCell =  row.createCell(++cellCount);
		sheet.autoSizeColumn(cellCount);
		nameCell.setCellStyle(style);
		nameCell.setCellValue("Employee Name");

		Cell projectNameCell =  row.createCell(++cellCount);
		sheet.autoSizeColumn(cellCount);
		projectNameCell.setCellStyle(style);
		projectNameCell.setCellValue("Project");

		Cell satisfactionIndicatorCell = row.createCell(++cellCount);
		sheet.autoSizeColumn(cellCount);
		satisfactionIndicatorCell.setCellValue("Feedback");
		satisfactionIndicatorCell.setCellStyle(style);

		Cell commentCell = row.createCell(++cellCount);
		commentCell.setCellStyle(style);
		sheet.autoSizeColumn(cellCount);
		commentCell.setCellValue("Comment");


	}

	/**
	 * Adds a row to area sheet
	 * @param employeeName
	 * @param project
	 * @param satisfactionIndicator
	 * @param comment
	 */
	public void addRow(String employeeName,String project,String satisfactionIndicator, String comment) {
		int cellCount = 0;
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

		Row row = sheet.createRow(++areaRowCount);

		Cell qualityNameCell = row.createCell(++cellCount);
		sheet.autoSizeColumn(cellCount);
		qualityNameCell.setCellStyle(style);
		qualityNameCell.setCellValue(employeeName);

		Cell projectNameCell = row.createCell(++cellCount);
		sheet.autoSizeColumn(cellCount);
		projectNameCell.setCellStyle(style);
		projectNameCell.setCellValue(project);

		Cell satisfactionIndicatorCell = row.createCell(++cellCount);
		sheet.autoSizeColumn(cellCount);
		satisfactionIndicatorCell.setCellValue(satisfactionIndicator);
		satisfactionIndicatorCell.setCellStyle(style);

		Cell percentageCell = row.createCell(++cellCount);
		sheet.autoSizeColumn(cellCount);
		percentageCell.setCellValue(comment);
		percentageCell.setCellStyle(style);
	}

	/**
	 * Generates main header
	 */
	private void generateHeader() {
		int cellCount = 0;
		Row row = sheet.createRow(++rowCount);

	    XSSFFont font= (XSSFFont) workbook.createFont();
	    font.setFontHeightInPoints((short)10);
	    font.setFontName("Arial");
	    font.setBold(true);
	    font.setItalic(false);

		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);

		Cell qualityNameCell =  row.createCell(++cellCount);
		sheet.autoSizeColumn(cellCount);
		qualityNameCell.setCellStyle(style);
		qualityNameCell.setCellValue("Quality");

		Cell satisfactionIndicatorCell = row.createCell(++cellCount);
		sheet.autoSizeColumn(cellCount);
		satisfactionIndicatorCell.setCellValue("Overall Feedback");
		satisfactionIndicatorCell.setCellStyle(style);

		Cell percentageCell = row.createCell(++cellCount);
		percentageCell.setCellStyle(style);
		sheet.autoSizeColumn(cellCount);
		percentageCell.setCellValue("Percentage with Positive Response");

	}

	/**
	 * Adds rows to main sheet
	 * @param qualityName
	 * @param satisfactionIndicator
	 * @param percentage
	 */
	public void addRow(String qualityName, String satisfactionIndicator, float percentage) {
		int cellCount = 0;
		percentage = percentage * 100;
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		percentage=Float.valueOf(twoDForm.format(percentage));
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

		Row row = sheet.createRow(++rowCount);

		Cell qualityNameCell = row.createCell(++cellCount);
		sheet.autoSizeColumn(cellCount);
		qualityNameCell.setCellStyle(style);
		qualityNameCell.setCellValue(qualityName);
		Cell satisfactionIndicatorCell = row.createCell(++cellCount);
		sheet.autoSizeColumn(cellCount);
		satisfactionIndicatorCell.setCellValue(satisfactionIndicator);
		satisfactionIndicatorCell.setCellStyle(style);
		Cell percentageCell = row.createCell(++cellCount);
		sheet.autoSizeColumn(cellCount);
		percentageCell.setCellValue(percentage);
		percentageCell.setCellStyle(style);
	}



	/**
	 * Triggers the generation of report
	 */
	public void generateReport() {
		Properties prop=new PropertyUtils().getConfigProperties();
		String reportLocation=prop.getProperty("ReportLocation");
		StringBuilder report = new StringBuilder(reportLocation);
		report.append(reportMonth).append("_report.xlsx");
		try (FileOutputStream outputStream = new FileOutputStream(
				new File(report.toString()))) {
			workbook.write(outputStream);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}