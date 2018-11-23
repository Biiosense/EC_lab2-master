import org.uncommons.maths.random.GaussianGenerator;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.List;
import java.util.Random;

public class MyMutation implements EvolutionaryOperator<double[]> {

    protected MyMutation(int max_generations) {
        this.max_generations = max_generations;
    }

    private int max_generations;
    private int generations = 1;
    private double rate = 0.5;

    public List<double[]> apply(List<double[]> population, Random random) {
        // initial population
        // need to change individuals, but not their number!

        // your implementation:

        /*  Swap Mutation */
        for (double[] individual : population) {
            for (int i = 0; i < individual.length; i++) {
                if (random.nextDouble() < rate) {
                    int swap_city = random.nextInt(individual.length);
                    double tmp = individual[i];
                    individual[i] = individual[swap_city];
                    individual[swap_city] = tmp;
                }
            }
        }

        /* Insert Mutation */
        /*
        for (double[] individual : population) {
            for (int i = 0; i < individual.length; i++) {
                if (random.nextDouble() < rate) {
                    int city_index = random.nextInt(individual.length);
                    if (city_index > i)
                    {
                        double tmp = individual[city_index];
                        for (int j = city_index; j > i+1; j--)
                            individual[j] = individual[j-1];
                        individual[i+1] = tmp;
                    }
                }
            }
        }*/

        generations++;
        rate = 0.05 + Math.exp(- (generations/(double)max_generations * 5));

        return population;
    }
}
