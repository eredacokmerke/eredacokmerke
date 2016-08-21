package eredacokmerke;

import java.util.ArrayList;
import java.util.List;

public class Engine
{

    private List<String> listeKategoriler;//kategorilerin tutulacagi liste
    private List<Makale> listeMakaleler;//makalelerin tutulacagi liste

    public Engine(int sayfa)
    {
        if (sayfa == SayfaYoneticisi.SAYFA_INDEX)//index sayfasinda kullanilacak degiskenler
        {
            listeKategoriler = new ArrayList<String>();
            listeMakaleler = new ArrayList<Makale>();
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

    public List<Makale> getListeMakaleler()
    {
        return listeMakaleler;
    }

    public void setListeMakaleler(List<Makale> listeMakaleler)
    {
        this.listeMakaleler = listeMakaleler;
    }
}
