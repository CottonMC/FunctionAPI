package io.github.cottonmc.functionapi.script;

import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;

import java.util.Objects;

public class FunctionAPIIdentifierImpl implements FunctionAPIIdentifier {
    private String namespace;
    private String path;

    public FunctionAPIIdentifierImpl(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionAPIIdentifierImpl that = (FunctionAPIIdentifierImpl) o;
        return Objects.equals(namespace, that.namespace) &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, path);
    }

    @Override
    public String toString() {
        return "FunctionAPIIdentifierImpl{" +
                "namespace='" + namespace + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
