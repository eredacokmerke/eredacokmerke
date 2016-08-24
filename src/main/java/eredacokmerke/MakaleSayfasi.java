package eredacokmerke;

public class MakaleSayfasi extends SayfaYoneticisi
{

    public MakaleSayfasi()
    {
    }

    @Override
    public boolean gerekliVerileriGetir(Engine eng)
    {
        super.gerekliVerileriGetir(eng);

        if (!VeritabaniYoneticisi.makaleGetir(eng))
        {
            return false;
        }
        if (!VeritabaniYoneticisi.kategorileriGetir(eng))
        {
            return false;
        }

        return true;
    }

}
