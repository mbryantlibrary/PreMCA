/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.exp;

import org.mb459.easy.premca.genesis.AgentGenotype;
import org.mb459.easy.premca.sim.World;

/**
 *
 * @author Miles Bryant
 */
public class Trial {
    World world;
    public Trial(float circleDisp, float circleX, AgentGenotype g, ExpParam params) {
        //int ranDX = (int)Math.round(Math.random() * 12 - 6);
        world = new World(
                circleDisp,
                circleX,
                g,
                params
        );
    }
    
    public float run() {
        float predSum = 0f;
        int tSteps = 0;
        while(!world.circleReachedAgent()) {
            world.step();
            predSum += world.getPredictionError();
            tSteps++;
        }
        //float max = 1.5 * (world.agent.r + world.circle.r);
        float max = 90;
        return predSum / tSteps;
    }
    
    public float clip(float x, float m) {
        if(x > m)
            return m;
        else
            return x;
    }
}
