/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mb459.easy.premca.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mb459
 */
public class FileSaver {
    private static final Logger LOG = Logger.getLogger(FileSaver.class.getName());
    
    
    public static void saveStringToFile(String stringToSave, String filename) {
        try {
            File file = new File(filename);
                new File(file.getParent()).mkdirs();
                PrintStream out = new PrintStream(file);
                out.print(stringToSave);
            } catch (FileNotFoundException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
    }
}
