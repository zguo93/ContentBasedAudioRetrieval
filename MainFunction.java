import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFunction {

	private JFrame frame;
	
	
	InputAndMatch main_function;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFunction window = new MainFunction();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFunction() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// 5 means points within 2*0.5/5 sec will be regarded as one single time.
		main_function=new InputAndMatch(5);
		
		JLabel Title = new JLabel("Content Based Audio Retrieval"
				+ " (WAV only)");
		Title.setBounds(98, 10, 270, 48);
		frame.getContentPane().add(Title);
		
		
		JLabel Display = new JLabel("");
		Display.setBounds(117, 198, 194, 54);
		frame.getContentPane().add(Display);
		
		JLabel lblSuccessful = new JLabel("Successful");
		lblSuccessful.setBounds(179, 68, 72, 15);
		lblSuccessful.setVisible(false);
		frame.getContentPane().add(lblSuccessful);
		
		
		JButton btnNewButton = new JButton("Add Music");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblSuccessful.setVisible(false);
				ReadAudio audio=new ReadAudio();
				int[] s=new int[audio.getLength()];
				audio.getData(s);
				String name=audio.name;
				
				Spectrogram test=new Spectrogram(s.length,s);
				double[][] d=test.getArray();
				int[][] max=test.findMax();
				

				main_function.points2hash(d.length, max, name);
				lblSuccessful.setVisible(true);
				
			}
		});
		btnNewButton.setBounds(163, 103, 93, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnSelectClipAnd = new JButton("Select Clip and Match");
		btnSelectClipAnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				

				ReadAudio audio=new ReadAudio();
				int[] s=new int[audio.getLength()];
				audio.getData(s);
				
				Spectrogram test=new Spectrogram(s.length,s);
				double[][] d=test.getArray();
				int[][] max=test.findMax();
				
				String bestmatch=main_function.match(max.length, max);
				Display.setText("best match is "+bestmatch);

			}
		});
		btnSelectClipAnd.setBounds(117, 153, 194, 23);
		frame.getContentPane().add(btnSelectClipAnd);
		
		
	}
}
