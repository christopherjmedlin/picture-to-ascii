package com.medlinchristopher.picturetoascii;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Panel on which the produced ASCII art is previewed on.
 *
 * Also contains a loading icon, which can be set as visible or invisible
 * by the setLoading method.
 *
 * @author Christopher Medlin
 */
public class PreviewPanel extends JPanel {

    private BufferedImage preview;
    private JLabel loading;

    /**
     * @param preview the default preview image.
     */
    public PreviewPanel (BufferedImage preview) {
        //set up the loading icon
        loading = new JLabel(new ImageIcon (
                this.getClass().getResource("/img/loading.gif")
        ));
    }

    /**
     * Sets the visibility of the loading icon.
     *
     * @param loading true if loading icon is visible, false otherwise.
     */
    public void setLoading (boolean loading) {
        this.loading.setVisible(loading);
    }

    /**
     * Sets the ASCII art that will be drawn to this panel.
     *
     * @param preview a 2 dimensional character array containing
     *                the ASCII art.
     */
    public void setPreview (BufferedImage preview) {
        this.preview = preview;
    }

    @Override
    public void paintComponent (Graphics g) {
        //draw the preview image (if any)
        g.drawImage(preview, 0, 0, null);
    }
}
