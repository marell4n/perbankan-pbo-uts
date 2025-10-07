import perbankan.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemPerbankan {
    // Daftar untuk menyimpan semua objek akun yang dibuat
    private static ArrayList<Akun> daftarAkun = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int pilihan = 0;

        while (pilihan != 5) {
            System.out.println("\n===== MENU PERBANKAN =====");
            System.out.println("1. Buat Akun Baru");
            System.out.println("2. Lakukan Transaksi"); // Diubah dari Setor/Tarik
            System.out.println("3. Lihat Informasi Akun Tunggal");
            System.out.println("4. Lihat Informasi Semua Akun");
            System.out.println("5. Keluar");
            System.out.println("======================================");
            System.out.print("Masukkan pilihan Anda: ");

            pilihan = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer setelah membaca angka

            switch (pilihan) {
                case 1:
                    buatAkunBaru();
                    break;
                case 2:
                    lakukanTransaksi(); // Memanggil metode transaksi yang baru
                    break;
                case 3:
                    lihatInfoAkunTunggal(); // Metode baru untuk melihat 1 akun
                    break;
                case 4:
                    lihatSemuaAkun();
                    break;
                case 5:
                    System.out.println("Terima kasih!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
        scanner.close();
    }

    // Fungsi untuk membuat akun baru
    public static void buatAkunBaru() {
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Nomor Akun: ");
        String nomorAkun = scanner.nextLine();
        System.out.print("Masukkan Saldo Awal: ");
        double saldo = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Pilih Jenis Akun: (1) Giro (2) Deposito (3) Tabungan");
        System.out.print("Pilihan: ");
        int jenis = scanner.nextInt();
        scanner.nextLine();

        if (jenis == 1) {
            System.out.print("Masukkan Batas Overdraft: ");
            double limit = scanner.nextDouble();
            scanner.nextLine();
            Giro akunGiroBaru = new Giro(nomorAkun, nama, saldo, limit);
            daftarAkun.add(akunGiroBaru);
            System.out.println("\n>> Akun Giro Berhasil Dibuat! <<");
            akunGiroBaru.getInfo();
        } else if (jenis == 2) {
            System.out.print("Masukkan Jangka Waktu (bulan): ");
            int bulan = scanner.nextInt();
            scanner.nextLine();
            Deposito akunDepositoBaru = new Deposito(nomorAkun, nama, saldo, bulan);
            daftarAkun.add(akunDepositoBaru);
            System.out.println("\n>> Akun Deposito Berhasil Dibuat! <<");
            akunDepositoBaru.getInfo();
        } else if (jenis == 3) {
            Tabungan akunTabunganBaru = new Tabungan(nomorAkun, nama, saldo);
            daftarAkun.add(akunTabunganBaru);
            System.out.println("\n>> Akun Tabungan Berhasil Dibuat! <<");
            akunTabunganBaru.getInfo();
        } else {
            System.out.println("Jenis akun tidak valid.");
        }
    }

    // ====================================================================
    // FUNGSI BARU UNTUK SEMUA TRANSAKSI
    // ====================================================================
    public static void lakukanTransaksi() {
        System.out.print("Masukkan Nomor Akun: ");
        String nomor = scanner.nextLine();
        Akun akun = cariAkun(nomor);

        if (akun == null) {
            System.out.println("Akun tidak ditemukan.");
            return;
        }

        // Tampilkan menu transaksi
        System.out.println("\nPilih Transaksi untuk Akun: " + akun.getNomorAkun());
        System.out.println("1. Setor Tunai");
        System.out.println("2. Tarik Tunai");

        // Jika akunnya adalah Tabungan, tampilkan menu tambahan
        if (akun instanceof Tabungan) {
            System.out.println("3. Pindahkan Dana ke Kantong Saving");
            System.out.println("4. Kembalikan Dana dari Kantong Saving");
        }
        System.out.print("Pilihan: ");
        int pilihanTransaksi = scanner.nextInt();
        scanner.nextLine();
        
        // Meminta jumlah hanya jika pilihan valid dan bukan menu saving yang tidak butuh jumlah
        double jumlah = 0;
        if (pilihanTransaksi > 0) {
            System.out.print("Masukkan Jumlah: ");
            jumlah = scanner.nextDouble();
            scanner.nextLine();
        }

        switch (pilihanTransaksi) {
            case 1:
                akun.setor(jumlah);
                break;
            case 2:
                akun.tarik(jumlah);
                break;
            case 3:
                if (akun instanceof Tabungan) {
                    ((Tabungan) akun).pindahkanKeSaving(jumlah);
                } else {
                    System.out.println("Pilihan tidak valid untuk jenis akun ini.");
                }
                break;
            case 4:
                if (akun instanceof Tabungan) {
                    ((Tabungan) akun).kembalikanDariSaving(jumlah);
                } else {
                    System.out.println("Pilihan tidak valid untuk jenis akun ini.");
                }
                break;
            default:
                System.out.println("Pilihan transaksi tidak valid.");
        }
    }

    // Fungsi untuk mencari akun berdasarkan nomor
    public static Akun cariAkun(String nomor) {
        for (Akun akun : daftarAkun) {
            if (akun.getNomorAkun().equalsIgnoreCase(nomor)) { // Dibuat case-insensitive
                return akun;
            }
        }
        return null; // Akun tidak ditemukan
    }
    
    // Fungsi baru untuk melihat info satu akun
    public static void lihatInfoAkunTunggal() {
        System.out.print("Masukkan Nomor Akun yang ingin dilihat: ");
        String nomor = scanner.nextLine();
        Akun akun = cariAkun(nomor);
        if (akun != null) {
            akun.getInfo();
        } else {
            System.out.println("Akun tidak ditemukan.");
        }
    }

    // Fungsi untuk menampilkan info semua akun
    public static void lihatSemuaAkun() {
        if (daftarAkun.isEmpty()) {
            System.out.println("\nBelum ada akun yang terdaftar.");
        } else {
            System.out.println("\n===== DAFTAR SEMUA AKUN =====");
            for (Akun akun : daftarAkun) {
                akun.getInfo();
            }
        }
    }
}