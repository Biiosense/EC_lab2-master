import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PichaudMaximeAlg {

    public static void main(String[] args) {

        String input_file = "test1.txt";
        List<int[]> cities_coords = readFile(input_file);

        double[][] distances = new double[cities_coords.size()][];
        for (int i = 0; i < cities_coords.size(); i++) {
            distances[i] = new double[i];
            for (int j = 0; j < i; j++) {
                int[] city1 = cities_coords.get(i);
                int[] city2 = cities_coords.get(j);
                distances[i][j] = Math.sqrt(Math.pow(city1[0] - city2[0], 2) + Math.pow(city1[1] - city2[1], 2));
            }
        }

        int dimension = distances.length; // dimension of problem
        int populationSize = 100; // size of population
        int generations = 1000; // number of generations

        Random random = new Random(); // random

        CandidateFactory<double[]> factory = new MyFactory(dimension); // generation of solutions

        ArrayList<EvolutionaryOperator<double[]>> operators = new ArrayList<EvolutionaryOperator<double[]>>();
        operators.add(new MyCrossover()); // Crossover
        operators.add(new MyMutation(generations)); // Mutation
        EvolutionPipeline<double[]> pipeline = new EvolutionPipeline<double[]>(operators);

        SelectionStrategy<Object> selection = new RouletteWheelSelection(); // Selection operator

        FitnessEvaluator<double[]> evaluator = new FitnessFunction(dimension, distances); // Fitness function

        EvolutionEngine<double[]> algorithm = new SteadyStateEvolutionEngine<double[]>(
                factory, pipeline, evaluator, selection, populationSize, false, random);

        algorithm.addEvolutionObserver(new EvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                //System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit + ". Score : " + 1/ (Math.sqrt(bestFit)));
                if (populationData.getGenerationNumber()%99 == 0)
                {
                    System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit + ". Score : " + 1/bestFit);
                }
            }
        });

        TerminationCondition terminate = new GenerationCount(generations);
        algorithm.evolve(populationSize, 1, terminate);
    }


    public static List<int[]> readFile(String filename) {
        List<int[]> cities = new ArrayList<int[]>();

        ClassLoader classLoader = PichaudMaximeAlg.class.getClassLoader();
        InputStream file = classLoader.getResourceAsStream(filename);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.matches("[0-9 ]+")) {
                    String[] values = line.split(" ");
                    if (values.length == 3) {
                        int[] coords = new int[2];
                        try {
                            coords[0] = Integer.parseInt(values[1]);
                            coords[1] = Integer.parseInt(values[2]);
                        } catch (NumberFormatException nfe) {
                            nfe.printStackTrace();
                        }
                        cities.add(coords);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cities;
    }
}
