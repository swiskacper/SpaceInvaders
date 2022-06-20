package Game;

import Config.DatabaseConnector;
import Domain.Player;
import Swing.MenuJPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Highscore implements ActionListener {
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Integer> score = new ArrayList<>();
    private MenuJPanel panel;
    private int points;
    private JTextField name;
    private Window window;
    private DatabaseConnector databaseConnector = new DatabaseConnector();
    private JButton button;

    public Highscore(MenuJPanel panel, JTextField name, int points, Window window, JButton button) {
        this.panel = panel;
        this.points = points;
        this.name = name;
        this.window = window;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!Objects.equals(name.getText(), " ") && points != -5) {
            names.add(name.getText());
            score.add(points);
        }

        Font font = new Font("Verdana", Font.BOLD, 10);
        tideUp();
        panel.setLayout(null);
        Object[][] objects = new Object[names.size() + 1][2];
        if (!name.getText().isEmpty() && points != 0)
            saveToDatabase(points, name);

        String col[] = {"id", "name", "score"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        getDataOfPlayers();
        for (int i = 0; i < databaseConnector.getNames().size(); i++) {
            String name = databaseConnector.getNames().get(i);
            String id = databaseConnector.getIds().get(i);
            String score = databaseConnector.getScores().get(i);
            Object[] data = {id, name, score};
            tableModel.addRow(data);
        }
        JTable table = new JTable(tableModel);
        table.setBackground(Color.BLACK);
        table.setForeground(Color.white);
        table.setFont(font);
        table.setVisible(true);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 400, 400);
        if(button!=null && name!=null) {
            name.setVisible(false);
            name.setOpaque(false);
            button.setVisible(false);
            button.setOpaque(false);
            button.removeAll();
            name.removeAll();
        }
        panel.add(pane);
        pane.updateUI();

        panel.updateUI();

    }

    private void saveToDatabase(int points, JTextField loginTextField) {
        Random random = new Random();
        databaseConnector.insertPlayer(new Player(String.valueOf(random.nextInt()), loginTextField.getText(), String.valueOf(points)));
    }

    private void getDataOfPlayers() {
        databaseConnector.getPlayers();
    }

    void tideUp() {
        int iVariable;
        String sVariable;
        for (int i = 0; i < names.size(); i++) {
            for (int k = 0; k < names.size(); k++) {
                if (score.get(i) > score.get(k)) {
                    iVariable = score.get(i);
                    score.set(i, score.get(k));
                    score.set(k, iVariable);
                    sVariable = names.get(i);
                    names.set(i, names.get(k));
                    names.set(k, sVariable);

                }
            }
        }
    }

    public static void readingFromFile(ArrayList<String> nameList, ArrayList<Integer> points) throws FileNotFoundException {
        File file = new File("rank.txt");
        Scanner in = new Scanner(file);
        String[] tab;
        while (in.hasNextLine()) {
            tab = in.nextLine().split("\t");
            nameList.add(tab[0]);
            points.add(Integer.parseInt(tab[1]));
        }
        saving(nameList, points);
    }

    public static void saving(ArrayList<String> nameList, ArrayList<Integer> points) throws FileNotFoundException {
        PrintWriter save = new PrintWriter("rank.txt");
        Set<String> set = new TreeSet();
        ArrayList<Integer> idx = new ArrayList<>();
        for (int i = 0; i < nameList.size(); i++) {
            if (set.contains(nameList.get(i))) {

            } else {
                idx.add(i);
            }
            set.add(nameList.get(i));

        }

        for (int i = 0; i < idx.size(); i++) {

            save.println(nameList.get(idx.get(i)) + "\t" + points.get(idx.get(i)));
        }

        save.flush();
        save.close();
    }
}
