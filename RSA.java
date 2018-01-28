import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA {

    public static void main(String[] args){

        boolean relativePrime=false;
        String text;

        //Ask for message to be encrypted
        System.out.println("Enter message to be encrypted:");
        Scanner scan = new Scanner(System.in);
        text = scan.nextLine();

        //Get two random prime numbers, p and q
        Random rand = new Random();
        BigInteger p = BigInteger.probablePrime(152, rand);
        BigInteger q = BigInteger.probablePrime(152, rand);
        System.out.println("p: " + p);
        System.out.println("q: " + q);

        //n=p*q
        BigInteger n = p.multiply(q);
        System.out.println("n: " + n);

        //t = (p-1)(q-1)
        BigInteger t = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        System.out.println("t: " + t);

        //Get e
        BigInteger e = new BigInteger(304, rand);

        while (!relativePrime){
            e = new BigInteger(304, rand);
            // gcd(e,t) = 1 && 1 < e < t
            if(gcd(e, t).equals(BigInteger.ONE) && e.compareTo(t)==-1){
                relativePrime=true;
            }
        }
        System.out.println("e: " + e);

        //Perform modInverse operation on e using t
        BigInteger d = e.modInverse(t);
        System.out.println("d: " + d);

        System.out.println("\n");

        //Convert string to numbers
        BigInteger convertedTxt = convertString(text);
        System.out.println("Converted Message: " + convertedTxt);

        //Encrypt the message
        BigInteger encryptedTxt = encrypt(convertedTxt, e, n);
        System.out.println("Encrypted: " + encryptedTxt);

        //Decrypt the encrypted message
        BigInteger decryptedTxt = decrypt(encryptedTxt, d, n);
        System.out.println("Decrypted: " + decryptedTxt);

        //Restore message to original state
        String reverted = revertConvert(decryptedTxt);
        System.out.println("Restored Message: " + reverted);

    }

    //Euclidean Algorithm
    public static BigInteger gcd(BigInteger a, BigInteger b)
    {
        if (b.equals(BigInteger.ZERO)){
            return a;
        }
        else{
            return gcd(b, a.mod(b));
        }
    }

    //Converts the string into ASCII
    public static BigInteger convertString(String text){
        String converted = "";
        text = text.toUpperCase();

        for(int i=0;i < text.length();i++){
            int ch = (int) text.charAt(i);
            converted +=ch;

        }

        BigInteger cipherBig = new BigInteger(String.valueOf(converted));
        return cipherBig;
    }

    //Convert the ASCII values back into original state
    public static String revertConvert(BigInteger text){
        String converted = "";
        String textValue = text.toString();

        for(int i=0;i< textValue.length();i+=2){
            int temp = Integer.parseInt(textValue.substring(i, i + 2));
            char ch = (char) temp;
            converted = converted + ch;
        }

        return converted;
    }

    //Encrypt text
    public static BigInteger encrypt(BigInteger text, BigInteger e, BigInteger n){
        return text.modPow(e, n);
    }

    //Decrypt text
    public static BigInteger decrypt(BigInteger text, BigInteger d, BigInteger n){
        return text.modPow(d, n);
    }

}
