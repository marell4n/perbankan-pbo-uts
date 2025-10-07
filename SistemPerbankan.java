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
            System.out.println("\n===== MENU PERBANKAN SEDERHANA =====");
            System.out.println("1. Buat Akun Baru");
            System.out.println("2. Setor Tunai");
            System.out.println("3. Tarik Tunai");
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
                    lakukanSetoran();
                    break;
                case 3:
                    lakukanPenarikan();
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
            return;
        }
        System.out.println("Akun berhasil dibuat!");
    }

    // Fungsi untuk mencari akun berdasarkan nomor
    public static Akun cariAkun(String nomor) {
        for (Akun akun : daftarAkun) {
            if (akun.getNomorAkun().equals(nomor)) {
                return akun;
            }
        }
        return null; // Akun tidak ditemukan
    }

    // Fungsi untuk melakukan setoran
    public static void lakukanSetoran() {
        System.out.print("Masukkan Nomor Akun Tujuan: ");
        String nomor = scanner.nextLine();
        Akun akun = cariAkun(nomor);

        if (akun != null) {
            System.out.print("Masukkan Jumlah Setoran: ");
            double jumlah = scanner.nextDouble();
            scanner.nextLine();
            akun.setor(jumlah);
        } else {
            System.out.println("Akun tidak ditemukan.");
        }
    }

    // Fungsi untuk melakukan penarikan
    public static void lakukanPenarikan() {
        System.out.print("Masukkan Nomor Akun: ");
        String nomor = scanner.nextLine();
        Akun akun = cariAkun(nomor);

        if (akun != null) {
            System.out.print("Masukkan Jumlah Penarikan: ");
            double jumlah = scanner.nextDouble();
            scanner.nextLine();
            akun.tarik(jumlah);
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