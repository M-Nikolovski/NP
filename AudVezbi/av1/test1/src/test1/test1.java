package test1;
import test1.Java8Tester.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class test1 {

//    public class Niza {
        int [] elementi;
        public int prosek() {
            int zbir = 0;
            for (int broj : elementi) {
                zbir += broj;
            }
            return zbir / elementi.length;
        }
//    }

    public static int brojDoProsek( ){
        //Vashiot kod tuka...
    }

    public static void main(String[] args) throws IOException{
        BufferedReader stdin = new BufferedReader( new InputStreamReader(System.in));
        String s = stdin.readLine();
        int N = Integer.parseInt(s); //N e broj na broevi koi ke se vnesuvaat
        Niza niza = new Niza();

        for(int i = 0; i < N; i++) {

        }


        System.out.println(brojDoProsek(niza));
    }

}
