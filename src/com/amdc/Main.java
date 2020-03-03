package com.amdc;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

import static java.awt.Color.*;

public class Main {
    private static JButton btnOk = new JButton("Ok");
    private static JButton btnHelp = new JButton("Help");
    private static JButton btnAdv = new JButton("Advanced");
    private static JButton btnCancel = new JButton("Cancel");
    private static JButton btnSelect = new JButton("On Bluetooth");
    private static JButton btnCreate = new JButton("Off Bluetooth");
    private static JButton btnClear = new JButton("  Clear  ");
    private static JButton btnColor = new JButton("   Color   ");
    private static JButton btnSysBase = new JButton("System Database...");
    private static JButton btnOptions = new JButton(" Options >> ");
    private static JLabel jLabel1 = new JLabel("Bluetooth Device: ");
    private static JLabel jLabel2 = new JLabel("UUID Key: ");
    private static JRadioButton jrbNone = new JRadioButton("None");
    private static JRadioButton jrbDB = new JRadioButton("Database:");
    private static JTextField jTextField1 = new JTextField(30);
    private static JTextField jTextField2 = new JTextField(30);
    static Insets insets = new Insets(4, 4, 4, 4);
    static JTextArea jta = new JTextArea(12, 40);
    static JFrame jFrame = new JFrame();
//    static Scanner scanner;
//    static String separator = File.separator;
//    static BufferedReader bufferedReader;
//    static FileReader file;
//    static String UTF8 = "UTF-8";
//    static CompoundBorder border;
    static Color color;


