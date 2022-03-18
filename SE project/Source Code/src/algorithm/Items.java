package algorithm;

public class Items {
	private int weight;
	private int value;
	private int maxWeight = 10;
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getMaxWeight() {
		return maxWeight;
	}

	public Items(int weight, int value) {
		super();
		this.weight = weight;
		this.value = value;
	}
}
