package com.smartprocessrefusao.erprefusao.enumerados;

public enum AluminumAlloyFootage {
	METRAGEM_3("3"), METRAGEM_6("6");

	private final String metragem;

	AluminumAlloyFootage(String metragem) {
		this.metragem = metragem;
	}

	public String getMetragem() {
		return metragem;
	}

	@Override
	public String toString() {
		return metragem + " m";
	}
}
