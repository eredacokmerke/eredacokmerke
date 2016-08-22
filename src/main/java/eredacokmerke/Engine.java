package eredacokmerke;

import java.util.ArrayList;
import java.util.List;

public class Engine
{

    private List<String> listeKategoriler;//kategorilerin tutulacagi liste
    private List<Makale> listeCokOkunanMakaleler;//cok okunan makalelerin tutulacagi liste
    private List<Makale> listeSonEklenenMakaleler;//son eklenen makalelerin tutulacagi liste

    public Engine(int sayfa)
    {
        if (sayfa == SayfaYoneticisi.SAYFA_INDEX)//index sayfasinda kullanilacak degiskenler
        {
            listeKategoriler = new ArrayList<String>();
            listeCokOkunanMakaleler = new ArrayList<Makale>();
            listeSonEklenenMakaleler = new ArrayList<Makale>();
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
}
