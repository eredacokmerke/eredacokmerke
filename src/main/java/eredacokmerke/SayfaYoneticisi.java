package eredacokmerke;

import static eredacokmerke.VeritabaniYoneticisi.vtBaglantisiOlustur;

public class SayfaYoneticisi
{

    /**
     * sayfa cagrildigi zaman yapilacak islemler
     *
     * @param eng : Engine nesnesi
     * @return basarili ise true yoksa false doner
     */
    public boolean cagrildi(Engine eng)
    {
        if (!sistemiBaslat())
        {
            return false;
        }
        if (!gerekliVerileriGetir(eng))
        {
            return false;
        }

        return true;
    }

    /**
     * sistem baslatildigi zaman yapilmasi gerekenler
     *
     * @return basarili ise true yoksa false doner
     */
    public boolean sistemiBaslat()
    {
        if (!vtBaglantisiOlustur())
        {
            return false;
        }

        return true;
    }

    /**
     * sayfa cagrildigi zaman icerik icin gerekli verileri veritabanindan
     * getirir. sayfalara ait siniflar tarafindan override edilmeli
     *
     * @param eng : Engine nesnesi
     * @return basarili ise true yoksa false doner
     */
    public boolean gerekliVerileriGetir(Engine eng)
    {
        return true;
    }
}
