package main.form;

import main.exception.AccesDeniedException;
import main.exception.DataTransformFailureException;
import main.exception.RequestFailureException;
import main.model.DiagnosisModel;
import main.model.PatientModel;
import main.model.TokenModel;
import main.web.DiagnosisRequestController;
import main.web.PatientRequestController;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class PatientsMenu extends Form {
    public static void switchForm(JFrame frame, TokenModel token) {
        JButton add = new JButton("Add patient");
        JButton delete = new JButton("Delete patient");
        JButton list = new JButton("Show patients");
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

        back.addActionListener(e -> {
            MainMenu.switchForm(frame, token);
        });

        final int gridWidth = 3, gridHeight = 6;

        Container cont = frame.getContentPane();
        cont.removeAll();
        cont.setLayout(new GridLayout(gridHeight, gridWidth));
        addSpaces(cont, gridWidth * (gridHeight - 4) / 2);
        addSpaces(cont, (gridWidth - 1) / 2);
        cont.add(add);
        addSpaces(cont, gridWidth - 1);
        cont.add(delete);
        addSpaces(cont, gridWidth - 1);
        cont.add(list);
        addSpaces(cont, gridWidth - 1);
        cont.add(back);
        addSpaces(cont, (gridWidth - 1) / 2);
        addSpaces(cont, gridWidth * (gridHeight - 4) / 2);

        frame.validate();
    }

    private static void add(JFrame frame, TokenModel token) {
        JLabel nameStLabel = new JLabel("First name:");
        JTextField nameSt = new JTextField();
        JLabel nameLLabel = new JLabel("Last name:");
        JTextField nameL = new JTextField();
        JLabel nameFLabel = new JLabel("Patronymic name:");
        JTextField nameF = new JTextField();
        JLabel diagnosisLabel = new JLabel("Diagnosis id:");
        JTextField diagnosis = new JTextField();
        JLabel wardLabel = new JLabel("Ward id:");
        JTextField ward = new JTextField();
        JButton back = new JButton("Return");
        JButton add = new JButton("Add");

        back.addActionListener(e -> {
            DiagnosisMenu.switchForm(frame, token);
        });

        add.addActionListener(e -> {
            String nameStS = nameSt.getText();
            String nameLS = nameL.getText();
            String nameFS = nameF.getText();
            String diagnosisS = diagnosis.getText();
            long diagnosisL = Long.parseLong(diagnosisS);
            String wardS = ward.getText();
            long wardL = Long.parseLong(wardS);
            if (nameStS == "" || nameLS == "" || diagnosisL < 1 || wardL < 1) {
                return;
            }

            PatientModel patient = new PatientModel(nameStS, nameLS, nameFS, diagnosisL, wardL);
            try {
                PatientRequestController.add(token, patient);
            } catch (AccesDeniedException ex) {
                JOptionPane.showMessageDialog(null, "Your token is not relevant. Please authorise again");
            } catch (RequestFailureException ex) {
                JOptionPane.showMessageDialog(null, "Request failed with " + ex.getMessage());
            } catch (DataTransformFailureException ex) {
                ex.printStackTrace();
            }
        });

        final int gridWidth = 4, gridHeight = 7;

        Container cont = frame.getContentPane();
        cont.removeAll();
        cont.setLayout(new GridLayout(gridHeight, gridWidth));
        addSpaces(cont, gridWidth / 2 - 1);
        cont.add(nameStLabel);
        cont.add(nameSt);
        addSpaces(cont, gridWidth - 2);
        cont.add(nameLLabel);
        cont.add(nameL);
        addSpaces(cont, gridWidth - 2);
        cont.add(nameFLabel);
        cont.add(nameF);
        addSpaces(cont, gridWidth - 2);
        cont.add(diagnosisLabel);
        cont.add(diagnosis);
        addSpaces(cont, gridWidth - 2);
        cont.add(wardLabel);
        cont.add(ward);
        addSpaces(cont, gridWidth - 2);
        cont.add(back);
        cont.add(add);
        addSpaces(cont, gridWidth / 2 - 1);

        frame.validate();
    }

    private static void delete(JFrame frame, TokenModel token) {
        JLabel idLabel = new JLabel("Patient id:");
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
                PatientRequestController.delete(token, idL);
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
            List<String> strings = PatientRequestController.getAll(token)
                    .stream()
                    .map(PatientModel::toString)
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
}
