/**
 * Create by Kannika Armstrong
 * TCSS342(Spring 2021): April 15, 2021
 * Assignment2 : Compressed Literature (Decoder class)
 * Professor. Christopher Paul Marriott
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Decoder {

    public void decode(){
        /** read all byte from the compressed file
         * https://howtodoinjava.com/java/io/read-file-content-into-byte-array/
         */
        try {
            byte[] decodeByte = Files.readAllBytes(Paths.get(new File("WarAndPeace_compression.txt").getPath()));

            /** the the byte file to binary string
             * https://stackoverflow.com/questions/12310017/how-to-convert-a-byte-to-its-binary-string-representation
             */

            StringBuilder decodeBinaryString = new StringBuilder();

            for (byte bytes: decodeByte) {
                String st = String.format("%8s",
                        Integer.toBinaryString(bytes & 0xFF)).replace(' ', '0');
                if (Integer.toBinaryString(bytes & 0xFF).length() == 0){
                    decodeBinaryString.append("0");
                }
                decodeBinaryString.append(st);
            }


            /** Rebuilding the Huffman tree code from our code.txt file
             */
            String bits = new String(Files.readAllBytes(Paths.get("WarAndPeace_codes.txt"))); // read our codes.txt
            Map<Character, String> decodedCodes = new HashMap<Character, String>();
            StringBuilder temp1 = new StringBuilder();
            for (int i = 0; i < bits.length(); i++) {
                char decodeChar;

                // if the character in bits is '=' and next is '0' or '1',
                // then i-1 is a character to be printed.
                if (bits.charAt(i) == '=' && (bits.charAt(i + 1) == '0' || bits.charAt(i + 1) == '1')) {
                    char key = bits.charAt(i - 1);
                    i++;
                    decodeChar = bits.charAt(i);

                    // adds the binary to the value of map
                    while (decodeChar == '0' || decodeChar == '1') {
                        temp1.append(decodeChar);
                        i++;
                        decodeChar = bits.charAt(i);
                    }
                    decodedCodes.put(key, temp1.toString());
                    temp1.delete(0, temp1.length());
                }
            }


            /** produces the decoded text file.
             */

            // get the character in the codes map
            Map<String, Character> binaryChar = new HashMap<String, Character>();
            for (Map.Entry<Character, String> entry : decodedCodes.entrySet()) {
                binaryChar.put(entry.getValue(), entry.getKey());
            }
            StringBuilder str = new StringBuilder(); // store the character of binary string
            StringBuilder decodeResult = new StringBuilder(); // all string from binary string
            for (int i = 0; i < decodeBinaryString.length(); i++) {
                str.append(decodeBinaryString.charAt(i));

                // appends the character of the binary string to the result.
                if (binaryChar.containsKey(str.toString())) {
                    decodeResult.append(binaryChar.get(str.toString()));
                    str.delete(0, str.length());
                }
            }

            File file = new File("WarAndPeace_decoded.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(decodeResult.toString());
            }
        } catch (Exception e){
            System.out.println("could not find the file to decode!!");
        }


        String fileName = "WarAndPeace.txt";
        File f = new File(fileName);
        long fileSize = f.length();
        System.out.println("\nOriginal file size: "
                    + String.format("%.2f", (double)fileSize/1024) + " KB");
        fileName = "WarAndPeace_decoded.txt";
        f = new File(fileName);
        fileSize = f.length();
        System.out.println("Decode file size: "
                + String.format("%.2f", (double)fileSize/1024) + " KB");
    }

    public void decoderTest() {
        long start = System.currentTimeMillis();
        decode();
        long end = System.currentTimeMillis();
        long compressedTime = end - start;
        System.out.println("Compressed time: " + compressedTime + " millisecond");
    }
}
