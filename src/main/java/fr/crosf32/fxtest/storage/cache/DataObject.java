package fr.crosf32.fxtest.storage.cache;

public class DataObject {
    private final String name;

    private static int objectCounter = 0;

    public DataObject(String name) {
        this.name = name;
    }

    public static DataObject get(String name) {
        objectCounter++;
        return new DataObject(name);
    }

    public String getName() {
        return name;
    }
}
