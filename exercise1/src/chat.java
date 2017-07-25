import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JLabel;

public class chat extends JFrame {

    private JPanel contentPane;
    private String input=new String();
    private String output=new String();
    private String label = "";
    JLabel lblNewLabel = new JLabel("欢迎");
    JButton button = new JButton("\u53D1\u9001");
    JTextArea textArea = new JTextArea();
    socketIn in = new socketIn();
    socketOut out = new socketOut();
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    chat frame = new chat();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public chat() {
        lblNewLabel = new JLabel("欢迎");
        button = new JButton("\u53D1\u9001");
        textArea = new JTextArea();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 800);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 539, 782, 214);
        contentPane.add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 31, 782, 157);
        scrollPane.validate();
        panel.add(scrollPane);

        textArea.setFont(new Font("微软雅黑", Font.BOLD, 20));
        scrollPane.setViewportView(textArea);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                input = textArea.getText();
                textArea.setText("");
                label=label+"                                                                      "+input+"\n";
                lblNewLabel.setText(label);
            }
        });
        button.setBounds(669, 187, 113, 27);
        panel.add(button);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(0, 0, 782, 541);
        contentPane.add(scrollPane_1);

        lblNewLabel.setFont(new Font("黑体", Font.PLAIN, 20));
        scrollPane_1.setViewportView(lblNewLabel);
        out.start();
        in.start();
    }

    public class socketOut extends Thread{
        public void run(){
            try {
                Socket client = new Socket("localhost", 8000);
                BufferedReader accept = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while(true){
                    output = accept.readLine();
                    label = label+"\n"+output;
                    lblNewLabel.setText(label);
                }
            }

            catch (IOException g){
                System.out.println("IOException"+"type:e "+g);
            }
        }
    }

    public class socketIn extends Thread{
        public void run(){
            try {
                Socket client = new Socket("localhost", 8000);
                PrintWriter send;
                send = new PrintWriter(client.getOutputStream(), true);
                while(true){
                    while(input.equals("")){

                    }
                    send.println(input);
                    input="";
                }
            }

            catch (IOException g){
                System.out.println("IOException"+"type:e "+g);
            }
        }
    }
}

