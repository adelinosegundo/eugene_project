package view;

/**
 * @author Wendell P. Barreto (wendellp.barreto@gmail.com) 
 * @role Full Stack Developer
 * @formation Informatics Technician | IFRN
 * @formation Bachelor of Software Engineering (on going) | UFRN
 * @date May 30, 2015
 *
 */

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

import controller.CollectionController;
import util.gene.signature.tests.GeneSignatureTestFacade;
import java.awt.Font;

public class MainWindow {

	private JFrame frame;
	
	private JPanel panel_1 = null;
	private JPanel panel_2 = null;

	private JLabel statusLabel = null;
	private JLabel lblSamples = null;
	private JLabel lblSamplesQuantity = null;
	private JLabel lblValidGenes = null;
	private JLabel lblExpressionsQuantity = null;
	private JLabel lblExpressions = null;
	private JLabel lblInvalidGenes = null;
	private JLabel lblValidGenesQuantity = null;
	private JLabel lblInvalidGenesQuantity = null;
	private JLabel lblGroups = null;
	private JLabel lblGroupsNames = null;
	private JLabel lblStatus = null;
	private JLabel lblValidations = null;
	private JLabel lblValidationStatus1 = null;
	private JLabel lblValidationStatus2 = null;
	private JLabel lblNewLabel = null;

	private JTextArea console = null;
	
	private JButton btnImportSamples = null;
	private JButton btnImportGroups = null;
	private JButton btnStart = null;
	private JButton executeTestButton = null;
	private JButton btnGroupValidationButton = null;
	private JButton btnLeaveOneOutValidation = null;
	private JButton btnDownloadDistanceMatrix = null;
	private JButton btnDownloadPValues = null;
	private JButton btnDownloadDendogram = null;

	private ButtonGroup testButtonGroup = null;

	private JRadioButton varianceTestRadioButton = null;
	private JRadioButton tTestStudentRadioButton = null;
	private JRadioButton wilcoxonTestRadioButton = null;
	private JRadioButton kolmogorovTestRadioButton = null;
	
	private BufferedImage offImage = null, onImage = null;
	
	private final JFileChooser fc;
	private final JFileChooser fcImage;

	private Collection collection;
	private static CollectionController collectionController;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		collectionController = new CollectionController();
		
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
		fcImage = new JFileChooser();
		FileNameExtensionFilter filterTXT = new FileNameExtensionFilter("Arquivos de texto .txt", "txt");
		FileNameExtensionFilter filterPNG = new FileNameExtensionFilter(".png", "png");
		fc.setFileFilter(filterTXT);
		fcImage.setFileFilter(filterPNG);

