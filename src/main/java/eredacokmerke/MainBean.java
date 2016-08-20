package eredacokmerke;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class MainBean
{

    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://" + System.getenv("OPENSHIFT_MYSQL_DB_HOST") + ":" + System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/" + System.getenv("OPENSHIFT_APP_NAME") + "?characterEncoding=utf8";
    private final String USER = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
    private final String PASS = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");

    private Connection conn;//vt baglantisi

    public MainBean()
    {
        conn = null;
    }

    /**
     * sistem baslatildigi zaman yapilmasi gerekenler
     */
    public void sistemiBaslat()
    {
        vtBaglantisiOlustur();
    }

    /**
     * veritabani baglantisi olusturur
     *
     * @return basarili ise true yoksa false doner
     */
    public boolean vtBaglantisiOlustur()
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (conn == null)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("hata 1 : " + e.getMessage());
            return false;
        }
        catch (SQLException e)
        {
            System.out.println("hata 2 : " + e.getMessage());
            return false;
        }
    }
}
