package com.chaoching.springframework.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
    private final List<PropertyValue> propertyValues;

    public PropertyValues() {
        this.propertyValues = new ArrayList<>();
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValues.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyValueName) {
        for (PropertyValue pv : this.propertyValues) {
            if (pv.getName().equals(propertyValueName)) {
                return pv;
            }
        }
        return null;
    }

    public void addPropertyValue(PropertyValue pv) {
        this.propertyValues.add(pv);
    }
}
