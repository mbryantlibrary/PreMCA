/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.ui.expanalysis;

import org.mb459.easy.premca.exp.ExpParam;
import org.mb459.easy.premca.genesis.AgentGenotype;
import org.mb459.easy.premca.sim.World;
import org.mb459.easy.premca.sim.ctrnn.CTRNNLayout;
import org.mb459.easy.premca.util.FileSaver;

/**
 *
 * @author mb459
 */
public class ExpRunLogger {

    String loggingDirectory = "";
    int nRuns = 10;
    AgentGenotype genotype;

    public ExpRunLogger(int nRuns, String loggingDirectory, AgentGenotype genotype) {
        this.nRuns = nRuns;
        this.loggingDirectory = loggingDirectory;
        this.genotype = genotype;
    }

    public void doLogging() {
        for (int i = 0; i < nRuns; i++) {
            World world = new World(
                    0,
                    0,
                    genotype,
                    new ExpParam()
            );
            world.setLoggingEnabled(true);
            while (!world.circleReachedAgent()) {
                world.step();
            }
            FileSaver.saveStringToFile(world.getLoggingData(), loggingDirectory + "\\run-" + i + ".txt");
        }
    }
}
