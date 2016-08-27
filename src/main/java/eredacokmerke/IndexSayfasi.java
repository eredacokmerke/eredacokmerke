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
        if (!VeritabaniYoneticisi.sonEklenenMakalelerNumaralandirmaSayisiGetir(eng))
        {
            return false;
        }
        if (!VeritabaniYoneticisi.sonEklenenMakaleleriGetir(eng))
        {
            return false;
        }
        if (!VeritabaniYoneticisi.cokOKunanMakaleleriGetir(eng))
        {
            return false;
        }

        return true;
    }
}
