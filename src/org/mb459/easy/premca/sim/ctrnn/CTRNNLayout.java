/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.sim.ctrnn;

import org.mb459.easy.premca.util.Range;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;



/**
 * Specifies a layout for a CTRNN, with multiple layers of neurons.
 * @author Miles
 */
public class CTRNNLayout {
    
    public static CTRNNLayout fromJSONFile(String filename) throws FileNotFoundException {
        CTRNNLayout layout = (new JSONParser(filename)).getLayout();
        layout.filename = filename;
        return layout;
    }
    
    public String filename = "";
    public ArrayList<Layer> layers = new ArrayList<>();
    public int genomeLength = 0;
    public ArrayList<Integer> sensorInputs = new ArrayList<>();
    public ArrayList<Integer> motorOutputs = new ArrayList<>();
    public HashMap<Integer,PredictionPair> predPairs = new HashMap<>();
    public GeneMapping geneMapping = new GeneMapping();
    
    public CTRNNLayout() {
    }
    
    public int getTotalN(){
        int t = 0;
        for(Layer l : layers)
            t += l.neurons.size();
        return t;
    }
    public void addLayer(Layer l) {
        l.layerID = layers.size();
        layers.add(l);
    }
    
    
    
    public Neuron getFromID(int ID) {
        for(Layer l : layers)
            for(Neuron n : l.neurons)
                if(ID == n.ID)
                    return n;
        return null;
    }
    
    public void setFromID(int ID, Neuron newNeuron) {
        for(Layer l : layers)
            for(int i = 0; i < l.neurons.size(); i++)
                if(ID == l.neurons.get(i).ID)
                    l.neurons.set(i, newNeuron);
    }
    
    public boolean isConnected(int neuronID, int backID) {
        return (getFromID(backID).conns.contains(neuronID)) |
               (getFromID(neuronID).conns.contains(backID));
    }
    
    public void setSensors(int nSensor) {
        layers = new ArrayList<>(2);
//        sensorLayer = new SensorLayer(nSensor);
        layers.add(new Layer());
//        for(int i = 0; i < nSensor;i++)
//            sensorLayer.neurons.get(i).conns.add(layers.get(0).neurons.get(i).ID);
    }
    
    public void addInterLayer(int n) {
        layers.add(new Layer());
        setPrevLayerForwardConns();
    }
    
    public void setMotors() {
        layers.add(new Layer());
        setPrevLayerForwardConns();
        //layers.get(layers.size() - 1).neurons.get(0).conns.add(motorLayer.neurons.get(0).ID);
        //layers.get(layers.size() - 1).neurons.get(1).conns.add(motorLayer.neurons.get(1).ID);
    }
    
//    public Layer getSensorNeuronLayer() {
//        return layers.get(0);
//    }
    
    public Layer getMotorNeuronLayer() {
        return layers.get(layers.size() - 1);
    }
    
    private void setPrevLayerForwardConns() {
        layers.get(layers.size() - 2).setAllForwardConnections(layers.get(layers.size() - 1));
    }
    
    
    
    public ArrayList<Neuron> getAllNeurons() {
        ArrayList<Neuron> neurons = new ArrayList<>(getTotalN());
        for(Layer l : layers)
                for(Neuron n : l.neurons)
                    neurons.add(n);
        return neurons;
    }
    
    public Neuron getMotorNeuron(boolean getLeft) {
        if(getLeft)
            return getFromID(motorOutputs.get(0));
        else
            return getFromID(motorOutputs.get(1));
    }
    
    public int getGenomeLength() {
        return genomeLength;
    }
    
    
    public void update(float[] genes) {
        for (Layer layer : layers) {
            for (Neuron neuron : layer.neurons) {
                float tauG,biasG,gainG;
                ArrayList<Float>weightsG = new ArrayList<>(neuron.weightsG.size());
                
                if(neuron.tauGID == -1)
                    tauG = neuron.tauG;
                else
                    tauG = genes[neuron.tauGID];
                
                if(neuron.biasGID == -1)
                    biasG = neuron.biasG;
                else
                    biasG = genes[neuron.biasGID];
                
                if(neuron.gainGID == -1)
                    gainG = neuron.gainG;
                else
                    gainG = genes[neuron.gainGID];
                
                for(int i = 0; i< neuron.weightsGID.size(); i++) {
                    if(neuron.weightsGID.get(i) == -1)
                        weightsG.add(neuron.weightsG.get(i));
                    else
                        weightsG.add(genes[neuron.weightsGID.get(i)]);
                }
                
                neuron.setGenes(tauG, biasG, gainG, weightsG);
            }
        }
    }
    
    
    //Utilities
    
    private static class JSONParser {

        private final CTRNNLayout layout;
        
        public JSONParser(String filename) throws FileNotFoundException {
            FileInputStream fs = new FileInputStream(filename);
            layout = new CTRNNLayout();
            parseJSON(new JSONObject(new JSONTokener(fs)));
        }
        
        public CTRNNLayout getLayout() {
            return layout;
        }
        
