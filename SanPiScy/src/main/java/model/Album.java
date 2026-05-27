package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Album implements Riproducibile
{
    private String titolo;
    private LocalDate dataUscita;

    private List<Brano> listaBrani;

    public Album(String titolo, LocalDate dataUscita)
    {
        this.titolo = titolo;
        this.dataUscita = dataUscita;
        this.listaBrani = new ArrayList<>(); // inizializzazione della lista per evitare NullPointerException
    }

    @Override
    public void riproduci(Utente utente)
    {
        System.out.println(" Avvio riproduzione dell'album: " + this.titolo);

        // ciclo for-each: per ogni brano nella nostra lista, lo facciamo suonare
        for (Brano b : this.listaBrani)
        {
            b.riproduci(utente); // col polimorfismo diamo al singolo brano il compito di farsi sentire!
        }

        System.out.println("⏹ Riproduzione album terminata.");
    }

    public void aggiungiBrano(Brano b)
    {
        this.listaBrani.add(b);
    }

    public int calcolaDurataTotale()
    {
        int totale = 0;
        for (Brano b : listaBrani) {
            totale += b.getDurataSecondi();
        }
        return totale;
    }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public LocalDate getDataUscita() { return dataUscita; }
    public void setDataUscita(LocalDate dataUscita) { this.dataUscita = dataUscita; }

    public List<Brano> getListaBrani() { return listaBrani;}
}