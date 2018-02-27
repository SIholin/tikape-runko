
package tikape.runko.domain;


public class AnnosRaakaAine {
    private String maara;
    private Integer jarjestys;
    private Integer annosId;
    private Integer raakaaineId;
    private String lisaohje;
    
    public AnnosRaakaAine(Integer annosId, Integer raakaaineId, Integer jarjestys, String maara, String lisaohje) {
        this.annosId = annosId;
        this.raakaaineId = raakaaineId;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.lisaohje = lisaohje;
    }
    public String getLisaohje() {
        return this.lisaohje;
    }
    public Integer getAnnosId() {
        return this.annosId;
    }
    
    public Integer getRaakaaineId() {
        return this.raakaaineId;
    }
    
    public Integer getJarjestys(){
        return this.jarjestys;
    }
    
    public String getMaara(){
        return this.maara;
    }
}
