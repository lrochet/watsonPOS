package javafx.application;

import java.awt.Dimension;
import java.awt.im.InputContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.javafx.geom.Rectangle;

import camera.PictureRecognition;
import camera.TakeWebCamSnapshot;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import speech.Text2Speech;
import speech.TranslateText;




public class GuiController implements Initializable {

	Logger LOG = LoggerFactory.getLogger(Webcam.class);

	/** Multi-format barcode reader */
	private MultiFormatReader barcodeReader;

	private BufferedImage grabbedImage;
	private Webcam selWebCam = null;
	private boolean stopCamera = false;
	private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();

	@FXML Button btn_cam;
	@FXML Button btn_cam_stop;
	@FXML Button btn_cam_start;

	@FXML
	private TextField displayInput;

	@FXML
	private TextField displayTotal;

	@FXML
	private TextField dateHeure;  

	@FXML
	private ImageView imageview;  

	@FXML
	private TextArea ticket;

	private int tot_TTC_cts;

	private static FruitsDB fruits= new FruitsDB();
	private static ItemcodeDB items= new ItemcodeDB();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {



		//
		barcodeReader = new MultiFormatReader();
		Map<DecodeHintType, Object> hints = new HashMap<>();
		List<BarcodeFormat> formats = new ArrayList<>();
		//hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
		formats.add(BarcodeFormat.EAN_13);
		//formats.add(BarcodeFormat.CODE_128);
		hints.put(DecodeHintType.PURE_BARCODE, formats);
		barcodeReader.setHints(hints);

		//date heure

		String pattern = "dd/MM/yyyy HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		dateHeure.setText(date);

		//Total transaction

		tot_TTC_cts=0;
		displayTotal.setText(StringTools.getFormattedAmount(tot_TTC_cts));

		//manage webcam
		initializeWebCam();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				setImageViewSize();
			}
		});
	}

	protected void setImageViewSize() {

		imageview.setPreserveRatio(true);
		//imageview.setFitWidth(600);
		//imageview.setFitWidth(320);
		//imageview.setFitHeight(280);

	}

	protected void initializeWebCam() {

		Task<Void> webCamInit = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				if(selWebCam == null)
				{
					selWebCam = Webcam.getWebcams().get(0);
					selWebCam.setCustomViewSizes(new Dimension[] { WebcamResolution.HD720.getSize() }); // register custom size
					selWebCam.setViewSize(WebcamResolution.HD720.getSize()); // set custom size					
					selWebCam.open();
				}else
				{
					closeCamera();
					selWebCam = Webcam.getWebcams().get(0);
					selWebCam.setCustomViewSizes(new Dimension[] { WebcamResolution.HD720.getSize() }); // register custom size
					selWebCam.setViewSize(WebcamResolution.HD720.getSize()); // set custom size					
					selWebCam.open();

				}



				startWebCamStream();
				return null;
			}

		};

		new Thread(webCamInit).start();

	}

	protected void startWebCamStream() {

		stopCamera  = false;
		Task<Void> task = new Task<Void>() {


			@Override
			protected Void call() throws Exception {

				while (!stopCamera) {
					try {
						if ((grabbedImage = selWebCam.getImage()) != null) {

							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									final Image mainimage = SwingFXUtils
											.toFXImage(grabbedImage, null);
									imageProperty.set(mainimage);
								}
							});

							grabbedImage.flush();

						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {

					}
				}
				return null;

			}

		};

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		imageview.imageProperty().bind(imageProperty);

	}

	private void closeCamera() {
		if(selWebCam != null)
		{		
			selWebCam.close();
			for (int i=0;i<selWebCam.getWebcamListeners().length;i++) {				
				selWebCam.removeWebcamListener(selWebCam.getWebcamListeners()[i]); 			
			}

		}
	}

	public void stopCamera(ActionEvent event) {
		stopCamera = true;
		btn_cam_start.setDisable(false);
		btn_cam_stop.setDisable(true);
	}

	public void startCamera(ActionEvent event) {
		stopCamera = false;
		startWebCamStream();
		btn_cam_start.setDisable(true);
		btn_cam_stop.setDisable(false);

	}

	public void disposeCamera(ActionEvent event) {
		stopCamera = true;
		closeCamera();
		//Webcam.shutdown();

	}

	@FXML
	protected void takeSnapshot() { 

		System.out.println("Capturing webcam image !...");
		displayInput.setText("");

		try {
			new File("picture.png").delete();
		} catch (Exception e) {

		}

		BufferedImage image =null;
		try {
			btn_cam.setDisable(false);
			image = selWebCam.getImage();
			ImageIO.write(image, "PNG", new File("picture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		} 	

		
		String ean = null;

		if (image != null) {
			
			//try to detect codebar
			
			String filePath = "picture.png";
			String charset = "UTF-8"; // or "ISO-8859-1"
			Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

			try {
				System.out.println("test if image is a codebar...");

				ean = QRCode.readQRCode(filePath, charset, hintMap);
				System.out.println("Data read ean: " + ean);
				
				ticket.appendText(StringTools.getItemLine(items.getDesc(ean),"  1",items.getPrice(ean))); 
				tot_TTC_cts=tot_TTC_cts+items.getPrice(ean);
				displayTotal.setText(StringTools.getFormattedTotal(tot_TTC_cts));
				
			} catch (NotFoundException e) {						
				e.printStackTrace();
			} catch (FileNotFoundException e) {						
				e.printStackTrace();
			} catch (IOException e) {						
				e.printStackTrace();
			}					  

			//if it's not a codebar try to recognize a fruit image
			//display recognized fruit on the virtual ticket
			//compute total
			
			if (ean==null) {
				PictureRecognition pr = new PictureRecognition(new File("./picture.png"));	

				System.out.println("resultat=<"+pr.getResultat()+">");

				if (pr.getResultat().length()>0) {

					int price=0;
					String qty="";

					if ( fruits.getPrice(pr.getResultat()) > 0)  {

						InputContext context = InputContext.getInstance();
						String language = context.getLocale().toString().substring(0, 2);
						String model = "fr-" + language;
						System.out.println("language ="+language );  

						if(language.equalsIgnoreCase("fr")==false) {
							TranslateText translated = new TranslateText(pr.getResultat(),model);
							translated.run();
							System.out.println("translated text="+translated.getOut_text());  
							Text2Speech t2s = new Text2Speech(translated.getOut_text(), language);
							t2s.run();
						} else {
							Text2Speech t2s = new Text2Speech(pr.getResultat(), language);
							t2s.run();
						}
						price= fruits.getPrice(pr.getResultat().toLowerCase());
						System.out.println("price=<"+price+">");
						qty  = fruits.getQty(pr.getResultat().toLowerCase());
						System.out.println("qty=<"+qty+">");

					}

					ticket.appendText(StringTools.getItemLine(pr.getResultat(),qty,price)); 
					tot_TTC_cts=tot_TTC_cts+price;
					displayTotal.setText(StringTools.getFormattedTotal(tot_TTC_cts));
					displayInput.setText("visual recognition score : " + Float.toString(100*pr.getScore())+ "%");

				} else {
					displayInput.setText("Article non reconnu !"); 
				}
				btn_cam.setDisable(false);
			}
		}
	}

	/*@FXML
	public void onEnter(ActionEvent ae){
	   System.out.println("enter key clicked"); 
	   System.exit(0);
	}*/

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) {    	


		System.out.println("clicked "+((Button)(event.getSource())).getId());

		String button_id =((Button)(event.getSource())).getId();
		String str="";

		char c = button_id.charAt(1+button_id.indexOf("_"));

		if (button_id.length()==5 && Character.isDigit(c)) { //if numeric key btn_1,..btn_9
			str=""+c;
		} else  {

			if (button_id.equalsIgnoreCase("btn_dot")) {
				str=".";
			} else if (button_id.equalsIgnoreCase("btn_del")) {
				displayInput.setText(""); 
			} else if (button_id.equalsIgnoreCase("btn_00")) {
				str="00";
			} else if (button_id.equalsIgnoreCase("btn_back")) {
				String new_str = displayInput.getText().substring(0, displayInput.getText().length()-1);           
				displayInput.setText(new_str);
			}  else if (button_id.equalsIgnoreCase("btn_logOff")) {
				closeCamera();
				System.exit(0);
			} else if (button_id.equalsIgnoreCase("btn_x")) {
				float valeur = Integer.parseInt(displayInput.getText())/100;
				String new_str = "" + valeur;
				displayInput.setText(new_str);

			} else if (button_id.equalsIgnoreCase("btn_enter")) {
				if (items.getDesc(displayInput.getText()) != null ) {

					ticket.appendText(StringTools.getItemLine(items.getDesc(displayInput.getText()),"  1",items.getPrice(displayInput.getText()))); 
					tot_TTC_cts=tot_TTC_cts+items.getPrice(displayInput.getText());
					displayTotal.setText(StringTools.getFormattedTotal(tot_TTC_cts));
				} else {

				}        	  
				displayInput.setText("");
			}

		}   

		displayInput.appendText(str);        

	}
	@FXML
	private void handleOnKeyPressed(KeyEvent event)
	{
		System.out.println("Pressed key text: " + event.getText());
		System.out.println("Pressed key code: " + event.getCode());
		if (event.getCode().toString().equalsIgnoreCase("CONTROL")) {
			takeSnapshot();
		}

	}

}