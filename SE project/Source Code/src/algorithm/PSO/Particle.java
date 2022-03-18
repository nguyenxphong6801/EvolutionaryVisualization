package algorithm.PSO;

import algorithm.Individual;

public class Particle extends Individual{
	
	private double velocity[] = new double[5];
	private double pBest=0;
	private int[] pBestParticle = new int[5];
	private static final int vMax=4;
	
	public Particle(int chromosomeLength) {
		super(chromosomeLength);
	}
	
	public double getpBest() {
		return pBest;
	}

	public void setpBest(double pBest) {
		this.pBest = pBest;
	}

	public double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(double[] velocity) {
		this.velocity = velocity;
	}

	public int[] getpBestParticle() {
		return pBestParticle;
	}

	public void setpBestParticle(int[] pBestParticle) {
		this.pBestParticle = pBestParticle;
	}

	public static int getVmax() {
		return vMax;
	}
}
