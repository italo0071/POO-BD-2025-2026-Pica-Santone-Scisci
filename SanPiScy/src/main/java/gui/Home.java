package gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class Home extends JFrame {

    // Contenitori Principali
    private JPanel pannelloPrincipale;
    private JPanel pannelloSidebar;
    private JPanel pannelloPagine;

    // Sidebar
    private JButton btnHome;
    private JButton btnPlaylist;
    private JButton cercaButton;
    private JButton btnCanzoni;

    // Pagine (CardLayout)
    private JPanel vistaHome;
    private JPanel vistaPlaylist;
    private JPanel vistaCerca;
    private JPanel vistaProfilo;
    private JPanel vistaPlayer;

    // TopBar Home
    private JPanel topBar;
    private JButton btnAlbum;
    private JButton btnPlaylist2;
    private JButton btnBraniPreferiti;
    private JButton btnProfilo;

    // Griglie e Card
    private JPanel contenutoScorribile;
    private JPanel grigliaRecenti;
    private JPanel cardAlbum1;
    private JPanel cardAlbum2;
    private JPanel cardAlbum3;
    private JPanel cardAlbum4;

    private JPanel sezioneCorrelati;
    private JPanel cardCorrelato1;
    private JPanel cardCorrelato2;
    private JPanel cardCorrelato3;
    private JPanel cardCorrelato4;
    private JPanel cardCorrelato5;
    private JPanel cardCorrelato6;

    // Cerca e Profilo
    private JPanel topBarCerca;
    private JTextField campoRicerca;
    private JScrollPane scrollProfilo;
    private JPanel contenutoProfilo;
    private JPanel headerProfilo;
    private JLabel fotoProfiloGrande;
    private JButton btnImpostazioni;
    private JButton btnMenu;

    // Player
    private JPanel pannelloPlayer;
    private JLabel lblCopertinaPiccola;
    private JLabel lblTitoloBrano;
    private JLabel lblArtistaBrano;
    private JLabel lblTempoAttuale;
    private JSlider sliderProgresso;
    private JLabel lblTempoTotale;
    private JButton iconaVolume;
    private JSlider sliderVolume;
    private JLabel lblCopertinaGrande;

    private void formattaCopertineDiDefault() {
        costruisciCopertinaInvariante(lblCopertinaGrande, "/album.png", 400, 400, 30);
        costruisciCopertinaInvariante(lblCopertinaPiccola, "/album.png", 60, 60, 10);
    }

    private void simulaEstrazioneDatiTraccia() {
        List<String> sorgenteTitoli = Arrays.asList("La Mia Parte Intollerante", "Fuori dal Tunnel");
        List<String> sorgenteArtisti = Arrays.asList("Caparezza", "Caparezza");

        String titoloSelezionato = sorgenteTitoli.get(0);
        String artistaSelezionato = sorgenteArtisti.get(0);

        if (lblTitoloBrano != null) lblTitoloBrano.setText(titoloSelezionato);
        if (lblArtistaBrano != null) lblArtistaBrano.setText(artistaSelezionato);
        if (lblTempoAttuale != null) lblTempoAttuale.setText("0:00");
        if (lblTempoTotale != null) lblTempoTotale.setText("4:23");
        if (sliderProgresso != null) sliderProgresso.setValue(0);
    }

    private void commutaPannelloVisibile(String chiavePannello) {
        if (pannelloPagine != null && pannelloPagine.getLayout() instanceof CardLayout) {
            CardLayout gestoreLayout = (CardLayout) pannelloPagine.getLayout();
            gestoreLayout.show(pannelloPagine, chiavePannello);
        }
    }

    private void rivelaControlliRiproduzione() {
        if (pannelloPlayer != null) {
            pannelloPlayer.setVisible(true);
            pannelloPlayer.revalidate();
            pannelloPlayer.repaint();
        }
    }

    private void assegnaIconaStandard(AbstractButton nodoInterattivo, String percorso, int ampiezza, int altezza) {
        if (nodoInterattivo != null) {
            URL locazioneRisorsa = getClass().getResource(percorso);
            if (locazioneRisorsa != null) {
                ImageIcon iconaGrezza = new ImageIcon(locazioneRisorsa);
                Image iconaInterpolata = iconaGrezza.getImage().getScaledInstance(ampiezza, altezza, Image.SCALE_SMOOTH);
                nodoInterattivo.setIcon(new ImageIcon(iconaInterpolata));
                nodoInterattivo.setIconTextGap(15);
                nodoInterattivo.setHorizontalAlignment(SwingConstants.LEFT);
            }
        }
    }

    private void costruisciAvatarCircolare(AbstractButton bottoneSorgente, String percorso, int diametro) {
        ImageIcon iconaSagomata = renderizzaImmagineVettoriale(percorso, diametro, diametro, new Ellipse2D.Float(0, 0, diametro, diametro));
        if (iconaSagomata != null && bottoneSorgente != null) {
            bottoneSorgente.setIcon(iconaSagomata);
            bottoneSorgente.setText("");
            bottoneSorgente.setContentAreaFilled(false);
            bottoneSorgente.setBorderPainted(false);
            forzaVincoliDimensionali(bottoneSorgente, diametro, diametro);
        }
    }

    private void costruisciAvatarCircolare(JLabel etichettaSorgente, String percorso, int diametro) {
        ImageIcon iconaSagomata = renderizzaImmagineVettoriale(percorso, diametro, diametro, new Ellipse2D.Float(0, 0, diametro, diametro));
        if (iconaSagomata != null && etichettaSorgente != null) {
            etichettaSorgente.setIcon(iconaSagomata);
            etichettaSorgente.setText("");
            etichettaSorgente.setOpaque(false);
            forzaVincoliDimensionali(etichettaSorgente, diametro, diametro);
        }
    }

    private void costruisciCopertinaInvariante(JLabel contenitoreGrafico, String percorso, int limiteX, int limiteY, int raggioSmussatura) {
        Shape profiloDiTaglio = new RoundRectangle2D.Float(0, 0, limiteX, limiteY, raggioSmussatura, raggioSmussatura);
        ImageIcon copertinaProcessata = renderizzaImmagineVettoriale(percorso, limiteX, limiteY, profiloDiTaglio);

        if (copertinaProcessata != null && contenitoreGrafico != null) {
            contenitoreGrafico.setIcon(copertinaProcessata);
            contenitoreGrafico.setText("");
            contenitoreGrafico.setOpaque(false);

            forzaVincoliDimensionali(contenitoreGrafico, limiteX, limiteY);

            contenitoreGrafico.setHorizontalAlignment(SwingConstants.CENTER);
            contenitoreGrafico.setVerticalAlignment(SwingConstants.CENTER);
        }
    }

    private void forzaVincoliDimensionali(JComponent componente, int limiteAsseX, int limiteAsseY) {
        Dimension strutturaRigida = new Dimension(limiteAsseX, limiteAsseY);
        componente.setPreferredSize(strutturaRigida);
        componente.setMinimumSize(strutturaRigida);
        componente.setMaximumSize(strutturaRigida);
    }

    private ImageIcon renderizzaImmagineVettoriale(String percorsoRelativo, int risoluzioneX, int risoluzioneY, Shape perimetroChiuso) {
        try {
            URL riferimentoMemoria = getClass().getResource(percorsoRelativo);
            if (riferimentoMemoria == null) return null;

            BufferedImage matricePixelOriginale = ImageIO.read(riferimentoMemoria);

            int dimensioneQuadrato = Math.min(matricePixelOriginale.getWidth(), matricePixelOriginale.getHeight());
            int traslazioneX = (matricePixelOriginale.getWidth() - dimensioneQuadrato) / 2;
            int traslazioneY = (matricePixelOriginale.getHeight() - dimensioneQuadrato) / 2;

            BufferedImage bloccoCentrale = matricePixelOriginale.getSubimage(traslazioneX, traslazioneY, dimensioneQuadrato, dimensioneQuadrato);

            Image bloccoScalato = bloccoCentrale.getScaledInstance(risoluzioneX, risoluzioneY, Image.SCALE_SMOOTH);

            BufferedImage pianoLavoro = new BufferedImage(risoluzioneX, risoluzioneY, BufferedImage.TYPE_INT_ARGB);
            Graphics2D processoreGraficoBase = pianoLavoro.createGraphics();
            processoreGraficoBase.drawImage(bloccoScalato, 0, 0, null);
            processoreGraficoBase.dispose();

            BufferedImage immagineCompletata = new BufferedImage(risoluzioneX, risoluzioneY, BufferedImage.TYPE_INT_ARGB);
            Graphics2D processoreGraficoAvanzato = immagineCompletata.createGraphics();
            processoreGraficoAvanzato.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            processoreGraficoAvanzato.setClip(perimetroChiuso);
            processoreGraficoAvanzato.drawImage(pianoLavoro, 0, 0, null);
            processoreGraficoAvanzato.dispose();

            return new ImageIcon(immagineCompletata);

        } catch (Exception erroreIOGrafico) {
            System.err.println("Fallimento nella generazione della matrice per: " + percorsoRelativo);
            return null;
        }
    }

    private void createUIComponents() {
        // Blocco riservato al framework
    }

    public Home() {
        setContentPane(pannelloPrincipale);
        setTitle("SanPiScy Music");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        if (vistaHome != null) vistaHome.setBackground(Color.decode("#F3E8FF"));
        if (vistaPlaylist != null) vistaPlaylist.setBackground(Color.decode("#F3E8FF"));
        if (vistaProfilo != null) vistaProfilo.setBackground(Color.decode("#F3E8FF"));
        if (vistaCerca != null) vistaCerca.setBackground(Color.decode("#F3E8FF"));
        if (vistaPlayer != null) vistaPlayer.setBackground(Color.decode("#E3D3F8"));
        if (pannelloSidebar != null) pannelloSidebar.setBackground(Color.decode("#E3D3F8"));

        if (pannelloPlayer != null) {
            pannelloPlayer.setBackground(Color.decode("#E3D3F8"));
            pannelloPlayer.setVisible(false);
        }

        assegnaIconaStandard(btnHome, "/home.png", 20, 20);
        assegnaIconaStandard(cercaButton, "/search.png", 20, 20);
        assegnaIconaStandard(btnPlaylist, "/list.png", 20, 20);
        assegnaIconaStandard(btnCanzoni, "/music.png", 20, 20);

        costruisciAvatarCircolare(btnProfilo, "/Generic avatar.png", 35);
        costruisciAvatarCircolare(fotoProfiloGrande, "/Generic avatar.png", 150);

        formattaCopertineDiDefault();

        if (sliderProgresso != null) {
            sliderProgresso.setOpaque(false);
            sliderProgresso.setValue(0);
        }
        if (sliderVolume != null) {
            sliderVolume.setOpaque(false);
            sliderVolume.setValue(50);
        }
        if (iconaVolume != null) {
            iconaVolume.setContentAreaFilled(false);
            iconaVolume.setBorderPainted(false);
        }

        if (btnHome != null) btnHome.addActionListener(e -> commutaPannelloVisibile("HOME"));
        if (btnPlaylist != null) btnPlaylist.addActionListener(e -> commutaPannelloVisibile("PLAYLIST"));
        if (btnProfilo != null) btnProfilo.addActionListener(e -> commutaPannelloVisibile("PROFILO"));

        if (btnCanzoni != null) {
            btnCanzoni.addActionListener(e -> {
                simulaEstrazioneDatiTraccia();
                commutaPannelloVisibile("PLAYER");
                rivelaControlliRiproduzione();
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Home().setVisible(true));
    }
}