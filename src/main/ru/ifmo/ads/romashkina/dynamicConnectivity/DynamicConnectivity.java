package ru.ifmo.ads.romashkina.dynamicConnectivity;

public interface DynamicConnectivity {
    void link(String v, String  u);
    void cut(String v, String  u);
    boolean areConnected(String v, String  u);
}
