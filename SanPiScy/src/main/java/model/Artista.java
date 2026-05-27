package model;

import java.util.ArrayList;
import java.util.List;

public class Artista extends Utente
{
    private String nomeDArte;
    private String biografia;
    private boolean verificato;

    private List<Album> albumPubblicati;

    public Artista(String nome, String cognome, String username, String email, String password, String nomeDArte, String biografia)
    {
        super(nome, cognome, username, email, password);
        this.nomeDArte = nomeDArte;
        this.biografia = biografia;
        this.verificato = false; // di default un nuovo profilo artista non ha ancora la spunta blu
        this.albumPubblicati = new ArrayList<>(); // inizializzazione della lista vuota per prevenire eccezioni
    }

    @Override
    public void ricevePubblicita()
    {
        System.out.println("check del poliformismo, bravo amo tu addirittura fatturi e non hai l'ad");
    }

    @Override
    public boolean puoScaricareOffline() { return true; }


    public void pubblicaAlbum(Album album) {
        this.albumPubblicati.add(album);
    }

    public String getNomeDArte() {
        return nomeDArte;
    }
    public void setNomeDArte(String nomeDArte) {
        this.nomeDArte = nomeDArte;
    }

    public String getBiografia() {
        return biografia;
    }
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public boolean isVerificato() {
        return verificato;
    }
    public void setVerificato(boolean verificato) {
        this.verificato = verificato;
    }

    public List<Album> getAlbumPubblicati() { return albumPubblicati; }  // l'inserimento degli album puo' farlo esclusivamente il metodo pubblicaAlbum().
}

