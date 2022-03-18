package algorithm.GA;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import algorithm.Algorithm;
import algorithm.Individual;

public class GeneticAlgorithm extends Algorithm{
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
    private int MAX_GEN = 20;
    private double best;

    private List<int[]> individuals  = new ArrayList<int[]>();
    private List<Double> fitness = new ArrayList<Double>();

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int[] weight, int[] value){
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        setItems(weight,value);
    }
    

    public double getBest() {
		return best;
	}

	public void setBest(double best) {
		this.best = best;
	}

	public List<Double> getFitness() {
		return fitness;
	}

	public void setFitness(List<Double> fitness) {
		this.fitness = fitness;
	}


	public Population initPopulation(int chromosomeLength){
        Population population = new Population(this.populationSize, chromosomeLength);
        return population;
    }

    //change eval
    public double evalPopulation(Population population){
        double count = 0;

        for(int i = 0; i < this.populationSize; i++){
            if(population.getIndividual(i).getFitness() == population.getIndividual(0).getFitness()){
                count++;
            }
        }

        if((count / this.populationSize) >= 0.30){
            return 1;
        }
        else {
            return 0;
        }
    }
    //change
    public boolean isTerminationConditionMet(Population population, int generation){
        if((generation > MAX_GEN) || (evalPopulation(population) == 1)){
            return true;
        }
        else {
            return false;
        }
    }

    public Individual selectParent(Population population){
        Individual individuals[] = population.getIndividuals();

        double populationFitness = 0;

        for(Individual individual : individuals){
            populationFitness += individual.calcFitness(individual.getChromosome(),items);
        }

        double rouletteWheel = Math.random() * populationFitness;

        double spin = 0;

        for(Individual individual : individuals){
            spin += individual.getFitness();
            if(spin >= rouletteWheel){
                return individual;
            }
        }

        return individuals[population.size() - 1];
    }
    public Population crossoverPopulation(Population population){
        Population newPopulation = new Population(population.size());

        for(int i = 0; i < population.size(); i++){
            Individual parent1 = population.getFittest(i);

            if((this.crossoverRate > Math.random()) && (i >= this.elitismCount)){
                Individual child = new Individual(parent1.getChromosomeLength());

                Individual parent2 = selectParent(population);

                for(int gene = 0; gene < parent1.getChromosomeLength(); gene++){
                    if(0.5 > Math.random()){
                        child.setGene(gene, parent1.getGene(gene));
                    }
                    else{
                        child.setGene(gene, parent2.getGene(gene));
                    }
                }

                newPopulation.setIndividual(i, child);
            }
            else {
                newPopulation.setIndividual(i, parent1);
            }
        }

        return newPopulation;
    }

    public Population mutatePopulation (Population population){
        Population newPopulation = new Population(this.populationSize);

        for(int i = 0; i < population.size(); i++){
            Individual individual = population.getFittest(i);

            for(int gene = 0; gene < individual.getChromosomeLength(); gene++){

                if(i >= this.elitismCount){

                    if(this.mutationRate > Math.random()){
                        int newGene = 1;

                        if(individual.getGene(gene) == 1){
                            newGene = 0;
                        }

                        individual.setGene(gene, newGene);
                    }
                }
            }

            newPopulation.setIndividual(i, individual);
        }
        return newPopulation;
    }
    
    public void calcPopulation(Population population) {

        for (Individual individual : population.getIndividuals()) {
            individual.calcFitness(individual.getChromosome(),items);
        }

    }
    
	public List<int[]> getIndividuals(){
		return individuals;
	}

    public void knapsackGA(){
        int generation = 1;

//        GeneticAlgorithm ga = new GeneticAlgorithm(this.populationSize, this.mutationRate, this.crossoverRate, this.elitismCount);
        Individual indi = new Individual(arraySize);
        Population population = initPopulation(arraySize);

        calcPopulation(population);

//          System.out.println("Generation: " + generation);
//          System.out.println("Best: " + population.getFittest(0).toString() + " | Fitness: " + population.getFittest(0).getFitness());

        for(Individual individual : population.getIndividuals()){
//            System.out.println("Individual: " + individual + " | Fitness : " + individual.getFitness());
            individuals.add(individual.getChromosome());
        }

        generation++;

        do{
//            System.out.println("-------------------------");
            population = crossoverPopulation(population);

            population = mutatePopulation(population);
            calcPopulation(population);

            indi = population.getFittest(0);

//            System.out.println("Generation: " + generation);
//            System.out.println("Best: " + population.getFittest(0).toString() + " | Fitness: " + population.getFittest(0).getFitness());

            for (Individual individual : population.getIndividuals()){
//                System.out.println("Individual: " + individual + " | Fitness : " + individual.getFitness());
                individuals.add(individual.getChromosome());
            }
            fitness.add(indi.getFitness());


            generation++;

        } while ((isTerminationConditionMet(population, generation)) == false);
        
//        step.stream().flatMap(List::stream)
//                .forEach(arr->System.out.println(Arrays.toString(arr) + " "));
        System.out.println(("Found solution in: " + (generation - 1) + " generation(s)"));
        System.out.println("Best solution: " + indi + " | Fitness: " + indi.getFitness());
        setBest(indi.getFitness());
    }

}