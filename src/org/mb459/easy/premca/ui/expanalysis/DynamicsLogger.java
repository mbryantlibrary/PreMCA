package org.mb459.easy.premca.ui.expanalysis;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mb459.easy.premca.exp.ExpParam;
import org.mb459.easy.premca.genesis.AgentGenotype;
import org.mb459.easy.premca.sim.World;
import org.mb459.easy.premca.util.StringToFile;

/**
 *
 * @author Miles Bryant (mb459@sussex.ac.uk)
 */
public class DynamicsLogger {
    public static void doLog(String filename,AgentGenotype g) {
        ExpParam param = new ExpParam();
        World world = new World(
                0,
                0,
                g,
                param
        );
        world.setLoggingEnabled(true);
        while(!world.circleReachedAgent()) {
            world.step();
        }
        try {
            StringToFile.save(filename,world.getLoggingData());
        } catch (IOException ex) {
            Logger.getLogger(DynamicsLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
