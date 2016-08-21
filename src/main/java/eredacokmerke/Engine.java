package eredacokmerke;

import java.util.ArrayList;
import java.util.List;

public class Engine
{

    private List<String> listeKategoriler;

    public Engine()
    {
        listeKategoriler = new ArrayList<String>();
    }

    public List<String> getListeKategoriler()
    {
        return listeKategoriler;
    }

    public void setListeKategoriler(List<String> listeKategoriler)
    {
        this.listeKategoriler = listeKategoriler;
    }
}
