package eredacokmerke;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class MainBean
{

    private final Engine engine;//global degiskenlerin tutuldugu engine nesnesi

    public MainBean()
    {
        engine = new Engine();
    }

    public void indexCagrildi()
    {
        IndexSayfasi indexSayfasi = new IndexSayfasi();
        if (!indexSayfasi.cagrildi(engine))
        {
            HataYoneticisi.yazdir(5, "MainBean.indexCagrildi metodunda hata olustu");
        }
    }

    public Engine getEngine()
    {
        return engine;
    }
}