		initializeComponents();
		initialize();
	}

	/**
	 * Initialize IFrame
	 */
	public void initializeComponents() {
		// Frames
		frame = new JFrame();
		frame.setResizable(false);
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
		
		lblInvalidGenes = new JLabel("Invalid Genes:");
		lblInvalidGenes.setBounds(512, 108, 97, 16);
		panel.add(lblInvalidGenes);
		
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

		btnImportSamples = new JButton("Import Samples");
		btnImportSamples.setBounds(18, 95, 178, 64);
		panel.add(btnImportSamples);

		btnImportGroups = new JButton("Import Groups");
		btnImportGroups.setBounds(18, 16, 178, 64);
		panel.add(btnImportGroups);

		btnStart = new JButton("Start");
		btnStart.setBounds(652, 98, 93, 35);
		frame.getContentPane().add(btnStart);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(6, 324, 358, 218);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		lblValidations = new JLabel("Validations");
		lblValidations.setBounds(147, 10, 70, 16);
		panel_1.add(lblValidations);
		
		btnGroupValidationButton = new JButton("Group Validation");
		btnGroupValidationButton.setEnabled(false);
		btnGroupValidationButton.setBounds(6, 60, 180, 50);
		panel_1.add(btnGroupValidationButton);
		
		btnLeaveOneOutValidation = new JButton("Leave One Out Validation");
		btnLeaveOneOutValidation.setEnabled(false);
		btnLeaveOneOutValidation.setBounds(6, 130, 180, 50);
		panel_1.add(btnLeaveOneOutValidation);
		
		lblValidationStatus1 = new JLabel("UNKNOWN");
		lblValidationStatus1.setHorizontalAlignment(SwingConstants.CENTER);
		lblValidationStatus1.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		lblValidationStatus1.setBounds(198, 60, 154, 72);
		panel_1.add(lblValidationStatus1);
		
		lblValidationStatus2 = new JLabel("Validate the Sample");
		lblValidationStatus2.setForeground(Color.LIGHT_GRAY);
		lblValidationStatus2.setHorizontalAlignment(SwingConstants.CENTER);
		lblValidationStatus2.setBounds(198, 130, 154, 16);
		panel_1.add(lblValidationStatus2);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_2.setBounds(376, 324, 371, 218);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		lblNewLabel = new JLabel("Downloads");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(135, 10, 100, 16);
		panel_2.add(lblNewLabel);
		
		btnDownloadDistanceMatrix = new JButton("Distance Matrix");
		btnDownloadDistanceMatrix.setEnabled(false);
		btnDownloadDistanceMatrix.setBounds(185, 50, 180, 50);
		panel_2.add(btnDownloadDistanceMatrix);
		
		btnDownloadPValues = new JButton("P-Values");
		btnDownloadPValues.setEnabled(false);
		btnDownloadPValues.setBounds(185, 105, 180, 50);
		panel_2.add(btnDownloadPValues);
		
		btnDownloadDendogram = new JButton("Dendogram");
		btnDownloadDendogram.setEnabled(false);
		btnDownloadDendogram.setBounds(185, 160, 180, 50);
		panel_2.add(btnDownloadDendogram);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		btnImportSamples.setEnabled(false);
		btnImportGroups.setEnabled(false);
		executeTestButton.setEnabled(false);

		console.append("Eugene says welcome to you!\n");
		console.append("Click 'Start' button to start.\n");
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				collection = new Collection();
				updateStatus();
				
				btnImportSamples.setEnabled(false);
				btnImportGroups.setEnabled(true);
				executeTestButton.setEnabled(false);
				
				statusLabel.setIcon(new ImageIcon(onImage));
				
				btnStart.setText("Restart");
				
				console.append("Aplication started/restarted and database cleaned.\n");
				console.append("Step 1 | Click 'Import Groups' button and upload the file with samples markers and groups names information.\n");
			}
		});
		
		btnImportGroups.addActionListener(new ActionListener() {
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
			           
			            btnImportSamples.setEnabled(true);
			            btnImportGroups.setEnabled(false);
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
		
		btnImportSamples.addActionListener(new ActionListener() {
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
			            
			            btnImportSamples.setEnabled(false);
			            executeTestButton.setEnabled(true);
			            btnGroupValidationButton.setEnabled(true);
			            btnDownloadDistanceMatrix.setEnabled(true);
			            btnDownloadPValues.setEnabled(true);
			            btnDownloadDendogram.setEnabled(true);
			        } catch(FileNotFoundException ex) {
			            console.append("Unable to open file '" + file.getName() + "'");                
			        } catch(IOException ex) {
			            console.append("Error reading file '" + file.getName() + "'");
			        }  

					updateStatus();
				}
			}
		});
		
		executeTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (varianceTestRadioButton.isSelected()){
					collectionController.performGenesValidation(collection, "variance");
				} else if (tTestStudentRadioButton.isSelected()) {
					collectionController.performGenesValidation(collection, "ttest");
				} else if (wilcoxonTestRadioButton.isSelected()) {
					collectionController.performGenesValidation(collection, "wilcoxon");
				} else if (kolmogorovTestRadioButton.isSelected()) {
					collectionController.performGenesValidation(collection, "kolmogorov");
				}
			}
		});

		btnGroupValidationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean result = collectionController.performGroupValidation(collection);

				if (result) {
					lblValidationStatus1.setText("VALID");
					lblValidationStatus1.setForeground(Color.green);
					lblValidationStatus2.setText("Able to leave-one-out");

					btnLeaveOneOutValidation.setEnabled(true);
				} else {
					lblValidationStatus1.setText("INVALID");
					lblValidationStatus1.setForeground(Color.red);
					lblValidationStatus2.setText("Unable to leave-one-out");

					btnLeaveOneOutValidation.setEnabled(false);
				}
			}
		});

		btnLeaveOneOutValidation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean result = collectionController.performLeaveOneOutValidation(collection);

				if (result) {
					lblValidationStatus1.setText("VALID");
					lblValidationStatus1.setForeground(Color.green);
					lblValidationStatus2.setText("");
				} else {
					lblValidationStatus1.setText("INVALID");
					lblValidationStatus1.setForeground(Color.red);
					lblValidationStatus2.setText("");
				}
			}
		});

		btnDownloadDistanceMatrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnVal = fc.showSaveDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					collectionController.writeDistanceMatrix(collection, file);
				}
			}
		});

		btnDownloadPValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnVal = fc.showSaveDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					collectionController.writePvalues(collection, file);
				}
			}
		});

		btnDownloadDendogram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnVal = fcImage.showSaveDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fcImage.getSelectedFile();
					
					collectionController.drawDendogram(collection, file);
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
