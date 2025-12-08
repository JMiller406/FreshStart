package Controller;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import Model.Game;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import View.GameWindow;

public class WindowController {
    private Game game;
    private TargetGridController tgc;
    private OceanGridController ogc;
    private StatusController sc;

    public WindowController(GameWindow view, Game game) {
        this.game = game;
        this.tgc = new TargetGridController(view.getTargetPanel(), game.getHumanTargetGrid());
        this.sc = new StatusController(view.getStatusPane(), game);
        this.ogc = new OceanGridController(view.getOceanPanel(), game.getHumanOceanGrid());

        // menus
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem openItem = new JMenuItem("Open");

        openItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open Game");

            int userSelection = fileChooser.showOpenDialog(view);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToOpen = fileChooser.getSelectedFile();
                System.out.println("Open file: " + fileToOpen.getAbsolutePath());
                ObjectInputStream is;
                Game oldGame = this.game;
                try{
                    is = new ObjectInputStream(new FileInputStream(fileToOpen.getAbsolutePath()));
                    this.game = (Game)is.readObject();
                    is.close();
                }catch(FileNotFoundException e1){
                    e1.printStackTrace();
                }catch(IOException e1){
                    e1.printStackTrace();
                }catch(ClassNotFoundException e1){
                    e1.printStackTrace();
                }

                // disconnect old controllers, wire up new ones
                tgc.disconnect();
                ogc.disconnect();
                sc.disconnect();

                tgc = null;
                ogc = null;
                sc = null;

                this.tgc = new TargetGridController(view.getTargetPanel(), this.game.getHumanTargetGrid());
                this.sc = new StatusController(view.getStatusPane(), this.game);
                this.ogc = new OceanGridController(view.getOceanPanel(), this.game.getHumanOceanGrid());

            }
        });
        openItem.setAccelerator(KeyStroke.getKeyStroke('0', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        fileMenu.add(openItem);


        JMenuItem saveItem = new JMenuItem("Save");

        saveItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Game");

            int userSelection = fileChooser.showSaveDialog(view);

            if(userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                try{
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileToSave.getAbsolutePath()));
                    os.writeObject(this.game);
                    os.close();
                }catch(FileNotFoundException e1){
                    e1.printStackTrace();
            }catch(IOException e1){
                    e1.printStackTrace();
                }
            }
        });
        saveItem.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        fileMenu.add(saveItem);
        view.setJMenuBar(menuBar);
        
    }
}
