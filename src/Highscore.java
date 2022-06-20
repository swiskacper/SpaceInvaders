import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Highscore implements ActionListener {
    ArrayList<String> names=new ArrayList<>();
    ArrayList <Integer> score=new ArrayList<>();
    JPanel2 panel;
    int points;
    JTextField name;
    public Highscore(JPanel2 panel,JTextField name, int points){
        this.panel=panel;
        this.points=points;
        this.name=name;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(name.getText()!=" " && points!=-5){
            names.add(name.getText());
            score.add(points);
        }
        Font font= new Font("Verdana", Font.BOLD, 10);

        try {
            readingFromFile(names,score);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tidyingUp();
        panel.setLayout(null);
        Object[][] objects = new Object[names.size() + 1][2];

        for (int i = 0; i < names.size(); i++) {
            Object[] tablica = {names.get(i), score.get(i)};
            objects[i] = tablica;
        }
        String[] column = new String[]{"Name", "Score"};
        DefaultTableModel model = new DefaultTableModel(objects, column) {
            public Class getColumnClass(int column) {
                return getValueAt(1, column).getClass();
            }
        };


        JTable tablica = new JTable(model);
        tablica.setBackground(Color.BLACK);
        tablica.setForeground(Color.white);
        tablica.setFont(font);
        tablica.setVisible(true);
        JScrollPane pane = new JScrollPane(tablica);
        pane.setBounds(200, 300, 200, 200);
        panel.add(pane);
    }

    void tidyingUp(){
        int iVariable;
        String sVariable;
        for(int i=0;i<names.size();i++){
            for(int k=0;k<names.size();k++){
                if(score.get(i)>score.get(k)){
                    iVariable=score.get(i);
                    score.set(i,score.get(k));
                    score.set(k,iVariable);
                    sVariable=names.get(i);
                    names.set(i,names.get(k));
                    names.set(k,sVariable);

                }
            }
        }
    }

    public static void readingFromFile(ArrayList<String> nameList,ArrayList <Integer> points) throws FileNotFoundException {
        File file = new File("rank.txt");
        Scanner in = new Scanner(file);
        String[] tab;
        while (in.hasNextLine()) {
            tab = in.nextLine().split("\t");
            nameList.add(tab[0]);
            points.add(Integer.parseInt(tab[1]));
        }
        saving(nameList,points);
    }

    public static void saving(ArrayList<String> nameList,ArrayList <Integer> points) throws FileNotFoundException {
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