    public static void main(String[] args) throws AWTException {
        JFrame jFrame = getFrame();
        btnOk.addActionListener(e -> JOptionPane.showMessageDialog(jFrame, "You are sure?", "Killer", JOptionPane.WARNING_MESSAGE));
        btnCancel.addActionListener(e -> JOptionPane.showConfirmDialog(jFrame, "You are sure?", " Title", JOptionPane.YES_NO_CANCEL_OPTION));
        btnHelp.addActionListener(e -> JOptionPane.showOptionDialog(jFrame, "You are sure?", " Title", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE,
                null, new Object[] {"one", "two", "three", "four", "five"}, 1));
        btnColor.addActionListener(e -> {
            jta.setBackground(JColorChooser.showDialog(jFrame, "Title", blue));
        });
        if(SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            PopupMenu pm = new PopupMenu();
            pm.add(new MenuItem("Start")).addActionListener(e-> System.out.println("Start"));
            pm.add(new MenuItem("Two"));
            pm.add(new MenuItem("Exit")).addActionListener(e -> System.exit(0));
            Image image = new ImageIcon("src/res/drawable/icon.png", "Scanner1").getImage();
            systemTray.add(new TrayIcon(image, "Description", pm));
        }
        jFrame.revalidate();

        btnSysBase.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith("txt");
                }
                @Override
                public String getDescription() {
                    return "Only txt files";
                }
            });
            jfc.showOpenDialog(jFrame);
            Scanner scan = null;
            try {
                scan = new Scanner(new FileReader(jfc.getSelectedFile()));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            assert scan != null;
            while (scan.hasNextLine()) { // обработка файла
                String line = scan.nextLine(); // чтение строк из файла
                jta.append(line + "\n");
            }
        });
    }

    public static class CreatePanel extends JPanel {
        public CreatePanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 1, 0.33,
                    GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
            add(new SourcePane(), gbc);
            add(new DatabasePane(), gbc);
            add(new SystemDatabasePane(), gbc);
            add(new ActionPane(), new GridBagConstraints(1, 0, 1, GridBagConstraints.REMAINDER, 0, 1,
                    GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0,0));
        }
    }

    public static class SourcePane extends JPanel {
        public SourcePane() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 0, 0,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0);
            add(jLabel1, gbc);
            add(jLabel2, gbc);
            gbc.gridx++;
            add(jTextField1, gbc);
            add(jTextField2, gbc);
        }
    }

    public static class DatabasePane extends JPanel {
        public DatabasePane() {
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Data from scanner", 0, 0, null, DARK_GRAY));
            GridBagConstraints gbc = new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 4, 1, 0.25, 1,
                    GridBagConstraints.WEST, GridBagConstraints.BOTH, insets, 0, 0);
            add(new JScrollPane(jta), gbc); // Область текста
            jta.setLineWrap(true); // автоматически на новую строку после заполнения
            gbc.gridwidth = 1; // по одной "клетке"
            gbc.gridy++; // с новой строки
            gbc.fill = GridBagConstraints.HORIZONTAL; // заполнение по горизонтали
            add(btnSelect, gbc);
            add(btnCreate, gbc);
            add(btnColor, gbc);
            add(btnClear, gbc);
            btnSelect.addActionListener(e -> jTextField1.setText(btnSelect.getText()));
            btnCreate.addActionListener(e -> jTextField1.setText(btnCreate.getText()));
//            btnColor.addActionListener(e -> jTextField1.setText(btnColor.getText()));
            btnClear.addActionListener(e -> jta.setText(""));
        }
    }

    public static class SystemDatabasePane extends JPanel {
        public SystemDatabasePane() {
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "System Database", 0, 0, null, DARK_GRAY));
//            setBorder(new CompoundBorder(new TitledBorder("System Database"), new EmptyBorder(8, 0, 8, 0)));
            GridBagConstraints gbc = new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 1, 0,
                    GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0);
            ButtonGroup buttonGroup = new ButtonGroup();
            add(jrbNone, gbc);
            buttonGroup.add(jrbNone);
            add(jrbDB, gbc);
            jrbDB.setSelected(true);
            buttonGroup.add(jrbDB);
            gbc.anchor = GridBagConstraints.CENTER;
            add(btnSysBase, gbc);
            jrbNone.addActionListener(e -> btnSysBase.setEnabled(false));
            jrbDB.addActionListener(e -> btnSysBase.setEnabled(true));
        }
    }

    public static class ActionPane extends JPanel {

        public ActionPane() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints(0, GridBagConstraints.RELATIVE, GridBagConstraints.REMAINDER, 1, 1, 0,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0);
            add(btnOk, gbc);
            btnOk.setMnemonic('O');
            add(btnCancel, gbc);
            btnCancel.setMnemonic('C');
            add(btnHelp, gbc);
            btnHelp.setMnemonic('H');
            add(btnAdv, gbc);
            btnAdv.setMnemonic('A');
            gbc.weighty = 1;
            gbc.anchor = GridBagConstraints.SOUTH;
            add(btnOptions, gbc);
            btnOptions.setMnemonic('p');
        }
    }

    private static JFrame getFrame() {
        jFrame = new JFrame("Scanner"); // create panel with name
        jFrame.add(new CreatePanel());// create method from class
        getMenuBar();
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) { System.out.println("Look and Feel not set"); }
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dm = toolkit.getScreenSize();
        jFrame.setBounds(dm.height / 4, dm.height / 4, 700, 550);
        jFrame.setVisible(true); // visualization panel
        return jFrame;
    }

    private static void getMenuBar() { // crate MenuBar
        JMenuBar jMenuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        file.setMnemonic('F'); // first key
        edit.setMnemonic('E'); // first key
        jMenuBar.add(file);
        jMenuBar.add(edit);
        file.add(new JMenuItem("New", new ImageIcon("src/res/drawable/20.png")));
        file.add(new JMenuItem("Add", new ImageIcon("src/res/drawable/21.png")));
        file.add(new JMenuItem("Open", new ImageIcon("src/res/drawable/22.png")));
        file.add(new JMenuItem("Save", new ImageIcon("src/res/drawable/23.png")));
        file.add(new JMenuItem("Import", new ImageIcon("src/res/drawable/24.png")));
        file.add(new JMenuItem("Export", new ImageIcon("src/res/drawable/25.png")));
        file.addSeparator(); // Separator

        JMenuItem exit = new JMenuItem("Exit", new ImageIcon("src/res/drawable/26.png"));
        exit.setMnemonic('E'); // warm key
        exit.setAccelerator(KeyStroke.getKeyStroke("ctrl E")); // сочетание кнопок для выхода
        exit.addActionListener(e -> System.exit(0)); // сокращенный слушатель для кнопки
        file.add(exit);

        edit.add(new JMenuItem("Cut", new ImageIcon("src/res/drawable/26.png")));
        edit.add(new JMenuItem("Copy", new ImageIcon("src/res/drawable/27.png")));
        edit.add(new JMenuItem("Paste", new ImageIcon("src/res/drawable/28.png")));
        edit.add(new JMenuItem("Delete", new ImageIcon("src/res/drawable/29.png")));
        edit.add(new JMenuItem("Find", new ImageIcon("src/res/drawable/23.png")));
        JMenu options = new JMenu("Options");
        edit.add(options);
        options.add("item One");
        options.add("item Two");
        jFrame.setJMenuBar(jMenuBar);
    }
}
