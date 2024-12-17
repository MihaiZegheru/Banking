package org.poo.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IDGenerator {
    private int id = 0;

    /**
     * Generates a unique identifier per living instance.
     * @return id as int
     */
    public int next() {
        id += 1;
        return id;
    }
}
