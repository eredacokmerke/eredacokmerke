package eredacokmerke;

import java.util.ArrayList;
import java.util.List;

public class Engine
{

    private List<Kategori> listeKategoriler;//kategorilerin tutulacagi liste
    private List<Makale> listeCokOkunanMakaleler;//cok okunan makalelerin tutulacagi liste
    private List<Makale> listeSonEklenenMakaleler;//son eklenen makalelerin tutulacagi liste
    private List<Makale> listeKategoriMakaleler;//kategoriye ait makalelerin tutulacagi liste
    private int okunanMakaleID;
    private Makale okunanMakale;
    private int okunanKategoriID;
    private int numaralandirmaSayfaNumarasi;//makale listesinin altindaki numaralandirmada hangi sayfada olundugunu tutuyor
    private int numaralandirmaToplamSayfaNumarasi;//makale listesinin altindaki numaralandirmanin toplam sayfa sayisi

    private final int sayfaBasinaSonEklenenMakale = 3;//makale listelerinde bir sayfada kac makale gosterilecek

    public Engine(int sayfa)
    {
        switch (sayfa)//index sayfasinda kullanilacak degiskenler
        {
            case SayfaYoneticisi.SAYFA_INDEX:
                listeKategoriler = new ArrayList<Kategori>();
                listeCokOkunanMakaleler = new ArrayList<Makale>();
                listeSonEklenenMakaleler = new ArrayList<Makale>();
                numaralandirmaSayfaNumarasi = 1;
                break;

            case SayfaYoneticisi.SAYFA_MAKALE:
                listeKategoriler = new ArrayList<Kategori>();
                okunanMakaleID = -1;
                okunanMakale = null;
                okunanKategoriID = -1;
                break;

            case SayfaYoneticisi.SAYFA_KATEGORI:
                listeKategoriler = new ArrayList<Kategori>();
                listeKategoriMakaleler = new ArrayList<Makale>();
                okunanKategoriID = -1;
                numaralandirmaSayfaNumarasi = 1;
                break;

            default:
                HataYoneticisi.yazdir(11, "hatali sayfa id si");
        }
    }

    /**
     * makalenin kategorisinin idsini doner
     *
     * @param makaleID : makalenin idsi
     * @return eger basarili ise kategori id, hata olursa -1 doner
     */
    public String kategoriIDyiGetir(int makaleID)
    {
        return VeritabaniYoneticisi.kategoriIDyiGetir(makaleID);
    }

    public void numaralamaTiklandi(int sayfa)
    {
        numaralandirmaSayfaNumarasi = sayfa;
        VeritabaniYoneticisi.sonEklenenMakaleleriGetir(this);
    }

    public List<Kategori> getListeKategoriler()
    {
        return listeKategoriler;
    }

    public void setListeKategoriler(List<Kategori> listeKategoriler)
    {
        this.listeKategoriler = listeKategoriler;
    }

    public List<Makale> getListeCokOkunanMakaleler()
    {
        return listeCokOkunanMakaleler;
    }

    public void setListeCokOkunanMakaleler(List<Makale> listeCokOkunanMakaleler)
    {
        this.listeCokOkunanMakaleler = listeCokOkunanMakaleler;
    }

    public List<Makale> getListeSonEklenenMakaleler()
    {
        return listeSonEklenenMakaleler;
    }

    public void setListeSonEklenenMakaleler(List<Makale> listeSonEklenenMakaleler)
    {
        this.listeSonEklenenMakaleler = listeSonEklenenMakaleler;
    }

    public int getOkunanMakaleID()
    {
        return okunanMakaleID;
    }

    public void setOkunanMakaleID(int okunanMakaleID)
    {
        this.okunanMakaleID = okunanMakaleID;
    }

    public Makale getOkunanMakale()
    {
        return okunanMakale;
    }

    public void setOkunanMakale(Makale okunanMakale)
    {
        this.okunanMakale = okunanMakale;
    }

    public List<Makale> getListeKategoriMakaleler()
    {
        return listeKategoriMakaleler;
    }

    public void setListeKategoriMakaleler(List<Makale> listeKategoriMakaleler)
    {
        this.listeKategoriMakaleler = listeKategoriMakaleler;
    }

    public int getOkunanKategoriID()
    {
        return okunanKategoriID;
    }

    public void setOkunanKategoriID(int okunanKategoriID)
    {
        this.okunanKategoriID = okunanKategoriID;
    }

    public int getNumaralandirmaSayfaNumarasi()
    {
        return numaralandirmaSayfaNumarasi;
    }

    public void setNumaralandirmaSayfaNumarasi(int numaralandirmaSayfaNumarasi)
    {
        this.numaralandirmaSayfaNumarasi = numaralandirmaSayfaNumarasi;
    }

    public int getNumaralandirmaToplamSayfaNumarasi()
    {
        return numaralandirmaToplamSayfaNumarasi;
    }

    public void setNumaralandirmaToplamSayfaNumarasi(int numaralandirmaToplamSayfaNumarasi)
    {
        this.numaralandirmaToplamSayfaNumarasi = numaralandirmaToplamSayfaNumarasi;
    }

    public int getSayfaBasinaSonEklenenMakale()
    {
        return sayfaBasinaSonEklenenMakale;
    }
}
