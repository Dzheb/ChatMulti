package org.example.dzplus;

// – Создать окно клиента чата.
// Окно должно содержать JtextField для ввода логина,
// пароля, IP-адреса сервера, порта подключения к серверу,
// область ввода сообщений,
// JTextArea область просмотра сообщений чата и
// JButton подключения к серверу и отправки сообщения в чат.
// Желательно сразу сгруппировать
// компоненты, относящиеся к серверу сгруппировать
// на JPanel сверху экрана, а
// компоненты, относящиеся к отправке сообщения –
// на JPanel снизу.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class ConnectWindow extends JFrame {

    static private final int WINDOW_HEIGHT = 640;
    static private final int WINDOW_WIDTH = 720;
    static private final int WINDOW_POS_X = 300;
    static private final int WINDOW_POS_Y = 100;
    static private final String WINDOW_NAME = "Подключение к серверу";
    static public ArrayList<ChatWindow> chatArray = new ArrayList<>();
    ;

    JTextField loginField = new JTextField("Введите ваш логин: ");
    JTextField passwordField = new JTextField("Введите ваш пароль: ");
    JTextField serverField = new JTextField("Введите адрес сервера и порт для подключения: ");

    JButton buttonConnect = new JButton("Подключиться");

    JPanel grid = new JPanel(new GridLayout(4, 1));


    ConnectWindow() {
        //свойства окна
        setTitle(WINDOW_NAME);
        setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        serverField.setMargin(new Insets(20, 50, 0, 20));
        loginField.setMargin(new Insets(20, 50, 0, 20));
        passwordField.setMargin(new Insets(20, 50, 0, 20));
//        JLabel labelLogin = new JLabel("Введите логин: ",SwingConstants.CENTER);
        grid.add(loginField);
        grid.add(passwordField);
        grid.add(serverField);
        grid.add(buttonConnect);

        add(grid);
        try {

            // Get the file
            File f = new File("notes.txt");

            // Create new file
            // if it does not exist
            if (f.createNewFile())
                System.out.println("File created");
            else
                System.out.println("File already exists");
        } catch (Exception e) {
            System.err.println(e);
        }


        buttonConnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //загрузка сообщений архива
                String archive = "";
                try {
                    archive = new Archive().readFile("notes.txt");
                } catch (Exception err) {
                    throw new RuntimeException(err);
                }
                String login = loginField.getText().replace("Введите ваш логин: ", "");

                ChatWindow chatWindow = new ChatWindow(login, archive);
                System.out.println(chatArray.toString());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                chatArray.add(chatWindow);
            }
        });

        setVisible(true);
    }
}