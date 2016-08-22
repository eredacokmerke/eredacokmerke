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
     * @return
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

            PreparedStatement pst = conn.prepareStatement("select BASLIK, ICERIK, OZET, OKUNMA  from MAKALE order by TARIH");
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String makaleBaslik = rs.getString("BASLIK");
                String makaleIcerik = rs.getString("ICERIK");
                String makaleOzet = rs.getString("OZET");
                String makaleOkunma = rs.getString("OKUNMA");

                Makale makale = new Makale(makaleBaslik, makaleIcerik, makaleOzet, "", "", makaleOkunma, null);

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
     * @return
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

            PreparedStatement pst = conn.prepareStatement("select BASLIK, ICERIK, OZET, OKUNMA  from MAKALE order by OKUNMA");
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String makaleBaslik = rs.getString("BASLIK");
                String makaleIcerik = rs.getString("ICERIK");
                String makaleOzet = rs.getString("OZET");
                String makaleOkunma = rs.getString("OKUNMA");

                Makale makale = new Makale(makaleBaslik, makaleIcerik, makaleOzet, "", "", makaleOkunma, null);

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
}
