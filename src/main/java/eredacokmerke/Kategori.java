package eredacokmerke;

public class Kategori
{

    String isim;
    String id;

    public Kategori(String isim, String id)
    {
        this.isim = isim;
        this.id = id;
    }

    public String getIsim()
    {
        return isim;
    }

    public void setIsim(String isim)
    {
        this.isim = isim;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
