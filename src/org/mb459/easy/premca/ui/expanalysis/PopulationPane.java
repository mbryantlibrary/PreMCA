/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.ui.expanalysis;

/**
 *
 * @author Miles
 */
public class PopulationPane extends javax.swing.JPanel {

    /**
     * Creates new form PopulationPane
     */
    public PopulationPane() {
        initComponents();
        
        ((PopulationTableModel)tblPop.getModel()).loadFromCSV(System.getProperty("user.dir") + "\\population-1.csv");
    }

    public PopulationTableModel.DataIndividual getSelectedInd() {
        if(tblPop.getSelectedRow() == -1)
            return null;
        return ((PopulationTableModel)tblPop.getModel()).getInd(tblPop.getSelectedRow());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPop = new javax.swing.JTable();

        tblPop.setAutoCreateRowSorter(true);
        tblPop.setModel(new PopulationTableModel());
        tblPop.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblPop.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblPop);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPop;
    // End of variables declaration//GEN-END:variables
}
