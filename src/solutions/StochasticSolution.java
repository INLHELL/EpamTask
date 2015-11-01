package solutions;

import java.util.Comparator;
import java.util.Map;

public class StochasticSolution {

    private Map<Integer, Double> distributionMap;
    private Map.Entry<Integer, Double>[] distributionArray;
    private int size;
    private Double maxProbability;

    public StochasticSolution(Map<Integer, Double> distributionMap) {
        if (distributionMap != null && distributionMap.isEmpty()) {
            throw new IllegalArgumentException("Distribution map is empty!");
        }
        this.distributionMap = distributionMap;
        maxProbability = distributionMap.values().stream().max(Comparator.<Double>naturalOrder()).get();
        size = distributionMap.size();
        distributionArray = new Map.Entry[size];
        fillDistributionArray();
    }

    public int getNumber() {
        boolean wasNotSelected = true;
        int index = 0;
        while (wasNotSelected) {
            index = (int) (size * Math.random());
            if (Math.random() < distributionArray[index].getValue() / maxProbability) {
                wasNotSelected = false;
            }
        }
        return distributionArray[index].getKey();
    }

    private void fillDistributionArray() {
        int i = 0;
        for (Map.Entry<Integer, Double> entry : distributionMap.entrySet()) {
            distributionArray[i++] = entry;
        }
    }
}
