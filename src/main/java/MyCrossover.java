import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.*;

public class MyCrossover extends AbstractCrossover<double[]> {
    protected MyCrossover() {
        super(1);
    }

    protected List<double[]> mate(double[] p1, double[] p2, int i, Random random) {
        ArrayList children = new ArrayList();

        // your implementation:
        if(p1.length != p2.length)
            return children;

        double crossover_probability = random.nextDouble();
        if (crossover_probability > 0.2) {
            children.add(p1);
            children.add(p2);
        }
        else {

            List<Double> child1_list = new ArrayList();
            List<Double> child2_list = new ArrayList();
            for (int j = 0; j < p1.length; j++) {
                child1_list.add(null);
                child2_list.add(null);
            }

            int first_index = random.nextInt((3*p1.length)/4);
            int second_index = 0;
            while (second_index <= first_index)
                second_index = random.nextInt(p1.length);

            for (int j = first_index; j < second_index; j++) {
                child1_list.set(j, p1[j]);
                child2_list.set(j, p2[j]);
            }

            int child1_index = second_index % p1.length;
            int child2_index = second_index % p1.length;
            for (int j = 0; j < p1.length; j++) {
                int parent_index = (j + second_index) % p1.length;
                if (!child1_list.contains(p2[parent_index])) {
                    child1_list.set(child1_index, p2[parent_index]);
                    child1_index = (child1_index + 1) % p1.length;
                }
                if (!child2_list.contains(p1[parent_index])) {
                    child2_list.set(child2_index, p1[parent_index]);
                    child2_index = (child2_index + 1) % p1.length;
                }
            }

            double[] child1 = new double[p1.length];
            double[] child2 = new double[p1.length];
            for (int j = 0; j < p1.length; j++) {
                child1[j] = child1_list.get(j);
                child2[j] = child2_list.get(j);
            }
            children.add(child1);
            children.add(child2);
        }

        return children;
    }
}
