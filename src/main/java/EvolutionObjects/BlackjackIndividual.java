package main.java.EvolutionObjects;

import ec.vector.IntegerVectorIndividual;

public class BlackjackIndividual extends IntegerVectorIndividual {

    public int[] visitedSituations;

    public BlackjackIndividual() {
        super();
        visitedSituations = new int[320];
        for (int i = 0; i < visitedSituations.length; i++) {
            visitedSituations[i] = 0;
        }
    }

    public void setVisitedSituations(int[] visitedSituations) {
            this.visitedSituations = visitedSituations;
    }

    public int[] getVisitedSituations() {
        return visitedSituations;
    }


}
