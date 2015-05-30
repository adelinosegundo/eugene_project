package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextArea;

import models.Collection;
import models.Gene;
import models.Kind;
import models.Sample;

import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;

public class MainWindow {

	private JFrame frame;
	private JLabel statusLabel = null;
	private BufferedImage offImage = null, onImage = null;

	private Collection collection;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de texto .txt", "txt");
		fc.setFileFilter(filter);

		// Frame
		frame = new JFrame();
		frame.setBounds(100, 100, 753, 736);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel project = new JLabel("<html><span style='width:300px;font-size:20px;text-transform:uppercase;text-align:center'>EU<span style='font-weight:bold;'>GENE</span> PROJECT</span></html>", JLabel.CENTER);
		project.setBounds(267, 24, 237, 50);
		frame.getContentPane().add(project);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(6, 136, 739, 176);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		/*
		 * Status icon
		 */
		try {
			onImage = ImageIO.read(new File("resources/on.png"));
			offImage = ImageIO.read(new File("resources/off.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		statusLabel = new JLabel();
		statusLabel.setBounds(615, 100, 30, 30);
		statusLabel.setIcon(new ImageIcon(offImage));
		frame.getContentPane().add(statusLabel);
		
		/*
		 * TextAreas
		 */
		JTextArea console = new JTextArea();
		console.setBounds(6, 549, 741, 159);
		frame.getContentPane().add(console);
		
		// Welcome 
		console.append("Eugene says welcome to you!\n");
		console.append("Click 'Start' button to start.\n");
				
		/*
		 * Buttons
		 */
		JButton addSamples = new JButton("Import Samples");
		addSamples.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					System.out.println("You chose to open this file: " + file.getName());
					
			        String line = null;

					try {
			            FileReader fileReader = new FileReader(file);
			            BufferedReader bufferedReader = new BufferedReader(fileReader);
			            console.append("Reading file " + file.getName() + " ...\n");
			            
			            while((line = bufferedReader.readLine()) != null) {
			            	String geneMarker = line.split(" ")[0].contains("	") ? line.split("	")[0] : line.split(" ")[0]; // Avoiding tabs as separator
			            	
			                Scanner scanner = new Scanner(line);
			                Double expression;
			                int counter = 0;
			                
			                while (scanner.hasNext()) {
				                if (scanner.hasNextDouble()){
				                	expression = scanner.nextDouble();
				                	
				                	Gene gene = new Gene(geneMarker, expression);
				                	collection.getSamples().get(counter).addGene(gene);
				                	
				                	counter++;
				                } else {
				                	scanner.next();
				                }
			                }
			            }
			            
			            bufferedReader.close();
			            
			            addSamples.setEnabled(false);
			        } catch(FileNotFoundException ex) {
			            console.append("Unable to open file '" + file.getName() + "'");                
			        } catch(IOException ex) {
			            console.append("Error reading file '" + file.getName() + "'");
			        }  

					collection.print();				       
				}
			}
		});
		addSamples.setBounds(18, 92, 178, 64);
		panel.add(addSamples);
		
		JButton addGroups = new JButton("Import Groups");
		addGroups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					System.out.println("You chose to open this file: " + file.getName());
					
			        String line = null;

					try {
			            FileReader fileReader = new FileReader(file);
			            BufferedReader bufferedReader = new BufferedReader(fileReader);
			            console.append("Reading file " + file.getName() + " ...\n");
			            
			            while((line = bufferedReader.readLine()) != null) {
			                String[] sampleMarkerAndKind = line.split(" ");
			                
			                
			                System.out.println("'" + sampleMarkerAndKind[0] + "'");
			                System.out.println("'" + sampleMarkerAndKind[1] + "'");
			                
			                String sampleName = sampleMarkerAndKind[0];
			                String kindName = sampleMarkerAndKind[1];
			                
			                Kind kind = collection.addAndReturnKind(kindName);
			                
			                collection.addSample(sampleName, kind);
			                
			            }
			            bufferedReader.close();   
			            
			            console.append("Step 2 | Click 'Import Samples' button and upload the file with genes names and samples expressions information.\n");
			           
			            addSamples.setEnabled(true);
			            addGroups.setEnabled(false);
			        } catch(FileNotFoundException ex) {
			            console.append("Unable to open file '" + file.getName() + "'");                
			        } catch(IOException ex) {
			            console.append("Error reading file '" + file.getName() + "'");
			        }  
					
					collection.print();
				}
			}
		});
		addGroups.setBounds(18, 16, 178, 64);
		panel.add(addGroups);
		
		JButton start = new JButton("Start"); // start
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				collection = new Collection();
				
				addSamples.setEnabled(false);
				addGroups.setEnabled(true);
				
				statusLabel.setIcon(new ImageIcon(onImage));
				
				start.setText("Restart");
				
				console.append("Aplication started/restarted and database cleaned.\n");
				console.append("Step 1 | Click 'Import Groups' button and upload the file with samples markers and groups names information.\n");
			}
		});
		start.setBounds(652, 98, 93, 35);
		frame.getContentPane().add(start);
		
		
		
		// Start action
		

		// Disable buttons
		addSamples.setEnabled(false);
		addGroups.setEnabled(false);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Variance Test");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(256, 16, 141, 23);
		panel.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Student's paired t-test");
		rdbtnNewRadioButton_1.setBounds(266, 51, 178, 23);
		panel.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Wilcoxon signed rank test");
		rdbtnNewRadioButton_2.setBounds(266, 86, 205, 23);
		panel.add(rdbtnNewRadioButton_2);
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("New radio button");
		rdbtnNewRadioButton_3.setBounds(266, 121, 141, 23);
		panel.add(rdbtnNewRadioButton_3);

		

		
	}
}
