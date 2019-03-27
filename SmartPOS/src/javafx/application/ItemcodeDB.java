package javafx.application;

import java.util.HashMap;



public class ItemcodeDB {
	
	private String itemcode;
	private String desc;
	private String price;
	
	private HashMap<String, String> hmap_desc;
	private HashMap<String, Integer> hmap_Price;
	
	public ItemcodeDB() {
		
		  /* declare HashMap */
	      hmap_Price = new HashMap<String , Integer>();
	      hmap_desc = new HashMap<String , String>();

	      
	      /*Adding elements to HashMap */
	      itemcode = "1"			    ;hmap_desc.put(itemcode, "Champagne V.Cliquot 70cl")	;hmap_Price.put(itemcode,  3220);
	      itemcode = "2"			    ;hmap_desc.put(itemcode, "panier tomates cerises")		;hmap_Price.put(itemcode,   420);
	      itemcode = "123456"		    ;hmap_desc.put(itemcode, "barquette fraises 500g")		;hmap_Price.put(itemcode,   750);
	      itemcode = "210000"     		;hmap_desc.put(itemcode, "Presse Geo mag")	 			;hmap_Price.put(itemcode,   550);
	      itemcode = "123456789012"		;hmap_desc.put(itemcode, "Nutella 1kg 85g")				;hmap_Price.put(itemcode,   890);	     
	      itemcode = "3250390060222" 	;hmap_desc.put(itemcode, "bloc domedia creative x50")	;hmap_Price.put(itemcode,   270);
	     
	      
	}

	public String getDesc(String itemcode) {		
		
			return hmap_desc.get(itemcode);
	}


	public int getPrice(String itemcode) {
		
		if ( hmap_Price.get(itemcode) !=null ) 
			return Integer.parseInt(""+hmap_Price.get(itemcode));
		else 
			return 0;
		
	}

	public static void main(String[] args) throws InterruptedException {

		ItemcodeDB fruits = new ItemcodeDB();
		System.out.println(fruits.hmap_Price.get("123456"));
		System.out.println(fruits.hmap_desc.get("123456"));
		
	}
	
}
