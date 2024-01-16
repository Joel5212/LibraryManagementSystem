package helpers;

import java.math.BigDecimal;

public class NumberHelper{
	public static boolean isNumeric(String str)
	{
		try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	public static boolean isDecimal(String str)
	{
		try {
			new BigDecimal(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
}