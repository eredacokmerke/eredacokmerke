package eredacokmerke;

public class IndexSayfasi extends SayfaYoneticisi
{

    public IndexSayfasi()
    {
    }

    @Override
    public boolean gerekliVerileriGetir(Engine eng)
    {
        super.gerekliVerileriGetir(eng);
        if (!VeritabaniYoneticisi.kategorileriGetir(eng))
        {
            return false;
        }

        return true;
    }
}
