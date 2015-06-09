package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
import models.Group;
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

import controller.GeneController;
import util.gene.signature.tests.GeneSignatureTestFacade;

public class MainWindow {

	private JFrame frame;
	
	private JLabel statusLabel = null;

	private JTextArea console = null;
	
	private JButton addSamples = null;
	private JButton addGroups = null;
	private JButton start = null;
	private JButton executeTestButton = null;

	private ButtonGroup testButtonGroup = null;

	private JRadioButton varianceTestRadioButton = null;
	private JRadioButton tTestStudentRadioButton = null;
	private JRadioButton wilcoxonTestRadioButton = null;
	private JRadioButton kolmogorovTestRadioButton = null;
	
	private JLabel lblSamples = null;
	private JLabel lblSamplesQuantity = null;
	private JLabel lblValidGenes = null;
	private JLabel lblExpressionsQuantity = null;
	private JLabel lblExpressions = null;
	private JLabel lvlInvalidGenes = null;
	private JLabel lblValidGenesQuantity = null;
	private JLabel lblInvalidGenesQuantity = null;
	private JLabel lblGroups = null;
	private JLabel lblGroupsNames = null;
	private JLabel lblStatus = null;

	private BufferedImage offImage = null, onImage = null;
	
	private final JFileChooser fc;

	private Collection collection;
	private static GeneController geneController;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		geneController = new GeneController();
		
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

		initializeComponents();
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

		lblSamples = new JLabel("Samples:");
		lblSamples.setBounds(512, 33, 61, 16);
		panel.add(lblSamples);
		
		lblSamplesQuantity = new JLabel("0");
		lblSamplesQuantity.setBounds(608, 33, 61, 16);
		panel.add(lblSamplesQuantity);
		
		lblValidGenes = new JLabel("Valid Genes:");
		lblValidGenes.setBounds(512, 83, 84, 16);
		panel.add(lblValidGenes);
		
		lblExpressionsQuantity = new JLabel("0");
		lblExpressionsQuantity.setBounds(608, 58, 61, 16);
		panel.add(lblExpressionsQuantity);
		
		lblExpressions = new JLabel("Expressions:");
		lblExpressions.setBounds(512, 58, 80, 16);
		panel.add(lblExpressions);
		
		lvlInvalidGenes = new JLabel("Invalid Genes:");
		lvlInvalidGenes.setBounds(512, 108, 97, 16);
		panel.add(lvlInvalidGenes);
		
		lblValidGenesQuantity = new JLabel("0");
		lblValidGenesQuantity.setBounds(608, 83, 61, 16);
		panel.add(lblValidGenesQuantity);
		
		lblInvalidGenesQuantity = new JLabel("0");
		lblInvalidGenesQuantity.setBounds(608, 108, 61, 16);
		panel.add(lblInvalidGenesQuantity);
		
		lblGroups = new JLabel("Groups:");
		lblGroups.setBounds(512, 133, 61, 16);
		panel.add(lblGroups);
		
		lblGroupsNames = new JLabel("none");
		lblGroupsNames.setBounds(608, 133, 112, 16);
		panel.add(lblGroupsNames);
		
		lblStatus = new JLabel("Status");
		lblStatus.setBounds(560, 10, 61, 16);
		panel.add(lblStatus);

		// Radio Buttons
		varianceTestRadioButton = new JRadioButton("Variance Test");
		varianceTestRadioButton.setSelected(true);
		varianceTestRadioButton.setBounds(256, 20, 141, 23);
		panel.add(varianceTestRadioButton);
		
		tTestStudentRadioButton = new JRadioButton("Student's paired t-test");
		tTestStudentRadioButton.setBounds(256, 45, 178, 23);
		panel.add(tTestStudentRadioButton);
		
		wilcoxonTestRadioButton = new JRadioButton("Wilcoxon signed rank test");
		wilcoxonTestRadioButton.setBounds(256, 70, 205, 23);
		panel.add(wilcoxonTestRadioButton);
		
