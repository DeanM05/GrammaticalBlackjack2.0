package main.java.EvolutionObjects;

import ec.EvolutionState;
import ec.simple.SimpleEvaluator;
import ec.util.MersenneTwisterFast;

public class BlackjackEvaluator extends SimpleEvaluator {

    private static final int NON_SPLIT_DECISIONS = 220;
    private static final int RESP_SPLIT = 4;
    private static final int[] NORMAL_VALUES = {1,2,3};

    @Override
    public void evaluatePopulation(EvolutionState state) {
        if (state.population.subpops.get(0).individuals.get(0).evaluated) {
            return;
        }
        for(int x = 0; x < state.population.subpops.size(); x++) {
            for (int y = 0; y < (state.population.subpops.get(x)).individuals.size(); y++) {
                BlackjackIndividual ind = ((BlackjackIndividual) (state.population.subpops.get(x)).individuals.get(y));
                int[] genome = (int[])ind.getGenome();
                for (int i = 0; i < NON_SPLIT_DECISIONS; i++) {
                    if (genome[i] == RESP_SPLIT) {
                        genome[i] = NORMAL_VALUES[new MersenneTwisterFast().nextInt(NORMAL_VALUES.length)];
                    }
                    ind.setGenome(genome);
                }
            }
        }
        super.evaluatePopulation(state);
    }
}
