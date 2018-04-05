package main.java.EvolutionObjects;

import ec.*;
import ec.simple.*;
import main.java.GUIObjects.GenomeDisplay;
import main.java.GameObjects.BlackjackGame;
import main.java.PlayerObjects.BlackjackPlayer;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;


public class BlackjackProblem extends Problem implements SimpleProblemForm {

    private static final double STARTING_MONEY = 100000;
    private static final int NUM_OF_GAMES = 150000;
    private static int jobNumber = 0;
    private static Logger logger = LogManager.getLogger("job-log");

    static {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Dean\\Documents\\logstash-5.6.2\\jobData.txt"));
            String lastLine = null;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                lastLine = currentLine;
            }
            reader.close();
            if (lastLine != null) {
                String[] lineSections = lastLine.split(Pattern.quote("|"));
                String[] jobSections = lineSections[0].split("=");
                jobNumber = Integer.parseInt(jobSections[1]) +  1;
            }
        } catch (Exception e) {
            System.out.println("Oh no");
        }

    }

    @Override
    public void evaluate(final EvolutionState state, final Individual ind,
                         final int subpopulation, final int threadnum) {
        if (ind.evaluated) return;

        if (!(ind instanceof BlackjackIndividual))
            state.output.fatal("Whoa!  It's not an BlackjackIndividual!!!", null);
        BlackjackIndividual ind2 = (BlackjackIndividual) ind;
        BlackjackPlayer player = new BlackjackPlayer(STARTING_MONEY, ind2);
        BlackjackGame sim = new BlackjackGame();
        sim.setJobNumber(jobNumber);
        sim.addPlayer(player);
        sim.addEvolutionData(state, threadnum);

        if (!(ind2.fitness instanceof SimpleFitness))
            state.output.fatal("Whoa!  It's not a SimpleFitness!!!", null);

        //Fitness = Final funds * ((number of visited situations) / 320)
        double fitness = sim.playGames(NUM_OF_GAMES);

        ((SimpleFitness) ind2.fitness).setFitness(state,
                /// ...the fitness...
                fitness,
                ///... is the individual ideal?  Indicate here...
                fitness >= (STARTING_MONEY + 100000));
        ind2.evaluated = true;
    }

    @Override
    public void describe(EvolutionState var1, Individual var2, int var3, int var4, int var5) {
        BlackjackIndividual ind = (BlackjackIndividual) var2;
        StringBuilder genomeString = new StringBuilder();
        for (int i = 0; i < ind.genome.length; i++) {
            genomeString.append(ind.genome[i] + "_");
        }
        logger.info("JobNumber=" + jobNumber + "|BestFitness=" + var2.fitness.fitness()
                    + "|Genome=" + genomeString.toString());
        new GenomeDisplay(ind.genome);
        JOptionPane.showMessageDialog(null, "Best Fitness: " + var2.fitness.fitness());
    }
}

	
	

