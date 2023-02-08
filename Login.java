package hospital_database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class Login implements ActionListener {
    JFrame jFrame;
    JLabel name_label, pass_label,name_l,pass_l;
    JTextField name_TextField,name_TextField1;
    JCheckBox P;
    JButton signup, login, create;
    JPasswordField  pass_TextField,pass_TextField2;
    Font font;
    Connection con;
    Statement stms;
    ResultSet rs;

    public Login() {
        font = new Font("TimesNewRoman", Font.BOLD, 17);
        jFrame = new JFrame();
        jFrame.setBounds(10, 10, 800, 400);
        jFrame.setTitle("Hospital Login.");
        jFrame.setFont(font);

        name_label = new JLabel();
        name_label.setText("Name :");
        name_label.setBounds(30, 80, 100, 30);
        jFrame.add(name_label);
        name_label.setFont(font);

        name_TextField = new JTextField();
        name_TextField.setBounds(200, 80, 180, 30);
        jFrame.add(name_TextField);
        name_TextField.setFont(font);

        pass_label = new JLabel();
        pass_label.setText("Password: ");
        pass_label.setBounds(30, 140, 100, 30);
        jFrame.add(pass_label);
        pass_label.setFont(font);

        pass_TextField = new JPasswordField();
        pass_TextField.setBounds(200, 140, 180, 30);
        jFrame.add(pass_TextField);
        pass_TextField.setFont(font);

        P=new JCheckBox();
        P.setBounds(450,140,20,30);
        jFrame.add(P);

        name_l=new JLabel();
        name_l.setText("Show Password.");
        name_l.setBounds(470,140,200,30);
        name_l.setFont(font);
        jFrame.add(name_l);


        signup = new JButton("SignUp");
        signup.setBounds(30, 200, 100, 30);
        jFrame.add(signup);
        signup.setFont(font);

        login = new JButton("Login");
        login.setBounds(250, 200, 100, 30);
        jFrame.add(login);
        login.setFont(font);


        jFrame.setLayout(null);
        jFrame.setVisible(true);

        signup.addActionListener(this);
        login.addActionListener(this);
        P.addActionListener(this);

        connectToDB();
    }
    public void connectToDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital?useTimezone=true&serverTimezone=UTC", "root", "");

            getData();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getData() {
        try {
            stms = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stms.executeQuery("select * from hospital ");

            showData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showData() {
        try {
            name_TextField.setText(rs.getString(1));
            pass_TextField.setText(rs.getString(2));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try
        {
            if (e.getSource()==login)
            {
                String name = name_TextField.getText();
                String pass = pass_TextField.getText();
                boolean b = false;

                while (rs.next() == true) {
                    String n = rs.getString(1);
                    String p = rs.getString(2);

                    if (Objects.equals(name, n) && Objects.equals(pass, p)) ;
                    {
                        b = true;
                    }
                }
                if (b == true) {
                    JOptionPane.showMessageDialog(jFrame, "Login Successfull");
                } else {
                    JOptionPane.showMessageDialog(jFrame, "Login ERROR !", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource()==signup) {
                jFrame.setVisible(false);
                Signup signup=new Signup();
            } else if (e.getSource()==P) {
                char q=pass_TextField.getEchoChar();
                pass_TextField.setEchoChar((char)0);

            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
