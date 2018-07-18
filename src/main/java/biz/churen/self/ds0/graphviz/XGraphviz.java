package biz.churen.self.ds0.graphviz;

import biz.churen.self.ds0.XBinaryTree;
import biz.churen.self.ds0.XQueue;
import biz.churen.self.ds0.XStack;
import biz.churen.self.ds0.XVector;
import biz.churen.self.ds0.graph.XAdjacencyMatrixGraph;

import java.io.*;
import java.util.List;

/**
 * Created by lihai5 on 2018/7/13.
 * 用 dot 画图的工具类
 * // dot -Tgif 1.dot -o 1.gif
 * // dot -Tjpg 1.dot -o 1.jpg
 */
public class XGraphviz {
  public static final String GRAPHVIZ_PATH = "/C:/Program Files (x86)/Graphviz2.38/bin/";
  public static final String GRAPHVIZ_WORK_DIR = "/D:/temp/ds0/";

  private static String writeDotToFile(String dot, String fileName, String workDict) {
    String fullPathName = workDict + fileName;
    try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fullPathName)))) {
      bw.write(dot);
      bw.flush();
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fullPathName;
  }

  public static <E> void doDrawVector(List<XVector<E>> vectors, String fileName)
      throws IOException, InterruptedException {
    for (XVector<E> v : vectors) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < v.size(); i++) {
        if (i > 0) { sb.append("|"); }
        sb.append(v.get(i));
      }
      sb.insert(0, "digraph G {\n\tnode [shape=record];\n\tstructV [shape=record,label=\"");
      sb.append("\"];\n}");
      String fullPathName = writeDotToFile(sb.toString(), fileName, GRAPHVIZ_WORK_DIR);
      String dotCmd = "dot -Tgif " + fileName + " -o "+ (fileName.split("\\."))[0]+".gif";
      String dotRs = doCmd(GRAPHVIZ_WORK_DIR, dotCmd);
    }
  }

  private static String doCmd(String workDict, String cmd) {
    StringBuilder rs = new StringBuilder();
    try {
      ProcessBuilder pb = new ProcessBuilder(cmd.split(" "));
      pb.redirectErrorStream(true);
      pb.directory(new File(workDict));
      Process process = pb.start();
      BufferedReader inStreamReader = new BufferedReader(
          new InputStreamReader(process.getInputStream()));
      String line;
      while((line = (inStreamReader.readLine())) != null){
        // System.out.println(line);
        rs.append(line);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return rs.toString();
  }

  public static <E> void doDrawStack(List<XStack<E>> vectors) {

  }

  public static <E> void doDrawQueue(List<XQueue<E>> vectors) {

  }

  public static <E> void doDrawBinaryTree(List<XBinaryTree<E>> vectors) {

  }

  public static <V, E> void doDrawGraph(List<XAdjacencyMatrixGraph<V, E>> graphs) {
    
  }
}
