
package tikape.runko.domain;


public class AnnosRaakaAine {
    private String maara;
    private Integer jarjestys;
    private Integer annosId;
    private Integer raakaaineId;
    
    public AnnosRaakaAine(Integer annosId, Integer raakaaineId, Integer jarjestys, String maara) {
        this.annosId = annosId;
        this.raakaaineId = raakaaineId;
        this.jarjestys = jarjestys;
        this.maara = maara;
    }
}
