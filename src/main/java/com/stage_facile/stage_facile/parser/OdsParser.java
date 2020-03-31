package com.stage_facile.stage_facile.parser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import com.stage_facile.stage_facile.models.Company;
import com.stage_facile.stage_facile.models.Industry;
import com.stage_facile.stage_facile.models.Internship;
import com.stage_facile.stage_facile.models.User;
/**
 * Parseur du fichier .ods fourni en annexe du projet.
 * Ce fichier contient des informations relatives à 
 * des conventions de stages exportées depuis le site
 * IPro.
 * Cette classe a pour but d'extraire les données du fichier
 * pour en créer : des utilisateurs, des stages, des entreprises,
 * des secteurs d'activité.
 */
public class OdsParser {
	
	private static File iproFile = new File("./classes/StagesExport_1.ods");
//	private static File iproFile = new File("./src/main/resources/StagesExport_1.ods");

	private static Sheet sheet;

	public OdsParser() throws IOException {
		sheet = loadFirstSheet();
	}

	private static Sheet loadFirstSheet() throws IOException {
		return SpreadSheet.createFromFile(iproFile).getSheet(0);
	}
	/**
	 * Extraire le nom de chaque colonne du fichier .ods
	 * @return La liste des noms de colonnes sous forme de String.
	 * @throws IOException si le fichier ne peut pas être ouvert.
	 */
	public static List<String> getHeaders() throws IOException {
		sheet = loadFirstSheet();
		List<String> headers = new ArrayList<>();

		for (int col = 0; col < sheet.getColumnCount(); col++) {
			// map the value of each column in row 0 to corresponding column index
			String cellText = sheet.getImmutableCellAt(col, 0).getTextValue();
			if (col == 0 || col > 10 && cellText.equals(""))
				continue;
			headers.add(cellText);
		}

		return headers;
	}
	
	/**
	 * Ne garde que les caractères alphanumériques d'une string 
	 * (afin d'avoir une adresse mail bien formée.)
	 * (e.g : sanitize("^bactérie$") -> bactrie)
	 * @param str
	 * @return 
	 */
	public static String sanitize(String str) {
		return str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
	}

	/**
	 * Extrait les informations relatives à tous les étudiants
	 * présents dans le fichier en leur attribuant une adresse mail
	 * étudiant AMU.
	 * @return La liste des étudiants parsés en User sans mot de passe, date de naissance,
	 * sexe, ou rôle.
	 */
	public static List<User> getStudents() {
		List<User> users = new ArrayList<>();
		try {
			sheet = loadFirstSheet();
			for (int row = 1; row < sheet.getRowCount(); row++) {				
				User user = parseStudent(row);
				users.add(user);
			}

		} catch (IOException e) {
			System.err.println(e);
		}
		
		return users;

	}
	
	/**
	 * Extrait les informations relatives à un étudiant
	 * présent dans le fichier en lui attribuant une adresse mail
	 * étudiant AMU.
	 * @return Un étudiant parsé en User sans mot de passe, date de naissance,
	 * sexe, ou rôle.
	 */
	public static User parseStudent(int row) {
		String fullName = sheet.getImmutableCellAt(3, row).getTextValue();
		String firstName = parseFirstName(fullName);
		String lastName = parseLastName(fullName);
		Date birthDate = getRandomBirthDate();
		boolean gender = getRandomGender();
		String email = sanitize(firstName) + "." + sanitize(lastName) + "@etu.univ-amu.fr";
		
		return new User(email, "password", firstName, lastName, birthDate, gender);
	}

	/**
	 * Extrait le prénom d'un nom complet bien formé.
	 * @param fullName (ex: Charlie LEROUX)
	 * @return le prénom qui est en seconde position. (ex: Charlie)
	 */
	private static String parseFirstName(String fullName) {
		return fullName.split(" ")[1];
	}
	
	/**
	 * Extrait puis normalise la capitalisation le nom de famille
	 * d'un nom complet bien formé.
	 * @param fullName (ex: Charlie LEROUX)
	 * @return le prénom qui est en première position. (ex: Leroux)
	 */
	private static String parseLastName(String fullName) {
		String lastName = fullName.split(" ")[0];
		String capitalized = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

		return capitalized;
	}
	
	private static boolean getRandomGender() {
		Random random = new Random();
		return random.nextBoolean();
	}
	
