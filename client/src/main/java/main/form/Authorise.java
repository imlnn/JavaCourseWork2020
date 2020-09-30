package main.form;

import main.exception.AccesDeniedException;
import main.exception.DataTransformFailureException;
import main.exception.RequestFailureException;
import main.model.AuthModel;
import main.model.RegisterModel;
import main.model.TokenModel;
import main.web.AuthRequestController;

import javax.swing.*;
import java.awt.*;

public class Authorise extends Form {
    public static void swithc(JFrame frame) {
        JLabel loginLabel = new JLabel("login:");
        JTextField login = new JTextField();
        JLabel passwordLabel = new JLabel("password");
        JTextField password = new JTextField();
        JButton authorize = new JButton("autorize");
        JButton register = new JButton("register");

        authorize.addActionListener(e -> {
            String lgn = login.getText();
            String pwd = password.getText();
            if (lgn == "" || pwd == "") {
                return;
            }
            try {
                TokenModel token = AuthRequestController.signIn(new AuthModel(lgn, pwd));
                MainMenu.switchForm(frame, token);
            } catch (AccesDeniedException ex) {
                JOptionPane.showMessageDialog(null, "Your token is not relevant. Please authorise again");
            } catch (RequestFailureException ex) {
                JOptionPane.showMessageDialog(null, "Request failed with " + ex.getMessage());
            } catch (DataTransformFailureException ex) {
                ex.printStackTrace();
            }
        });

        register.addActionListener(e -> {
            String lgn = login.getText();
            String pwd = password.getText();
            if (lgn == "" || pwd == "") {
                return;
            }
            try {
                AuthRequestController.register(new RegisterModel(lgn, pwd));
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
        cont.add(loginLabel);
        cont.add(login);
        addSpaces(cont, gridWidth - 2);
        cont.add(passwordLabel);
        cont.add(password);
        addSpaces(cont, gridWidth - 2);
        cont.add(register);
        cont.add(authorize);
        addSpaces(cont, gridWidth / 2 - 1);
        addSpaces(cont, gridWidth * (gridHeight - 3) / 2);

        frame.validate();
    }
}
