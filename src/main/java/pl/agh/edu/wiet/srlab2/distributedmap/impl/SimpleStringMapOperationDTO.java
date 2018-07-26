package pl.agh.edu.wiet.srlab2.distributedmap.impl;

import pl.agh.edu.wiet.srlab2.distributedmap.SimpleStringMap;

public class SimpleStringMapOperationDTO {
    private SimpleStringMap.Operation operation;
    private String key;
    private String value;

    public SimpleStringMapOperationDTO(SimpleStringMap.Operation operation, String key, String value) {
        this.operation = operation;
        this.key = key;
        this.value = value;
    }

    public SimpleStringMapOperationDTO(SimpleStringMap.Operation operation, String key) {
        this(operation, key, null);
    }

    public SimpleStringMapOperationDTO() {}

    public SimpleStringMap.Operation getOperation() {
        return operation;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleStringMapOperationDTO that = (SimpleStringMapOperationDTO) o;

        if (operation != that.operation) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = operation != null ? operation.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
