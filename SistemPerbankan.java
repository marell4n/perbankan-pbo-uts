import perbankan.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemPerbankan {
    //daftar untuk menyimpan semua objek akun yang dibuat
    private static ArrayList<Akun> daftarAkun = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int pilihan = 0;

        while (pilihan != 5) {
            System.out.println("\n===== MENU PERBANKAN =====");
            System.out.println("1. Buat Akun Baru");
            System.out.println("2. Lakukan Transaksi");
            System.out.println("3. Lihat Informasi Akun");
            System.out.println("4. Lihat Informasi Semua Akun");
            System.out.println("5. Keluar");
            System.out.println();
            System.out.print("Masukkan pilihan Anda: ");

            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    buatAkunBaru();
                    break;
                case 2:
                    transaksi();
                    break;
                case 3:
                    lihatInfoAkun(); //metode baru untuk melihat 1 akun
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

    //fungsi untuk membuat akun baru
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
            System.out.print("Masukkan Batas Penarikan: ");
            double limit = scanner.nextDouble();
            scanner.nextLine();
            Akun akun = new Giro(nomorAkun, nama, saldo, limit);
            daftarAkun.add(akun);
            System.out.println("\n>> Akun Giro Berhasil Dibuat! <<");
            akun.getInfo();
        } else if (jenis == 2) {
            System.out.print("Masukkan Jangka Waktu (bulan): ");
            int bulan = scanner.nextInt();
            scanner.nextLine();
            Akun akun = new Deposito(nomorAkun, nama, saldo, bulan);
            daftarAkun.add(akun);
            System.out.println("\n>> Akun Deposito Berhasil Dibuat! <<");
            akun.getInfo();
        } else if (jenis == 3) {
            Akun akun = new Tabungan(nomorAkun, nama, saldo);
            daftarAkun.add(akun);
            System.out.println("\n>> Akun Tabungan Berhasil Dibuat! <<");
            akun.getInfo();
        } else {
            System.out.println("Jenis akun tidak valid.");
        }
    }

    //fungsi melakukan transaksi
    public static void transaksi() {
        System.out.print("Masukkan Nomor Akun: ");
        String nomor = scanner.nextLine();
        Akun akun = cariAkun(nomor);

        if (akun == null) {
            System.out.println("Akun tidak ditemukan.");
            return;
        }

        //menu transaksi
        System.out.println("\nPilih Transaksi untuk Akun: " + akun.getNomorAkun());
        System.out.println("1. Setor Tunai");
        System.out.println("2. Tarik Tunai");

        //menu tambahan jika jenisnya tabungan untuk saving
        if (akun instanceof Tabungan) {
            System.out.println("3. Pindahkan Dana ke Kantong Saving");
            System.out.println("4. Kembalikan Dana dari Kantong Saving");
        }
        System.out.print("Pilihan: ");
        int pilihanTransaksi = scanner.nextInt();
        scanner.nextLine();
        
        //meminta jumlah hanya jika pilihan valid
        double jumlah = 0;
        if (pilihanTransaksi > 0) {
            System.out.print("Masukkan Jumlah: ");
            jumlah = scanner.nextDouble();
            scanner.nextLine();
        } else 

        switch (pilihanTransaksi) {
            case 1:
                akun.setor(jumlah);
                break;
            case 2:
                akun.tarik(jumlah);
                break;
            case 3:
                if (akun instanceof Tabungan tabungan) {
                    tabungan.pindahkanKeSaving(jumlah);
                } else {
                    System.out.println("Pilihan tidak valid untuk jenis akun ini.");
                }
                break;
            case 4:
                if (akun instanceof Tabungan tabungan) {
                    tabungan.kembalikanDariSaving(jumlah);
                } else {
                    System.out.println("Pilihan tidak valid untuk jenis akun ini.");
                }
                break;
            default:
                System.out.println("Gagal! Pilihan transaksi tidak valid.");
        }
    }

    // Fungsi untuk mencari akun berdasarkan nomor
    public static Akun cariAkun(String nomor) {
        for (Akun akun : daftarAkun) {
            if (akun.getNomorAkun().equalsIgnoreCase(nomor)) { //case-insensitive
                return akun;
            }
        }
        return null; //akun tidak ditemukan
    }
    
    //fungsi menampilkan info satu akun
    public static void lihatInfoAkun() {
        System.out.print("Masukkan Nomor Akun yang ingin dilihat: ");
        String nomor = scanner.nextLine();
        Akun akun = cariAkun(nomor);
        if (akun != null) {
            akun.getInfo();
        } else {
            System.out.println("Akun tidak ditemukan.");
        }
    }

    //fungsi menampilkan info semua akun
    public static void lihatSemuaAkun() {
        if (daftarAkun.isEmpty()) {
            System.out.println("\nBelum ada akun yang terdaftar.");
        } else {
            System.out.println("\n===== DAFTAR SEMUA AKUN =====");
            for (Akun akun : daftarAkun) {
                akun.infoAkun();
            }
        }
    }
}