package mx.itson.sgi.data_access.entities;

public enum Beca {
    DEPORTIVA(50,"deportiva"),
    SEC(50,"sec"),
    CIVICA(50,"civica"),
    NINGUNA(0,"ninguna");
    
    private final int procentajeDescuento;
    private final String tipo;

    private Beca(int procentajeDescuento, String tipo){
        this.procentajeDescuento = procentajeDescuento;
        this.tipo = tipo;
    }

    public String getTipo(){
        return tipo;
    }

    public int getProcentajeDescuento(){
        return procentajeDescuento;
    }
}
