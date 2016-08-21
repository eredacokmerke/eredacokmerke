package eredacokmerke;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class MainBean
{

    public MainBean()
    {
        System.out.println("MainBean ");
        //conn = null;
        //engine = new Engine();
    }

    public void indexCagrildi()
    {
        IndexSayfasi indexSayfasi = new IndexSayfasi();
        if (!indexSayfasi.cagrildi())
        {
            HataYoneticisi.yazdir(5, "MainBean.indexCagrildi metodunda hata olustu");
        }
    }
}
