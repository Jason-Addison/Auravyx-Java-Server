package external;

import command.Command;
import server.Server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Owner on 5/18/2017.
 */
public class Console
{

    JFrame frame;
    JTextArea console;
    JScrollPane consolePane;
    JScrollBar consoleBar;
    JTextField consoleField;
    int width = 1200;
    int height = 600;
    static String log = "";
    static boolean logToConsole = true;
    static long startTime = System.currentTimeMillis();
    public Console()
    {
        frame = new JFrame();//"Sandbox Server | " + Server.IP_ADDRESS + " | " + Server.PORT);
        frame.setSize(width, height);
        frame.setVisible(true);

        BufferedImage icon = null;
        try
        {
            icon = ImageIO.read(new File(Server.dir + "res/icon.png"));
        }
        catch (Exception e)
        {

        }

        frame.setIconImage(icon);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        console = new JTextArea("hello");
        console.setEditable(false);
        console.setBounds(0, 0, 100000, 200);
        //console.setAutoscrolls(true);
        //frame.add(console);
        console.setFont(new Font("Arial", Font.PLAIN, 12));
        consolePane = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        consolePane.setBounds(0, 0, width, (int) (height * 0.8f));
        consolePane.setWheelScrollingEnabled(true);
        //consolePane.setBounds(0, 0, 50, 50);
        consoleField = new JTextField();
        consoleField.setBounds(0, height - 85, width, 50);
        consoleField.setFont(new Font("Arial", Font.BOLD, 20));
        console.setText("dasddsad as \n + sadafs");
        consoleField.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Command.execute(consoleField.getText());
                consoleField.setText("");
            }
        });
        frame.add(consolePane);
        frame.add(consoleField);
        frame.setLayout(null);
    }

    public void update()
    {
        float currentTime = ((float) (System.currentTimeMillis() - startTime)) / 1000;
        frame.setTitle("Sandbox Server | IP : " + Server.IP_ADDRESS + " | Port : " + Server.PORT + " | Players : " + Server.getPlayerCount() + " | UT : " + currentTime);

        console.setText(log);
        if(frame.getWidth() != width || frame.getHeight() != height)
        {
            resize();
        }
        frame.repaint();
    }

    private void resize()
    {
        width = frame.getWidth();
        height = frame.getHeight();
        consolePane.setBounds(5, 5, width - 50, (int) (height * 0.8f));
        consoleField.setBounds(0, height - (int) (height * 0.2f), width, (int) (height * 0.1f));
        consoleField.setBounds(0, height - 85, width, 50);
        consolePane.setBounds(0, 0, width - 15, (int) (height * 0.8f));
    }

    public static void log(String message)
    {
        if(logToConsole)
        {
            System.out.println(message);
        }
        log = log + message + System.lineSeparator();
    }
    public static void log(String from, String message)
    {
        log("<" + from + "> : " + message);
    }
}
