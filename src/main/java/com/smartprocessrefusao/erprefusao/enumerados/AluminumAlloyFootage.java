package com.smartprocessrefusao.erprefusao.enumerados;

public enum AluminumAlloyFootage {
	METRAGEM_3_0("3,0"), METRAGEM_3_5("3,5"), METRAGEM_4_0("4,0"), METRAGEM_4_5("4,5"), METRAGEM_5_0("5,0"), METRAGEM_5_5("5,5"), METRAGEM_6_0("6,0");

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
