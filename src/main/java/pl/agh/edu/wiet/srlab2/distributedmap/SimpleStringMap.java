package pl.agh.edu.wiet.srlab2.distributedmap;

public interface SimpleStringMap {
    boolean containsKey(String key);
    String get(String key);
    String put(String key, String value);
    String remove(String key);

    enum Operation {
        PUT,
        REMOVE
    }
}
