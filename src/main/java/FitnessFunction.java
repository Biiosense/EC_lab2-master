import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;
import java.util.Random;

public class FitnessFunction implements FitnessEvaluator<double[]> {
    // don't change!!!!

    public FitnessFunction(int dimension, double[][] distances) {
        this.dimension = dimension;
        this.distances = distances;
    }

    private int dimension;
    private double[][] distances;

    public double getFitness(double[] solution, List<? extends double[]> list) {

        double result = 0;
        for (int i=0; i<dimension-1; i++) {
            int city1 = (int) Math.max(solution[i], solution[i + 1]);
            int city2 = (int) Math.min(solution[i], solution[i + 1]);
            result += distances[city1][city2];
        }
        return 1 / (result);
    }

    public boolean isNatural() {
        return true;
    }
}
