import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class AppProcDistri extends JFrame implements ActionListener {
    private JTextField textEstado;
    private JButton updateButton;
    private Statement smt;
    private String[] estados = { "SINALOA", "SONORA", "JALISCO", "BAJA CALIFORNIA", "CHIHUAHUA", "DURANGO", "ZACATECAS",
            "VERACRUZ", "OAXACA" };
    private Connection con;

    public static void main(String[] args) {
        new AppProcDistri();
    }

    public AppProcDistri() {
        super("PROYECTO TESEBADA");
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
        Inicioseccion myinicio = new Inicioseccion("Banco", this);
        smt = myinicio.getStmt();
        con = myinicio.getCon();
        textEstado = new JTextField(15);
        updateButton = new JButton("Actualizar");
        textEstado.setBounds(50, 100, 300, 30);
        lbl1.setBounds(50, 70, 300, 30);
        updateButton.setBounds(150, 160, 100, 30);
        add(lbl1);
        add(textEstado);
        add(updateButton);
        revalidate();
        repaint();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == updateButton) {
            if (textEstado.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "EL ESTADO NO PUEDE ESTAR VACIO",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!ConsultaEstado())
                JOptionPane.showMessageDialog(
                        null,
                        "NO EXISTEN DATOS CON ESTE ESTADO",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            updateDatos();
        }
    }

    private void updateDatos() {
        String query = "select chequera,saldo from chequera where estado = '" + textEstado.getText() + "'";
        try {
            ResultSet tuplas = smt.executeQuery(query);
            con.setAutoCommit(false);
            while (tuplas.next()) {
                String id = tuplas.getString(1);
                int nuevoSaldo = tuplas.getInt(2) + (int) (Math.random() * 10 + 1) * 1000;
                actualizar(id, nuevoSaldo);
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException error) {
            try {
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(error.getMessage());
        }
        System.out.println("se actualizaron los datos correctamente");
    }

    private void actualizar(String id, int nuevoSaldo) {
        try {
            PreparedStatement pstmt = con
                    .prepareStatement("update CHEQUERA set saldo = ? where CHEQUERA=? and estatus='A'");
            pstmt.setInt(1, nuevoSaldo);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void llenarBd() {
        for (int i = 0; i < 2000; i++) {
            int id = (int) (Math.random() * 100000) + 20000;
            int estado = (int) (Math.random() * 9);
            String query = "insert into chequera VALUES('CHEQUE" + id + "'," + estado * 1000 + i + ",'"
                    + estados[estado] + "','" + (i % 3 != 0 ? 'A' : 'B') + "')";
            try {
                smt.executeUpdate(query);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Fallo al llenar",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

    public boolean ConsultaEstado() {
        String query = "select chequera from chequera where estado = '" + textEstado.getText() + "'";
        ResultSet tuplas;
        try {
            tuplas = smt.executeQuery(query);
            return tuplas.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}