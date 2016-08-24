package eredacokmerke;

public class KategoriSayfasi extends SayfaYoneticisi
{

    public KategoriSayfasi()
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
        if (!VeritabaniYoneticisi.kategoriMakaleleriGetir(eng))
        {
            return false;
        }

        return true;
    }
}
