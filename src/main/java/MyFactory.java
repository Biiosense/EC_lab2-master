import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MyFactory extends AbstractCandidateFactory<double[]> {

    private int dimension;

    public MyFactory(int dimension) {
        this.dimension = dimension;
    }

    public double[] generateRandomCandidate(Random random) {
        double[] solution = new double[dimension];
        // your implementation:

        for (int i = 0; i < dimension; i++)
            solution[i] = i;

        for (int i = dimension - 1; i > 0; i--)
        {
            int index = random.nextInt(i + 1);

            int a = (int) solution[index];
            solution[index] = solution[i];
            solution[i] = a;
        }

        return solution;
    }
}
