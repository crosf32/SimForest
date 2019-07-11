package fr.crosf32.fxtest.utils;

import com.opencsv.CSVWriter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvForestGenerator {
    public void generateCsv(List<String[]> stats) {
        try {
            CSVWriter writer = new CSVWriter(
                    new OutputStreamWriter(new FileOutputStream("export.csv"),
                            StandardCharsets.UTF_8), ';', '"', '"', "\n");
            for (String[] ar : stats) {
                writer.writeNext(ar);
            }

            writer.close();
            Desktop.getDesktop().open((new File("./")));
        } catch(Exception e) {
            System.out.println("Impossible de générer le csv");
        }
    }
}
