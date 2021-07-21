/**
 * Create by Kannika Armstrong
 * TCSS342(Spring 2021): April 15, 2021
 * Assignment2 : Compressed Literature (Encoder class)
 * Professor. Christopher Paul Marriott
 */

import java.io.*;
import java.util.*;


public class Encoder {

    private String inputFileName = "WarAndPeace.txt"; // final filename for the uncompressed input file
    private String outputFileName = "WarAndPeace_compression.txt"; // filename for the compressed output file
    private String codesFileName = "WarAndPeace_codes.txt"; // filename for the codes output file

//    private String inputFileName = "Text2Test"; // final filename for the uncompressed input file
//    private String outputFileName = "test_compression.txt"; // filename for the compressed output file
//    private String codesFileName = "test_codes.txt"; // filename for the codes output file


    private String text; // the text loaded in from the input file
    private Map<Character, Integer> frequencies; //the frequency of each character in the input file
    private HuffmanNode huffmanTree; // root of HuffMan Tree
    private Map<Character, String> codes; // the codes assigned to each character by the Huffman algorithm
    private byte[] encodedText; //the encoded binary string

    public Encoder() {
        this.frequencies = new HashMap<>();
        this.codes = new HashMap<>();
        this.text = "";
    }

    /** Using BufferedReader to read the input and loaded into text
     */
    private void readInputFile() {
//        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(inputFileName))) {
//            int singleCharInt;
//            char singleChar;
//            StringBuilder s = new StringBuilder();
//            while((singleCharInt = bufferedInputStream.read()) != -1) {
//                s.append((char) singleCharInt);
//            }
//            bufferedInputStream.close();
//            text = s.toString();
//        } catch (Exception e) {
//            System.out.println("could not find the file!!");
//        }
        try (BufferedReader bufferedReader = new BufferedReader((new FileReader(inputFileName)))) {
            int singleCharInt;
            StringBuilder readResult = new StringBuilder();
            while ((singleCharInt = bufferedReader.read()) != -1) {
                readResult.append((char) singleCharInt);

            }
            bufferedReader.close();
            text = readResult.toString();
        } catch (Exception e) {
            System.out.println("could not find the file!!");
        }
    }

    /** Count the frequency of each character in the input file, using HashMap
     */
    private void countFrequency() {
        readInputFile();
        frequencies = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            if (!frequencies.containsKey(text.charAt(i))) {
                frequencies.put(text.charAt(i), 0);
            }
            frequencies.put(text.charAt(i), frequencies.get(text.charAt(i)) + 1);
        }
    }

    private void buildTree() {
        countFrequency();
        // Create a single Huffman node for each character weighted by its count.
        // Add all the nodes to a priority queue.

        MyPriorityQueue<HuffmanNode> pq = new MyPriorityQueue<>();
        for(char c :frequencies.keySet()) {
            pq.offer(new HuffmanNode(frequencies.get(c), c));
        }
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            pq.offer(new HuffmanNode(left, right));
        }
        huffmanTree = pq.poll();
        assignCodes(huffmanTree, "");
    }

    private void assignCodes(HuffmanNode root, String code) {
        // assignsHuffman codes to each character in codes
        if (root != null) {
            if (root.left == null && root.right == null) {
                codes.put(root.c, code);

            } else {
                assignCodes(root.left, code + "0");
                assignCodes(root.right, code + "1");
            }
        }
    }

    private void encode() {
        buildTree();
//        System.out.println(text.length());
//        System.out.println("Character Frequency Map = " + frequencies);
//        System.out.println("\nCharacter Prefix Map = " + codes);

        //For each character in text append the code to an intermediate string.
        StringBuilder s = new StringBuilder();
        List<Byte> bits = new ArrayList<Byte>();
        for (int i = 0; i < text.length(); i++) {
            s.append(codes.get(text.charAt(i)));

            while (s.length() >= 8) {
                int charByte = Integer.parseInt(s.substring(0, 8), 2);
                bits.add((byte) charByte);
                s.delete(0, 8);
            }
        }
        encodedText = new byte[bits.size()];
        for (int i = 0; i < encodedText.length; i++) {
            encodedText[i] = bits.get(i);
        }
    }

    private void writeOutputFile() {
        encode();
        // writes the contents of encodedText to the outputFileName.
        File outputCompressedFile = new File(outputFileName);
        try{
            FileOutputStream compressedFile = new FileOutputStream(outputCompressedFile);
            compressedFile.write(encodedText);
            compressedFile.close();
        } catch (Exception e){
            System.out.println("could not find the file to compressed!!");

        }
        // print out the original file size
        String fileName1 = "WarAndPeace.txt";
        File f = new File(fileName1);
        long fileSize1 = f.length();
        System.out.println("\nUncompressed file size: "
                + String.format("%.2f", (double)fileSize1/1024) + " KB");
        // print out the compression file size
        String fileName2 = "WarAndPeace_compression.txt";
        f = new File(fileName2);
        long fileSize2 = f.length();
        System.out.println("Compressed file size: "
                + String.format("%.2f", (double)fileSize2/1024) + " KB");
        // the ratio after compressed file
        System.out.println("Compressed ratio: "
                + (double)(fileSize2 * 100 / fileSize1) + "%");

    }

    private void writeCodesFile() {
        encode();
        // writes the contents of codes to codeFileName.
        try{
            FileWriter codeOutput = new FileWriter(codesFileName);
            codeOutput.write(codes.toString());
            codeOutput.close();
        } catch (Exception e){
            System.out.println("could not find the file to write!!");
        }
    }

    @Override
    public String toString() {
        return "Encoder{" +
                "inputFileName='" + inputFileName + '\'' +
                ", outputFileName='" + outputFileName + '\'' +
                ", codesFileName='" + codesFileName + '\'' +
                ", text='" + text + '\'' +
                ", frequencies=" + frequencies +
                ", encodedText=" + Arrays.toString(encodedText) +
                '}';
    }

    // HuffmanNode should be a private class of Encoder.
    // The additional constructor should be used to mergeHuffmanNodes during the compression algorithm.
    private class HuffmanNode implements Comparable<HuffmanNode> {
        public int weight;
        public char c;
        public HuffmanNode left;
        public HuffmanNode right;

        public HuffmanNode(int weight, char c) {
            this.weight = weight;
            this.c = c;
            this.left = null;
            this.right = null;
        }

        public HuffmanNode(HuffmanNode left, HuffmanNode right) {
            this.left = left;
            this.right = right;
            weight = left.weight + right.weight;
        }

        @Override
        public int compareTo(HuffmanNode o) {
            return Integer.compare(this.weight, o.weight);
        }

        @Override
        public String toString() {
            return "Characters: " + c +
                    " weight:" + weight;
        }
    }

    public void encoderTest() {
        long start = System.currentTimeMillis();
        writeOutputFile();
        writeCodesFile();
        long end = System.currentTimeMillis();
        long compressedTime = end - start;
        System.out.println("Compressed time: " + compressedTime + " millisecond");
    }
}
