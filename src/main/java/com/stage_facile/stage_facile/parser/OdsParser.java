package com.stage_facile.stage_facile.parser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import com.stage_facile.stage_facile.models.Company;
import com.stage_facile.stage_facile.models.Industry;
import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.models.User;

public class OdsParser {

	private static File iproFile = new File("./src/main/resources/StagesExport_1.ods");
	private static Sheet sheet;
	
	public OdsParser() throws IOException {
		sheet = loadFirstSheet();
	}
	
	private static Sheet loadFirstSheet() throws IOException {
		return SpreadSheet.createFromFile(iproFile).getSheet(0);
	}
	
	public static List<String> getHeaders() throws IOException {
		sheet = loadFirstSheet();
		List<String> headers = new ArrayList<>();
		
		for (int col = 0; col < sheet.getColumnCount(); col++) {
			//map the value of each column in row 0 to corresponding column index
			String cellText = sheet.getImmutableCellAt(col, 0).getTextValue();
			if (col == 0 || col > 10 && cellText.equals("")) continue;
			headers.add(cellText);
		}
		
		return headers;
	}
	
	// TODO
	@SuppressWarnings("unused")
	public static List<User> getStudents() throws IOException {
		List<User> users = new ArrayList<>();
		sheet = loadFirstSheet();
		for (int row = 1; row < sheet.getRowCount(); row++) {
			String fullName = sheet.getImmutableCellAt(3, row).getTextValue();
			String firstName = parseFirstName(fullName);
			String lastName = parseLastName(fullName);
			
		}
		
		return users;
	}

	private static String parseFirstName(String fullName) {
		return fullName.split(" ")[1];
	}

	private static String parseLastName(String fullName) {
		String lastName = fullName.split(" ")[0];
		String capitalized = lastName.substring(0, 1).toUpperCase() 
				+ lastName.substring(1);
		
		return capitalized;
	}

	public static Set<String> getIndustries() {
		Set<String> industries = new HashSet<>();
		try {
			sheet = loadFirstSheet();
			for (int row = 1; row < sheet.getRowCount(); row++) {
				String industry = sheet.getImmutableCellAt(8, row).getTextValue();
				if(!industry.equals("")) industries.add(industry);
				
			}
		} catch (IOException e) {
				System.err.println(e);
			}
		
		return industries;
	}
	
	private static Industry parseIndustry(int row) {
		String industry = sheet.getImmutableCellAt(8, row).getTextValue();
		return new Industry(industry);
		
	}
	
	public static List<Company> getCompanies() {
		List<Company> companies = new ArrayList<>();
		try {
			sheet = loadFirstSheet();
			
			for (int row = 1; row < sheet.getRowCount(); row++) {
				String country = sheet.getImmutableCellAt(9, row).getTextValue();
				String companyName = sheet.getImmutableCellAt(10, row).getTextValue();
				String address = sheet.getImmutableCellAt(11, row).getTextValue();
				String postalCode = sheet.getImmutableCellAt(12, row).getTextValue();
				String city = sheet.getImmutableCellAt(13, row).getTextValue();
				Industry industry = parseIndustry(row);
				Company company = new Company(companyName, industry, address, postalCode, city, country);
				companies.add(company);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		
		return companies;
	}
	
	private static Company parseCompany(int row) throws IOException {
		sheet = loadFirstSheet();
		String country = sheet.getImmutableCellAt(9, row).getTextValue();
		String companyName = sheet.getImmutableCellAt(10, row).getTextValue();
		String address = sheet.getImmutableCellAt(11, row).getTextValue();
		String postalCode = sheet.getImmutableCellAt(12, row).getTextValue();
		String city = sheet.getImmutableCellAt(13, row).getTextValue();
		Industry industry = parseIndustry(row);
		return new Company(companyName, industry, address, postalCode, city, country);
		
	}
	
	public static List<Internship> getInternships() {
		List<Internship> internships = new ArrayList<>();
		try {
			sheet = loadFirstSheet();
			
			for (int row = 1; row < sheet.getRowCount(); row++) {
				String beginDateUnformattedString = 
						sheet.getImmutableCellAt(18, row).getTextValue();
				String endDateUnformattedString = 
						sheet.getImmutableCellAt(19, row).getTextValue();
				LocalDate beginDate = parseDate(beginDateUnformattedString);
				LocalDate endDate = parseDate(endDateUnformattedString);
				
				String function = sheet.getImmutableCellAt(7, row).getTextValue();
				String description = sheet.getImmutableCellAt(6, row).getTextValue();
				String location = sheet.getImmutableCellAt(9, row).getTextValue();
				String stipendString = sheet.getImmutableCellAt(20, row).getTextValue();
				Float stipend = parseStipend(stipendString);
				String conventionRefString = sheet.getImmutableCellAt(1, row).getTextValue();
				Long conventionRef = Long.parseLong(conventionRefString);
				String experienceLevel = sheet.getImmutableCellAt(5, row).getTextValue();
				List<String> managers = Arrays.asList(sheet.getImmutableCellAt(23, row).getTextValue());
				boolean validated = parseValidated(sheet.getImmutableCellAt(2, row).getTextValue());
				Company company = parseCompany(row);
				Industry industry = parseIndustry(row);
				
				Internship internship = new Internship(beginDate, endDate, function, 
						description, location, stipend, conventionRef, experienceLevel, 
						managers, validated, null, company, industry);
				internships.add(internship);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		return internships;
	}
	
	private static Float parseStipend(String stipendString) {
		if (stipendString.contentEquals("")) return 0F;
		try {
			return Float.parseFloat(stipendString);
		} catch (NumberFormatException e) {
			return -1F;
		}
	}

	private static boolean parseValidated(String validated) {
		return validated.contains("Valid");
	}

	private static int parseDay(String day) {
		return (int) (Long.parseLong(day) % 100L);
	}
	
	private static Month parseMonth(String month) {
		switch(month.toLowerCase()) {
			case "janvier": return Month.JANUARY;
			case "février": return Month.FEBRUARY;
			case "mars": return Month.MARCH;
			case "avril": return Month.APRIL;
			case "mai": return Month.MAY;
			case "juin": return Month.JUNE;
			case "juillet": return Month.JULY;
			case "août": return Month.AUGUST;
			case "septembre": return Month.SEPTEMBER;
			case "octobre": return Month.OCTOBER;
			case "novembre": return Month.NOVEMBER;
			case "décembre": return Month.DECEMBER;
			default: return null;
		}
		
	}
	
	private static LocalDate parseDate(String beginDateUnformattedString) {
		String[] dateStringArray = beginDateUnformattedString.split(" ");
		int day = parseDay(dateStringArray[0]);
		Month month = parseMonth(dateStringArray[1]);
		int year = Integer.parseInt(dateStringArray[2]);
		
		LocalDate date = LocalDate.of(year, month, day);
		return date;
	}

	public static void main(String[] args) throws IOException {
		getInternships().forEach(System.out::println);
	}
}
