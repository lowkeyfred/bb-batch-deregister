package edu.scupi;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class FileHandler {
    public static TreeMap<String, List<String>> getMap(File file) {
        TreeMap<String, List<String>> cuList = new TreeMap<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            for (String[] nextLine : reader) {
                if (2 == nextLine.length) {
                    String course = nextLine[0].trim();

                    if (cuList.containsKey(course)) {
                        cuList.get(course).add(nextLine[1]);
                        cuList.get(course).sort(Comparator.naturalOrder());
                    } else {
                        List<String> users = new ArrayList<>();
                        users.add(nextLine[1]);
                        cuList.put(course, users);
                    }
                }

            }
        } catch (IOException e) {
            cuList = null;
        }

        return cuList;
    }
}
