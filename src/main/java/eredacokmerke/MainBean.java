package eredacokmerke;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class MainBean
{

    private Engine engine;//global degiskenlerin tutuldugu engine nesnesi

    public MainBean()
    {

    }

    public void indexCagrildi()
    {
        IndexSayfasi indexSayfasi = new IndexSayfasi();
        engine = new Engine(SayfaYoneticisi.SAYFA_INDEX);
        if (!indexSayfasi.cagrildi(engine))
        {
            HataYoneticisi.yazdir(5, "MainBean.indexCagrildi metodunda hata olustu");
        }
    }

    public void makaleCagrildi()
    {
        int id = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

        MakaleSayfasi makaleSayfasi = new MakaleSayfasi();
        engine = new Engine(SayfaYoneticisi.SAYFA_MAKALE);
        engine.setOkunanMakaleID(id);

        if (!makaleSayfasi.cagrildi(engine))
        {
            HataYoneticisi.yazdir(8, "MainBean.indexCagrildi metodunda hata olustu");
        }
    }

    public Engine getEngine()
    {
        return engine;
    }
}
