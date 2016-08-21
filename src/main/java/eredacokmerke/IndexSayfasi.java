package eredacokmerke;

public class IndexSayfasi extends SayfaYoneticisi
{

    public IndexSayfasi()
    {
    }

    @Override
    public boolean gerekliVerileriGetir()
    {
        super.gerekliVerileriGetir();
        if (!VeritabaniYoneticisi.kategorileriGetir())
        {
            return false;
        }

        return true;
    }
}
