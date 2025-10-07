import perbankan.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SistemPerbankanGUI extends JFrame {

    // Penyimpanan data utama
    private static ArrayList<Akun> daftarAkun = new ArrayList<>();

    // Komponen Utama GUI
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Komponen untuk Panel Buat Akun
    private JTextField inputNama, inputNomorAkun, inputSaldoAwal;
    private JTextField inputGiroLimit, inputDepositoBulan, inputTabunganBunga;
    private JComboBox<String> comboJenisAkun;
    private JPanel panelInputSpesifik;
    private CardLayout layoutInputSpesifik;

    // Komponen untuk Panel Transaksi
    private JTextField inputTransNomorAkun, inputTransJumlah;
    private JTextArea areaInfoAkun;

    public SistemPerbankanGUI() {
        // Pengaturan Window Utama (JFrame)
        setTitle("Sistem Perbankan GUI");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menggunakan CardLayout untuk berganti-ganti panel/layar
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Membuat setiap panel (layar) aplikasi
        JPanel panelMenu = buatPanelMenu();
        JPanel panelBuatAkun = buatPanelBuatAkun();
        JPanel panelLihatAkun = buatPanelLihatAkun();
        JPanel panelTransaksi = buatPanelTransaksi();

        // Menambahkan panel-panel ke dalam mainPanel
        mainPanel.add(panelMenu, "MENU");
        mainPanel.add(panelBuatAkun, "BUAT_AKUN");
        mainPanel.add(panelLihatAkun, "LIHAT_AKUN");
        mainPanel.add(panelTransaksi, "TRANSAKSI");

        // Menambahkan mainPanel ke frame dan menampilkannya
        add(mainPanel);
        cardLayout.show(mainPanel, "MENU"); // Tampilkan menu utama pertama kali
    }

    private JPanel buatPanelMenu() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        JLabel judul = new JLabel("MENU UTAMA", SwingConstants.CENTER);
        judul.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnBuat = new JButton("1. Buat Akun Baru");
        btnBuat.addActionListener(e -> cardLayout.show(mainPanel, "BUAT_AKUN"));

        JButton btnTransaksi = new JButton("2. Lakukan Transaksi");
        btnTransaksi.addActionListener(e -> cardLayout.show(mainPanel, "TRANSAKSI"));

        JButton btnLihat = new JButton("3. Lihat Semua Akun");
        btnLihat.addActionListener(e -> {
            perbaruiTampilanInfoAkun();
            cardLayout.show(mainPanel, "LIHAT_AKUN");
        });

        panel.add(judul);
        panel.add(btnBuat);
        panel.add(btnTransaksi);
        panel.add(btnLihat);

        return panel;
    }

    private JPanel buatPanelBuatAkun() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.add(new JLabel("Nama:"));
        inputNama = new JTextField();
        formPanel.add(inputNama);

        formPanel.add(new JLabel("Nomor Akun:"));
        inputNomorAkun = new JTextField();
        formPanel.add(inputNomorAkun);
        
        formPanel.add(new JLabel("Saldo Awal:"));
        inputSaldoAwal = new JTextField();
        formPanel.add(inputSaldoAwal);
        
        formPanel.add(new JLabel("Jenis Akun:"));
        String[] jenis = {"Giro", "Deposito", "Tabungan"};
        comboJenisAkun = new JComboBox<>(jenis);
        formPanel.add(comboJenisAkun);

        layoutInputSpesifik = new CardLayout();
        panelInputSpesifik = new JPanel(layoutInputSpesifik);

        JPanel panelGiro = new JPanel(new GridLayout(0, 2, 5, 5));
        panelGiro.add(new JLabel("Limit Overdraft:"));
        inputGiroLimit = new JTextField();
        panelGiro.add(inputGiroLimit);

        JPanel panelDeposito = new JPanel(new GridLayout(0, 2, 5, 5));
        panelDeposito.add(new JLabel("Jangka Waktu (Bulan):"));
        inputDepositoBulan = new JTextField();
        panelDeposito.add(inputDepositoBulan);
        
        JPanel panelTabungan = new JPanel(new GridLayout(0, 2, 5, 5));
        panelTabungan.add(new JLabel("Suku Bunga (%):"));
        inputTabunganBunga = new JTextField();
        panelTabungan.add(inputTabunganBunga);
        
        panelInputSpesifik.add(panelGiro, "Giro");
        panelInputSpesifik.add(panelDeposito, "Deposito");
        panelInputSpesifik.add(panelTabungan, "Tabungan");

        comboJenisAkun.addActionListener(e -> {
            String pilihan = (String) comboJenisAkun.getSelectedItem();
            layoutInputSpesifik.show(panelInputSpesifik, pilihan);
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBuat = new JButton("Buat Akun");
        btnBuat.addActionListener(e -> prosesBuatAkun());
        
        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        buttonPanel.add(btnKembali);
        buttonPanel.add(btnBuat);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(panelInputSpesifik, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel buatPanelTransaksi() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.add(new JLabel("Nomor Akun:"));
        inputTransNomorAkun = new JTextField();
        formPanel.add(inputTransNomorAkun);

        formPanel.add(new JLabel("Jumlah:"));
        inputTransJumlah = new JTextField();
        formPanel.add(inputTransJumlah);

        JPanel tombolAksiPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JButton btnSetor = new JButton("Setor");
        btnSetor.addActionListener(e -> prosesTransaksi("SETOR"));
        
        JButton btnTarik = new JButton("Tarik");
        btnTarik.addActionListener(e -> prosesTransaksi("TARIK"));
        
        JButton btnPindah = new JButton("Pindah ke Saving");
        btnPindah.addActionListener(e -> prosesTransaksi("PINDAH"));
        
        JButton btnKembalikan = new JButton("Kembalikan dari Saving");
        btnKembalikan.addActionListener(e -> prosesTransaksi("KEMBALIKAN"));
        
        tombolAksiPanel.add(btnSetor);
        tombolAksiPanel.add(btnTarik);
        tombolAksiPanel.add(btnPindah);
        tombolAksiPanel.add(btnKembalikan);

        JButton btnKembali = new JButton("Kembali ke Menu");
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(tombolAksiPanel, BorderLayout.CENTER);
        panel.add(btnKembali, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel buatPanelLihatAkun() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        areaInfoAkun = new JTextArea();
        areaInfoAkun.setEditable(false);
        areaInfoAkun.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaInfoAkun);

        JButton btnKembali = new JButton("Kembali ke Menu");
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        panel.add(new JLabel("DAFTAR SEMUA AKUN", SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnKembali, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void prosesBuatAkun() {
        try {
            String nama = inputNama.getText();
            String noAkun = inputNomorAkun.getText();
            double saldo = Double.parseDouble(inputSaldoAwal.getText());
            String jenis = (String) comboJenisAkun.getSelectedItem();

            if (nama.isEmpty() || noAkun.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama dan Nomor Akun tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            switch (jenis) {
                case "Giro":
                    double limit = Double.parseDouble(inputGiroLimit.getText());
                    daftarAkun.add(new Giro(noAkun, nama, saldo, limit));
                    break;
                case "Deposito":
                    int bulan = Integer.parseInt(inputDepositoBulan.getText());
                    daftarAkun.add(new Deposito(noAkun, nama, saldo, bulan));
                    break;
                case "Tabungan":
                    double bunga = Double.parseDouble(inputTabunganBunga.getText());
                    daftarAkun.add(new Tabungan(noAkun, nama, saldo, bunga));
                    break;
            }
            JOptionPane.showMessageDialog(this, "Akun " + jenis + " berhasil dibuat!");
            inputNama.setText("");
            inputNomorAkun.setText("");
            inputSaldoAwal.setText("");
            inputGiroLimit.setText("");
            inputDepositoBulan.setText("");
            inputTabunganBunga.setText("");
            cardLayout.show(mainPanel, "MENU");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input saldo/limit/bulan/bunga harus berupa angka!", "Error Input", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void prosesTransaksi(String jenisTransaksi) {
        String noAkun = inputTransNomorAkun.getText();
        Akun akun = cariAkun(noAkun);

        if (akun == null) {
            JOptionPane.showMessageDialog(this, "Akun dengan nomor " + noAkun + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double jumlah = Double.parseDouble(inputTransJumlah.getText());
            String pesanSukses = "";

            switch (jenisTransaksi) {
                case "SETOR":
                    akun.setor(jumlah);
                    pesanSukses = "Setoran berhasil. Saldo baru: " + akun.getSaldo();
                    break;
                case "TARIK":
                    akun.tarik(jumlah);
                    pesanSukses = "Silakan periksa saldo Anda.";
                    break;
                case "PINDAH":
                    if (akun instanceof Tabungan) {
                        ((Tabungan) akun).pindahkanKeSaving(jumlah);
                        pesanSukses = "Dana berhasil dipindah ke Saving.";
                    } else {
                        JOptionPane.showMessageDialog(this, "Fitur ini hanya untuk Akun Tabungan.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    break;
                case "KEMBALIKAN":
                     if (akun instanceof Tabungan) {
                        ((Tabungan) akun).kembalikanDariSaving(jumlah);
                        pesanSukses = "Dana berhasil dikembalikan dari Saving.";
                    } else {
                        JOptionPane.showMessageDialog(this, "Fitur ini hanya untuk Akun Tabungan.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    break;
            }
            JOptionPane.showMessageDialog(this, pesanSukses, "Transaksi Berhasil", JOptionPane.INFORMATION_MESSAGE);
            inputTransNomorAkun.setText("");
            inputTransJumlah.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah transaksi harus berupa angka!", "Error Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void perbaruiTampilanInfoAkun() {
        if (daftarAkun.isEmpty()) {
            areaInfoAkun.setText("Belum ada akun yang terdaftar.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Akun akun : daftarAkun) {
            sb.append("---------------------------------\n");
            sb.append("Nama Pemilik : ").append(akun.getNama()).append("\n");
            sb.append("Nomor Akun   : ").append(akun.getNomorAkun()).append("\n");
            sb.append("Saldo Utama  : ").append(akun.getSaldo()).append("\n");

            if (akun instanceof Giro) {
                sb.append("Jenis Akun   : Giro\n");
                sb.append("Limit        : ").append(((Giro) akun).getLimit()).append("\n");
            } else if (akun instanceof Deposito) {
                sb.append("Jenis Akun   : Deposito\n");
            } else if (akun instanceof Tabungan) {
                 sb.append("Jenis Akun   : Tabungan\n");
                 sb.append("Saldo Saving : ").append(((Tabungan) akun).getSaldoSaving()).append("\n");
            }
        }
        areaInfoAkun.setText(sb.toString());
    }

    private Akun cariAkun(String nomor) {
        for (Akun akun : daftarAkun) {
            if (akun.getNomorAkun().equalsIgnoreCase(nomor)) {
                return akun;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGUI = new MainGUI();
            mainGUI.setVisible(true);
        });
    }
}