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

public class Signup implements ActionListener {
    JFrame jFrame;
    JLabel name_l,pass_l,name;
    JCheckBox pass;
    JTextField name_TextField1;
    JButton create;
    JPasswordField  pass_TextField2;
    Font font;
    Connection con;

    Statement stms;
    ResultSet rs;

    public Signup()
    {
        font = new Font("TimesNewRoman", Font.BOLD, 17);
        jFrame = new JFrame();
        jFrame.setBounds(10, 10, 700, 400);
        jFrame.setTitle("Hospital Signup Page.");
        jFrame.setFont(font);

        name_l = new JLabel();
        name_l.setText("Name :");
        name_l.setBounds(30, 80, 100, 30);
        jFrame.add(name_l);
        name_l.setFont(font);

        name_TextField1 = new JTextField();
        name_TextField1.setBounds(200, 80, 180, 30);
        jFrame.add(name_TextField1);
        name_TextField1.setFont(font);

        pass_l = new JLabel();
        pass_l.setText("Password: ");
        pass_l.setBounds(30, 140, 100, 30);
        jFrame.add(pass_l);
        pass_l.setFont(font);

        pass_TextField2 = new JPasswordField();
        pass_TextField2.setBounds(200, 140, 180, 30);
        jFrame.add(pass_TextField2);
        pass_TextField2.setFont(font);

        pass=new JCheckBox();
        pass.setBounds(400,140,20,30);
        jFrame.add(pass);
        pass.setFont(font);

        name=new JLabel();
        name.setText("Show Password.");
        name.setBounds(430,140,200,30);
        jFrame.add(name);
        name.setFont(font);

        create = new JButton("Create");
        create.setBounds(250, 200, 100, 30);
        create.setFont(font);
        jFrame.add(create);
        create.addActionListener(this);
        pass.addActionListener(this);
        jFrame.setLayout(null);
        jFrame.setVisible(true);
    }

    public void showData() {
        try {
            name_TextField1.setText(rs.getString(1));
            pass_TextField2.setText(rs.getString(2));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if(e.getSource()==create) {
                String user = name_TextField1.getText();
                String pass = String.valueOf(pass_TextField2.getPassword());
                ;

                Class.forName("com.mysql.cj.jdbc.Driver");

                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital?useTimezone=true&serverTimezone=UTC", "root", "");

                Statement stmt = con.createStatement();
                rs = stmt.executeQuery("select * from hospital");
                boolean b = false;
                while (rs.next() == true) {
                    String u = rs.getString(1);
                    String p = rs.getString(2);

                    if (Objects.equals(user, u) && Objects.equals(pass, p)) {
                        b = true;
                    }

                }
                if (b == true) {
                    JOptionPane.showMessageDialog(jFrame, "UserName Already Exists");
                } else {

                    stmt.executeUpdate("insert into hospital values('" + user + "','" + pass + "')");

                    con.close();
                    JOptionPane.showMessageDialog(jFrame, "Done SignUp ");
                    jFrame.setVisible(false);
                    Login login=new Login();


                }
            } else if (e.getSource()==pass) {
                char q=pass_TextField2.getEchoChar();
                pass_TextField2.setEchoChar((char)0);
            }

        }
        catch (Exception exx)
        {
            exx.printStackTrace();
        }
    }
}

