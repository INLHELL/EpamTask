import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProbabilityDistributionGenerator {
    public Map<Integer, Double> generate(int size, int scale) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size has to be greater then zero!");
        }

        Set<Integer> numbers = generateNumbers(size);
        List<Double> distribution = generateDistribution(size, scale);

        checkDistribution(distribution);

        Map<Integer, Double> distributionMap = new HashMap<>(size);

        final Iterator<Integer> numbersIterator = numbers.iterator();
        final Iterator<Double> distributionIterator = distribution.iterator();
        while (distributionIterator.hasNext() && numbersIterator.hasNext()) {
            distributionMap.put(numbersIterator.next(), distributionIterator.next());
        }

        return distributionMap;
    }

    private void checkDistribution(List<Double> distribution) {
        Double totalProbability = distribution.stream().collect(Collectors.summingDouble(value -> value));
        BigDecimal roundedProbability = new BigDecimal(totalProbability);
        roundedProbability = roundedProbability.setScale(1, RoundingMode.HALF_UP);
        totalProbability = roundedProbability.doubleValue();
        if (totalProbability > 1.0 || totalProbability < 0.9) {
            throw new IllegalArgumentException("Probability must be less then 1.0 and greater then 0.99999, current =" +
                    " " + totalProbability);
        }
    }

    protected Set<Integer> generateNumbers(int size) {
        Random random = new Random();
        Set<Integer> numbers = Stream.generate(() -> random.nextInt()).limit(size).collect(Collectors.toSet());
        return numbers;
    }

    protected List<Double> generateDistribution(int size, int scale) {
        List<Double> distributions = Stream.generate(() -> Math.random()).limit(size).collect(Collectors.toList());
        final Double sum = distributions.stream().collect(Collectors.summingDouble(value -> value));
        distributions.replaceAll(distribution -> {
            double random = (1 / sum) * distribution;
            if (scale != -1) {
                BigDecimal roundedRandom = new BigDecimal(random);
                roundedRandom = roundedRandom.setScale(scale, RoundingMode.HALF_UP);
                random = roundedRandom.doubleValue();
                return random;
            } else {
                return Math.random();
            }
        });
        return distributions;
    }
}
