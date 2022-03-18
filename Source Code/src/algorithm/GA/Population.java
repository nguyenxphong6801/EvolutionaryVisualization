package algorithm.GA;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import algorithm.Individual;

public class Population {
    private Individual population[];
    private double populationFitness = -1;

    public Population(int populationSize){
        this.population = new Individual[populationSize];
    }

    public Population(int populationSize, int chromosomeLength){
        this.population = new Individual[populationSize];

        for(int i = 0; i < populationSize; i++){
            Individual individual = new Individual(chromosomeLength);
            this.population[i] = individual;
        }

    }

    public Individual[] getIndividuals(){
        return this.population;
    }

    public Individual getFittest(int offset) {
        Arrays.sort(this.population, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if (o1.getFitness() > o2.getFitness()) {
                    return -1;
                } else if (o1.getFitness() < o2.getFitness()) {
                    return 1;
                }
                return 0;
            }
        });
        return this.population[offset];
    }

    public void setPopulationFitness(double fitness){
        this.populationFitness = fitness;
    }

    public double getPopulationFitness(){
        return this.populationFitness;
    }

    public int size(){
        return this.population.length;
    }

    public Individual setIndividual(int index, Individual individual){
        return population[index] = individual;
    }

    public Individual getIndividual(int index){
        return population[index];
    }
}