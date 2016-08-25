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

    @Override
    public boolean okunmaSayisiniArttir(Engine eng)
    {
        super.okunmaSayisiniArttir(eng);

        if (!VeritabaniYoneticisi.makaleOkunmaSayisiArttir(eng))
        {
            return false;
        }
        if (!VeritabaniYoneticisi.kategoriOkunmaSayisiArttir(eng))
        {
            return false;
        }

        return true;
    }
}
