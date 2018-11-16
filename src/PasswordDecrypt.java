import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.codec.digest.Crypt;


public class PasswordDecrypt {

    public static void main(String[] args) throws IOException {
        int count = 0;
        long start = System.nanoTime(); // for sequential program is better to use nanoTime than currentTimeMills()(that is wall-clock time)
        String fileName="./PswDb/db100.txt";
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        ArrayList<String> psws = new ArrayList<>();
        ArrayList<String> hashes = new ArrayList<>();
        while(scanner.hasNextLine()){
            //process each line
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            psws.add(parts[0]);
            hashes.add(parts[1]);
        }
        scanner.close();
        int size=psws.size();
        System.out.println("Num psw: " + size);
        for(int i=0;i<size;i++){
            int d=1;
            int m=1;
            int y=1940;
            boolean found=false;
            while(!found && y<=2010){
                while(!found && m<=12){
                    while(!found && d<=31){
                        String dd=String.format("%02d", d);
                        String mm=String.format("%02d", m);
                        String yyyy=Integer.toString(y);
                        String psw=yyyy+mm+dd;
                        String hash=Crypt.crypt(psw, "parallel");
                        if(hashes.get(i).equals(hash)){
                            found=true;
                            count++;
                            System.out.println("Psw db: " + psws.get(i) + " Psw generated: " + psw + " finds, crypt: " + hashes.get(i));
                        }
                        d++;
                    }
                    d=1;
                    m++;
                }
                m=1;
                y++;
            }
        }
        long finish = System.nanoTime();
        long timeelaps = (finish-start)/1000000;
        System.out.println();
        System.out.println("Psw decrypted: " + count + " Time elapsed: " + timeelaps +"ms " + timeelaps/1000 +"s");
    }

}

