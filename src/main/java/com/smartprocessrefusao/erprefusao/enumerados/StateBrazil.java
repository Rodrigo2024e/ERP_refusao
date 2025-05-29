package com.smartprocessrefusao.erprefusao.enumerados;

public enum StateBrazil {

    AC(1L, "AC", "Acre", "Brasil"),
    AL(2L, "AL", "Alagoas", "Brasil"),
    AM(3L, "AM", "Amazonas", "Brasil"),
    AP(4L, "AP", "Amapá", "Brasil"),
    BA(5L, "BA", "Bahia", "Brasil"),
    CE(6L, "CE", "Ceará", "Brasil"),
    DF(7L, "DF", "Distrito Federal", "Brasil"),
    ES(8L, "ES", "Espírito Santo", "Brasil"),
    GO(9L, "GO", "Goiás", "Brasil"),
    MA(10L, "MA", "Maranhão", "Brasil"),
    MG(11L, "MG", "Minas Gerais", "Brasil"),
    MS(12L, "MS", "Mato Grosso do Sul", "Brasil"),
    MT(13L, "MT", "Mato Grosso", "Brasil"),
    PA(14L, "PA", "Pará", "Brasil"),
    PB(15L, "PB", "Paraíba", "Brasil"),
    PE(16L, "PE", "Pernambuco", "Brasil"),
    PI(17L, "PI", "Piauí", "Brasil"),
    PR(18L, "PR", "Paraná", "Brasil"),
    RJ(19L, "RJ", "Rio de Janeiro", "Brasil"),
    RN(20L, "RN", "Rio Grande do Norte", "Brasil"),
    RO(21L, "RO", "Rondônia", "Brasil"),
    RR(22L, "RR", "Roraima", "Brasil"),
    RS(23L, "RS", "Rio Grande do Sul", "Brasil"),
    SC(24L, "SC", "Santa Catarina", "Brasil"),
    SE(25L, "SE", "Sergipe", "Brasil"),
    SP(26L, "SP", "São Paulo", "Brasil"),
    TO(27L, "TO", "Tocantins", "Brasil");

    private final Long id;
    private final String uf;
    private final String nameState;
    private final String country;

    StateBrazil(Long id, String uf, String nameState, String country) {
        this.id = id;
        this.uf = uf;
        this.nameState = nameState;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getUf() {
        return uf;
    }

    public String getNameState() {
        return nameState;
    }

    public String getCountry() {
        return country;
    }

    public static StateBrazil fromUf(String uf) {
        if (uf == null || uf.isBlank()) {
            throw new IllegalArgumentException("UF não pode ser nula ou vazia.");
        }
        for (StateBrazil state : values()) {
            if (state.getUf().equalsIgnoreCase(uf)) {
                return state;
            }
        }
        throw new IllegalArgumentException("UF inválida: " + uf);
    }

    public static StateBrazil fromId(Long id) {
        for (StateBrazil state : values()) {
            if (state.getId().equals(id)) {
                return state;
            }
        }
        throw new IllegalArgumentException("ID de estado inválido: " + id);
    }
}


