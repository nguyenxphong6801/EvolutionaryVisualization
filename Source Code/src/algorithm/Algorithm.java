package algorithm;

import java.util.List;
import java.util.ArrayList;

public abstract class Algorithm {
    protected int arraySize = 5;
    protected int maxWeight = 10;
    protected List<Items> items = new ArrayList<Items>();
    
    public int getArraySize() {
    	return this.arraySize;
    }

    public int getMaxWeight(){
        return maxWeight;
    }
    
    public void setItems(int[] weight, int[] value){
    	items.clear();
    	for (int i=0;i<arraySize;i++) {
    		this.items.add(new Items(weight[i],value[i]));
    	}
    }
    
    public List<Items> getItems(){
    	return this.items;
    }

}