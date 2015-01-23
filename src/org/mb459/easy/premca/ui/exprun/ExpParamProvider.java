/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.ui.exprun;

import org.mb459.easy.premca.exp.ExpParam;
import org.mb459.easy.premca.sim.ctrnn.CTRNNLayout;

/**
 *
 * @author Miles
 */
public interface ExpParamProvider {
    public ExpParam getParams();
    public CTRNNLayout getCTRNNLayout();
}
