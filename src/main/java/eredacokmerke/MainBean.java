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

    /**
     * index sayfasi cagrildi
     */
    public void indexCagrildi()
    {
        IndexSayfasi indexSayfasi = new IndexSayfasi();
        engine = new Engine(SayfaYoneticisi.SAYFA_INDEX);
        if (!indexSayfasi.cagrildi(engine))
        {
            HataYoneticisi.yazdir(5, "MainBean.indexCagrildi metodunda hata olustu");
        }
    }

    /**
     * makale sayfasi cagrildi
     */
    public void makaleCagrildi()
    {
        int id = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

        MakaleSayfasi makaleSayfasi = new MakaleSayfasi();
        engine = new Engine(SayfaYoneticisi.SAYFA_MAKALE);
        engine.setOkunanMakaleID(id);
        engine.setOkunanKategoriID(Integer.parseInt(engine.kategoriIDyiGetir(engine.getOkunanMakaleID())));

        if (!makaleSayfasi.cagrildi(engine))
        {
            HataYoneticisi.yazdir(8, "MainBean.makaleCagrildi metodunda hata olustu");
        }
    }

    /**
     * kategori sayfasi cagrildi
     */
    public void kategoriCagrildi()
    {
        int id = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

        KategoriSayfasi kategoriSayfasi = new KategoriSayfasi();
        engine = new Engine(SayfaYoneticisi.SAYFA_KATEGORI);
        engine.setOkunanKategoriID(id);

        if (!kategoriSayfasi.cagrildi(engine))
        {
            HataYoneticisi.yazdir(12, "MainBean.kategoriCagrildi metodunda hata olustu");
        }
    }

    public Engine getEngine()
    {
        return engine;
    }
}
