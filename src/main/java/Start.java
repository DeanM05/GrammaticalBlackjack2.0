package main.java;

import ec.Evolve;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

public class Start {

    public static void main(String[] args) {

        String[] runConfig = new String[]{
                Evolve.A_FILE,
                "src/main/resources/blackjack." +
                        "params"
        };
        Evolve.main(runConfig);
    }
}
