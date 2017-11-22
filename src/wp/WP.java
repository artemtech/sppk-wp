package wp;

import java.util.Scanner;

/**
 *
 * @author sofyan_s
 * @author dwi_wahyu_a
 */
public class WP {

    private static int JUMLAH_KRITERIA;

    public static void main(String[] args) {
        Scanner inputs = new Scanner(System.in);
        System.out.print("masukkan jumlah kriteria: ");
        JUMLAH_KRITERIA = inputs.nextInt();
        /*
         * [
         * Nama Kriteria : bobot ]
         */
        String daftarKriteria[][] = new String[JUMLAH_KRITERIA][3];
        for (int i = 0; i < daftarKriteria.length; i++) {
            System.out.printf("Kriteria C%d:\n", i + 1);
            System.out.println("-----------------");
            System.out.print("Nama Kriteria:");
            daftarKriteria[i][0] = inputs.next();
            inputs.nextLine();
            System.out.print("Bobot:");
            daftarKriteria[i][1] = inputs.next();
            System.out.print("Golongan (Benefit/Cost) (B/C)?");
            daftarKriteria[i][2] = inputs.next();
            System.out.println("-----------------");
        }
        System.out.println("==============================================");
        //------------------------------------------------------------------
        System.out.print("Masukkan jumlah alternatif: ");
        int JUMLAH_ALTERNATIF = inputs.nextInt();
        String alternatif[][] = new String[JUMLAH_ALTERNATIF][JUMLAH_KRITERIA + 1];
        for (int i = 0; i < alternatif.length; i++) {
            System.out.printf("Alternatif ke%d:\n", i + 1);
            System.out.println("-----------------");
            System.out.print("Nama Alternatif:");
            alternatif[i][0] = inputs.next();
            inputs.nextLine();
            for (int j = 1; j < alternatif[i].length; j++) {
                System.out.printf("C%d : ", j);
                alternatif[i][j] = inputs.next();
                inputs.nextLine();
            }
            System.out.println("------------------");
        }

        putuskanWP(daftarKriteria, alternatif);
    }

    private static void putuskanWP(String[][] daftarKriteria, String[][] alternatif) {
        double normalisasi[] = normalisasiW(daftarKriteria);
        System.out.println("hasil normalisasi: ");
        for (int i = 0; i < normalisasi.length; i++) {
            System.out.printf("W%d : ", i + 1);
            System.out.println(normalisasi[i]);
        }
        double vektorS[] = hitungVektorS(daftarKriteria,alternatif,normalisasi);
        System.out.println("hasil VektorS: ");
        for (int i = 0; i < vektorS.length; i++) {
            System.out.printf("S%d : ", i + 1);
            System.out.println(vektorS[i]);
        }
//        double preferensi[] = hitungPreferensi(vektorS);
//        urutkan(preferensi);
    }

    private static double[] normalisasiW(String[][] daftarKriteria) {
        double hasil[] = new double[daftarKriteria.length];
        for (int i = 0; i < hasil.length; i++) {
            double bobotSaatIni = Double.parseDouble(daftarKriteria[i][1]); // variabel bobot 
            double totalBobot = 0;
            double normalisasi = 0;
            for (int j = 0; j < daftarKriteria.length; j++) {
                totalBobot += Double.parseDouble(daftarKriteria[j][1]);
            }
            normalisasi = bobotSaatIni / totalBobot;
            hasil[i] = normalisasi;
        }
        return hasil;
    }

    private static double[] hitungVektorS(String[][] daftarKriteria, String[][] alternatif, double[] normalisasi) {
        double hasil[] = new double[alternatif.length];
        
        for (int i = 0; i < hasil.length; i++) {
            double hasilSi = 0;
            double hasilPerAlternatif = 1;
            for (int j = 0; j < alternatif[i].length - 1; j++) {
                // kalau cost normalisasi negatif (untuk pangkat)
                if (daftarKriteria[i][2].compareToIgnoreCase("c") == 0) {
                    hasilSi = Math.pow(Double.parseDouble(alternatif[i][j+1]), -normalisasi[j]);
                }
                if(daftarKriteria[i][2].compareToIgnoreCase("b") == 0){
                    hasilSi = Math.pow(Double.parseDouble(alternatif[i][j+1]), normalisasi[j]);
                }
                // S(i) = c(i)(j) ^ w(i)
//                System.out.println("Normalisasi j:"+normalisasi[j]);
                hasilPerAlternatif = hasilPerAlternatif * hasilSi;
            }
            hasil[i] = hasilPerAlternatif;

        }
        return hasil;
    }

    private static double[] hitungPreferensi(double[] vektorS) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void urutkan(double[] preferensi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}