/**
 * Create by Kannika Armstrong
 * TCSS342(Spring 2021): April 15, 2021
 * Assignment2 : Compressed Literature (Main class)
 * Professor. Christopher Paul Marriott
 */
import java.io.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("========== Program: Encoding and Decoding Text File ==========\n" );
        encoderTest();
        decoderTest();
        compareFile();
        testMyPriorityQueue();


    }
    /** use to run the encoder class
     *
     */

    public static void encoderTest(){
        System.out.println("===================== Encoder Processing ====================");
        Encoder e = new Encoder();
        e.encoderTest();
        System.out.println();

    }

    /** use to run decoder class
     *
     */

    public static void decoderTest(){
        System.out.println("===================== Decoder Processing =====================");
        Decoder d = new Decoder();
        d.decoderTest();
        System.out.println();
    }

    /** use to Compare the original file and the decoder file
     *
     */
    private static void compareFile() {
        System.out.println("========== Comparing Original file and Decoder file ==========");
        System.out.println();
        System.out.println("The Original file: WarAndPeace.txt");
        System.out.println("The Decode file: WarAndPeace_decoded.txt");
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader("WarAndPeace.txt"));
            BufferedReader reader2 = new BufferedReader(new FileReader("WarAndPeace_decoded.txt"));
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            boolean areEqual = true;
            int lineNum = 1;
            while (line1 != null || line2 != null) {
                if (line1 == null || line2 == null) {
                    areEqual = false;
                    break;
                } else if (!line1.equalsIgnoreCase(line2)) {
                    areEqual = false;
                    break;
                }
                line1 = reader1.readLine();
                line2 = reader2.readLine();
                lineNum++;
            }
            if (areEqual) {
                System.out.println("The comparing result: Two files have same content.");
            } else {
                System.out.println("The comparing result: Two files have different content. They differ at line " + lineNum);
                System.out.println("File1 has " + line1 + "\nFile2 has " + line2 + " \nat line " + lineNum);
            }
            reader1.close();
            reader2.close();
        } catch (Exception e){
            System.out.println("Could not find the file!!");
        }
        System.out.println();
    }


    /** Test my Priority Queue class
     *
     */
    private static void testMyPriorityQueue() {
        System.out.println("================= Test MyPriorityQueue Class =================\n");
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();

        // empty test
        System.out.println("Initial ArrayList: Expected size: 0, Actual : " + pq.toString() + " size: " + pq.size());

        System.out.println("\nTest offer() : input = [15, 7, 9, 10, 12, 0, 3, 1, 5, 2, 4, 6, 8]\n");

        // tests offer()
        pq.offer(15);
        pq.offer(7);
        pq.offer(9);
        pq.offer(10);
        pq.offer(12);
        pq.offer(0);
        pq.offer(3);
        pq.offer(1);
        pq.offer(5);
        pq.offer(2);
        pq.offer(4);
        pq.offer(6);
        pq.offer(8);


        System.out.println("Expected      : [0, 1, 3, 5, 2, 6, 7, 15, 10, 12, 4, 9, 8] size: 13");
        System.out.println("Actual Result : " + pq.toString() + " size: " + pq.size());

        // tests poll()
        System.out.println("\nTest poll():  Expected: '0', Actual: " + pq.poll());

        // after poll(), checks the elements of MyPriorityQueue.
        System.out.println("\nHuffman Tree after call poll()");
        System.out.println("Expected      : [1, 2, 3, 5, 4, 6, 7, 15, 10, 12, 8, 9] size: 12");
        System.out.println("Actual Result : " + pq.toString() + " size: " + pq.size());

        // tests poll() again
        System.out.println("\nTest poll():  Expected: '1', Actual: " + pq.poll());

        // after poll(), checks the elements of MyPriorityQueue.
        System.out.println("\nHuffman Tree after call poll()");
        System.out.println("Expected      : [2, 4, 3, 5, 8, 6, 7, 15, 10, 12, 9] size: 11");
        System.out.println("Actual Result : " + pq.toString() + " size: " + pq.size());

        // tests offer() again
        pq.offer(0);
        System.out.println("\nTest offer(): offer(0)");

        // after poll(), checks the elements of MyPriorityQueue.
        System.out.println("\nHuffman Tree after call offer(0)");
        System.out.println("Expected      : [0, 4, 2, 5, 8, 3, 7, 15, 10, 12, 9, 6] size: 12");
        System.out.println("Actual Result : " + pq.toString() + " size: " + pq.size());
    }
}
