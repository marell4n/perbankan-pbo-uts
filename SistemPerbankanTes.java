import perbankan.*;
import java.util.ArrayList;

public class SistemPerbankanTes {

    // Daftar untuk menyimpan semua objek akun yang dibuat untuk pengujian.
    private static ArrayList<Akun> daftarAkun = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("\n--- AKUN GIRO ---");
        //membuat objek Giro
        Giro akunGiro = new Giro("G-001", "Azza", 5000000, 1000000);
        daftarAkun.add(akunGiro);
        System.out.println(">> Berhasil membuat Akun Giro baru.");
        akunGiro.getInfo();

        //menguji metode setor
        System.out.println("\n-> Setor: 1000000");
        akunGiro.setor(1000000);

        //menguji metode tarik
        System.out.println("\n-> Tarik = 500000");
        akunGiro.tarik(500000);

        System.out.println("\n-> Tarik = 10000000");
        akunGiro.tarik(10000000);
        System.out.println("Saldo: " + akunGiro.getSaldo());

        System.out.println("\n--- AKUN DEPOSITO ---");
        //membuat objek Deposito
        Deposito akunDeposito = new Deposito("D-001", "Nadia", 10000000, 12);
        daftarAkun.add(akunDeposito);
        System.out.println(">> Berhasil membuat Akun Deposito baru.");
        akunDeposito.getInfo();

        //menguji metode tarik
        System.out.println("\n-> Tarik = 1000000");
        akunDeposito.tarik(1000000);
        System.out.println("Saldo tetap: " + akunDeposito.getSaldo());


        System.out.println("\n--- AKUN TABUNGAN ---");
        //membuat objek Tabungan
        Tabungan akunTabungan = new Tabungan("T-001", "Ned", 2000000);
        daftarAkun.add(akunTabungan);
        System.out.println(">> Berhasil membuat Akun Tabungan baru.");
        akunTabungan.getInfo();

        //menguji pindah ke saving
        System.out.println("\n-> Saldo yang ditambahkan ke saving: 500000");
        akunTabungan.pindahkanKeSaving(500000);
        System.out.println("Saldo Utama: " + akunTabungan.getSaldo() + ", Saldo Saving: " + akunTabungan.getSaldoSaving());

        System.out.println("\n-> Saldo yang ditambahkan ke saving: 2000000");
        akunTabungan.pindahkanKeSaving(2000000);
        System.out.println("Saldo Utama: " + akunTabungan.getSaldo() + ", Saldo Saving: " + akunTabungan.getSaldoSaving());

        //menguji kembalikan dari saving
        System.out.println("\n-> Tarik saldo dari saving: 200000");
        akunTabungan.kembalikanDariSaving(200000);
        System.out.println("Saldo Utama: " + akunTabungan.getSaldo() + ", Saldo Saving: " + akunTabungan.getSaldoSaving());

        System.out.println("\n-> Tarik saldo dari saving: 500000");
        akunTabungan.kembalikanDariSaving(500000);
        System.out.println("Saldo Utama: " + akunTabungan.getSaldo() + ", Saldo Saving: " + akunTabungan.getSaldoSaving());


        System.out.println("\n--- FUNGSI MANAJEMEN AKUN ---");

        System.out.println("\n-> Menampilkan semua akun yang terdaftar:");
        lihatSemuaAkun();

        System.out.println("\n-> Cari akun dengan nomor: T-001");
        Akun akunDitemukan = cariAkun("T-001");
        if (akunDitemukan != null) {
            System.out.println("Akun ditemukan! Info:");
            akunDitemukan.getInfo();
             System.out.println("Jenis Akun: " + akunDitemukan.getClass().getSimpleName());
        } else {
            System.out.println("Akun tidak ditemukan.");
        }

        System.out.println("\n-> Cari akun dengan nomor: 'X-999'");
        Akun akunTidakDitemukan = cariAkun("X-999");
        if (akunTidakDitemukan != null) {
            System.out.println("Akun ditemukan! Info:");
            akunTidakDitemukan.getInfo();
        } else {
            System.out.println("Akun tidak ditemukan.");
        }

    }

    //untuk mencari akun berdasarkan nomor
    public static Akun cariAkun(String nomor) {
        for (Akun akun : daftarAkun) {
            if (akun.getNomorAkun().equalsIgnoreCase(nomor)) { //case-insensitive
                return akun;
            }
        }
        return null; //akun tidak ditemukan
    }

    // Fungsi menampilkan info semua akun
    public static void lihatSemuaAkun() {
        if (daftarAkun.isEmpty()) {
            System.out.println("\nBelum ada akun yang terdaftar.");
        } else {
            System.out.println("\n===== DAFTAR SEMUA AKUN =====");
            for (Akun akun : daftarAkun) {
                akun.infoAkun();
                System.out.println("Jenis Akun: " + akun.getClass().getSimpleName());
                System.out.println("--------------------");
            }
        }
    }
}