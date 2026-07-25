package Lineales.Estaticas;

public class Fecha {
    private int dia, anio;
    private String mes;


    public Fecha(int unDia, String unMes, int unAnio){
        this.anio = unAnio;
        this.dia = unDia;
        this.mes = unMes;
    }

    public String toString(){
        return  " "+ dia + " "+ mes + " "+
          anio + " .";          


    }
}
