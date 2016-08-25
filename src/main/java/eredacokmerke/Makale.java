package eredacokmerke;

public class Makale
{

    String id;
    String baslik;
    String icerik;
    String ozet;
    String tarih;
    String yazar;
    String okunma;
    String etiket;
    String etiketID;
    String resim;

    public Makale(String id, String baslik, String icerik, String ozet, String tarih, String yazar, String okunma, String etiket, String etiketID, String resim)
    {
        this.id = id;
        this.baslik = baslik;
        this.icerik = icerik;
        this.ozet = ozet;
        this.tarih = tarih;
        this.yazar = yazar;
        this.okunma = okunma;
        this.etiket = etiket;
        this.etiketID = etiketID;
        this.resim = resim;
    }

    public String getEtiketID()
    {
        return etiketID;
    }

    public void setEtiketID(String etiketID)
    {
        this.etiketID = etiketID;
    }

    public String getOkunma()
    {
        return okunma;
    }

    public void setOkunma(String okunma)
    {
        this.okunma = okunma;
    }

    public String getOzet()
    {
        return ozet;
    }

    public void setOzet(String ozet)
    {
        this.ozet = ozet;
    }

    public String getBaslik()
    {
        return baslik;
    }

    public void setBaslik(String baslik)
    {
        this.baslik = baslik;
    }

    public String getIcerik()
    {
        return icerik;
    }

    public void setIcerik(String icerik)
    {
        this.icerik = icerik;
    }

    public String getTarih()
    {
        return tarih;
    }

    public void setTarih(String tarih)
    {
        this.tarih = tarih;
    }

    public String getYazar()
    {
        return yazar;
    }

    public void setYazar(String yazar)
    {
        this.yazar = yazar;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getEtiket()
    {
        return etiket;
    }

    public void setEtiket(String etiket)
    {
        this.etiket = etiket;
    }

    public String getResim()
    {
        return resim;
    }

    public void setResim(String resim)
    {
        this.resim = resim;
    }
}
