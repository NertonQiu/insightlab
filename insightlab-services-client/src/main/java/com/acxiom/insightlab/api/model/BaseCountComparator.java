package com.acxiom.insightlab.api.model;

import java.util.Comparator;

public class BaseCountComparator implements Comparator<PersonicxPortraitRow> {
    public int compare(PersonicxPortraitRow obj1, PersonicxPortraitRow obj2) {
        return obj1.getBaseCount()- obj2.getBaseCount();
    }
}