package helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class LoanCalcHelper {
	public static Integer daysOverdue(Date dueDate) {
		LocalDate currentDate = LocalDate.now();

		Integer daysBetween = null;

		if (currentDate.isAfter(DateHelper.dateToLocalDate(dueDate))) {
			daysBetween = (int) ChronoUnit.DAYS.between(DateHelper.dateToLocalDate(dueDate), currentDate);
		}
		else
		{
			daysBetween = 0;
		}
		

		return daysBetween;
	}

	public static BigDecimal calcOverdueFine(Integer daysOverdue, BigDecimal dailyPriceOfItem) {
		
		BigDecimal loanFine = null;
		
		if(daysOverdue != 0)
		{
			BigDecimal overdueDailyPriceOfItem = dailyPriceOfItem.multiply(new BigDecimal(1.10)).setScale(2, RoundingMode.UP);
			loanFine = overdueDailyPriceOfItem.multiply(new BigDecimal(daysOverdue)).setScale(2, RoundingMode.UP);
			return loanFine;
		}
		else {
			loanFine = new BigDecimal(0);
		}
		return loanFine;
	}

	public static BigDecimal currentLoanPayment(Date dueDate, Date startDate, BigDecimal dailyPriceOfItem) {
		LocalDate currentDate = LocalDate.now();
		LocalDate startDateLD = DateHelper.dateToLocalDate(startDate);
		LocalDate dueDateLD = DateHelper.dateToLocalDate(dueDate);
		long daysNotOverdue = 0;
		long daysOverdue = 0;
		BigDecimal loanPayment = null;
		BigDecimal overdueFine = null;
		BigDecimal currentLoanPayment = null;
		
		if (!currentDate.isAfter(dueDateLD)) {
			daysNotOverdue = ChronoUnit.DAYS.between(startDateLD, currentDate) + 1;
			currentLoanPayment = dailyPriceOfItem.multiply(new BigDecimal(daysNotOverdue));
		} else {
			daysNotOverdue = ChronoUnit.DAYS.between(startDateLD, dueDateLD) + 1;
			daysOverdue = ChronoUnit.DAYS.between(dueDateLD, currentDate);
			loanPayment = dailyPriceOfItem.multiply(new BigDecimal(daysNotOverdue)).setScale(2, RoundingMode.UP);
			overdueFine = LoanCalcHelper.calcOverdueFine((int) daysOverdue, dailyPriceOfItem);
			currentLoanPayment = loanPayment.add(overdueFine).setScale(2, RoundingMode.UP);;
		}
		return currentLoanPayment;
	}

	public static Date getCurrentDate() {
		return DateHelper.localDateToDate(LocalDate.now());
	}

	public static boolean isMoreThanSixMonthsLater(LocalDate currentDate, LocalDate targetDate) {

		long monthsDifference = ChronoUnit.MONTHS.between(currentDate, targetDate);

		return monthsDifference > 6;
	}
}