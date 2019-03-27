package camera;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;


public class PictureRecognition {

	private String resultat;
	private float score;

	private static String apiKey  = "5M-oAGxvmS-bXYiCLC1fTEiRwESXNXBpSjP_AMSAc_7m";//-GiCpHMUwTLgpV3mfnluH83u2r9yhbiNhWNLiAU_vzbb";

	public PictureRecognition(File file){
		
		IamOptions options = new IamOptions.Builder()
		.apiKey(apiKey)
		.build();

		VisualRecognition service = new VisualRecognition("2018-03-19", options);

		InputStream imagesStream;
		try {
			imagesStream = new FileInputStream(file);

			ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
			.imagesFile(imagesStream)
			.imagesFilename(file.getName())
			.threshold((float) 0.6)
			.classifierIds(Arrays.asList("fruitsModel_1473243807"))
			.build();

			ClassifiedImages result = service.classify(classifyOptions).execute();
			System.out.println(result);
			String str_result = ""+result;
			int start = 10 + str_result.indexOf("\"class\": ");
			if ( str_result.indexOf("\"class\": ")  >0  ) {
				int end = str_result.indexOf("," ,start) -1;
				resultat = str_result.substring(start, end);
				start =  str_result.indexOf("\"score\": ");
				end = str_result.indexOf("}",start+1) -1 ;
				System.out.println("score (string) =<"+str_result.substring(start+10 , end).trim()+">");
				score = Float.parseFloat(str_result.substring(start+9, end).trim());
			} else {
				resultat = "";
			}
			System.out.println();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	

	public String getResultat() {
		return resultat;
	}



	public float getScore() {
		return score;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PictureRecognition pr = new PictureRecognition(new File("./picture.png"));
		System.out.println("resultat=<"+pr.getResultat()+">");;
	}

}
