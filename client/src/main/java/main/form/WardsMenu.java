package main.form;

import main.exception.AccesDeniedException;
import main.exception.DataTransformFailureException;
import main.exception.RequestFailureException;
import main.model.DiagnosisModel;
import main.model.PatientModel;
import main.model.TokenModel;
import main.model.WardModel;
import main.web.DiagnosisRequestController;
import main.web.WardRequestController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WardsMenu extends Form {
    public static void switchForm(JFrame frame, TokenModel token) {
        JButton add = new JButton("Add ward");
        JButton delete = new JButton("Delete ward");
        JButton list = new JButton("Show wards");
        JButton patients = new JButton("Get patients in ward");
        JButton back = new JButton("Return");

        add.addActionListener(e -> {
            add(frame, token);
        });

        delete.addActionListener(e -> {
            delete(frame, token);
        });

        list.addActionListener(e -> {
            list(frame, token);
        });

        patients.addActionListener(e -> {
            patients(frame, token);
        });

        back.addActionListener(e -> {
            MainMenu.switchForm(frame, token);
        });

        final int gridWidth = 3, gridHeight = 7;

        Container cont = frame.getContentPane();
        cont.removeAll();
        cont.setLayout(new GridLayout(gridHeight, gridWidth));
        addSpaces(cont, gridWidth * (gridHeight - 5) / 2);
        addSpaces(cont, (gridWidth - 1) / 2);
        cont.add(add);
        addSpaces(cont, gridWidth - 1);
        cont.add(delete);
        addSpaces(cont, gridWidth - 1);
        cont.add(list);
        addSpaces(cont, gridWidth - 1);
        cont.add(patients);
        addSpaces(cont, gridWidth - 1);
        cont.add(back);
        addSpaces(cont, (gridWidth - 1) / 2);
        addSpaces(cont, gridWidth * (gridHeight - 5) / 2);

        frame.validate();
    }

    private static void add(JFrame frame, TokenModel token) {
        JLabel nameLabel = new JLabel("Ward name:");
        JTextField name = new JTextField();
        JLabel maxLabel = new JLabel("Max patients:");
        JTextField max = new JTextField();
        JButton back = new JButton("Return");
        JButton add = new JButton("Add");

        back.addActionListener(e -> {
            WardsMenu.switchForm(frame, token);
        });

        add.addActionListener(e -> {
            String nameS = name.getText();
            String maxS = max.getText();
            long maxL = Long.parseLong(maxS);
            if (nameS == "" || maxL < 1) {
                return;
            }

            WardModel ward = new WardModel(nameS, maxL);
            try {
                WardRequestController.add(token, ward);
            } catch (AccesDeniedException ex) {
                JOptionPane.showMessageDialog(null, "Your token is not relevant. Please authorise again");
            } catch (RequestFailureException ex) {
                JOptionPane.showMessageDialog(null, "Request failed with " + ex.getMessage());
            } catch (DataTransformFailureException ex) {
                ex.printStackTrace();
            }
        });

        final int gridWidth = 4, gridHeight = 5;

        Container cont = frame.getContentPane();
        cont.removeAll();
        cont.setLayout(new GridLayout(gridHeight, gridWidth));
        addSpaces(cont, gridWidth * (gridHeight - 3) / 2);
        addSpaces(cont, gridWidth / 2 - 1);
        cont.add(nameLabel);
        cont.add(name);
        addSpaces(cont, gridWidth - 2);
        cont.add(maxLabel);
        cont.add(max);
        addSpaces(cont, gridWidth - 2);
        cont.add(back);
        cont.add(add);
        addSpaces(cont, gridWidth / 2 - 1);
        addSpaces(cont, gridWidth * (gridHeight - 3) / 2);

        frame.validate();
    }

    private static void delete(JFrame frame, TokenModel token) {
        JLabel idLabel = new JLabel("Ward id:");
        JTextField id = new JTextField();
        JButton back = new JButton("Return");
        JButton add = new JButton("Delete");

        back.addActionListener(e -> {
            WardsMenu.switchForm(frame, token);
        });

        add.addActionListener(e -> {
            String idS = id.getText();
            long idL = Long.parseLong(idS);
            if (idL < 1) {
                return;
            }

            try {
                WardRequestController.delete(token, idL);
            } catch (AccesDeniedException ex) {
                JOptionPane.showMessageDialog(null, "Your token is not relevant. Please authorise again");
            } catch (RequestFailureException ex) {
                JOptionPane.showMessageDialog(null, "Request failed with " + ex.getMessage());
            } catch (DataTransformFailureException ex) {
                ex.printStackTrace();
            }
        });

        final int gridWidth = 4, gridHeight = 5;

        Container cont = frame.getContentPane();
        cont.removeAll();
        cont.setLayout(new GridLayout(gridHeight, gridWidth));
        addSpaces(cont, gridWidth * (gridHeight - 3) / 2);
        addSpaces(cont, gridWidth / 2 - 1);
        cont.add(idLabel);
        cont.add(id);
        addSpaces(cont, gridWidth - 2);
        addSpaces(cont, gridWidth);
        cont.add(back);
        cont.add(add);
        addSpaces(cont, gridWidth / 2 - 1);
        addSpaces(cont, gridWidth * (gridHeight - 3) / 2);

        frame.validate();
    }

    private static void list(JFrame frame, TokenModel token) {
        try {
            List<String> strings = WardRequestController.getAll(token)
                    .stream()
                    .map(WardModel::toString)
                    .collect(Collectors.toList());
            printList(frame, token, strings, 1);
        } catch (AccesDeniedException ex) {
            JOptionPane.showMessageDialog(null, "Your token is not relevant. Please authorise again");
        } catch (RequestFailureException ex) {
            JOptionPane.showMessageDialog(null, "Request failed with " + ex.getMessage());
        } catch (DataTransformFailureException ex) {
            ex.printStackTrace();
        }
    }

    private static void patients(JFrame frame, TokenModel token) {
        JLabel idLabel = new JLabel("Ward id:");
        JTextField id = new JTextField();
        JButton back = new JButton("Return");
        JButton add = new JButton("Show");

        back.addActionListener(e -> {
            WardsMenu.switchForm(frame, token);
        });

        add.addActionListener(e -> {
            String idS = id.getText();
            long idL = Long.parseLong(idS);
            if (idL < 1) {
                return;
            }

            List<String> models;
            try {
                models = WardRequestController.getPatientsInWard(token, idL)
                        .stream()
                        .map(PatientModel::toString)
                        .collect(Collectors.toList());
                printList(frame, token, models, 1);
            } catch (AccesDeniedException ex) {
                JOptionPane.showMessageDialog(null, "Your token is not relevant. Please authorise again");
            } catch (RequestFailureException ex) {
                JOptionPane.showMessageDialog(null, "Request failed with " + ex.getMessage());
            } catch (DataTransformFailureException ex) {
                ex.printStackTrace();
            }

        });

        final int gridWidth = 4, gridHeight = 5;

        Container cont = frame.getContentPane();
        cont.removeAll();
        cont.setLayout(new GridLayout(gridHeight, gridWidth));
        addSpaces(cont, gridWidth * (gridHeight - 3) / 2);
        addSpaces(cont, gridWidth / 2 - 1);
        cont.add(idLabel);
        cont.add(id);
        addSpaces(cont, gridWidth - 2);
        addSpaces(cont, gridWidth);
        cont.add(back);
        cont.add(add);
        addSpaces(cont, gridWidth / 2 - 1);
        addSpaces(cont, gridWidth * (gridHeight - 3) / 2);

        frame.validate();
    }
}
