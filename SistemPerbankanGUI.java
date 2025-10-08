import perbankan.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class SistemPerbankanGUI extends JFrame {

    // Penyimpanan data utama
    private static ArrayList<Akun> daftarAkun = new ArrayList<>();

    // Komponen Utama GUI
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Palet Warna Monokrom
    private Color latarBelakang = Color.WHITE;
    private Color teks = Color.BLACK;
    private Color latarTombol = new Color(240, 240, 240);

    // Komponen Panel Buat Akun
    private JTextField inputNama, inputNomorAkun, inputSaldoAwal;
    private JTextField inputGiroLimit, inputDepositoBulan;
    private JComboBox<String> comboJenisAkun;
    private JPanel panelInputSpesifik;
    private CardLayout layoutInputSpesifik;

    // Komponen Panel Transaksi
    private JTextField inputTransNomorAkun, inputTransJumlah;
    private JTextArea areaInfoAkun;

    public SistemPerbankanGUI() {
        // Pengaturan Window Utama
        setTitle("Sistem Perbankan");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(latarBelakang);

        // Membuat setiap "layar" aplikasi
        mainPanel.add(buatPanelMenu(), "MENU");
        mainPanel.add(buatPanelBuatAkun(), "BUAT_AKUN");
        mainPanel.add(buatPanelTransaksi(), "TRANSAKSI");
        mainPanel.add(buatPanelLihatAkun(), "LIHAT_AKUN");

        add(mainPanel);
        cardLayout.show(mainPanel, "MENU"); // Tampilkan menu saat start
    }

    private JPanel buatPanelMenu() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 15));
        panel.setBorder(new EmptyBorder(40, 80, 40, 80));
        panel.setBackground(latarBelakang);

        JLabel judul = new JLabel("MENU PERBANKAN", SwingConstants.CENTER);
        judul.setFont(new Font("Arial", Font.BOLD, 22));
        judul.setForeground(teks);

        JButton btnBuat = createStyledButton("Buat Akun Baru");
        btnBuat.addActionListener(e -> cardLayout.show(mainPanel, "BUAT_AKUN"));

        JButton btnTransaksi = createStyledButton("Lakukan Transaksi");
        btnTransaksi.addActionListener(e -> cardLayout.show(mainPanel, "TRANSAKSI"));

        JButton btnLihatSemua = createStyledButton("Lihat Semua Akun");
        btnLihatSemua.addActionListener(e -> {
            perbaruiTampilanInfoAkun();
            cardLayout.show(mainPanel, "LIHAT_AKUN");
        });

        panel.add(judul);
        panel.add(btnBuat);
        panel.add(btnTransaksi);
        panel.add(btnLihatSemua);

        return panel;
    }

    private JPanel buatPanelBuatAkun() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(latarBelakang);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBackground(latarBelakang);
        
        formPanel.add(createStyledLabel("Nama:"));
        inputNama = new JTextField();
        formPanel.add(inputNama);

        formPanel.add(createStyledLabel("Nomor Akun:"));
        inputNomorAkun = new JTextField();
        formPanel.add(inputNomorAkun);
        
        formPanel.add(createStyledLabel("Saldo Awal:"));
        inputSaldoAwal = new JTextField();
        formPanel.add(inputSaldoAwal);
        
        formPanel.add(createStyledLabel("Jenis Akun:"));
        String[] jenis = {"Giro", "Deposito", "Tabungan"};
        comboJenisAkun = new JComboBox<>(jenis);
        formPanel.add(comboJenisAkun);

        layoutInputSpesifik = new CardLayout();
        panelInputSpesifik = new JPanel(layoutInputSpesifik);
        panelInputSpesifik.setBackground(latarBelakang);

        JPanel panelGiro = new JPanel(new GridLayout(0, 2, 10, 10));
        panelGiro.setBackground(latarBelakang);
        panelGiro.add(createStyledLabel("Batas Penarikan:"));
        inputGiroLimit = new JTextField();
        panelGiro.add(inputGiroLimit);

        JPanel panelDeposito = new JPanel(new GridLayout(0, 2, 10, 10));
        panelDeposito.setBackground(latarBelakang);
        panelDeposito.add(createStyledLabel("Jangka Waktu (Bulan):"));
        inputDepositoBulan = new JTextField();
        panelDeposito.add(inputDepositoBulan);
        
        // Panel kosong untuk Tabungan karena tidak ada input tambahan
        JPanel panelTabungan = new JPanel();
        panelTabungan.setBackground(latarBelakang);
        
        panelInputSpesifik.add(panelGiro, "Giro");
        panelInputSpesifik.add(panelDeposito, "Deposito");
        panelInputSpesifik.add(panelTabungan, "Tabungan");

        comboJenisAkun.addActionListener(e -> {
            String pilihan = (String) comboJenisAkun.getSelectedItem();
            layoutInputSpesifik.show(panelInputSpesifik, pilihan);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(latarBelakang);
        JButton btnBuat = createStyledButton("Buat Akun");
        btnBuat.addActionListener(e -> prosesBuatAkun());
        
        JButton btnKembali = createStyledButton("Kembali");
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
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(latarBelakang);
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBackground(latarBelakang);
        formPanel.add(createStyledLabel("Nomor Akun:"));
        inputTransNomorAkun = new JTextField();
        formPanel.add(inputTransNomorAkun);

        formPanel.add(createStyledLabel("Jumlah:"));
        inputTransJumlah = new JTextField();
        formPanel.add(inputTransJumlah);

        JPanel tombolAksiPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        tombolAksiPanel.setBackground(latarBelakang);
        
        JButton btnSetor = createStyledButton("Setor Tunai");
        btnSetor.addActionListener(e -> prosesTransaksi("SETOR"));
        
        JButton btnTarik = createStyledButton("Tarik Tunai");
        btnTarik.addActionListener(e -> prosesTransaksi("TARIK"));
        
        JButton btnPindah = createStyledButton("Pindah ke Saving");
        btnPindah.addActionListener(e -> prosesTransaksi("PINDAH"));
        
        JButton btnKembalikan = createStyledButton("Kembalikan dari Saving");
        btnKembalikan.addActionListener(e -> prosesTransaksi("KEMBALIKAN"));
        
        tombolAksiPanel.add(btnSetor);
        tombolAksiPanel.add(btnTarik);
        tombolAksiPanel.add(btnPindah);
        tombolAksiPanel.add(btnKembalikan);

        JButton btnKembali = createStyledButton("Kembali ke Menu");
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(tombolAksiPanel, BorderLayout.CENTER);
        panel.add(btnKembali, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel buatPanelLihatAkun() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(latarBelakang);
        
        areaInfoAkun = new JTextArea();
        areaInfoAkun.setEditable(false);
        areaInfoAkun.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaInfoAkun.setForeground(teks);
        JScrollPane scrollPane = new JScrollPane(areaInfoAkun);

        JButton btnKembali = createStyledButton("Kembali ke Menu");
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        JLabel judul = new JLabel("DAFTAR SEMUA AKUN", SwingConstants.CENTER);
        judul.setFont(new Font("Arial", Font.BOLD, 18));
        
        panel.add(judul, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnKembali, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // --- Metode Logika ---

    private void prosesBuatAkun() {
        try {
            String nama = inputNama.getText();
            String noAkun = inputNomorAkun.getText();
            if (nama.isEmpty() || noAkun.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama dan Nomor Akun tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double saldo = Double.parseDouble(inputSaldoAwal.getText());
            String jenis = (String) comboJenisAkun.getSelectedItem();

            Akun akunBaru = null;
            switch (jenis) {
                case "Giro":
                    double limit = Double.parseDouble(inputGiroLimit.getText());
                    akunBaru = new Giro(noAkun, nama, saldo, limit);
                    break;
                case "Deposito":
                    int bulan = Integer.parseInt(inputDepositoBulan.getText());
                    akunBaru = new Deposito(noAkun, nama, saldo, bulan);
                    break;
                case "Tabungan":
                    akunBaru = new Tabungan(noAkun, nama, saldo);
                    break;
            }
            daftarAkun.add(akunBaru);
            
            // Tampilkan info akun baru di dialog
            String info = "Akun " + jenis + " berhasil dibuat!\n\n" + getInfoAsString(akunBaru);
            JOptionPane.showMessageDialog(this, info, "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Reset fields
            inputNama.setText("");
            inputNomorAkun.setText("");
            inputSaldoAwal.setText("");
            inputGiroLimit.setText("");
            inputDepositoBulan.setText("");
            cardLayout.show(mainPanel, "MENU");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input saldo atau angka lainnya harus valid!", "Error Input", JOptionPane.ERROR_MESSAGE);
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
            String pesanAksi = "";

            switch (jenisTransaksi) {
                case "SETOR":
                    akun.setor(jumlah);
                    pesanAksi = "Setoran sejumlah " + jumlah + " berhasil.";
                    break;
                case "TARIK":
                    akun.tarik(jumlah); // Pesan sukses/gagal sudah ada di dalam methodnya
                    pesanAksi = "Proses penarikan selesai.";
                    break;
                case "PINDAH":
                case "KEMBALIKAN":
                    if (akun instanceof Tabungan) {
                        if(jenisTransaksi.equals("PINDAH")) ((Tabungan) akun).pindahkanKeSaving(jumlah);
                        else ((Tabungan) akun).kembalikanDariSaving(jumlah);
                        pesanAksi = "Transaksi saving selesai.";
                    } else {
                        JOptionPane.showMessageDialog(this, "Fitur ini hanya untuk Akun Tabungan.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    break;
            }
            String info = pesanAksi + "\n\n" + getInfoAsString(akun);
            JOptionPane.showMessageDialog(this, info, "Transaksi Berhasil", JOptionPane.INFORMATION_MESSAGE);
            inputTransNomorAkun.setText("");
            inputTransJumlah.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah transaksi harus berupa angka!", "Error Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void perbaruiTampilanInfoAkun() {
        if (daftarAkun.isEmpty()) {
            areaInfoAkun.setText("\n   Belum ada akun yang terdaftar.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Akun akun : daftarAkun) {
            sb.append(getInfoAsString(akun));
            sb.append("\n"); // Spasi antar akun
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

    // --- Metode Bantuan ---
    
    private String getInfoAsString(Akun akun) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- INFO AKUN ---\n");
        sb.append("Nama Pemilik : ").append(akun.getNama()).append("\n");
        sb.append("Nomor Akun   : ").append(akun.getNomorAkun()).append("\n");
        sb.append("Saldo Utama  : ").append(akun.getSaldo()).append("\n");

        if (akun instanceof Giro) {
            sb.append("Jenis Akun   : Giro\n");
            sb.append("Limit        : ").append(((Giro) akun).getLimit()).append("\n");
        } else if (akun instanceof Deposito) {
            sb.append("Jenis Akun   : Deposito\n");
            // sb.append("Jangka Waktu : ").append(((Deposito) akun).getJangkaWaktuBulan()).append(" bulan\n"); // Perlu getter jika private
        } else if (akun instanceof Tabungan) {
             sb.append("Jenis Akun   : Tabungan\n");
             sb.append("Saldo Saving : ").append(((Tabungan) akun).getSaldoSaving()).append("\n");
        }
        sb.append("--------------------");
        return sb.toString();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(latarTombol);
        button.setForeground(teks);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(teks);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemPerbankanGUI().setVisible(true);
        });
    }
}