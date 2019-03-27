package javafx.application;

import java.util.HashMap;



public class FruitsDB {
	
	private String qty;
	private String price;
	
	private HashMap<String, String> hmap_Qty;
	private HashMap<String, Integer> hmap_Price;
	
	public FruitsDB() {
		
		  /* This is how to declare HashMap */
	      hmap_Price = new HashMap<String , Integer>();
	      hmap_Qty = new HashMap<String , String>();

	      
	      /*Adding elements to HashMap
	       * descriptors must be in LOWERCASE
	       * */
	      
	      String desc;
	      
	      desc = "citrons verts"		;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc, 1190);
	      desc = "citrons"     			;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  249);
	      desc = "grenadilles"			;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc, 2290);	     
	      desc = "kiwi"					;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  638);	
	      desc = "noix"					;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  898);	
	      desc = "noisettes"			;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  990);	
	      desc = "bananes"				;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  159);	
	      desc = "clementines"			;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  324);	
	      desc = "fraises charlotte"	;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  590);	
	      desc = "fraises clery"		;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc, 1740);	
	      desc = "fraises gariguette"	;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc, 1245);	
	      desc = "fraises Carpentras"	;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc, 1195);	
	      desc = "letchi"				;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc, 1690);
	      desc = "mangoustans"			;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc, 1550);
	      desc = "mangues"				;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc, 1390);
	      desc = "rambutans"			;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc, 1890);
	      desc = "oranges"				;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  380);	
	      desc = "pamplemousses"		;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  290);	
	      desc = "rambutan"				;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc, 1890);
	      desc = "pitaya jaune"			;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc, 1845);	
	      desc = "pitaya rouge"			;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc, 1790);	
	      desc = "poires"				;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  390);	
	      desc = "pommes"				;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  300);
	      desc = "pommes red"			;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  295);
	      desc = "pommes elstar"		;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  160);
	      desc = "pommes fuji"			;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  490);
	      desc = "pommes golden"		;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  249);
	      desc = "pommes granny smith"	;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  180);
	      desc = "pommes pink lady"		;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  490);
	      desc = "pommes royal gala"	;hmap_Qty.put(desc, "1kg")	;hmap_Price.put(desc,  399);
	      
	      
	}

	public String getQty(String descriptor) {
		
		if ( hmap_Qty.containsKey(descriptor.toLowerCase()) ) 
			return hmap_Qty.get(descriptor.toLowerCase());
		else 
			return "";
	}


	public int getPrice(String descriptor) {
		
		if ( hmap_Price.get(descriptor.toLowerCase()) !=null ) 
			return Integer.parseInt(""+hmap_Price.get(descriptor.toLowerCase()));
		else 
			return 0;
		
	}

	public static void main(String[] args) throws InterruptedException {

		FruitsDB fruits = new FruitsDB();
		System.out.println(fruits.hmap_Price.get("KIWi"));
		System.out.println(fruits.hmap_Qty.get("banane"));
		System.out.println(fruits.hmap_Qty.get("fraises carpentras"));
		
	}
	
}