        private ArrayList<CTRNNParamRanges> getParams(JSONObject jlayout) {
            JSONArray jparams = jlayout.getJSONArray("param_ranges");
            ArrayList<CTRNNParamRanges> tparams  = new ArrayList<>(jparams.length());
            //layout.genomeLength = jlayout.getInt("genomeLength");
            for(int i = 0; i< jparams.length(); i++) {
                JSONObject p = jparams.getJSONObject(i);
                tparams.add(new CTRNNParamRanges(
                        new Range(p.getJSONArray("tauR").getInt(0),p.getJSONArray("tauR").getInt(1)),
                        new Range(p.getJSONArray("gainR").getInt(0),p.getJSONArray("gainR").getInt(1)),
                        new Range(p.getJSONArray("biasR").getInt(0),p.getJSONArray("biasR").getInt(1)),
                        new Range(p.getJSONArray("weightsR").getInt(0),p.getJSONArray("weightsR").getInt(1))
                ));
            }
            return tparams;
        }
        
        private void parseJSON(JSONObject jlayout) {
            layout.layers.clear();
            ArrayList<CTRNNParamRanges> tparams  = getParams(jlayout);

            JSONArray jlayers = jlayout.getJSONArray("layers");
            ArrayList<Layer> tLayers = new ArrayList<>(jlayers.length());

            for(int i = 0; i < jlayers.length(); i++) {
                
                JSONArray jNeurs = jlayers.getJSONObject(i).getJSONArray("neurons");
                
                ArrayList<Neuron> tNeurs = new ArrayList<>(jNeurs.length());
                
                for(int j = 0; j < jNeurs.length(); j++) {
                    JSONObject tn = jNeurs.getJSONObject(j);
                    int ID  = tn.getInt("id");
                    CTRNNParamRanges param = tparams.get(tn.getInt("paramID"));
                    ArrayList<Integer> conns;
                    JSONArray jconns = tn.optJSONArray("conns");
                    if(jconns == null) {
                        conns = null;
                    } else {
                        conns = new ArrayList<>(jconns.length());
                        for(int k =0; k< jconns.length(); k++) {
                            conns.add(jconns.getInt(k));
                        }
                    }
                    Neuron n = new Neuron(
                            ID,
                            param,
                            conns
                    );
                    String tauStr = tn.getString("tau");
                    int tGID = -1;
                    if(tauStr.substring(0,1).equals("g")) {
                        tGID = Integer.parseInt(tauStr.substring(1));
                        layout.geneMapping.add(tGID, n.ID, GeneMapping.Parameter.TAU);
                    }
                    else
                        n.tauG = Float.valueOf(tauStr);

                    String biasStr = tn.getString("bias");
                    int bGID = -1;
                    if(biasStr.substring(0,1).equals("g")) {
                        bGID = Integer.parseInt(biasStr.substring(1));
                        layout.geneMapping.add(bGID, n.ID, GeneMapping.Parameter.BIAS);
                    }
                    else
                        n.biasG = Float.valueOf(biasStr);

                    String gainStr = tn.getString("gain");
                    int gGID = -1;
                    if(gainStr.substring(0,1).equals("g")) {
                        gGID = Integer.parseInt(gainStr.substring(1));
                        layout.geneMapping.add(gGID, n.ID, GeneMapping.Parameter.GAIN);
                    }
                    else
                        n.gainG = Float.valueOf(gainStr);

                    JSONArray weights = tn.optJSONArray("weights");
                    ArrayList<Integer> wGID = new ArrayList<>();
                    ArrayList<Float> wG = new ArrayList<>();
                    if(weights != null) {
                        for(int k = 0; k < weights.length(); k++) {
                            int t = -1;
                            String w = weights.getString(k);
                            if(w.substring(0,1).equals("g")) {
                                t = Integer.parseInt(w.substring(1));
                                wGID.add(t);
                                wG.add(0.0f);
                                int neuronID2 = n.conns.get(k);
                                layout.geneMapping.addWeight(t, n.ID, neuronID2);
                            } else {
                                wGID.add(t);
                                wG.add(Float.parseFloat(w));
                            }                        
                        }
                        n.weightsG = wG;
                    }

                    n.setGeneLoci(tGID, bGID, gGID, wGID);
                    tNeurs.add(n);
                    
                    if(tn.has("predPair")) {
                        int predPair = tn.optInt("predPair");
                        PredictionPair pair;
                        if(layout.predPairs.containsKey(predPair)) {
                            pair = layout.predPairs.get(predPair);
                        } else {
                            pair = new PredictionPair();
                        }
                        pair.addNeuron(n);
                        layout.predPairs.put(predPair, pair);
                    }
                }
                tLayers.add(new Layer(tNeurs));
            }
            layout.genomeLength = layout.geneMapping.getGeneCount();
            layout.layers = tLayers;
            layout.sensorInputs.clear();
            for(int i = 0; i < jlayout.getJSONArray("sensorInputs").length(); i++)
                layout.sensorInputs.add(jlayout.getJSONArray("sensorInputs").getInt(i));

            layout.motorOutputs.clear();
            for(int i = 0; i < jlayout.getJSONArray("motorOutputs").length(); i++)
                layout.motorOutputs.add(jlayout.getJSONArray("motorOutputs").getInt(i));

        }
    }
}
