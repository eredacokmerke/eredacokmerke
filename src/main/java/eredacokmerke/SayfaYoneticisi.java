package eredacokmerke;

import static eredacokmerke.VeritabaniYoneticisi.vtBaglantisiOlustur;

public class SayfaYoneticisi
{

    /**
     * sayfa cagrildigi zaman yapilacak islemler
     *
     * @return basarili ise true yoksa false doner
     */
    public boolean cagrildi()
    {
        if (!sistemiBaslat())
        {
            return false;
        }
        if (!gerekliVerileriGetir())
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
     * @return basarili ise true yoksa false doner
     */
    public boolean gerekliVerileriGetir()
    {
        return true;
    }
}
