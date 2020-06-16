// Sheriyar Shakeel m3u8 converter v1
package gui;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import classes.Downloader;
import classes.MotionPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class Home {

	private JFrame frame;
	private static Downloader toConvert = new Downloader();
	private static 	ArrayList<String> commandi = new ArrayList<String>();
	private JLabel path = new JLabel("PATH FILE");
	private JProgressBar progressBar = new JProgressBar();
	int i;
	private JLabel download = new JLabel("DOWNLOAD 0 OF 0");
	private JButton convertButton = new JButton("CONVERT");
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void writeOnLabel(String path1,JLabel path)
	{
		path.setText(path1);
		path.setVisible(true);
		path.setOpaque(true);
	}
	
	public Home() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setBounds(100, 100, 450, 239);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		path.setVisible(false);
		download.setForeground(Color.BLACK);
		convertButton.setEnabled(false);
		JPanel head = new MotionPanel(frame);		
		head.setBackground(Color.GRAY);
		head.setBounds(0, 0, 450, 64);
		frame.getContentPane().add(head);
		head.setLayout(null);
		
		JLabel label = new JLabel("m3u8 converter v1.0");
		label.setBounds(0, 11, 441, 58);
		head.add(label);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Montserrat", Font.BOLD, 40));
		label.setBackground(new Color(51, 0, 102));
		
		JLabel x = new JLabel("X");
		x.setFont(new Font("Montserrat", Font.BOLD, 20));
		x.setBackground(new Color(51, 0, 102));
		x.setBounds(432, 0, 18, 25);
		head.add(x);
		x.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		JPanel body = new MotionPanel(frame);
		body.setBackground(Color.DARK_GRAY);
		body.setBounds(0, 63, 450, 174);
		frame.getContentPane().add(body);
		body.setLayout(null);
		
		
		convertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				progressBar.setValue(0);
			    progressBar.setStringPainted(true);
				
				int progressbarValue = toConvert.LetturaCartella();// get all the files in the directory
				progressBar.setMaximum(100*progressbarValue);
				
				new Thread(new Runnable() {
				      public void run() {

							try {
								toConvert.addLinks();//add lisk in a arraylist
								Iterator addressesOfVideos = toConvert.linkOfFile.iterator();
								
								while(addressesOfVideos.hasNext()) //
						    	{
									toConvert.download((String) addressesOfVideos.next(),i);
									i++;
									SwingUtilities.invokeLater(new Runnable() {
							            public void run() {
							              progressBar.setValue(i*100);
							              String sup = "DOWNLOAD " + i + " OF "+ progressbarValue; 
							              writeOnLabel(sup, download);
							            }
							          });
						    	}
								
								JOptionPane.showMessageDialog(frame, "DOWNLOAD COMPLETED");
								frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								JOptionPane.showMessageDialog(frame, "ERROR!");
							} 
		
							
				      }
				    }).start();

			}
		});
		
		convertButton.setForeground(Color.BLACK);
		convertButton.setFont(new Font("Montserrat", Font.BOLD, 16));
		convertButton.setBackground(Color.GRAY);
		convertButton.setBounds(10, 111, 159, 35);
		body.add(convertButton);
		
		
		JButton btnOpenDirfile = new JButton("OPEN DIR");
		btnOpenDirfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("choosertitle");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			  
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			      toConvert.setFolderPath(chooser.getSelectedFile().toString() + "\\");

			      writeOnLabel(toConvert.getFolderPath(), path);
			      convertButton.setEnabled(true);
			    } else {
			      System.out.println("No Selection ");
			    }
			}
		});
		btnOpenDirfile.setForeground(Color.BLACK);
		btnOpenDirfile.setFont(new Font("Montserrat", Font.BOLD, 14));
		btnOpenDirfile.setBackground(Color.GRAY);
		btnOpenDirfile.setBounds(10, 62, 159, 35);
		body.add(btnOpenDirfile);
		
		path.setBackground(Color.DARK_GRAY);
		path.setFont(new Font("Montserrat", Font.BOLD, 12));
		path.setForeground(Color.BLACK);
		path.setBounds(10, 11, 430, 40);
		body.add(path);
		
		
		progressBar.setBounds(200, 62, 213, 35);
		body.add(progressBar);
		
		
		download.setFont(new Font("Montserrat", Font.BOLD, 14));
		download.setBackground(Color.DARK_GRAY);
		download.setBounds(200, 111, 240, 35);
		body.add(download);
		
	}
}
