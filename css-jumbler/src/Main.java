import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static String output = "";

    public static void main(String[] args) throws Exception {

        File dir = new File("./res/");
        File in = dir.listFiles()[0];
        String name = in.getName();
        System.out.println(name);
        String extension = name.substring(name.indexOf("."));

        if(!extension.equals("css")) {
            FileReader fr;
            BufferedReader br;
            FileWriter fw;
            BufferedWriter bw;

            try {
                fr = new FileReader(in);
                br = new BufferedReader(fr);
                List<List<String>> list = new ArrayList<List<String>>();

                String ln = "";
                String all = "";

                while ((ln = br.readLine()) != null) {
                    all += ln + "\n";
                }

                Node root = new Node(null, "");
                Node cur = root;

                String block = "";

                for(int i = 0; i < all.length(); i++) {
                    String charc = "" + all.charAt(i);
                    if(all.charAt(i) == '{') {
                        block += charc;
                        Node child = new Node(cur, block);
                        cur.ch.add(child);
                        cur = child;
                        block = "";
                    } else if(all.charAt(i) == '}') {
                        cur.body += block;
                        cur.end = charc;
                        cur = cur.par;
                        block = "";
                    } else if(all.charAt(i) == ';') {
                        cur.body += (block + charc);
                        block = "";
                    } else {
                        block += charc;
                    }
                }

                br.close();
                fr.close();

                jumble(root);

                fw = new FileWriter(in, false);
                bw = new BufferedWriter(fw);

                bw.write(output);

                bw.close();
                fw.close();

            } catch(Exception e) {
                System.out.println("Error has occured");
                e.printStackTrace();
            }

        } else {
            System.out.println("File is NOT a CSS file");
        }

    }

    public static void jumble(Node cur) {
        if(cur.ch.isEmpty()) {
             output += (cur.header + cur.body + cur.end);
        } else {
            output += cur.header;
            int[] order = genPerm(cur.ch.size());
            for(int i = 0; i < order.length; i++) {
                jumble(cur.ch.get(order[i]));
            }
            output += ("\n" + cur.end);
        }
    }

    public static int[] genPerm(int n) {
        int[] arr = new int[n];
        for(int i = 0; i < n; i++)
            arr[i] = i;
        for(int i = n-1; i >= 1; i--) {
            int index = (int)(Math.random() * (i+1));
            int swap = arr[index];
            arr[index] = arr[i];
            arr[i] = swap;
        }
        return arr;
    }

}

class Node {
    Node par;
    List<Node> ch;
    String header = "", body = "", end = "";

    public Node(Node par, String header) {
        this.par = par;
        ch = new ArrayList<Node>();
        this.header = header;
    }
}
