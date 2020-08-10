import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


class unicodeAlg {

    String encrypt(String unencrypted, int key) {
        StringBuilder res = new StringBuilder();
        for (char c : unencrypted.toCharArray()) {
            res.append((char) (c + key));
        }
        return res.toString();
    }

    String decrypt(String encrypted, int key) {
        StringBuilder res = new StringBuilder();
        for (char c : encrypted.toCharArray()) {
            res.append((char) (c - key));
        }
        return res.toString();
    }

}

class shiftAlg {
    //a = 97, 97 - 122

    String encrypt(String unencrypted, int key) {
        StringBuilder res = new StringBuilder();
        for (char c : unencrypted.toCharArray()) {
            if (c < 'a' || c > 'z') {
                res.append(c);
            } else {
                int cAppend = Character.toLowerCase(c) + key;

                if (cAppend > 122) {
                    cAppend = ((cAppend - 'a') - 26) + 'a';
                }
                res.append(Character.toUpperCase(c) == c ? (char) Character.toUpperCase(cAppend) : (char)  cAppend);
            }
        }

        return res.toString();
    }

    String decrypt(String encrypted, int key) {
        StringBuilder res = new StringBuilder();
        for (char c : encrypted.toCharArray()) {
            if (c < 'a' || c > 'z') {
                res.append(c);
            } else {
                int cAppend = Character.toLowerCase(c) - key;

                if (cAppend < 97) {
                    cAppend = ((cAppend - 'z') + 26) + 'z';
                }
                res.append(Character.toUpperCase(c) == c ? (char) Character.toUpperCase(cAppend) : (char) cAppend);
            }
        }

        return res.toString();
    }
}

class AlgorithmSelector {

    public static String Do(String alg,String mode, String text, int key) {
        //default : Encryption

        switch (alg) {
            case "unicode" :
                if (mode.equals("dec")) {
                    return new unicodeAlg().decrypt(text, key);
            } else {
                    return new unicodeAlg().encrypt(text, key);
                }
            //default alg : shift
            default:
                if (mode.equals("dec")) {
                    return new shiftAlg().decrypt(text, key);
                } else {
                    return new shiftAlg().encrypt(text, key);
                }

        }

    }
}

public class Main {
    public static void main(String[] args) {

        String text = "";
        String mode = "";
        String alg = "";
        int key = 0;

        String pathIn = "";
        String pathOut = "";

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-alg")) {
                alg = args[i + 1];
            }
            if (args[i].equals("-mode")) {
                mode = args[i + 1];
            }
            if (args[i].equals("-key")) {
                key = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-data")) {
                text = args[i + 1];
            }

            if (args[i].equals("-in")) {
                pathIn = args[i + 1];
            }
            if (args[i].equals("-out")) {
                pathOut = args[i + 1];
            }
        }


        if (!pathIn.isEmpty()) {
            File fileIn = new File(pathIn);
            try (Scanner s = new Scanner(fileIn)) {
                while (s.hasNextLine()) {
                    text = s.nextLine();
                }

                File fileOut = new File(pathOut);

                FileWriter write = new FileWriter(fileOut);
                write.write(AlgorithmSelector.Do(alg, mode, text, key));
                write.close();

            } catch (Exception e) {
                System.out.println("Error caused : " + e.getMessage());
            }

        } else {
            System.out.println(AlgorithmSelector.Do(alg, mode, text, key));
        }
    }

}
