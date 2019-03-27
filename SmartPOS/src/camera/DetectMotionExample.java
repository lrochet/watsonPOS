package camera;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamPanel;



/**
 * Detect motion.
 * 
 * @author Lionel Rochet IBM France
 */
public class DetectMotionExample extends JFrame implements Runnable {

	private static final long serialVersionUID = -585739158170333370L;

	private static final int INTERVAL = 300; // ms

	private ImageIcon motion = null;
	private ImageIcon nothing = null;
	private JLabel label = null;

	private Webcam webcam = Webcam.getDefault();

	private int pixelThreshold = 19;
	private double areaThreshold = 12.0;
	



	public DetectMotionExample() {

		try {
			motion  = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/CloudWait.gif")));
			nothing = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/blank.gif")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		label = new JLabel(nothing);

		Thread updater = new Thread(this, "updater-thread");
		updater.setDaemon(true);
		updater.start();

		setTitle("Motion Detector");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		webcam.setViewSize(new Dimension(640, 480));

		WebcamPanel panel = new WebcamPanel(webcam);

		add(panel);
		add(label);

		pack();
		setVisible(true);
	}

	public static void main(String[] args) throws InterruptedException {

		new DetectMotionExample();

	}

	public void run() {


		WebcamMotionDetector detector = new WebcamMotionDetector(webcam, pixelThreshold , areaThreshold ,INTERVAL);
		boolean done =false;

		detector.start();

		while (!done) {

			Icon icon = label.getIcon();
			if (detector.isMotion()) {
				if (icon != motion) {
					label.setIcon(motion);
				}
				try {
					File f = new File(String.format("picture.png"));
					ImageIO.write(webcam.getImage(), "PNG", new File(String.format("picture.png")));
					new PictureRecognition(f);

				} catch (IOException e) {					
					e.printStackTrace();
				}

			} else {

				if (icon != nothing) {
					label.setIcon(nothing);
				}
			}

			try {
				Thread.sleep(INTERVAL * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}