		kolmogorovTestRadioButton = new JRadioButton("Kolmogorov Smirnov Test");
		kolmogorovTestRadioButton.setBounds(256, 95, 205, 23);
		panel.add(kolmogorovTestRadioButton);
		
		// Radio Buttons Group
		testButtonGroup = new ButtonGroup();
		testButtonGroup.add(varianceTestRadioButton);
		testButtonGroup.add(tTestStudentRadioButton);
		testButtonGroup.add(wilcoxonTestRadioButton);
		testButtonGroup.add(kolmogorovTestRadioButton);
		
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
		executeTestButton.setEnabled(false);

		console.append("Eugene says welcome to you!\n");
		console.append("Click 'Start' button to start.\n");
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				collection = new Collection();
				updateStatus();
				
//				try {
//				    Thread.sleep(1000);                 
//				    console.append(".\n");
//				    Thread.sleep(1000);
//				    console.append("..\n");
//				} catch(InterruptedException ex) {
//				    Thread.currentThread().interrupt();
//				}
				
				addSamples.setEnabled(false);
				addGroups.setEnabled(true);
				executeTestButton.setEnabled(false);
				
				statusLabel.setIcon(new ImageIcon(onImage));
				
				start.setText("Restart");
				
				console.append("Aplication started/restarted and database cleaned.\n");
				console.append("Step 1 | Click 'Import Groups' button and upload the file with samples markers and groups names information.\n");
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
			                String[] sampleMarkerAndGroup = line.split(" ");
			                
			                
			                System.out.println("'" + sampleMarkerAndGroup[0] + "'");
			                System.out.println("'" + sampleMarkerAndGroup[1] + "'");
			                
			                String sampleName = sampleMarkerAndGroup[0];
			                String groupName = sampleMarkerAndGroup[1];
			                
			                Group group = collection.addAndReturnGroup(groupName);
			                
			                collection.addSample(sampleName, group);
			                
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
					updateStatus();
				}
			}
		});
		
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
				                	Group group = collection.getSamples().get(counter).getGroup();
				                	Gene gene = new Gene(geneMarker, expression, group);
				                	collection.getSamples().get(counter).addGene(gene);
				                	
				                	counter++;
				                } else {
				                	scanner.next();
				                }
			                }
			            }
			            
			            bufferedReader.close();
			            
			            addSamples.setEnabled(false);
			            executeTestButton.setEnabled(true);
			        } catch(FileNotFoundException ex) {
			            console.append("Unable to open file '" + file.getName() + "'");                
			        } catch(IOException ex) {
			            console.append("Error reading file '" + file.getName() + "'");
			        }  

					collection.print();	
					updateStatus();
				}
			}
		});
		
		executeTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (varianceTestRadioButton.isSelected()){
					geneController.validateGenesByPvalue(collection, "variance");
				} else if (tTestStudentRadioButton.isSelected()) {
					geneController.validateGenesByPvalue(collection, "ttest");
				} else if (wilcoxonTestRadioButton.isSelected()) {
					geneController.validateGenesByPvalue(collection, "wilcoxon");
				} else if (kolmogorovTestRadioButton.isSelected()) {
					geneController.validateGenesByPvalue(collection, "kolmogorov");

				}
			}
		});
	}
	
	/**
	 * Update status values to number of genes, samples and groups
	 */
	private void updateStatus() {
		lblSamplesQuantity.setText(Integer.toString(collection.getSamplesQuantity()));
		lblExpressionsQuantity.setText(Integer.toString(collection.getExpressionsQuantity()));
		lblValidGenesQuantity.setText(Integer.toString(collection.getValidGenesQuantity()));
		lblInvalidGenesQuantity.setText(Integer.toString(collection.getInvaliGenesQuantity()));
		lblGroupsNames.setText(collection.getGroupsNames());
	}
}
