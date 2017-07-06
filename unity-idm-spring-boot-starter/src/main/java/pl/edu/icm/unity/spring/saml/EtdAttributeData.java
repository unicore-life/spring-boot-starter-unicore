package pl.edu.icm.unity.spring.saml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.synchronizedList;

final class EtdAttributeData {
    private final Map<String, List<String>> attributes = new ConcurrentHashMap<>();

    private EtdAttributeData() {
    }

    void put(String name, String value) {
        if (attributes.get(name) == null) {
            attributes.put(name, synchronizedList(new ArrayList()));
        }
        attributes.get(name).add(value);
    }

    void put(String name, List<String> values) {
        if (attributes.get(name) == null) {
            attributes.put(name, synchronizedList(new ArrayList()));
        }
        attributes.get(name).addAll(values);
    }

    Map<String, List<String>> getAttributes() {
        return attributes;
    }

    void merge(EtdAttributeData other) {
        for (Map.Entry<String, List<String>> entry : other.getAttributes().entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    boolean containsKey(String name) {
        return attributes.containsKey(name);
    }

    static class Builder {
        static EtdAttributeData build() {
            return new EtdAttributeData();
        }
    }
}
