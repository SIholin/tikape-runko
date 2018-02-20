
package tikape.runko.domain;


public class Annos {
    private String nimi;
    private Integer id;
    
    public Annos(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public String getNimi() {
        return this.nimi;
    }
}
