package speech;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslationResult;
import com.ibm.watson.developer_cloud.language_translator.v3.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

/**
 * @author lrochet
 * copyright IBM france 2018
 */
public class TranslateText implements Runnable{

	private static String apiKey       = "VE-JHD3goYosiDae5bpu7btV08mHg6cbFu_u2dTVVH-0";
	private static String endpoint_url = "https://gateway.watsonplatform.net/language-translator/api";
	private static String apiVersion   = "2018-05-01" ;
	private String model = "fr-de" ;
	
	private  String in_text ;
	private  String out_text ;

	public TranslateText(String in_text, String model) {
		super();	
		this.in_text=in_text;
		this.model=model;
	}


	public  String getModel() {
		return model;
	}


	public  void setModel(String model) {
		this.model = model;
	}




	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TranslateText t2s = new TranslateText("Salut, ça te dit de parler allemand ?","fr-en");
		t2s.run();
	}
	
	@Override
	public void run() {	


		/**
		 * convert text to sound file using watson text-to-speech api
		 */
		IamOptions options = new IamOptions.Builder()
		.apiKey(apiKey)
		.build();


		LanguageTranslator languageTranslator = new LanguageTranslator(apiVersion, options);

		languageTranslator.setEndPoint(endpoint_url);


		TranslateOptions translateOptions = new TranslateOptions.Builder()
		.addText(in_text)
		.modelId(model)
		.build();

		TranslationResult result = languageTranslator.translate(translateOptions)
				.execute();
		out_text = result.getTranslations().get(0).getTranslationOutput();
		System.out.println(out_text);	

	}


	public String getOut_text() {
		return out_text;
	}



}
