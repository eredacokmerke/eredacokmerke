package eredacokmerke;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

            PreparedStatement pst = conn.prepareStatement("select ISIM from KATEGORI");
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String kategoriIsim = rs.getString("ISIM");

                eng.getListeKategoriler().add(kategoriIsim);
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
                    + "select m.ID, m.BASLIK, m.ICERIK, m.OZET, m.OKUNMA, k.RESIM, k.ISIM "
                    + "from MAKALE as m, KATEGORI as k "
                    + "where m.ETIKET=k.ID "
                    + "order by TARIH");
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String makaleID = rs.getString("m.ID");
                String makaleBaslik = rs.getString("m.BASLIK");
                String makaleIcerik = rs.getString("m.ICERIK");
                String makaleOzet = rs.getString("m.OZET");
                String makaleOkunma = rs.getString("m.OKUNMA");
                String makaleResim = rs.getString("k.RESIM");
                String makaleEtiket = rs.getString("k.ISIM");

                Makale makale = new Makale(makaleID, makaleBaslik, makaleIcerik, makaleOzet, "", "", makaleOkunma, makaleEtiket, makaleResim);

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
                    + "select m.ID, m.BASLIK, m.ICERIK, m.OZET, m.OKUNMA, k.RESIM "
                    + "from MAKALE AS m, KATEGORI AS k "
                    + "where m.ETIKET=k.ID "
                    + "order by OKUNMA");
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String makaleID = rs.getString("m.ID");
                String makaleBaslik = rs.getString("m.BASLIK");
                String makaleIcerik = rs.getString("m.ICERIK");
                String makaleOzet = rs.getString("m.OZET");
                String makaleOkunma = rs.getString("m.OKUNMA");
                String makaleResim = rs.getString("k.RESIM");

                Makale makale = new Makale(makaleID, makaleBaslik, makaleIcerik, makaleOzet, "", "", makaleOkunma, "", makaleResim);

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
                    + "select m.BASLIK, m.ICERIK, m.OKUNMA, k.RESIM, k.ISIM "
                    + "from MAKALE AS m, KATEGORI AS k "
                    + "where m.ETIKET=k.ID and m.ID=?");
            pst.setString(1, String.valueOf(eng.getOkunanMakaleID()));
            ResultSet rs = pst.executeQuery();
            if (rs.first())
            {
                String makaleBaslik = rs.getString("m.BASLIK");
                String makaleIcerik = rs.getString("m.ICERIK");
                String makaleOkunma = rs.getString("m.OKUNMA");
                String makaleResim = rs.getString("k.RESIM");
                String makaleEtiket = rs.getString("k.ISIM");

                Makale makale = new Makale(String.valueOf(eng.getOkunanMakaleID()), makaleBaslik, makaleIcerik, "", "", "", makaleOkunma, makaleEtiket, makaleResim);

                eng.setOkunanMakale(makale);

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
}
