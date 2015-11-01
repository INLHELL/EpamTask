package solutions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class NaiveHugeArraySolution {

    private Map<Integer, Double> distributionMap;
    private int[] distributionArray;
    private int scale;

    public NaiveHugeArraySolution(Map<Integer, Double> distributionMap) {
        if (distributionMap != null && distributionMap.isEmpty()) {
            throw new IllegalArgumentException("Distribution map is empty!");
        }
        this.distributionMap = distributionMap;
        int arraySize = calculateArraySize();
        distributionArray = new int[arraySize];
        fillDistributionArray();
    }

    public int getNumber() {
        BigDecimal roundedRandom = new BigDecimal(Math.random());
        roundedRandom = roundedRandom.setScale(scale, RoundingMode.HALF_UP);
        double random = roundedRandom.doubleValue();
        final int index = randomDoubleToArrayIndex(random);
        return distributionArray[index];
    }

    private void fillDistributionArray() {
        int position = 0;
        for (Map.Entry<Integer, Double> numberWithDistribution : distributionMap.entrySet()) {
            final Integer number = numberWithDistribution.getKey();
            final Double distribution = numberWithDistribution.getValue();
            final int numberOfPositions = (int) (distribution * Math.pow(10, scale));
            for (int i = 0; i < numberOfPositions && position < distributionArray.length; i++, position++) {
                distributionArray[position] = number;
            }
        }
    }

    private int calculateArraySize() {
        int longestValue = -1;
        for (Double distribution : distributionMap.values()) {
            final int length = digitsAfterDecimalPoint(distribution);
            longestValue = longestValue < length ? length : longestValue;
        }
        scale = longestValue;
        int arraySize = (int) Math.pow(10, scale);

        return arraySize;
    }

    private int digitsAfterDecimalPoint(Double distribution) {
        final String asString = String.valueOf(distribution);
        final String[] split = asString.split("\\.");
        return split[1].length();
    }

    private int randomDoubleToArrayIndex(Double random) {
        int arrayIndex = (int) (random * Math.pow(10, scale));
        return arrayIndex;
    }
}
