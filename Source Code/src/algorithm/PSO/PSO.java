package algorithm.PSO;

import java.util.List;

import algorithm.Algorithm;

import java.util.ArrayList;
import java.util.Arrays;

public class PSO extends Algorithm{
	
	private double w;
	private double c1;
	private double c2;
	private List<Particle> particle = new ArrayList<Particle>();
	private double gBest;
	private List<Integer> gBestParticle = new ArrayList<Integer>();
	private int iter=0;
	private int iterMax = 25;
	private List<int[]> individuals = new ArrayList<int[]>();
	private int[] temp;
	private List<Double> fitness = new ArrayList<Double>();
	
	public PSO(int[] weight, int[] value) {
		setItems(weight,value);
	}
	
	public double getBest() {
		return gBest;
	}
	
	public List<int[]> getIndividuals(){
		return individuals;
	}

	public List<Double> getFitness() {
		return fitness;
	}

	public void initialize() {
		for (int i=0;i<15;i++) {
			Particle par = new Particle(getArraySize());
			this.particle.add(par);
		}
		
		for (int i=0;i<getArraySize();i++) {
			gBestParticle.add(0);
		}
	}
	
	public void updateCoef() {
		this.w = 0.4 * ((iter - iterMax) / (iterMax * iterMax)) + 0.4;
		this.c1 = (-3) * (iter/iterMax) + 3.5;
		this.c2 = 3 * (iter/iterMax) + 0.5;
	}
	
	public void move(Particle par) {
		double[] v = par.getVelocity();
		int[] c = par.getChromosome();
		int[] p = par.getpBestParticle();
		
		for (int j=0;j<v.length;j++) {
			//Update velocity
			v[j] = w * v[j] + c1 * Math.random() * (p[j]-c[j]) + c2 * Math.random() * (gBestParticle.get(j) - c[j]);
			
			//Update position
			double temp = (c[j] + v[j] + par.getVmax()) / (1 + 2 * par.getVmax());
			if (Math.random() < temp) {
				par.setGene(j, 1);
			} else {
				par.setGene(j, 0);
			}
		}
		par.setVelocity(v);
	}
	
	public void knapsackPSO() {
		initialize();
		while (iter < iterMax) {
			for (Particle i:particle) {
				
				i.setFitness(items);
				
				if (i.getFitness() > i.getpBest()) {
					
					i.setpBest(i.getFitness());
					i.setpBestParticle(i.getChromosome());
				}
				if (i.getpBest() > gBest) {
					this.gBest = i.getpBest();
					gBestParticle = new ArrayList<Integer>();
					for (int j=0;j<i.getpBestParticle().length;j++) {
						gBestParticle.add(i.getpBestParticle()[j]);
					}
				}
			}
			
			updateCoef();
			for (Particle i :particle) {
				int[] arr = i.getChromosome();
				temp = Arrays.copyOf(arr, arr.length);
				this.individuals.add(temp);
				move(i);
			}
			
			fitness.add(gBest);
			iter++;
		}
		
		System.out.println(gBest);
		for (int k=0;k<gBestParticle.size();k++) {
			System.out.print(gBestParticle.get(k));
		}
		System.out.println();
	}
}