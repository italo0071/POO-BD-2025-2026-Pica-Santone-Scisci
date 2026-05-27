package model;

public class UtenteFree extends Utente
{
    private int skipMassimi;
    private int riproduzioneOnDemand;

    public UtenteFree(String nome, String cognome, String username, String email, String password, int riproduzioneOnDemand)
    {
        super(nome, cognome, username, email, password);
        this.skipMassimi = 3; // ogni UtenteFree nasce con 3 skip
        this.riproduzioneOnDemand = riproduzioneOnDemand;
    }


    @Override
    public void ricevePubblicita()
    {
        System.out.println("bell 'sta pubblicita' eh?");
    }

    @Override
    public boolean puoScaricareOffline()
    {
        return false;
    }

    //gestione dei limiti giornalieri
    public void decrementaSkip()
    {
        if (this.skipMassimi > 0)
        {
            this.skipMassimi--;
            System.out.println("Skip effettuato. Skip rimanenti: " + this.skipMassimi);
        } else
            {
                 System.out.println("Hai esaurito gli skip a disposizione!");
            }
    }

    public void decrementaOnDemand()
    {
        if (this.riproduzioneOnDemand > 0)
        {
            this.riproduzioneOnDemand--;
        }
    }

    public int getSkipMassimi() { return skipMassimi; }
    public void setSkipMassimi(int skipMassimi) { this.skipMassimi = skipMassimi; }

    public int getRiproduzioneOnDemand() { return riproduzioneOnDemand; }
    public void setRiproduzioneOnDemand(int riproduzioneOnDemand) { this.riproduzioneOnDemand = riproduzioneOnDemand; }
}
