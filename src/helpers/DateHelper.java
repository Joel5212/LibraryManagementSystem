package helpers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateHelper {
	public static String dateToYYYYMMddDate(Date date) {
		// Create a SimpleDateFormat object to format the date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		// Format the date as a string without the time
		String formattedDate = dateFormat.format(date);

		return formattedDate;
	}

	public static Date localDateToDate(LocalDate localDate) {
		// Convert LocalDate to String with the desired format
		String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		try {
			// Parse the formatted date String to a Date object
			return new SimpleDateFormat("yyyy-MM-dd").parse(formattedDate);
		} catch (ParseException e) {
			// Handle the ParseException if needed
			e.printStackTrace();
			return null;
		}
	}

	public static LocalDate dateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}