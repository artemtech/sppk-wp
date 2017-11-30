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
//        System.out.print("masukkan jumlah kriteria: ");
//        JUMLAH_KRITERIA = inputs.nextInt();
//         dummy data
        JUMLAH_KRITERIA = 4;
        /*
         * [
         * Nama Kriteria : bobot ]
         */
        String daftarKriteria[][] = new String[JUMLAH_KRITERIA][3];
        // dummy data:
        daftarKriteria[0][0] = "jarak";
        daftarKriteria[0][1] = "3";
        daftarKriteria[0][2] = "c";
        
        daftarKriteria[1][0] = "harga";
        daftarKriteria[1][1] = "4";
        daftarKriteria[1][2] = "c";
        
        daftarKriteria[2][0] = "kualitas pupuk";
        daftarKriteria[2][1] = "5";
        daftarKriteria[2][2] = "b";
        
        daftarKriteria[3][0] = "dokumentasi";
        daftarKriteria[3][1] = "2";
        daftarKriteria[3][2] = "b";
        
//        for (int i = 0; i < daftarKriteria.length; i++) {
//            System.out.printf("Kriteria C%d:\n", i + 1);
//            System.out.println("-----------------");
//            System.out.print("Nama Kriteria:");
//            daftarKriteria[i][0] = inputs.next();
//            inputs.nextLine();
//            System.out.print("Bobot:");
//            daftarKriteria[i][1] = inputs.next();
//            System.out.print("Golongan (Benefit/Cost) (B/C)?");
//            daftarKriteria[i][2] = inputs.next();
//            System.out.println("-----------------");
//        }
        System.out.println("==============================================");
        //------------------------------------------------------------------
        System.out.print("Masukkan jumlah alternatif: ");
//        int JUMLAH_ALTERNATIF = inputs.nextInt();
        int JUMLAH_ALTERNATIF = 3;
        String alternatif[][] = new String[JUMLAH_ALTERNATIF][JUMLAH_KRITERIA + 1];
//        //dummy data:
        alternatif[0][0] = "cv. surya bahagia";
        alternatif[0][1] = "66";
        alternatif[0][2] = "50";
        alternatif[0][3] = "4";
        alternatif[0][4] = "3";

        alternatif[1][0] = "cv. lambang keseangan";
        alternatif[1][1] = "30";
        alternatif[1][2] = "150";
        alternatif[1][3] = "5";
        alternatif[1][4] = "5";

        alternatif[2][0] = "cv. seger waras";
        alternatif[2][1] = "35";
        alternatif[2][2] = "70";
        alternatif[2][3] = "5";
        alternatif[2][4] = "4";
//        for (int i = 0; i < alternatif.length; i++) {
//            System.out.printf("Alternatif ke%d:\n", i + 1);
//            System.out.println("-----------------");
//            System.out.print("Nama Alternatif:");
//            alternatif[i][0] = inputs.next();
//            inputs.nextLine();
//            for (int j = 1; j < alternatif[i].length; j++) {
//                System.out.printf("C%d : ", j);
//                alternatif[i][j] = inputs.next();
//                inputs.nextLine();
//            }
//            System.out.println("------------------");
//        }

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
        System.out.println("===========================");
//        double totalVektorS = hitungTotalVektorS(vektorS);
//        System.out.println("total VektorS: "+totalVektorS);
        double preferensi[] = hitungPreferensi(vektorS);
        for (int i = 0; i < preferensi.length; i++) {
            System.out.printf("V%d : ", i + 1);
            System.out.println(preferensi[i]);
        }
        System.out.println("===========================");
        urutkan(preferensi,alternatif);
        for (int i = 0; i < preferensi.length; i++) {
            System.out.printf("V%d : ", i + 1);
            System.out.println(preferensi[i]);
        }
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
        double hasil[] = new double[alternatif.length]; //3
        
        for (int i = 0; i < hasil.length; i++) { // 0 - 2
            double hasilSi = 0;
            double hasilPerAlternatif = 1;
//            System.out.println("alternatif ke: "+i);
            for (int j = 1; j < alternatif[i].length; j++) { // 1 - 4
                // kalau cost normalisasi negatif (untuk pangkat)
                if (daftarKriteria[j-1][2].compareToIgnoreCase("c") == 0) {
//                    System.out.printf("cost! %d %d\n",i,j);
                    hasilSi = Math.pow(Double.parseDouble(alternatif[i][j]), -normalisasi[j-1]);
                }
                else if(daftarKriteria[j-1][2].compareToIgnoreCase("b") == 0){
//                    System.out.printf("benefit!%d %d\n",i,j);
                    hasilSi = Math.pow(Double.parseDouble(alternatif[i][j]), normalisasi[j-1]);
                }
//                System.out.println("Normalisasi j:"+normalisasi[j]);
                hasilPerAlternatif = hasilPerAlternatif * hasilSi;
            }
            hasil[i] = hasilPerAlternatif;

        }
        return hasil;
    }

    private static double[] hitungPreferensi(double[] vektorS) {
        double total = hitungTotalVektorS(vektorS);
        double hasil[] = new double[vektorS.length];
        for (int i = 0; i < vektorS.length; i++) {
            hasil[i]=vektorS[i]/total;
        }
        return hasil;
    }

    private static double hitungTotalVektorS(double[] vektorS){
        double total = 0;
        for (double d : vektorS) {
            total += d;
        }
        return total;
    }
    
    private static void urutkan(double[] preferensi,String[][] alternatif) {
        // selection sorting
        for (int i = 0; i < preferensi.length-1; i++) {
            int min_index = i;
            for (int j = i+1; j < preferensi.length; j++) {
                if (preferensi[j] > preferensi[min_index]) {
                    min_index = j;
                }
            }
            // swap:
            double tmp = preferensi[min_index];
            preferensi[min_index] = preferensi[i];
            preferensi[i] = tmp;   
        }
    }

}
