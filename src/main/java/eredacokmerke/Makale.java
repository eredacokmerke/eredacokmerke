package eredacokmerke;

import java.util.List;

public class Makale
{
    String id;
    String baslik;
    String icerik;
    String ozet;
    String tarih;
    String yazar;
    String okunma;
    List<String> listeEtiketler;

    public Makale(String id, String baslik, String icerik, String ozet, String tarih, String yazar, String okunma, List<String> listeEtiketler)
    {
        this.id = id;
        this.baslik = baslik;
        this.icerik = icerik;
        this.ozet = ozet;
        this.tarih = tarih;
        this.yazar = yazar;
        this.okunma = okunma;
        this.listeEtiketler = listeEtiketler;
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

    public List<String> getListeEtiketler()
    {
        return listeEtiketler;
    }

    public void setListeEtiketler(List<String> listeEtiketler)
    {
        this.listeEtiketler = listeEtiketler;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
