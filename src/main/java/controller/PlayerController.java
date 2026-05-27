package controller;

import model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import exception.ElementoVuotoException;
import exception.SkipEsauritiException;

public class PlayerController
{
    // DA RIMUOVERE : questa lista serve solo come finto DB temporaneo in RAM.
    private List<Cronologia> registroCronologia;

    // ATTRIBUTI DI STATO DEL PLAYER: tengono traccia di cosa sta suonando ORA
    private List<Brano> codaRiproduzioneAttuale;
    private int indiceBranoCorrente;
    private Utente utenteAttivo;
    private long millisecondiInizioRiproduzione;

    public PlayerController()
    {
        this.registroCronologia = new ArrayList<>();
        this.codaRiproduzioneAttuale = new ArrayList<>();
        this.indiceBranoCorrente = -1; // -1 significa che il player è vuoto/spento
    }


    public void avviaRiproduzione(Riproducibile elemento, Utente utente) throws ElementoVuotoException
    {
        this.utenteAttivo = utente;
        this.codaRiproduzioneAttuale.clear();

        System.out.println("messagino di check, l'utente " + utente.getUsername() + " sta richiedendo di ascoltare");
        utente.ricevePubblicita();

        if (elemento instanceof Brano)
        {
            this.codaRiproduzioneAttuale.add((Brano) elemento);
            System.out.println("▶ Coda creata: sei triste");
        }
        else if (elemento instanceof Album)
        {
            Album album = (Album) elemento;
            this.codaRiproduzioneAttuale.addAll(album.getListaBrani());
            System.out.println("▶ Coda creata: beccati st'album \"" + album.getTitolo() + "\" (" + codaRiproduzioneAttuale.size() + " brani)");
        }
        else if (elemento instanceof Playlist)
        {
            Playlist playlist = (Playlist) elemento;
            this.codaRiproduzioneAttuale.addAll(playlist.getListaBrani());
            System.out.println("▶  Coda creata: dj set già pronto \"" + playlist.getNome() + "\" (" + codaRiproduzioneAttuale.size() + " brani)");
        }

        if (!codaRiproduzioneAttuale.isEmpty())
        {
            this.indiceBranoCorrente = 0; // ci posizioniamo sul primo brano
            suonaBranoCorrente();
        } else
        {
            throw new ElementoVuotoException("Errore Player: Impossibile riprodurre un elemento privo di brani!");
        }
    }

    public void canzoneFinitaNaturale()
    {
        if (indiceBranoCorrente == -1 || codaRiproduzioneAttuale.isEmpty()) return;

        Brano branoFinito = codaRiproduzioneAttuale.get(indiceBranoCorrente);

        registraAscolto(utenteAttivo, branoFinito, branoFinito.getDurataSecondi(), false);

        procediAlProssimo();
    }

    public void cliccatoAvantiOSkip() throws SkipEsauritiException
    {
        if (indiceBranoCorrente == -1 || codaRiproduzioneAttuale.isEmpty()) {
            System.out.println("Nessun brano in riproduzione.");
            return;
        }

        Brano branoSkippato = codaRiproduzioneAttuale.get(indiceBranoCorrente);

        long millisecondiFine = System.currentTimeMillis();
        int secondiAscoltatiEffettivi = (int) ((millisecondiFine - millisecondiInizioRiproduzione) / 1000);

        if (secondiAscoltatiEffettivi > branoSkippato.getDurataSecondi())
        {
            secondiAscoltatiEffettivi = branoSkippato.getDurataSecondi();
        }

        if (utenteAttivo instanceof UtenteFree) {
            UtenteFree uf = (UtenteFree) utenteAttivo;
            if (uf.getSkipMassimi() <= 0) {
                this.indiceBranoCorrente = -1; // spegniamo il player
                throw new SkipEsauritiException("Limite raggiunto: l'utente " + utenteAttivo.getUsername() + " ha esaurito i 3 skip giornalieri.");
            }
            uf.decrementaSkip();
        }

        registraAscolto(utenteAttivo, branoSkippato, secondiAscoltatiEffettivi, false);
        procediAlProssimo();
    }

    private void procediAlProssimo()
    {
        if (indiceBranoCorrente + 1 < codaRiproduzioneAttuale.size())
        {
            indiceBranoCorrente++;
            suonaBranoCorrente();
        } else
        {
            System.out.println("\n⏹ Fine della coda di riproduzione.");
            this.indiceBranoCorrente = -1;
        }
    }

    // riproduzione stoppata a metà, salva comunque i secondi esatti ascoltati finora
    public void premiStop() {
        if (indiceBranoCorrente != -1)
        {
            Brano branoInterrotto = codaRiproduzioneAttuale.get(indiceBranoCorrente);

            long millisecondiFine = System.currentTimeMillis();
            int secondiAscoltatiEffettivi = (int) ((millisecondiFine - millisecondiInizioRiproduzione) / 1000);

            if (secondiAscoltatiEffettivi > branoInterrotto.getDurataSecondi())
            {
                secondiAscoltatiEffettivi = branoInterrotto.getDurataSecondi();
            }

            registraAscolto(utenteAttivo, branoInterrotto, secondiAscoltatiEffettivi, false);

            System.out.println("⏸ Riproduzione interrotta dall'utente.");
            this.indiceBranoCorrente = -1;
        }
    }

    private void suonaBranoCorrente()
    {
        if (indiceBranoCorrente >= 0 && indiceBranoCorrente < codaRiproduzioneAttuale.size()) {
            Brano inRiproduzione = codaRiproduzioneAttuale.get(indiceBranoCorrente);

            System.out.println("Traccia " + (indiceBranoCorrente + 1) + " di " + codaRiproduzioneAttuale.size());
            inRiproduzione.riproduci(utenteAttivo);

            this.millisecondiInizioRiproduzione = System.currentTimeMillis();

        } else
        {
            System.out.println("Errore indice di riproduzione.");
        }
    }

    private void registraAscolto(Utente utente, Brano brano, int secondiEffettivi, boolean isOnDemand)
    {
        Cronologia nuovoAscolto = new Cronologia(
                LocalDateTime.now(),
                secondiEffettivi,
                isOnDemand,
                utente,
                brano
        );

        this.registroCronologia.add(nuovoAscolto);

        System.out.println("Salvato ascolto parziale: \"" + brano.getTitolo() +
                "\" per " + secondiEffettivi + "s su " + brano.getDurataSecondi() + "s totali.");
    }

    // TO-REMOVE FOR DATABASE
    public List<Cronologia> getRegistroCronologia() { return registroCronologia; }
}