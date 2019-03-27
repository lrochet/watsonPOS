package javafx.application;

import java.awt.im.InputContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class StringTools {
	
	public StringTools () {
		
	}
	public static void main(String[] args) {
		InputContext context = InputContext.getInstance();  
        System.out.println(context.getLocale().toString().substring(0, 2));  
		final Locale defaultLocale = Locale.getDefault();
	    System.out.println("  default.locale.language = " +defaultLocale.getLanguage());
	    System.out.println("  default.locale.country = " + defaultLocale.getCountry());
	    System.out.println("  default.locale.display.language = " + defaultLocale.getDisplayLanguage());
	    System.out.println("  default.locale.display.country = " + defaultLocale.getDisplayCountry());
	  
		/*String chaine = "croque monsieur";
		System.out.println("resultat=<"+padRight(chaine,20)+">");
		System.out.println("resultat=<"+padLeft(chaine,20)+">");
		System.out.println(getFormattedAmount(120050));
		*/
	}
	
	public static String padRight(String s, int n) {
	     return String.format("%-" + n + "s", s);  
	}

	public static String padLeft(String s, int n) {
	    return String.format("%" + n + "s", s);  
	}
	
	 /**
     * Returns the amount in cents as formatted amount
     *
     * @param amount
     * @return
     */
    public static String getFormattedAmount(int amount) {
        int cents = amount % 100;
        int euros = (amount - cents) / 100;

        String camount;
        
        if (cents <= 9) {
            camount = 0 + "" + cents;
        } else {
            camount = "" + cents;
        }
        
       
        return ""+ euros + "." + cents;

    }
    
    public static String getItemLine(String desc, String qty, int amount) {    	
    	
    	
    	return StringTools.padRight(desc,25) 						+ " "  + 
		StringTools.padLeft(qty,6)              					+ " "  +
		StringTools.padLeft(getFormattedAmount(amount),8) 			+ " €" +
		"\n\r";
    }
    
    public static String getFormattedTotal(int amount) {
    	return StringTools.padRight("TOTAL TTC",25) 						+ " "  + 
		StringTools.padLeft(" ",6)              					+ " "  +
		StringTools.padLeft(getFormattedAmount(amount),8) 			+ " €" ;		
    }
	


}
