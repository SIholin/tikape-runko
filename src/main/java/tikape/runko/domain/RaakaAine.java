
package tikape.runko.domain;


public class RaakaAine {
    private String nimi;
    private Integer id;
    
    public RaakaAine(Integer id, String nimi) {
        this.nimi = nimi;
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public String getNimi() {
        return this.nimi;
    }
}