	private static Date getRandomBirthDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Random random = new Random();
		int day = random.nextInt(29) + 1;
		int month = random.nextInt(12) + 1;
		int year = random.nextInt(10) + 1990;
		
		
		try {
			return formatter.parse(day + "-" + month + "-" + year);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Renvoie l'ensemble des noms de secteurs d'activités uniques présents dans le fichier.
	 */
	public static Set<String> getIndustries() {
		Set<String> industries = new HashSet<>();
		for (int row = 1; row < sheet.getRowCount(); row++) {
			String industry = sheet.getImmutableCellAt(8, row).getTextValue();
			if (!industry.trim().equals(""))
				industries.add(industry);

		}

		return industries;
	}

	/**
	 * Renvoie le secteur d'activités relatif à la ligne passée en paramètre.
	 * @param row ligne dans le fichier
	 * @return
	 */
	private static Industry parseIndustry(int row) {
		String industry = sheet.getImmutableCellAt(8, row).getTextValue();
		return new Industry(industry);

	}

	/**
	 * Extrait les informations relatives à toutes les entreprises
	 * présentes dans le fichier.
	 * @return La liste des entreprises
	 */
	public static List<Company> getCompanies() {
		List<Company> companies = new ArrayList<>();
		try {
			sheet = loadFirstSheet();

			for (int row = 1; row < sheet.getRowCount(); row++) {
				companies.add(parseCompany(row));
			}
		} catch (IOException e) {
			System.err.println(e);
		}

		return companies;
	}
	
	/**
	 * Extrait les informations relatives à une entreprise
	 * présente dans le fichier avec les champs suivants :
	 * nom, secteur d'activités, adresse, code postal, ville, pays.
	 * @return l'entreprise parsée en Company
	 */
	private static Company parseCompany(int row) throws IOException {
		String country = sheet.getImmutableCellAt(9, row).getTextValue();
		String companyName = sheet.getImmutableCellAt(10, row).getTextValue();
		String address = sheet.getImmutableCellAt(11, row).getTextValue();
		String postalCode = sheet.getImmutableCellAt(12, row).getTextValue();
		String city = sheet.getImmutableCellAt(13, row).getTextValue();
		Industry industry = parseIndustry(row);
		return new Company(companyName, industry, address, postalCode, city, country);

	}

	/**
	 * Extrait les informations relatives à l'ensemble des stages
	 * présente dans le fichier en renseignants les champs suivants :
	 * stagiaire concerné, date de début, date de fin, intitulé du stage, 
	 * description des activités confiées, lieu du stage, gratification/mois,
	 * référence de la convention, niveau académique, liste des gestionnaires
	 * de la convention, état de la convention (validée ou pas), entreprise
	 * du stage et secteur d'activités.
	 * @return Un stage parsé en Internship.
	 */
	public static List<Internship> getInternships() {
		List<Internship> internships = new ArrayList<>();
		try {
			sheet = loadFirstSheet();

			for (int row = 1; row < sheet.getRowCount(); row++) {
				User student = parseStudent(row);
				String beginDateUnformattedString = sheet.getImmutableCellAt(18, row).getTextValue();
				String endDateUnformattedString = sheet.getImmutableCellAt(19, row).getTextValue();
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
				

				Internship internship = new Internship(student, beginDate, endDate, function, description, location, stipend,
						conventionRef, experienceLevel, managers, validated, null, company, industry);
				internships.add(internship);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		return internships;
	}

	/**
	 * Extrait l'information relative à la gratification
	 * d'un stage et la parse en nombre décimal.
	 * @param stipendString le montant de la gratification
	 * sous forme de chaine de caractères.
	 * @return Le montant parsé en flottant si bien formé, 
	 * 0. si stipendString est vide, et -1. si stipendString
	 * est mal formé.
	 */
	private static Float parseStipend(String stipendString) {
		if (stipendString.contentEquals(""))
			return 0F;
		try {
			return Float.parseFloat(stipendString);
		} catch (NumberFormatException e) {
			return -1F;
		}
	}

	/**
	 * Parse les cellules du fichiers contenant la chaîne
	 * "Validée par un gestionnaire de conventions" si la
	 * convention est validée, ou "" sinon.
	 * @param validated chaîne de caractère présente dans la cellule.
	 * @return true si la cellule contient la sous-chaîne 'Valid'.
	 * false sinon.
	 */
	private static boolean parseValidated(String validated) {
		return validated.contains("Valid");
	}

	/**
	 * Les dates dans le fichiers contiennent des préfixes (ex:5184656802).
	 * Cette méthode vise à ne garder que les 2 derniers nombres
	 * @param day
	 */
	private static int parseDay(String day) {
		return (int) (Long.parseLong(day) % 100L);
	}

	/**
	 * Transforme le mois sous forme de chaîne en le numéro
	 * qui lui correspond.
	 * @param month
	 */
	private static Month parseMonth(String month) {
		switch (month.toLowerCase()) {
		case "janvier":
			return Month.JANUARY;
		case "février":
			return Month.FEBRUARY;
		case "mars":
			return Month.MARCH;
		case "avril":
			return Month.APRIL;
		case "mai":
			return Month.MAY;
		case "juin":
			return Month.JUNE;
		case "juillet":
			return Month.JULY;
		case "août":
			return Month.AUGUST;
		case "septembre":
			return Month.SEPTEMBER;
		case "octobre":
			return Month.OCTOBER;
		case "novembre":
			return Month.NOVEMBER;
		case "décembre":
			return Month.DECEMBER;
		default:
			return null;
		}

	}
	/**
	 * Parse la date en utilisant un parseur de jour, de mois et d'année.
	 * @param beginDateUnformattedString.
	 * @return la date formattée en LocalDate.
	 */
	private static LocalDate parseDate(String beginDateUnformattedString) {
		String[] dateStringArray = beginDateUnformattedString.split(" ");
		int day = parseDay(dateStringArray[0]);
		Month month = parseMonth(dateStringArray[1]);
		int year = Integer.parseInt(dateStringArray[2]);

		LocalDate date = LocalDate.of(year, month, day);
		return date;
	}

}
