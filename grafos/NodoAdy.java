package grafos;

public class NodoAdy {

    //Variables
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private int etiqueta;

    //Constructor
   /* public NodoAdy(){
        this.vertice = null;
        this.sigAdyacente = null;
        this.etiqueta = -1;
    }*/
    public NodoAdy(NodoVert vertice, int etiqueta){
        this.vertice = vertice;
        this.sigAdyacente = null;
        this.etiqueta = etiqueta;
    }

    //Getters
    public NodoVert getVertice(){
        return this.vertice;
    }
    public NodoAdy getSigAdyacente(){
        return this.sigAdyacente;
    }
    public int getEtiqueta(){
        return this.etiqueta;
    }
    
    //Setters
    public void setVertice(NodoVert vert){
        this.vertice = vert;
    }

    public void setSigAdyacente(NodoAdy siguiente){
        this.sigAdyacente = siguiente;
    }

    public void setEtiqueta(int etiqueta){
        this.etiqueta = etiqueta;
    }

}
