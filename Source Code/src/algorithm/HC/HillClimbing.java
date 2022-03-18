package algorithm.HC;

import java.util.ArrayList;
import java.util.List;

import algorithm.Algorithm;
import algorithm.Individual;

public class HillClimbing extends Algorithm {
	private  double newValue ;
	private  double currentValue = 0;
	private Individual curentIndividual = new Individual(arraySize) ;
	private Individual newIndividual = new Individual(arraySize);
	private List<int[]> individuals = new ArrayList<>();	
	private List<Double> fitness = new ArrayList<Double>();
	
	public HillClimbing(int[] weight, int[] value) {
		setItems(weight,value);
	}
	
	private void compareValues(double currentValue, double newValue, Individual individual) {
		if (currentValue < newValue) {
			setCurrentValue(newValue);
			setCurrentIndividual(individual);	
		}
	}
	
	private double generateValue() {
		
		Individual individual = new Individual(arraySize);
		setNewIndividual(individual);
		
		individual.setFitness(items);
		newValue = individual.getFitness();
		compareValues(currentValue, newValue, individual);
			
		return getCurrentValue();
			
	}
	
	public double getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}
	
	public void setCurrentIndividual(Individual individual) {
		this.curentIndividual = individual;
	}
	
	
	public Individual getNewIndividual() {
		return newIndividual;
	}

	public void setNewIndividual(Individual individual) {
		this.newIndividual = individual;
	}
	
	public List<int[]> getIndividuals(){
		return individuals;
	}

	public List<Double> getFitness() {
		return fitness;
	}

	public void knapsackHC() {
		
		for(int i = 0; i< 25; i++) {
			System.out.println("Fitness : " + generateValue() + "| New Individual: " + newIndividual +"| Current Individual: " + curentIndividual);
			individuals.add(curentIndividual.getChromosome());
			individuals.add(newIndividual.getChromosome());
			fitness.add(getCurrentValue());
		}
		
		
	}
	
}