package org.poo.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IDGenerator {
    private int id = 0;

    public int next() {
        id += 1;
        return id;
    }
}
