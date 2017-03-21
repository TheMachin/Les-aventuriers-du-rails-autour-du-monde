package org.m1;

import static org.junit.Assert.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class ApplicationTriTest {

    long numbers[] = {4, 3, 8, 9, 7, 2, 1, 5};
    long sortedNumbers[] = {1, 2, 3, 4, 5, 7, 8, 9};
    long testNumbers[];

    @BeforeEach
    public void init() throws Exception {
        testNumbers = new long[8];
        System.arraycopy(numbers, 0, testNumbers, 0, 8);
    }

    @AfterEach
    public void clear() throws Exception {
    }

    /**
     * Test de la méthode triFusionNonRecursif
     */
    @Test
    public void testTriFusionNonRecursif() {
        System.out.println("triFusionNonRecursif");
        long[] a = numbers;
        ApplicationTri.triFusionNonRecursif(a);
        assertArrayEquals(sortedNumbers, numbers);
        //fail();
    }

    /**
     * Test de la méthode triFusionRecursif
     */
    @Test
    public void testTriFusionRecursif() {
        System.out.println("triFusionRecursif");
        long[] a = numbers;
        ApplicationTri.triFusionRecursif(a);
        assertArrayEquals(sortedNumbers, numbers);
        //fail();
    }

    /**
     * Test de la méthode triABulle
     */
    @Test
    public void testTriABulle() {
        System.out.println("triABulle");
        long[] a = numbers;
        ApplicationTri.triABulle(a);
        assertArrayEquals(sortedNumbers, numbers);
        //fail();
    }

    /**
     * Test de la méthode quicksort
     */
    @Test
    public void testQuicksort() {
        System.out.println("quicksort");
        long[] a = numbers;
        ApplicationTri.quicksort(a);
        assertArrayEquals(sortedNumbers, numbers);
        //fail();
    }

    /**
     * Test de la méthode fusion
     */
    @Test
    public void testFusion() {
        System.out.println("fusion");
        long[] a = {3, 4, 8, 9, 1, 2, 5, 7};
        long[] tableauTravail = {0, 0, 0, 0, 0, 0, 0, 0};
        int pointeurInf = 0;
        int pointeurMax = 4;
        int borneMax = 7;
        ApplicationTri.fusion(a, tableauTravail, pointeurInf, pointeurMax, borneMax);
        assertArrayEquals(sortedNumbers, a);
        //fail();
    }

    /**
     * Test de la méthode partition
     */
    @Test
    public void testPartition() {
        System.out.println("partition");
        long numbers[] = {4, 3, 5, 2, 1, 7, 8, 9};
        long numbersExpected[] = {1, 3, 5, 2, 9, 7, 8, 4};
        int gauche = 0;
        int droite = 7;
        int pivot = 4;
        int result = ApplicationTri.partition(numbers, gauche, droite, pivot);
        assertArrayEquals(numbersExpected, numbers);
        //fail();
    }

    /**
     * Test de la méthode swap
     */
    @Test
    public void testSwap() {
        System.out.println("swap");
        long numbers[] = {4, 2, 8, 9, 7, 3, 1, 5};
        long numbersExpected[] = {4, 2, 9, 8, 7, 3, 1, 5};
        int i = 2;
        int j = 3;
        ApplicationTri.swap(numbers, i, j);
        assertArrayEquals(numbersExpected, numbers);
        //fail();
    }
}
