import solutions.NaiveHugeArraySolution;
import solutions.StochasticSolution;

import java.util.HashMap;
import java.util.Map;

public class ProbabilityDistributionTest {
    private static class Mock extends ProbabilityDistributionGenerator {

    }

    private static Mock mock = new Mock();

    public static void main(String[] args) {
        final Map<Integer, Double> integerDoubleMap = mock.generate(5, 3);
        System.out.println(integerDoubleMap);

        NaiveHugeArraySolution naiveHugeArraySolution = new NaiveHugeArraySolution(integerDoubleMap);
        final int number = naiveHugeArraySolution.getNumber();
        System.out.println(number);


        StochasticSolution stochasticSolution = new StochasticSolution(integerDoubleMap);
        Map<Integer, Integer> stochasticCheckMap = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            final int stochasticSolutionNumber = stochasticSolution.getNumber();
            Integer counter = stochasticCheckMap.get(stochasticSolutionNumber);
            if (counter == null) {
                stochasticCheckMap.put(stochasticSolutionNumber, 1);
            } else {
                stochasticCheckMap.replace(stochasticSolutionNumber, ++counter);
            }
        }
        System.out.println(stochasticCheckMap);


    }

}
