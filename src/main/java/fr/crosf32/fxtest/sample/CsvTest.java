package fr.crosf32.fxtest.sample;

import com.opencsv.CSVWriter;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class CsvTest {
    public static void main(String[] args) throws Exception {

        CSVWriter writer = new CSVWriter(
                new OutputStreamWriter(new FileOutputStream("test4.csv"),
                        StandardCharsets.UTF_8), ';', '"', '"', "\n");
        String[] t = new String[]{"fdfd", "dfdfd"};
        List<String[]> aa = new ArrayList<>();
        aa.add(t);
        for (String[] ar : aa) {
            writer.writeNext(ar);
        }

        writer.close();

    }
}