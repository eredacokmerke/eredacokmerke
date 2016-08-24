package eredacokmerke;

import java.util.ArrayList;
import java.util.List;

public class Engine
{

    private List<String> listeKategoriler;//kategorilerin tutulacagi liste
    private List<Makale> listeCokOkunanMakaleler;//cok okunan makalelerin tutulacagi liste
    private List<Makale> listeSonEklenenMakaleler;//son eklenen makalelerin tutulacagi liste
    private int okunanMakaleID;
    private Makale okunanMakale;

    public Engine(int sayfa)
    {
        switch (sayfa)//index sayfasinda kullanilacak degiskenler
        {
            case SayfaYoneticisi.SAYFA_INDEX:
                listeKategoriler = new ArrayList<String>();
                listeCokOkunanMakaleler = new ArrayList<Makale>();
                listeSonEklenenMakaleler = new ArrayList<Makale>();
                break;

            case SayfaYoneticisi.SAYFA_MAKALE:
                listeKategoriler = new ArrayList<String>();
                okunanMakaleID = -1;
                okunanMakale = null;
                break;
        }
    }

    public List<String> getListeKategoriler()
    {
        return listeKategoriler;
    }

    public void setListeKategoriler(List<String> listeKategoriler)
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
}
