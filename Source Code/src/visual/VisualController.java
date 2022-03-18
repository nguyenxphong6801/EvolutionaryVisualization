package visual;

import java.net.URL;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import algorithm.Algorithm;
import algorithm.GA.GeneticAlgorithm;
import algorithm.HC.HillClimbing;
import algorithm.PSO.PSO;
import javafx.animation.FillTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class VisualController implements Initializable{
	private int[] weight = new int[5];
	private int[] value = new int[5];
	@FXML private GridPane pane;
	private List<Label> randomw = new ArrayList<Label>();
	private List<Label> randomv = new ArrayList<Label>();
	@FXML private ChoiceBox box;
	private ObservableList<String> dbTypeList = FXCollections.observableArrayList("Genetic Algorithm","PSO","Hill Climbing");
	private List<int[]> step = new ArrayList<int[]>();
	private List<Double> fitness = new ArrayList<Double>();
	private List<Rectangle> binary = new ArrayList<Rectangle>();
	private List<FillTransition> fill = new ArrayList<FillTransition>();
	
	@FXML private Label label1;
	@FXML private Label label2;
	@FXML private Label label3;
	@FXML private Label label4;
	@FXML private Label label5;
	@FXML private Label label6;
	@FXML private Label label7;
	@FXML private Label label8;
	@FXML private Label label9;
	@FXML private Label label10;
	@FXML private Label label11;
	@FXML private Label label12;
	@FXML private Label label13;
	@FXML private Label label14;
	@FXML private Label label15;
	private List<Label> name = new ArrayList<Label>();
	
	@FXML private Label bestVal;
	private double best;
	private int iter=0;
	
	public void random(ActionEvent ae) {
		Random ran = new Random();
		iter=0;
		for (int i=0;i<5;i++) {
			if (randomw.size()<=i) {
				randomw.add(new Label());
			}
			
			if (randomv.size()<=i) {
				randomv.add(new Label());
			}
			
			weight[i] = ran.nextInt(10)+1;
			randomw.get(i).setText(String.valueOf(weight[i]));	
			pane.getChildren().remove(randomw.get(i));
			pane.add(randomw.get(i), i+5, 3);
			GridPane.setHalignment(randomw.get(i),HPos.CENTER);
			
			value[i] = ran.nextInt(10)+1;
			randomv.get(i).setText(String.valueOf(value[i]));	
			pane.getChildren().remove(randomv.get(i));
			pane.add(randomv.get(i), i+5, 4);
			GridPane.setHalignment(randomv.get(i),HPos.CENTER);
		}
	}
	
	public void resetIter(MouseEvent me) {
		iter=0;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		box.setItems(dbTypeList);
	}
	
	public void run(ActionEvent ae) throws InterruptedException {
		if (weight[0]!=0) {
			addLabel();
			if (box.getSelectionModel().getSelectedItem()!=null) {
				String str =(String) box.getSelectionModel().getSelectedItem();
				
				switch(str) {
				case "Genetic Algorithm":
					setLabelGA();
					GeneticAlgorithm ga = new GeneticAlgorithm(15, 0.1, 0.9, 1,weight,value);
					solve(ga);
					String str1 = "Best Value: " + best;
					bestVal.setText(str1);
					binary.clear();
					for (int j=0;j<15;j++) {
						for (int k=0;k<5;k++) {
							Rectangle rec = new Rectangle(30,30);
							rec.setFill(Color.WHITE);
							rec.setStroke(Color.BLACK);
							binary.add(rec);
							pane.getChildren().remove(binary.get(j*5+k));
							pane.add(binary.get(j*5+k), k+5, j+5);
							GridPane.setHalignment(rec,HPos.CENTER);
						}
					}
					for (int[]i:step) {
						System.out.println(Arrays.toString(i));
					}
					fill.clear();
					for (int j=0;j<15;j++) {
						for (int k=0;k<5;k++) {
							FillTransition trans = new FillTransition();
							fill.add(trans);
							fill.get(j*5+k).setCycleCount(1);  
							fill.get(j*5+k).setDuration(Duration.millis(2000));
							if (step.get(countStep(j,15))[k]==0) {
								fill.get(j*5+k).setToValue(Color.WHITE);
							} else {
								fill.get(j*5+k).setToValue(Color.BLACK);
							}
							fill.get(j*5+k).setShape(binary.get(j*5+k));
							fill.get(j*5+k).play();
						}
					}
					break;
					
				case "PSO":
					setLabelPSO();
					PSO pso = new PSO(weight,value);
					solve(pso);
					for (int[]i:step) {
						System.out.println(Arrays.toString(i));
					}
					str1 = "Best Value: " + best;
					bestVal.setText(str1);
					binary.clear();
					for (int j=0;j<15;j++) {
						for (int k=0;k<5;k++) {
							Rectangle rec = new Rectangle(30,30);
							rec.setFill(Color.WHITE);
							rec.setStroke(Color.BLACK);
							binary.add(rec);
							pane.getChildren().remove(binary.get(j*5+k));
							pane.add(binary.get(j*5+k), k+5, j+5);
							GridPane.setHalignment(rec,HPos.CENTER);
						}
					}
	//				step.stream().flatMap(List::stream)
	//		        .forEach(arr->System.out.println(Arrays.toString(arr) + " "));
					fill.clear();
					for (int j=0;j<15;j++) {
						for (int k=0;k<5;k++) {
							FillTransition trans = new FillTransition();
							fill.add(trans);
							fill.get(j*5+k).setCycleCount(1);  
							fill.get(j*5+k).setDuration(Duration.millis(2000));
							if (step.get(countStep(j,15))[k]==0) {
								fill.get(j*5+k).setToValue(Color.WHITE);
							} else {
								fill.get(j*5+k).setToValue(Color.BLACK);
							}
							fill.get(j*5+k).setShape(binary.get(j*5+k));
							fill.get(j*5+k).play();
						}
					}
					
					break;
				case "Hill Climbing":
					setLabelHC();
					HillClimbing hc = new HillClimbing(weight,value);
					solve(hc);
	//				step.stream().flatMap(List::stream)
	//		        .forEach(arr->System.out.println(Arrays.toString(arr) + " "));
					
					str1 = "Best Value: " + best;
					bestVal.setText(str1);
					for (int[]i:step) {
						System.out.println(Arrays.toString(i));
					}
					if (binary.size()>0) {
						for (int j=0;j<15;j++) {
							for (int k=0;k<5;k++) {
								pane.getChildren().remove(binary.get(j*5+k));
							}
						}
					}
					
					binary.clear();
					for (int j=0;j<2;j++) {
						for (int k=0;k<5;k++) {
							Rectangle rec = new Rectangle(30,30);
							rec.setFill(Color.WHITE);
							rec.setStroke(Color.BLACK);
							binary.add(rec);
							pane.add(binary.get(j*5+k), k+5, j+5);
							GridPane.setHalignment(rec,HPos.CENTER);
						}
					}
					fill.clear();
					for (int j=0;j<2;j++) {
						for (int k=0;k<5;k++) {
							FillTransition trans = new FillTransition();
							fill.add(trans);
							fill.get(j*5+k).setCycleCount(1);  
							fill.get(j*5+k).setDuration(Duration.millis(2000));
							if (step.get(countStep(j,2))[k]==0) {
								fill.get(j*5+k).setToValue(Color.WHITE);
							} else {
								fill.get(j*5+k).setToValue(Color.BLACK);
							}
							fill.get(j*5+k).setShape(binary.get(j*5+k));
							fill.get(j*5+k).play();
						}
					}
					break;
				default:
					System.out.println("Error");
					break;
				}
			} else {
				System.out.println("No Algorithm chosen");
			}
		} else {
			JOptionPane.showMessageDialog(null, "No array existed");
		}
	}
	
	public void runOneStep(ActionEvent ae) {
		if (weight[0]!=0) {
			addLabel();
			if (box.getSelectionModel().getSelectedItem()!=null) {
				String str =(String) box.getSelectionModel().getSelectedItem();
				
				switch(str) {
				case "Genetic Algorithm":
					setLabelGA();
					if (iter==0) {
						GeneticAlgorithm ga = new GeneticAlgorithm(15, 0.1, 0.9, 1,weight,value);
						solve(ga);
					}
					for (double i:fitness) {
						System.out.println(i);
					}
					if (iter<(step.size()/15)-1) {
						String str1 = "Best Value: " + fitness.get(iter);
						bestVal.setText(str1);
						binary.clear();
						for (int j=0;j<15;j++) {
							for (int k=0;k<5;k++) {
								Rectangle rec = new Rectangle(30,30);
								rec.setFill(Color.WHITE);
								rec.setStroke(Color.BLACK);
								binary.add(rec);
								pane.getChildren().remove(binary.get(j*5+k));
								pane.add(binary.get(j*5+k), k+5, j+5);
								GridPane.setHalignment(rec,HPos.CENTER);
							}
						}
						
						fill.clear();
						for (int j=0;j<15;j++) {
							for (int k=0;k<5;k++) {
								FillTransition trans = new FillTransition();
								fill.add(trans);
								fill.get(j*5+k).setCycleCount(1);  
								fill.get(j*5+k).setDuration(Duration.millis(2000));
								if (step.get(iter*15+j)[k]==0) {
									fill.get(j*5+k).setToValue(Color.WHITE);
								} else {
									fill.get(j*5+k).setToValue(Color.BLACK);
								}
								fill.get(j*5+k).setShape(binary.get(j*5+k));
								fill.get(j*5+k).play();
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Algorithm Complete");
						iter = 0;
					} 
					iter++;
					break;
					
				case "PSO":
					setLabelPSO();
					if (iter==0) {
						PSO pso = new PSO(weight,value);
						solve(pso);
					}
					for (double i:fitness) {
						System.out.println(i);
					}
					
					if (iter<(step.size()/15)-1) {
						String str1 = "Best Value: " + fitness.get(iter);
						bestVal.setText(str1);
						binary.clear();
						for (int j=0;j<15;j++) {
							for (int k=0;k<5;k++) {
								Rectangle rec = new Rectangle(30,30);
								rec.setFill(Color.WHITE);
								rec.setStroke(Color.BLACK);
								binary.add(rec);
								pane.getChildren().remove(binary.get(j*5+k));
								pane.add(binary.get(j*5+k), k+5, j+5);
								GridPane.setHalignment(rec,HPos.CENTER);
							}
						}
						fill.clear();
						for (int j=0;j<15;j++) {
							for (int k=0;k<5;k++) {
								FillTransition trans = new FillTransition();
								fill.add(trans);
								fill.get(j*5+k).setCycleCount(1);  
								fill.get(j*5+k).setDuration(Duration.millis(2000));
								if (step.get(iter*15+j)[k]==0) {
									fill.get(j*5+k).setToValue(Color.WHITE);
								} else {
									fill.get(j*5+k).setToValue(Color.BLACK);
								}
								fill.get(j*5+k).setShape(binary.get(j*5+k));
								fill.get(j*5+k).play();
							}
						}
					} else{
						JOptionPane.showMessageDialog(null, "Algorithm Complete");
						iter = 0;
					} 
					iter++;
					break;
					
				case "Hill Climbing":
					setLabelHC();
					if (iter==0) {
						HillClimbing hc = new HillClimbing(weight,value);
						solve(hc);
					}
					
					if (binary.size()>10) {
						for (int j=0;j<15;j++) {
							for (int k=0;k<5;k++) {
								pane.getChildren().remove(binary.get(j*5+k));
							}
						}
					}
					
					
					binary.clear();
					for (int j=0;j<2;j++) {
						for (int k=0;k<5;k++) {
							Rectangle rec = new Rectangle(30,30);
							rec.setFill(Color.WHITE);
							rec.setStroke(Color.BLACK);
							binary.add(rec);
							pane.add(binary.get(j*5+k), k+5, j+5);
							GridPane.setHalignment(rec,HPos.CENTER);
						}
					}
					for (double i:fitness) {
						System.out.println(i);
					}
					if (iter<(step.size()/2)) {
						String str1 = "Best Value: " + fitness.get(iter);
						bestVal.setText(str1);
						
						
						fill.clear();
						for (int j=0;j<2;j++) {
							for (int k=0;k<5;k++) {
								FillTransition trans = new FillTransition();
								fill.add(trans);
								fill.get(j*5+k).setCycleCount(1);  
								fill.get(j*5+k).setDuration(Duration.millis(2000));
								if (step.get(iter*2+j)[k]==0) {
									fill.get(j*5+k).setToValue(Color.WHITE);
								} else {
									fill.get(j*5+k).setToValue(Color.BLACK);
								}
								fill.get(j*5+k).setShape(binary.get(j*5+k));
								fill.get(j*5+k).play();
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Algorithm Complete");
						iter = 0;
					} 
					iter++;
					break;
				default:
					System.out.println("Error");
					break;
				}
			} else {
				System.out.println("No Algorithm chosen");
			}
		} else {
			JOptionPane.showMessageDialog(null, "No array existed");
		}
	}
	
	public void solve(Algorithm algo) {		
		if (algo instanceof PSO) {
			PSO pso = new PSO(weight,value);
			pso.knapsackPSO();
			step = new ArrayList<int[]>(pso.getIndividuals());
			best = pso.getBest();
			fitness = new ArrayList<Double>(pso.getFitness());
		} else if (algo instanceof GeneticAlgorithm) {
			GeneticAlgorithm ga = new GeneticAlgorithm(15, 0.1, 0.9, 1,weight,value);
			ga.knapsackGA();
			step = new ArrayList<int[]>(ga.getIndividuals());
			best = ga.getBest();
			fitness = new ArrayList<Double>(ga.getFitness());
		} else if (algo instanceof HillClimbing){
			HillClimbing hc = new HillClimbing(weight,value);
			hc.knapsackHC();
			step = new ArrayList<int[]>(hc.getIndividuals());
			best = hc.getCurrentValue();
			fitness = new ArrayList<Double>(hc.getFitness());
		} else {
			System.out.println("Error");
		}
	}
	
	public void addLabel() {
		name.clear();
		name.add(label1);
		name.add(label2);
		name.add(label3);
		name.add(label4);
		name.add(label5);
		name.add(label6);
		name.add(label7);
		name.add(label8);
		name.add(label9);
		name.add(label10);
		name.add(label11);
		name.add(label12);
		name.add(label13);
		name.add(label14);
		name.add(label15);
	}
	
	public void setLabelGA() {
		for (int i=0;i<name.size();i++) {
			int j=i+1;
			name.get(i).setText("Individual " + j);
		}
	}
	
	public void setLabelPSO() {
		for (int i=0;i<name.size();i++) {
			int j=i+1;
			name.get(i).setText("Particle " + j);
		}
	}
	
	public void setLabelHC() {
		name.get(0).setText("Current Value");
		name.get(1).setText("New Value");
		for (int i=2;i<name.size();i++) {
			name.get(i).setText("");
		}
	}
	
	public int countStep(int j,int num) {
		return ((step.size()/num)-1)*num+j;
	}
	
}
