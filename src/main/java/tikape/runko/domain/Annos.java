
package tikape.runko.domain;


public class Annos {
    private String nimi;
    private Integer id;
    private String ohje;
    
    public Annos(Integer id, String nimi, String ohje) {
        this.id = id;
        this.nimi = nimi;
        this.ohje = ohje;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public String getNimi() {
        return this.nimi;
    }
    
    public String getOhje(){
        return this.ohje;
    }
}
