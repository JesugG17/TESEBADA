package PROCDISTRIBUIDO;

import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;


public class AppProcDistri extends JFrame implements ActionListener {
    private JTextField textField;
    private JButton updateButton;
    private Statement smt;
    public static void main(String[] args) {
        new AppProcDistri();
    }

    public AppProcDistri() {
        super("PROYECTO TESEBADA");
        smt = Conexion.getStatement();
        interfaz();
        eventos();
    }

    private void eventos() {
        updateButton.addActionListener(this);
    }

    private void interfaz() {
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        JLabel lbl1 = new JLabel("Ingrese el estado");
        textField = new JTextField(15);
        updateButton = new JButton("Actualizar");
        textField.setBounds(50, 100, 300, 30);
        lbl1.setBounds(50, 70, 300, 30);
        updateButton.setBounds(150, 160, 100, 30);
        add(lbl1);
        add(textField);
        add(updateButton);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (textField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "EL ESTADO NO PUEDE ESTAR VACIO",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!existeEstado()) {
            return;
        }


        int saldo;
        do {
            saldo = new Random().nextInt(9001) + 1000;
        } while(saldo % 1000 != 0);
    }

    public boolean existeEstado() {

        String query = "SELECT * FROM CHEQUERA";
        try {
            ResultSet tuplas = smt.executeQuery(query);

            if (!tuplas.next()) {
                JOptionPane.showMessageDialog(
                        null,
                        "NO EXISTEN DATOS CON ESTE ESTADO",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            System.out.println(tuplas.getString("chequera"));

        } catch (Exception error) {
            System.out.println(error.getMessage());
        }


        return true;
    }
}