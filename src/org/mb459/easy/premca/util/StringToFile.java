/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mb459.easy.premca.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mb459.easy.premca.exp.ExperimentController;

/**
 *
 * @author Miles Bryant (mb459@sussex.ac.uk)
 */
public class StringToFile {
    public static void save(String filename, String stringToSave) throws IOException {
            Files.createDirectories(Paths.get(filename).getParent());
            File infoFile = new File(filename);
            try (FileWriter out = new FileWriter(infoFile)) {
                out.append(stringToSave);                
                out.flush();
            } catch (IOException ex) {
            throw(ex);
        }
    }
}
