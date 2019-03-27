package speech;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

/**
 * @author lrochet
 * copyright IBM france 2018
 */
public class Text2Speech implements Runnable{

	private String audioFile = "speech.wav";
	
	private  String texte ;
	private String speech_language;
	
	public Text2Speech(String texte,String out_language) {
		super();
		this.texte=texte;
		System.out.println("texte="+texte);
		System.out.println("out_language="+out_language);
		
		if (out_language.equalsIgnoreCase("fr")) {
			speech_language="fr-FR_ReneeVoice";
		}else if (out_language.equalsIgnoreCase("pt")) {
			speech_language="pt-BR_IsabelaVoice"; 			
		}else if (out_language.equalsIgnoreCase("de")){
			speech_language="de-DE_BirgitVoice";
		}else if (out_language.equalsIgnoreCase("es")){
			speech_language="es-LA_SofiaVoice";
		} else {
			speech_language="en-US_AllisonVoice";
		}
		
	}
	
	
	
	
	public String getAudioFile() {
		return audioFile;
	}




	public void setAudioFile(String audioFile) {
		this.audioFile = audioFile;
	}




	




	




	public String getTexte() {
		return texte;
	}




	public void setTexte(String texte) {
		this.texte = texte;
	}




	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Text2Speech t2s = new Text2Speech("citrons", "fr");
		t2s.run();
	}
		@Override
		public void run() {	
		
		
		/**
		 * convert text to sound file using watson text-to-speech api
		 */
		IamOptions options = new IamOptions.Builder()
	    .apiKey("KKC3J5od7kiWWKQom20C6Gjz8Ls2vw_-pQEJwZQrRTpG")
	    .build();

		TextToSpeech textToSpeech = new TextToSpeech(options);
		textToSpeech.setEndPoint("https://stream.watsonplatform.net/text-to-speech/api/");
		
		try {
			  SynthesizeOptions synthesizeOptions =
			    new SynthesizeOptions.Builder()
			      .text(texte)
			      .accept("audio/wav")			     
			      .voice(speech_language)
			      .build();

			  InputStream inputStream =
			    textToSpeech.synthesize(synthesizeOptions).execute();
			  InputStream in = WaveUtils.reWriteWaveHeader(inputStream);

			  OutputStream out = new FileOutputStream(audioFile);
			  byte[] buffer = new byte[1024];
			  int length;
			  while ((length = in.read(buffer)) > 0) {
			    out.write(buffer, 0, length);
			  }

			  out.close();
			  in.close();
			  inputStream.close();		 		  
			  
		} catch (IOException e) {
			  e.printStackTrace();
		}
		
		 /**
		  * play generated sound file
		  */
		
		try {
			PlaySound.playSound(audioFile);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		  
	}

	

}
