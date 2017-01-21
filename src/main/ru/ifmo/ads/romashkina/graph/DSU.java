package ru.ifmo.ads.romashkina.graph;

public class DSU {
    private int[] parent;
    private int[] rank;

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    private int get(int i) {
        if (parent[i] != i) {
            parent[i] = get(parent[i]);
        }
        return parent[i];
    }

    public void union(int x, int y) {
        x = get(x);
        y = get(y);
        if (x == y) return;

        if (rank[x] == rank[y]) {
            rank[x]++;
        }

        if (rank[x] < rank[y]) {
            parent[x] = y;
        } else {
            parent[y] = x;
        }
    }

    public boolean connected(int x, int y) {
        return get(x) == get(y);
    }
}
