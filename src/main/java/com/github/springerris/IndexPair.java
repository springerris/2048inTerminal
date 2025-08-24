package com.github.springerris;

public record IndexPair(int x, int y) {
    @Override
    public String toString() {
        return "x="+x+" y="+y;
    }
}
