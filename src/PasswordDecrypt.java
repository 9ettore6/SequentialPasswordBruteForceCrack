import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.codec.digest.Crypt;


public class PasswordDecrypt {

    public static void main(String[] args) throws IOException {
        String fileName="./PswDb/db100.txt";
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        ArrayList<String> psws = new ArrayList<String>();
        ArrayList<String> hashes = new ArrayList<String>();
        while(scanner.hasNextLine()){
            //process each line
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            psws.add(parts[0]);
            hashes.add(parts[1]);
        }
        scanner.close();
        int size=psws.size();
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
    }

}

