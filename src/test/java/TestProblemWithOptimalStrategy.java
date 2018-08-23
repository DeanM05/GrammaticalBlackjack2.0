package test.java;

import ec.EvolutionState;
import ec.simple.SimpleFitness;
import main.java.EvolutionObjects.BlackjackIndividual;
import main.java.EvolutionObjects.BlackjackProblem;
import main.java.GUIObjects.GenomeDisplay;
import org.junit.Test;

import javax.swing.*;

public class TestProblemWithOptimalStrategy {
    private static final int[] optimalStrategyGenome = {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 3, 3, 3, 3, 1, 1, 1, 1,
            1, 3, 3, 3, 3, 3, 3, 3, 3, 1,
            1, 3, 3, 3, 3, 3, 3, 3, 3, 1,
            1, 1, 1, 2, 2, 2, 1, 1, 1, 1,
            1, 2, 2, 2, 2, 2, 1, 1, 1, 1,
            1, 2, 2, 2, 2, 2, 1, 1, 1, 1,
            1, 2, 2, 2, 2, 2, 1, 1, 1, 1,
            1, 2, 2, 2, 2, 2, 1, 1, 1, 1,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            //Soft Hands
            1, 1, 1, 1, 3, 3, 1, 1, 1, 1,
            1, 1, 1, 1, 3, 3, 1, 1, 1, 1,
            1, 1, 1, 3, 3, 3, 1, 1, 1, 1,
            1, 1, 1, 3, 3, 3, 1, 1, 1, 1,
            1, 1, 3, 3, 3, 3, 1, 1, 1, 1,
            1, 2, 3, 3, 3, 3, 2, 2, 1, 1,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            //SplittableHands
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            1, 4, 4, 4, 4, 4, 4, 1, 1, 1,
            1, 4, 4, 4, 4, 4, 4, 1, 1, 1,
            1, 1, 1, 1, 4, 4, 1, 1, 1, 1,
            1, 3, 3, 3, 3, 3, 3, 3, 3, 1,
            1, 4, 4, 4, 4, 4, 1, 1, 1, 1,
            1, 4, 4, 4, 4, 4, 4, 1, 1, 1,
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            2, 4, 4, 4, 4, 4, 2, 4, 4, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2
    };

    @Test
    public void TestProblemWithOptimalStrategy() {
        BlackjackIndividual ind = new BlackjackIndividual();
        EvolutionState state = new EvolutionState();
        ind.genome = optimalStrategyGenome;
        ind.fitness = new SimpleFitness();
        BlackjackProblem problem = new BlackjackProblem();
        problem.evaluate(state, ind, 0, 0);
    }

}
