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
        /** DAFTAR KRITERIA...
         * Nama Kriteria :: nilai bobot :: kategori Benefit/Cost]
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
//        System.out.print("Masukkan jumlah alternatif: ");
//        int JUMLAH_ALTERNATIF = inputs.nextInt();
        int JUMLAH_ALTERNATIF = 3;
        
        /** DAFTAR ALTERNATIF..
         * [0] -> nama alternatif
         * [1 - n-2] -> nilai bobot / nilai referensi
         * [n-1] -> menampung hasil perhitungan nilai preferensi
         */
        
        String alternatif[][] = new String[JUMLAH_ALTERNATIF][JUMLAH_KRITERIA + 2];
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
//            for (int j = 1; j < alternatif[i].length-1; j++) {
//                System.out.printf("C%d : ", j);
//                alternatif[i][j] = inputs.next();
//                inputs.nextLine();
//            }
//            System.out.println("------------------");
//        }

        hitungWP(daftarKriteria, alternatif);
    }

    private static void hitungWP(String[][] daftarKriteria, String[][] alternatif) {
        // urutan awal yang diberikan user:
        for (int i = 0; i < alternatif.length; i++) {
            System.out.printf("Urutan%d : ", i + 1);
            System.out.println(alternatif[i][0]);
        }
        System.out.println("=============================");
        
        //normalisasikan daftar bobot kriteria
        
        double normalisasi[] = normalisasiW(daftarKriteria);
        System.out.println("hasil normalisasi: ");
        for (int i = 0; i < normalisasi.length; i++) {
            System.out.printf("W%d : ", i + 1);
            System.out.println(normalisasi[i]);
        }
        
        //hitung vektor S dari alternatif solusi yang diberikan
        
        double vektorS[] = hitungVektorS(daftarKriteria,alternatif,normalisasi);
        System.out.println("hasil VektorS: ");
        for (int i = 0; i < vektorS.length; i++) {
            System.out.printf("S%d : ", i + 1);
            System.out.println(vektorS[i]);
        }
        System.out.println("===========================");

        // hitung nilai preferensi dari vektor S tiap alternatif 
        
        double preferensi[] = hitungPreferensi(vektorS,alternatif);
        for (int i = 0; i < preferensi.length; i++) {
            System.out.printf("V%d : ", i + 1);
            System.out.println(preferensi[i]);
        }
        System.out.println("===========================");
        
        // mengurutkan alternatif berdasarkan nilai preferensi
        
        urutkan(alternatif);
        for (int i = 0; i < alternatif.length; i++) {
            System.out.printf("Urutan %d : %s \t%s \n", i + 1,alternatif[i][0],alternatif[i][alternatif[i].length-1]);
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
            for (int j = 1; j < alternatif[i].length-1; j++) { // 1 - 4
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

    private static double[] hitungPreferensi(double[] vektorS,String[][] alternatif) {
        double total = hitungTotalVektorS(vektorS);
        double hasil[] = new double[vektorS.length];
        for (int i = 0; i < vektorS.length; i++) {
            hasil[i]=vektorS[i]/total;
            alternatif[i][alternatif[i].length-1] = String.valueOf(hasil[i]);
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
    
    private static void urutkan(String[][] alternatif) {
        // selection sorting
        for (int i = 0; i < alternatif.length-1; i++) {
            int min_index = i;
            for (int j = i+1; j < alternatif.length; j++) {
                if (Double.parseDouble(alternatif[j][alternatif[j].length-1]) > Double.parseDouble(alternatif[min_index][alternatif[j].length-1])) {
                    min_index = j;
                }
            }
            // swap:
            String[] tmp = alternatif[min_index];
            alternatif[min_index] = alternatif[i];
            alternatif[i] = tmp;   
        }
    }

}
