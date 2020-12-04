/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prova.pkg2.ed2.tacyanne.bernadete.lima.pimentel;

import java.util.Scanner;

/**
 *
 * @author Dulce
 */
public class Prova2ED2TacyanneBernadeteLimaPimentel {

    /**
     * @param args the command line arguments
     */
    public static int[] insertionSort(int[] array) {
        int[] ordenado = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            ordenado[i] = array[i];
            for (int j = i; j > 0; j--) {
                if (ordenado[i] < array[j]) {
                    ordenado[i] = array[j];
                } else if (ordenado[i] > array[j]) {
                    ordenado[j] = array[i];
                }
            }
        }
        return ordenado;
    }

    public static int[] ordenarCrescente(int[] array) {
        for (int fixo = 1; fixo < array.length; fixo++) {
            for (int var = fixo; var >= 1 && array[var] < array[var - 1]; var--) {
                // Troca os elementos
                array[var] += array[var - 1];
                array[var - 1] = array[var] - array[var - 1];
                array[var] -= array[var - 1];
            }
        }
        return array;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Scanner teclado = new Scanner(System.in);
        int[] numeros = new int[]{8, 2, 5, 3, 10, 7, 1, 4, 6, 9};
        numeros = ordenarCrescente(numeros);
        for (int i = 0; i < numeros.length; i++) {
            System.out.println(numeros[i]);
        }
    }

}
