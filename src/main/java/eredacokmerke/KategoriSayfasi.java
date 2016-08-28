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
        if (!VeritabaniYoneticisi.kategoriMakalelerNumaralandirmaSayisiGetir(eng))
        {
            return false;
        }
        if (!VeritabaniYoneticisi.kategoriMakaleleriGetir(eng))
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean okunmaSayisiniArttir(Engine eng)
    {
        super.okunmaSayisiniArttir(eng);

        if (!VeritabaniYoneticisi.kategoriOkunmaSayisiArttir(eng))
        {
            return false;
        }

        return true;
    }
}
