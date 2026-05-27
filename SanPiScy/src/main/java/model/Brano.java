package model;

public class Brano implements Riproducibile
{
    private String titolo;
    private int durataSecondi;
    private String genere;

    public Brano(String titolo, int durataSecondi, String genere)
    {
        this.titolo = titolo;
        this.durataSecondi = durataSecondi;
        this.genere = genere;
    }

    @Override
    public void riproduci(Utente utente)
    {
        System.out.println("▶ In ascolto: " + this.titolo + " [" + this.genere + " - " + this.durataSecondi + "s]");
    }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public int getDurataSecondi() { return durataSecondi; }
    public void setDurataSecondi(int durataSecondi) { this.durataSecondi = durataSecondi; }

    public String getGenere() { return genere; }
    public void setGenere(String genere) { this.genere = genere; }
}