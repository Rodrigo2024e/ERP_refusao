package com.smartprocessrefusao.erprefusao.enumerados;

public enum AluminumAlloyPol {
    POLEGADA_4("4"),
    POLEGADA_5("5"),
    POLEGADA_6("6"),
    POLEGADA_7("7");

    private final String polegada;

    AluminumAlloyPol(String polegada) {
        this.polegada = polegada;
    }

    public String getPolegada() {
        return polegada;
    }

    @Override
    public String toString() {
        return polegada + " Pol";
    }
}
