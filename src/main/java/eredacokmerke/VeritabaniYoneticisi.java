package eredacokmerke;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class VeritabaniYoneticisi
{

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://" + System.getenv("OPENSHIFT_MYSQL_DB_HOST") + ":" + System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/" + System.getenv("OPENSHIFT_APP_NAME") + "?characterEncoding=utf8";
    private static final String USER = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
    private static final String PASS = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");

    private static Connection conn;//vt baglantisi

    /**
     * veritabani baglantisi olusturur
     *
     * @return basarili ise true yoksa false doner
     */
    public static boolean vtBaglantisiOlustur()
    {
        if (conn == null)
        {
            try
            {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                if (conn == null)
                {
                    HataYoneticisi.yazdir(1, "veritabani baglantisi kurulamadi");
                    return false;
                }
                else
                {
                    return true;
                }
            }
            catch (ClassNotFoundException e)
            {
                HataYoneticisi.yazdir(2, e.getMessage());
                return false;
            }
            catch (SQLException e)
            {
                HataYoneticisi.yazdir(3, e.getMessage());
                return false;
            }
        }

        return true;
    }

    /**
     * veritabanindan kategorileri alip Engine -> listeKategoriler i doldurur
     *
     * @param eng : Engine nesnesi
     * @return basarili ise true yoksa false doner
     */
    public static boolean kategorileriGetir(Engine eng)
    {
        if (conn == null)//vt baglantisi yoksa acalim
        {
            if (!vtBaglantisiOlustur())
            {
                return false;
            }
        }
        try
        {
            eng.getListeKategoriler().clear();

            PreparedStatement pst = conn.prepareStatement("select ID, ISIM from KATEGORI");
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String kategoriIsim = rs.getString("ISIM");
                String kategoriID = rs.getString("ID");

                Kategori kategori = new Kategori(kategoriIsim, kategoriID);

                eng.getListeKategoriler().add(kategori);
            }

            return true;
        }
        catch (SQLException e)
        {
            HataYoneticisi.yazdir(4, e.getMessage());
            return false;
        }
    }

    /**
     * veritabanindan son eklenen makaleleri alip Engine -> listeMakaleler i
     * doldurur
     *
     * @param eng : Engine nesnesi
     * @return basarili ise true yoksa false doner
     */
    public static boolean sonEklenenMakaleleriGetir(Engine eng)
    {
        if (conn == null)//vt baglantisi yoksa acalim
        {
            if (!vtBaglantisiOlustur())
            {
                return false;
            }
        }
        try
        {
            eng.getListeSonEklenenMakaleler().clear();

            PreparedStatement pst = conn.prepareStatement(""
                    + "select m.ID, m.BASLIK, m.ICERIK, m.OZET, m.OKUNMA, m.TARIH, k.RESIM, k.ISIM, k.ID "
                    + "from MAKALE as m, KATEGORI as k "
                    + "where m.ETIKET=k.ID "
                    + "order by TARIH "
                    + "limit ?, ?");
            pst.setInt(1, (eng.getNumaralandirmaSayfaNumarasi() - 1) * eng.getSayfaBasinaSonEklenenMakale());
            pst.setInt(2, eng.getSayfaBasinaSonEklenenMakale());
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String makaleID = rs.getString("m.ID");
                String makaleBaslik = rs.getString("m.BASLIK");
                String makaleOzet = rs.getString("m.OZET");
                String makaleOkunma = rs.getString("m.OKUNMA");
                String makaleTarih = rs.getString("m.TARIH");
                String makaleResim = rs.getString("k.RESIM");
                String makaleEtiket = rs.getString("k.ISIM");
                String makaleEtiketID = rs.getString("k.ID");

                Makale makale = new Makale(makaleID, makaleBaslik, "", makaleOzet, unixTimeToTarih(Long.parseLong(makaleTarih)), "", makaleOkunma, makaleEtiket, makaleEtiketID, makaleResim);

                eng.getListeSonEklenenMakaleler().add(makale);
            }

            return true;
        }
        catch (SQLException e)
        {
            HataYoneticisi.yazdir(6, e.getMessage());
            return false;
        }
    }

    /**
     * son eklenen makaleler listesinin altindaki numaralandirma kisminda kac
     * sayfa sayfa numarasi gosterilecegini belirliyor
     *
     * @param eng : Engine nesnesi
     * @return
     */
    public static boolean sonEklenenMakalelerNumaralandirmaSayisiGetir(Engine eng)
    {
        if (conn == null)//vt baglantisi yoksa acalim
        {
            if (!vtBaglantisiOlustur())
            {
                HataYoneticisi.yazdir(21, "vt baglantisi olusturulurken hata olustu");
                eng.setNumaralandirmaToplamSayfaNumarasi(-1);
                return false;
            }
        }
        try
        {
            PreparedStatement pst = conn.prepareStatement("select count(*) from MAKALE");
            ResultSet rs = pst.executeQuery();
            if (rs.first())
            {
                int toplamSayfa = Integer.parseInt(rs.getString("count(*)")) / eng.getSayfaBasinaSonEklenenMakale() + 1;
                eng.setNumaralandirmaToplamSayfaNumarasi(toplamSayfa);
                return true;
            }
            else
            {
                HataYoneticisi.yazdir(22, "vt sorgusunda hata olustu");
                eng.setNumaralandirmaToplamSayfaNumarasi(-1);
                return false;
            }
        }
        catch (SQLException e)
        {
            HataYoneticisi.yazdir(23, e.getMessage());
            eng.setNumaralandirmaToplamSayfaNumarasi(-1);
            return false;
        }
    }

    /**
     * veritabanindan cok okunan makaleleri alip Engine -> listeMakaleler i
     * doldurur
     *
     * @param eng : Engine nesnesi
     * @return basarili ise true yoksa false doner
     */
    public static boolean cokOKunanMakaleleriGetir(Engine eng)
    {
        if (conn == null)//vt baglantisi yoksa acalim
        {
            if (!vtBaglantisiOlustur())
            {
                return false;
            }
        }
        try
        {
            eng.getListeCokOkunanMakaleler().clear();

            PreparedStatement pst = conn.prepareStatement(""
                    + "select m.ID, m.BASLIK, m.ICERIK, m.OZET, m.OKUNMA, m.TARIH, k.ISIM, k.RESIM "
                    + "from MAKALE AS m, KATEGORI AS k "
                    + "where m.ETIKET=k.ID "
                    + "order by OKUNMA desc limit ?");
            pst.setInt(1, eng.getSayfaBasinaCokOkunanMakale());
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String makaleID = rs.getString("m.ID");
                String makaleBaslik = rs.getString("m.BASLIK");
                String makaleOzet = rs.getString("m.OZET");
                String makaleOkunma = rs.getString("m.OKUNMA");
                String makaleTarih = rs.getString("m.TARIH");
                String makaleIsim = rs.getString("k.ISIM");
                String makaleResim = rs.getString("k.RESIM");

                Makale makale = new Makale(makaleID, makaleBaslik, "", makaleOzet, unixTimeToTarih(Long.parseLong(makaleTarih)), "", makaleOkunma, makaleIsim, "", makaleResim);

                eng.getListeCokOkunanMakaleler().add(makale);
            }

            return true;
        }
        catch (SQLException e)
        {
            HataYoneticisi.yazdir(7, e.getMessage());
            return false;
        }
    }

    /**
     * engine.okunanMakaleID li makalenin verilerini vertabanindan getirir
     *
     * @param eng : Engine nesnesi
     * @return basarili ise true yoksa false doner
     */
    public static boolean makaleGetir(Engine eng)
    {
        if (conn == null)//vt baglantisi yoksa acalim
        {
            if (!vtBaglantisiOlustur())
            {
                return false;
            }
        }
        try
        {
            PreparedStatement pst = conn.prepareStatement(""
                    + "select m.BASLIK, m.ICERIK, m.OKUNMA, m.TARIH, k.RESIM, k.ISIM, k.ID "
                    + "from MAKALE AS m, KATEGORI AS k "
                    + "where m.ETIKET=k.ID and m.ID=?");
            pst.setString(1, String.valueOf(eng.getOkunanMakaleID()));
            ResultSet rs = pst.executeQuery();
            if (rs.first())
            {
                String makaleBaslik = rs.getString("m.BASLIK");
                String makaleIcerik = rs.getString("m.ICERIK");
                String makaleOkunma = rs.getString("m.OKUNMA");
                String makaleTarih = rs.getString("m.TARIH");
                String makaleResim = rs.getString("k.RESIM");
                String makaleEtiket = rs.getString("k.ISIM");
                String makaleKategoriID = rs.getString("k.ID");

                Makale makale = new Makale(String.valueOf(eng.getOkunanMakaleID()), makaleBaslik, makaleIcerik, "", unixTimeToTarih(Long.parseLong(makaleTarih)), "", makaleOkunma, makaleEtiket, makaleKategoriID, makaleResim);

                eng.setOkunanMakale(makale);
                eng.setOkunanKategoriID(Integer.parseInt(makaleKategoriID));

                return true;
            }
            else
            {
                HataYoneticisi.yazdir(9, "result set bos");
                return false;
            }
        }
        catch (SQLException e)
        {
            HataYoneticisi.yazdir(10, e.getMessage());
            return false;
        }
    }

    /**
     * veritabanindan kategoriye ait makaleleri alip Engine ->
     * listeKategoriMakaleler i doldurur
     *
     * @param eng : Engine nesnesi
     * @return basarili ise true yoksa false doner
     */
    public static boolean kategoriMakaleleriGetir(Engine eng)
    {
        if (conn == null)//vt baglantisi yoksa acalim
        {
            if (!vtBaglantisiOlustur())
            {
                return false;
            }
        }
        try
        {
            eng.getListeKategoriMakaleler().clear();

            PreparedStatement pst = conn.prepareStatement(""
                    + "select m.ID, m.BASLIK, m.OZET, m.OKUNMA, m.TARIH, k.RESIM, k.ISIM, k.ID "
                    + "from MAKALE AS m, KATEGORI AS k "
                    + "where m.ETIKET=k.ID and k.ID=? "
                    + "limit ?, ?");
            pst.setString(1, String.valueOf(eng.getOkunanKategoriID()));
            pst.setInt(2, (eng.getNumaralandirmaSayfaNumarasi() - 1) * eng.getSayfaBasinaKategoriMakale());
            pst.setInt(3, eng.getSayfaBasinaKategoriMakale());
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String makaleID = rs.getString("m.ID");
                String makaleBaslik = rs.getString("m.BASLIK");
                String makaleOzet = rs.getString("m.OZET");
                String makaleOkunma = rs.getString("m.OKUNMA");
                String makaleTarih = rs.getString("m.TARIH");
                String makaleResim = rs.getString("k.RESIM");
                String makaleEtiket = rs.getString("k.ISIM");
                String makaleEtiketID = rs.getString("k.ID");

                Makale makale = new Makale(makaleID, makaleBaslik, "", makaleOzet, unixTimeToTarih(Long.parseLong(makaleTarih)), "", makaleOkunma, makaleEtiket, makaleEtiketID, makaleResim);

                eng.getListeKategoriMakaleler().add(makale);
            }

            return true;
        }
        catch (SQLException e)
        {
            HataYoneticisi.yazdir(13, e.getMessage());
            return false;
        }
    }
    
    /**
     * son eklenen makaleler listesinin altindaki numaralandirma kisminda kac
     * sayfa sayfa numarasi gosterilecegini belirliyor
     *
     * @param eng : Engine nesnesi
     * @return
     */
    public static boolean kategoriMakalelerNumaralandirmaSayisiGetir(Engine eng)
    {
        if (conn == null)//vt baglantisi yoksa acalim
        {
            if (!vtBaglantisiOlustur())
            {
                HataYoneticisi.yazdir(24, "vt baglantisi olusturulurken hata olustu");
                eng.setNumaralandirmaToplamSayfaNumarasi(-1);
                return false;
            }
        }
        try
        {
            PreparedStatement pst = conn.prepareStatement(""
                    + "select count(*) from MAKALE AS m, KATEGORI AS k "
                    + "where m.ETIKET=k.ID and k.ID=?");
            pst.setString(1, String.valueOf(eng.getOkunanKategoriID()));
            ResultSet rs = pst.executeQuery();
            if (rs.first())
            {
                int toplamSayfa = Integer.parseInt(rs.getString("count(*)")) / eng.getSayfaBasinaKategoriMakale() + 1;
                eng.setNumaralandirmaToplamSayfaNumarasi(toplamSayfa);
                System.out.println("eredacokmerke.VeritabaniYoneticisi.kategoriMakalelerNumaralandirmaSayisiGetir() toplam sayfa : "+toplamSayfa);
                return true;
            }
            else
            {
                HataYoneticisi.yazdir(25, "vt sorgusunda hata olustu");
                eng.setNumaralandirmaToplamSayfaNumarasi(-1);
                return false;
            }
        }
        catch (SQLException e)
        {
            HataYoneticisi.yazdir(26, e.getMessage());
            eng.setNumaralandirmaToplamSayfaNumarasi(-1);
            return false;
        }
    }

    /**
     * makale sayfasina girildikce veritabaninda makalenin okunma sayisini 1
     * arttirir
     *
     * @param eng : Engine nesnesi
     * @return basarili ise true yoksa false doner
     */
    public static boolean makaleOkunmaSayisiArttir(Engine eng)
    {
        if (conn == null)//vt baglantisi yoksa acalim
        {
            if (!vtBaglantisiOlustur())
            {
                return false;
            }
        }
        try
        {
            PreparedStatement pst = conn.prepareStatement("update MAKALE set OKUNMA = OKUNMA + 1 where ID = ?");
            pst.setString(1, String.valueOf(eng.getOkunanMakaleID()));
            if (pst.executeUpdate() == 0)
            {
                HataYoneticisi.yazdir(14, "makale okunma bilgisi guncellenemedi");
            }

            return true;
        }
        catch (SQLException e)
        {
            HataYoneticisi.yazdir(15, e.getMessage());
            return false;
        }
    }

    /**
     * kategori sayfasina girildikce veritabaninda kategorinin okunma sayisini 1
     * arttirir
     *
     * @param eng : Engine nesnesi
     * @return basarili ise true yoksa false doner
     */
    public static boolean kategoriOkunmaSayisiArttir(Engine eng)
    {
        if (conn == null)//vt baglantisi yoksa acalim
        {
            if (!vtBaglantisiOlustur())
            {
                return false;
            }
        }
        try
        {
            PreparedStatement pst = conn.prepareStatement("update KATEGORI set OKUNMA = OKUNMA + 1 where ID = ?");
            pst.setString(1, String.valueOf(eng.getOkunanKategoriID()));
            if (pst.executeUpdate() == 0)
            {
                HataYoneticisi.yazdir(16, "kategori okunma bilgisi guncellenemedi");
            }

            return true;
        }
        catch (SQLException e)
        {
            HataYoneticisi.yazdir(17, e.getMessage());
            return false;
        }
    }

    /**
     * makalenin kategorisinin idsini doner
     *
     * @param makaleID : makalenin idsi
     * @return eger basarili ise kategori id, hata olursa -1 doner
     */
    public static String kategoriIDyiGetir(int makaleID)
    {
        if (conn == null)//vt baglantisi yoksa acalim
        {
            if (!vtBaglantisiOlustur())
            {
                HataYoneticisi.yazdir(18, "vt baglantisi olusturulurken hata olustu");
                return "-1";
            }
        }
        try
        {
            PreparedStatement pst = conn.prepareStatement("select k.ID from MAKALE AS m, KATEGORI AS k where m.ETIKET=k.ID and m.ID=?");
            pst.setString(1, String.valueOf(makaleID));
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                String kategoriID = rs.getString("k.ID");
                return kategoriID;
            }
            else
            {
                HataYoneticisi.yazdir(19, "result set bos");
                return "-1";
            }
        }
        catch (SQLException e)
        {
            HataYoneticisi.yazdir(20, e.getMessage());
            return "-1";
        }
    }

    /**
     * vt deki unixtime ı okunabilir tarihe çevirir
     *
     * @param unixtime : unixtime degeri
     * @return unixtime in formatlanmis hali
     */
    public static String unixTimeToTarih(long unixtime)
    {
        Date date = new Date(unixtime * 1000L); // 1000 saniyeyi milisnaniyeye cevirmek icin
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM, yyyy", new Locale("tr"));
        String formattedDate = sdf.format(date);

        return formattedDate;
    }
}
