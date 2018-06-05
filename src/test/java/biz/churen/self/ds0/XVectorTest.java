package biz.churen.self.ds0;

import org.junit.jupiter.api.*;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by lihai5 on 2018/6/5.
 * JUnit5 Test For XVector.java
 */
@DisplayName("XVectorTest Doing")
class XVectorTest {

  XVectorTest(TestInfo testInfo) {
    assertEquals("XVectorTest Doing", testInfo.getDisplayName());
  }

  @BeforeAll static void beforeAll(TestInfo testInfo) {
    System.out.println(testInfo.getDisplayName() + " ==> Start ");
  }

  @AfterAll
  static void afterAll(TestInfo testInfo) {
    System.out.println(testInfo.getDisplayName() + " ==> Done ");
  }

  @AfterEach
  void afterEach(TestInfo testInfo) {
    String methodName = testInfo.getTestClass().get().getName()
                        + "."
                        + testInfo.getTestMethod().get().getName();
    System.out.println("Test Method ["+ methodName +"] Done");
  }

  @Test void bubble() {
  }

  @Test void bubbleSort() {
  }

  @Test void merge() {
  }

  @Test void mergeSort() {
  }

  @Test void partition() {
  }

  @Test void quickSort() {
  }

  @Test void heapSort() {
  }

  @Test void sort() {
  }

  @Test void unSort() {
  }

  @Test void unSort1() {
  }

  @Test void size() {
  }

  @Test void empty() {
  }

  @Test void disOrdered() {
  }

  @Test void find() {
  }

  @Test void search() {
  }

  @Test void binarySearch() {
    String[] arr = {"a", "b", "c", "d", "e", "f"};
    XVector<String> v = new XVector<>(arr, arr.length);
    int idx = v.binarySearch("a", 0, v.size(), String::compareTo);
    assertEquals(idx, 0);

    idx = v.binarySearch("b", 0, v.size(), String::compareTo);
    assertEquals(idx, 1);

    idx = v.binarySearch("c", 0, v.size(), String::compareTo);
    assertEquals(idx, 2);

    idx = v.binarySearch("d", 0, v.size(), String::compareTo);
    assertEquals(idx, 3);

    idx = v.binarySearch("e", 0, v.size(), String::compareTo);
    assertEquals(idx, 4);

    idx = v.binarySearch("f", 0, v.size(), String::compareTo);
    assertEquals(idx, 5);

    idx = v.binarySearch("h", 0, v.size(), String::compareTo);
    assertEquals(idx, -1);
  }

  @Test void fibonacciSearch() {
    String[] arr = {"a", "b", "c", "d", "e", "f"};
    XVector<String> v = new XVector<>(arr, arr.length);
    int idx = v.fibonacciSearch("a", 0, v.size(), String::compareTo);
    assertEquals(idx, 0);

    idx = v.fibonacciSearch("b", 0, v.size(), String::compareTo);
    assertEquals(idx, 1);

    idx = v.fibonacciSearch("c", 0, v.size(), String::compareTo);
    assertEquals(idx, 2);

    idx = v.fibonacciSearch("d", 0, v.size(), String::compareTo);
    assertEquals(idx, 3);

    idx = v.fibonacciSearch("e", 0, v.size(), String::compareTo);
    assertEquals(idx, 4);

    idx = v.fibonacciSearch("f", 0, v.size(), String::compareTo);
    assertEquals(idx, 5);

    idx = v.fibonacciSearch("h", 0, v.size(), String::compareTo);
    assertEquals(idx, -1);
  }

  @Test void get() {
    String[] arrC = {"a", "b", "c", "d"};
    XVector<String> c = new XVector<>(arrC, arrC.length);
    assertEquals(c.get(0), "a");
    assertEquals(c.get(1), "b");
    assertEquals(c.get(3), "d");
    assertThrows(IndexOutOfBoundsException.class, () -> c.get(4));
  }

  @Test void put() {
    String[] arrC = {"a", "b", "c"};
    XVector<String> c = new XVector<>(arrC, arrC.length);

    String[] arrD = {"a", "b", "d"};
    XVector<String> d = new XVector<>(arrD, arrD.length);
    c.put(2, "d");
    assertTrue(c.equals(d));

    assertThrows(IndexOutOfBoundsException.class, () -> c.put(5, "e"));
  }

  @Test void remove() {
  }

  @Test void insert() {
    XVector<String> a = new XVector<>(String.class);
    a.insert("a");

    String[] arrB = {"a"};
    XVector<String> b = new XVector<>(arrB, arrB.length);
    assertTrue(a.equals(b));

    a.insert("b");
    b.insert("b");
    assertTrue(a.equals(b));

    String[] arrC = {"a", "b", "c", "d"};
    XVector<String> c = new XVector<>(arrC, arrC.length);
    a.insert("d");
    a.insert(2, "c");
    assertTrue(a.equals(c));
  }

  @Test void deDuplicate() {
    String[] arrA = {"a", "c", "b", "d", "c", "d", "e", "d", "e", "e", "f"};
    XVector<String> a = new XVector<>(arrA, arrA.length);
    assertTrue(a.disOrdered() > 0);
    a.deDuplicate();
    assertEquals(a.size(), 6);

    String[] arrB = {"a", "b", "c", "d", "e", "f"};
    XVector<String> b = new XVector<>(arrB, arrB.length);
    assertTrue(a.equals(b));
  }

  @Test void uniquify() {
    String[] arrA = {"a", "a", "b", "b", "c", "d", "d", "d", "d", "e", "f"};
    XVector<String> a = new XVector<>(arrA, arrA.length);
    assertEquals(a.disOrdered(), 0);
    a.uniquify();
    assertEquals(a.size(), 6);

    String[] arrB = {"a", "b", "c", "d", "e", "f"};
    XVector<String> b = new XVector<>(arrB, arrB.length);
    assertTrue(a.equals(b));
  }

  @Test void traverse() {
    String[] arrA = {"a", "b", "c"};
    XVector<String> a = new XVector<>(arrA, arrA.length);
    XVector<String> b = new XVector<>(String.class, arrA.length);
    Consumer<String> consumer = s -> {
      System.out.println(s);
      b.insert(s);
    };
    a.traverse(consumer);
    assertTrue(a.equals(b));
  }

  @Test void equals() {
    String[] arrA = {"a", "b", "c"};
    XVector<String> a = new XVector<>(arrA, arrA.length);
    XVector<String> b = new XVector<>(arrA, arrA.length);
    assertEquals(true, a.equals(b));

    XVector<String> c = null;
    assertEquals(false, a.equals(c));
    XVector<String> d = new XVector<>(arrA, arrA.length - 1);
    assertEquals(false, a.equals(d));
  }
}
