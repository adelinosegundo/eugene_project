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
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class MainWindow {

	private JFrame frame;
	private JLabel statusLabel = null;

	private JButton addSamples = null;
	private JButton addGroups = null;
	private JButton start = null;
	private JButton JButton executeTestButton = null;

	private JTextArea console = null;
	
	private BufferedImage offImage = null, onImage = null;
	final JFileChooser fc;

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
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de texto .txt", "txt");
		fc.setFileFilter(filter);

		initializeFrameAndPanels();
		initialize();
	}

	/**
	 * Initialize IFrame
	 */
	public void initializeComponents() {
		// Frames
		frame = new JFrame();
		frame.setBounds(100, 100, 753, 736);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Panels
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(6, 136, 739, 176);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		// Images
		try {
			onImage = ImageIO.read(new File("resources/on.png"));
			offImage = ImageIO.read(new File("resources/off.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Separators
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBackground(Color.GRAY);
		separator.setForeground(Color.GRAY);
		separator.setBounds(224, 16, 5, 145);
		panel.add(separator);

		// TextAreas
		console = new JTextArea();
		console.setBounds(6, 549, 741, 159);
		frame.getContentPane().add(console);

		// Labels
		JLabel project = new JLabel(
			"<html><span style='width:300px;font-size:20px;text-transform:uppercase;text-align:center'>EU<span style='font-weight:bold;'>GENE</span> PROJECT</span></html>", JLabel.CENTER);
		project.setBounds(267, 24, 237, 50);
		frame.getContentPane().add(project);

		statusLabel = new JLabel();
		statusLabel.setBounds(615, 100, 30, 30);
		statusLabel.setIcon(new ImageIcon(offImage));
		frame.getContentPane().add(statusLabel);

		JLabel lblSamples = new JLabel("Samples:");
		lblSamples.setBounds(513, 43, 61, 16);
		panel.add(lblSamples);
		
		JLabel lblSamplesValue = new JLabel("0");
		lblSamplesValue.setBounds(609, 43, 61, 16);
		panel.add(lblSamplesValue);
		
		JLabel lblValidGenes = new JLabel("Valid Genes:");
		lblValidGenes.setBounds(513, 93, 84, 16);
		panel.add(lblValidGenes);
		
		JLabel lblExpressionsValue = new JLabel("0");
		lblExpressionsValue.setBounds(609, 68, 61, 16);
		panel.add(lblExpressionsValue);
		
		JLabel lblExpressions = new JLabel("Expressions:");
		lblExpressions.setBounds(513, 68, 80, 16);
		panel.add(lblExpressions);
		
		JLabel lvlInvalidGenes = new JLabel("Invalid Genes:");
		lvlInvalidGenes.setBounds(513, 118, 97, 16);
		panel.add(lvlInvalidGenes);
		
		JLabel lblValidGenesValue = new JLabel("0");
		lblValidGenesValue.setBounds(609, 93, 61, 16);
		panel.add(lblValidGenesValue);
		
		JLabel lblInvalidGenesValue = new JLabel("0");
		lblInvalidGenesValue.setBounds(609, 118, 61, 16);
		panel.add(lblInvalidGenesValue);
		
		JLabel lblGroups = new JLabel("Groups:");
		lblGroups.setBounds(513, 143, 61, 16);
		panel.add(lblGroups);
		
		JLabel lblGroupsNames = new JLabel("none");
		lblGroupsNames.setBounds(609, 143, 112, 16);
		panel.add(lblGroupsNames);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(563, 16, 61, 16);
		panel.add(lblStatus);

		// Radio Buttons
		JRadioButton varianceTestButton = new JRadioButton("Variance Test");
		varianceTestButton.setSelected(true);
		varianceTestButton.setBounds(256, 20, 141, 23);
		panel.add(varianceTestButton);
		
		JRadioButton tTestStudentButton = new JRadioButton("Student's paired t-test");
		tTestStudentButton.setBounds(256, 45, 178, 23);
		panel.add(tTestStudentButton);
		
		JRadioButton wilcoxonTestButton = new JRadioButton("Wilcoxon signed rank test");
		wilcoxonTestButton.setBounds(256, 70, 205, 23);
		panel.add(wilcoxonTestButton);
		
		JRadioButton chiSquareTestButton = new JRadioButton("Chi Square Test");
		chiSquareTestButton.setBounds(256, 95, 141, 23);
		panel.add(chiSquareTestButton);
		
		// Buttons
		executeTestButton = new JButton("Execute Test");
		executeTestButton.setBounds(260, 125, 150, 35);
		panel.add(executeTestButton);

		addSamples = new JButton("Import Samples");
		addSamples.setBounds(18, 95, 178, 64);
		panel.add(addSamples);

		addGroups = new JButton("Import Groups");
		addGroups.setBounds(18, 16, 178, 64);
		panel.add(addGroups);

		start = new JButton("Start");
		start.setBounds(652, 98, 93, 35);
		frame.getContentPane().add(start);
		
		

	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addSamples.setEnabled(false);
		addGroups.setEnabled(false);

		console.append("Eugene says welcome to you!\n");
		console.append("Click 'Start' button to start.\n");
		
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
		
	}
}
