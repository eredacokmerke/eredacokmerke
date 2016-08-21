package eredacokmerke;

public class HataYoneticisi
{

    /**
     * hata mesajini yazdirir
     *
     * @param id : hata id si
     * @param mesaj : hata mesaji
     */
    public static void yazdir(int id, String mesaj)
    {
        System.out.println("hata " + String.valueOf(id) + " : " + mesaj);
    }
}
