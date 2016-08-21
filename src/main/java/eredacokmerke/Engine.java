package eredacokmerke;

import java.util.ArrayList;
import java.util.List;

public class Engine
{

    private static List<String> listeKategoriler = new ArrayList<String>();

    public static List<String> getListeKategoriler()
    {
        return listeKategoriler;
    }

    public static void setListeKategoriler(List<String> listeKategoriler)
    {
        Engine.listeKategoriler = listeKategoriler;
    }
}
