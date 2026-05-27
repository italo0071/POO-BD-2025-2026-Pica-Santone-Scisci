package org.example;

import gui.Home; // <-- Ora importiamo correttamente la nuova classe "Home"
import controller.PlayerController;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Avvia l'interfaccia grafica nel thread corretto di Java Swing
        SwingUtilities.invokeLater(() -> {

            // 1. Creiamo il controller che gestisce la logica musicale
            PlayerController controller = new PlayerController();

            // 2. Avviamo la nostra nuova interfaccia "Home"
            Home app = new Home();

            app.setVisible(true);
        });
    }
}