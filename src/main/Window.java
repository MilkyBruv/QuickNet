package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Window {

    public static JFrame frame;
    public static JPanel panel;
    public static JPanel recievedFilesPanel;
    public static JPanel toSendFilesPanel;
    public static JPanel sentFilesPanel;
    public static List<File> recievedFiles = new ArrayList<File>() {};
    public static List<File> toSendFiles = new ArrayList<File>() {};
    public static List<File> sentFiles = new ArrayList<File>() {};

    // Buttons
    public static JButton recieveButton = new JButton("Recieve Files");
    public static JButton sendButton = new JButton("Send Files");
    public static JButton endButton = new JButton("End Session");
    public static JTextArea recievedFilesText = new JTextArea();
    public static JTextArea toSendFilesText = new JTextArea();
    public static JTextArea sentFilesText = new JTextArea();

    public static void init() {

        try { 

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

        } catch(Exception ignored) {

            // don't care!!!

        }

        // Create window and content panel
        frame = new JFrame("QuickNet");
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(900, 400));
        frame.getContentPane().add(panel);

        recievedFilesPanel = new JPanel();
        toSendFilesPanel = new JPanel();
        sentFilesPanel = new JPanel();

        recievedFilesPanel.setPreferredSize(new Dimension(300, 400));
        toSendFilesPanel.setPreferredSize(new Dimension(300, 400));
        sentFilesPanel.setPreferredSize(new Dimension(300, 400));
        recievedFilesPanel.setBackground(new Color(0xC3C3C3));
        toSendFilesPanel.setBackground(new Color(0xC3C3C3));
        sentFilesPanel.setBackground(new Color(0xC3C3C3));
        recievedFilesPanel.setBorder(BorderFactory.createLineBorder(new Color(0x818181)));
        toSendFilesPanel.setBorder(BorderFactory.createLineBorder(new Color(0x818181)));
        sentFilesPanel.setBorder(BorderFactory.createLineBorder(new Color(0x818181)));
        
        // button test
        sendButton.setPreferredSize(new Dimension(80, 40));
        toSendFilesText.setPreferredSize(new Dimension(290, 200));
        toSendFilesText.setEditable(false);
        FileDropHandler dropper = new FileDropHandler();
        toSendFilesText.setDropTarget(new DropTarget(toSendFilesText, dropper));

        sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                for (File file : dropper.getFiles()) {
            
                    toSendFilesText.append(file.getName() + "\n");
        
                }

            }

        });

        toSendFilesPanel.add(toSendFilesText, BorderLayout.NORTH);
        toSendFilesPanel.add(sendButton, BorderLayout.SOUTH);

        frame.add(recievedFilesPanel, BorderLayout.WEST);
        frame.add(toSendFilesPanel, BorderLayout.CENTER);
        frame.add(sentFilesPanel, BorderLayout.EAST);
        
        // Set properties and display window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private static class FileDropHandler extends DropTargetAdapter {

        private List<File> files;

        public FileDropHandler() {

            this.files = new ArrayList<File>() {};

        }

        public List<File> getFiles() {

            return this.files;

        }

        @Override
        public void drop(DropTargetDropEvent event) {
            
            try {
                
                event.acceptDrop(DnDConstants.ACTION_COPY);
                @SuppressWarnings("unchecked")
                List<File> droppedFiles = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                for (File file : droppedFiles) {
                    
                    this.files.add(file);

                }

            } catch (Exception e) {

                System.out.println("cuh");

            }

        }

    }
    
}
