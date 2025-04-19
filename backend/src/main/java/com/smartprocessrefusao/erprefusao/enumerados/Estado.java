package com.smartprocessrefusao.erprefusao.enumerados;

public enum Estado {

	AC(1, "Acre", "AC", "Brasil"),
    AL(2, "Alagoas", "AL", "Brasil"),
    AP(3, "Amapá", "AP", "Brasil"),
    AM(4, "Amazonas", "AM", "Brasil"),
    BA(5, "Bahia", "BA", "Brasil"),
    CE(6, "Ceará", "CE", "Brasil"),
    DF(7, "Distrito Federal", "DF", "Brasil"),
    ES(8, "Espírito Santo", "ES", "Brasil"),
    GO(9, "Goiás", "GO", "Brasil"),
    MA(10, "Maranhão", "MA", "Brasil"),
    MT(11, "Mato Grosso", "MT", "Brasil"),
    MS(12, "Mato Grosso do Sul", "MS", "Brasil"),
    MG(13, "Minas Gerais", "MG", "Brasil"),
    PA(14, "Pará", "PA", "Brasil"),
    PB(15, "Paraíba", "PB", "Brasil"),
    PR(16, "Paraná", "PR", "Brasil"),
    PE(17, "Pernambuco", "PE", "Brasil"),
    PI(18, "Piauí", "PI", "Brasil"),
    RJ(19, "Rio de Janeiro", "RJ", "Brasil"),
    RN(20, "Rio Grande do Norte", "RN", "Brasil"),
    RS(21, "Rio Grande do Sul", "RS", "Brasil"),
    RO(22, "Rondônia", "RO", "Brasil"),
    RR(23, "Roraima", "RR", "Brasil"),
    SC(24, "Santa Catarina", "SC", "Brasil"),
    SP(25, "São Paulo", "SP", "Brasil"),
    SE(26, "Sergipe", "SE", "Brasil"),
    TO(27, "Tocantins", "TO", "Brasil");

	private final int id;
    private final String nome;
    private final String uf;
    private final String pais;

    Estado(int id, String nome, String uf, String pais) {
    	this.id = id;
    	this.nome = nome;
        this.uf = uf;
        this.pais = pais;
    }

   
	public int getId() {
		return id;
	}


	public String getNome() {
        return nome;
    }

    public String getUf() {
        return uf;
    }

	public String getPais() {
		return pais;
	}
    
    
	 public static Estado fromUf(String uf) {
	        for (Estado estado : values()) {
	            if (estado.name().equalsIgnoreCase(uf)) {
	                return estado;
	            }
	        }
	        throw new IllegalArgumentException("UF inválida: " + uf);
	    }
	
	
	
}