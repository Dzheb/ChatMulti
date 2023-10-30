package org.example.dzplus;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.awt.BorderLayout.CENTER;
import static org.example.dzplus.ConnectWindow.chatArray;

public class ChatWindow extends JFrame {
    static private final int WINDOW_HEIGHT = 640;
    static private final int WINDOW_WIDTH = 720;
    static private final int WINDOW_POS_X = 300;
    static private final int WINDOW_POS_Y = 100;
    static private final String WINDOW_NAME = "Чат с сервером";

    JTextArea textOutput = new JTextArea("");

    JLabel label = new JLabel("Введите сообщение серверу: ", SwingConstants.CENTER);
    JTextField textInput = new JTextField();
    JTextField textLogin = new JTextField();

    JButton buttonConnect = new JButton("Отправить");

    JPanel grid = new JPanel(new GridLayout(5, 1));

    ChatWindow(String login, String archive) {
        //свойства окна
        setTitle(WINDOW_NAME);
        setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setFont(new Font("TimesRoman", Font.BOLD, 22));

        textOutput.setEditable(false);
        textOutput.append(archive);
        textOutput.setBackground(Color.GRAY);
        textOutput.setMargin(new Insets(20, 50, 0, 20));
        textInput.setMargin(new Insets(20, 50, 0, 20));
        textLogin.setText("Привет " + login);
        textLogin.setMargin(new Insets(20, 50, 0, 20));
        grid.add(textOutput);
        grid.add(textLogin);
        grid.add(label);
        grid.add(textInput);
        grid.add(buttonConnect);

        buttonConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //запись сообщения в файл архива
                String textMessage = LocalDateTime.now().format(DateTimeFormatter.
                        ofPattern("yy/MM/dd HH:mm:ss")).toString() + " " + login + " : " + textInput.getText() + "\n";
                new Archive().writeFile("notes.txt", textMessage);
                //загрузка сообщений архива
                String archive = "";
                try {
                    archive = new Archive().readFile("notes.txt");
                    for (ChatWindow item : chatArray) {
                        item.textOutput.setText(archive);
                    }
                } catch (Exception err) {
                    throw new RuntimeException(err);
                }
            }
        });
        add(grid);
        setVisible(true);
    }
}