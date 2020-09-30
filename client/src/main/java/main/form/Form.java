package main.form;

import main.model.TokenModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Form {
    protected static void addSpaces(Container container, int count) {
        for (int i = 0; i < count; ++i) {
            container.add(Box.createGlue());
        }
    }

    protected static void printList(JFrame frame, TokenModel token, List<String> elements, int currentPage) {
        java.util.List<JLabel> productsLabels = elements.stream()
                .map(p -> {
                    return new JLabel(p);
                })
                .collect(Collectors.toList());

        JButton prev = new JButton("<<");
        JButton next = new JButton(">>");
        JLabel page = new JLabel("Page: " + currentPage);
        JButton back = new JButton(("Return"));

        final int elementsOnPage = 8;
        final int elementsCount = productsLabels.size();
        ListPageListener listener = new ListPageListener(frame, token, elements, currentPage, elementsCount / elementsOnPage + (elementsCount % elementsOnPage > 0 ? 1 : 0));
        prev.addActionListener(listener);
        next.addActionListener(listener);
        back.addActionListener(e -> {
            MainMenu.switchForm(frame, token);
        });

        final int outGridHeight = 6;
        final int gridWidth = 4, gridHeight = 7;
        Container cont = frame.getContentPane();
        cont.removeAll();
        cont.setLayout(new GridLayout(outGridHeight, 1));
        addSpaces(cont, 1);

        List<Container> productsContainers = new ArrayList<>();
        for (int i = elementsOnPage * (listener.getCurrentPage() - 1); i < elementsOnPage * (listener.getCurrentPage()); i += 2) {
            Container temp = new Container();
            final int rows = elementsOnPage / (outGridHeight - 2), columns = 3;
            temp.setLayout(new GridLayout(rows, columns));
            addSpaces(temp, (columns - 1) / 2);
            temp.add(i < elementsCount ? productsLabels.get(i) : new JLabel(""));
            addSpaces(temp, (columns - 1));
            temp.add(i + 1 < elementsCount ? productsLabels.get(i + 1) : new JLabel(""));
            addSpaces(temp, (columns - 1) / 2);

            productsContainers.add(temp);

        }
        Container optionsContainer = new Container();
        final int optionsWidth = 5;
        optionsContainer.setLayout(new GridLayout(2, optionsWidth));

        addSpaces(optionsContainer, (optionsWidth - 3) / 2);
        optionsContainer.add(prev);
        optionsContainer.add(page);
        optionsContainer.add(next);
        addSpaces(optionsContainer, (optionsWidth - 3) / 2);

        addSpaces(optionsContainer, (optionsWidth - 1) / 2);
        optionsContainer.add(back);
        addSpaces(optionsContainer, (optionsWidth - 1) / 2);

        productsContainers.forEach(container -> {
            cont.add(container);
        });
        cont.add(optionsContainer);
        frame.validate();
    }

    static class ListPageListener implements ActionListener {
        private JFrame frame;
        private TokenModel token;
        private int currentPage;
        private int maxPage;
        private List<String> products;

        public ListPageListener(JFrame frame, TokenModel token, List<String> products, int currentPage, int maxPage) {
            super();
            this.frame = frame;
            this.currentPage = currentPage;
            this.products = products;
            this.maxPage = maxPage;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (button.getText() == "<<") {
                prev();
            } else if (button.getText() == ">>") {
                next();
            }
        }

        private void next() {
            if (currentPage != maxPage) {
                ++currentPage;
                printList(frame, token, products, currentPage);
            }
        }

        private void prev() {
            if (currentPage != 1) {
                --currentPage;
                printList(frame, token, products, currentPage);
            }
        }

        public int getCurrentPage() {
            return currentPage;
        }
    }
}
