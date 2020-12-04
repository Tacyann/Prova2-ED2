package heapBinario;

import java.util.Stack;

import colecoes.ColecaoComparavel;
import colecoes.MyIterator;
import java.lang.reflect.Array;

public abstract class Heap<E extends Comparable<E>> extends
        ColecaoComparavel<E> {

    protected Comparable[] heapArray;
    /*
     *Método recursivo que divide o vetor em dois e depois os mescla e ordena
     */

    public void marge(int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            marge(inicio, meio);
            marge(meio + 1, fim);
            mesclar(inicio, meio, fim);
        }
    }

    public void intercala(int inicio, int fim, int meio) {
        int poslivre = inicio, iniciovetor1 = inicio, iniciovetor2 = meio + 1, i;
        Comparable[] aux = new Comparable[inicio + fim + 1];
        while (iniciovetor1 <= meio && iniciovetor2 <= fim) {
            if (heapArray[iniciovetor1].compareTo(heapArray[iniciovetor2]) > 1) {
                aux[poslivre] = heapArray[iniciovetor1];
                iniciovetor1++;
            } else {
                aux[poslivre] = heapArray[iniciovetor2];
                iniciovetor2++;
            }
            poslivre++;
        }
        for (i = iniciovetor1; i <= meio; i++) {
            aux[poslivre] = heapArray[i];
            poslivre++;
        }
        for (i = iniciovetor2; i <= fim; i++) {
            aux[poslivre] = heapArray[i];
            poslivre++;
        }
        for (i = inicio; i <= fim; i++) {
            heapArray[i] = aux[i];
        }
    }

    private void mesclar(int inicio, int meio, int fim) {
        int tamanho = fim - inicio + 1;
        Comparable[] temp = new Comparable[tamanho];
        System.arraycopy(heapArray, inicio, temp, 0, tamanho);

        int i = 0;
        int j = meio - inicio;

        for (int posicao = 0; posicao < tamanho; posicao++) {
            if (j <= tamanho - 1) {
                if (temp[i] == null || temp[j] == null) {
                    heapArray[inicio + posicao] = temp[i];
                    break;
                }
                if (i <= meio - inicio) {
                    if (temp[i].compareTo(temp[j]) > 1) {
                        heapArray[inicio + posicao] = temp[i++];
                    } else {
                        heapArray[inicio + posicao] = temp[j++];
                    }
                } else {
                    heapArray[inicio + posicao] = temp[j++];
                }
            } else {
                heapArray[inicio + posicao] = temp[i++];
            }
        }

    }

    public static void selectionSort(int[] array) {
        for (int fixo = 0; fixo < array.length - 1; fixo++) {
            int menor = fixo;

            for (int i = menor + 1; i < array.length; i++) {
                if (array[i] < array[menor]) {
                    menor = i;
                }
            }
            if (menor != fixo) {
                // Troca
                int t = array[fixo];
                array[fixo] = array[menor];
                array[menor] = t;
            }
        }
    }
    
    /*
    public Long[] ordenarCrescente(Long[] array) {
    for(int fixo = 1; fixo < array.length; fixo++) {
        for (int var = fixo; var >= 1 && array[var] < array[var - 1]; var--) {
            // Troca os elementos
            array[var] += array[var - 1];
	    array[var - 1] = array[var] - array[var - 1];
	    array[var] -= array[var - 1];
        }
    }
    return array;
}
    */
    
    public static void insertionSort(int[] array){
        
        for(int i =0; i < array.length; i++){
            int[]ordenado = new int[array[i]];
            for(int j =i;j< 0;j++){
                if(j<i){
                  ordenado[j] = array[i];  
            }   
            }
        }
        
    }

    /* 
     * Ordena dois trechos ordenados e adjacente de vetores e ordena-os
     * conjuntamente
     */
    @Override
    public void clear() {
        heapArray = new Comparable[heapArray.length];
        numItens = 0;
    }

    // n�o suportados
    @Override
    public boolean remove(E obj) {
        try {
            throw new Exception("Recurso n�o suportado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public E retrieve(E obj) {
        try {
            throw new Exception("Recurso n�o suportado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean contains(E obj) {
        try {
            throw new Exception("Recurso n�o suportado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected abstract void borbulheParaBaixo(int indice);

    protected abstract void borbulheParaCima(int indice);

    @Override
    public boolean add(E obj) {
        //redimensiona
        if (numItens == heapArray.length) {
            Comparable[] novoHeapArray = new Comparable[numItens
                    + (numItens / 2)];
            System.arraycopy(heapArray, 0, novoHeapArray, 0, numItens);
            heapArray = novoHeapArray;
        }

        //add
        heapArray[numItens] = obj;
        borbulheParaCima(numItens);
        numItens++;
        return true;

    }

    public E remove() {
        if (numItens > 0) {
            Comparable obj = heapArray[0];

            numItens--;
            heapArray[0] = heapArray[numItens];
            heapArray[numItens] = null;

            borbulheParaBaixo(0);
            return (E) obj;
        }
        return null;
    }

    public Heap() {
        heapArray = new Comparable[5];
    }

    public Heap(int capInicial) {
        heapArray = new Comparable[capInicial];
    }

    private class HeapIterator implements MyIterator<E> {

        private Heap<E> heap;
        private Comparable removerTalvez;

        @Override
        public E getFirst() {
            // testa o tipo da instancia corrente
            if (Heap.this instanceof HeapMax) {
                heap = new HeapMax<E>(numItens);
            } else {
                heap = new HeapMin<E>(numItens);
            }

            // copia os dados pro heap do iterator
            System.arraycopy(heapArray, 0, heap.heapArray, 0, numItens);
            heap.numItens = numItens;

            removerTalvez = heap.remove();
            return (E) removerTalvez;
        }

        @Override
        public E getNext() {
            removerTalvez = heap.remove();
            return (E) removerTalvez;
        }

        @Override
        public void remove() {
            int pos = 0;
            boolean achou = false;
            // acha a posi��o
            for (pos = 0; pos < heapArray.length; pos++) {
                if (removerTalvez.compareTo(heapArray[pos]) == 0) {
                    achou = true;
                    break;
                }
            }

            if (achou) {
                Comparable obj = heapArray[pos];
                numItens--;
                heapArray[pos] = heapArray[numItens];
                heapArray[numItens] = null;

                borbulheParaBaixo(pos);
            }
        }
    }

    @Override
    public MyIterator<E> iterator() {
        return new HeapIterator();
    }

    public static void main(String[] args) {
        HeapMax<Integer> heapMax = new HeapMax<>(2);
        heapMax.add(26);
        heapMax.add(31);
        heapMax.add(98);
        heapMax.add(15);
        heapMax.add(30);
        heapMax.add(84);
        heapMax.add(51);
        heapMax.add(27);
        heapMax.add(65);
        heapMax.add(29);
        heapMax.add(71);
        heapMax.marge(0, heapMax.size());
        for (Object i : heapMax.toArray()) {
            Integer it = (Integer) i;
            System.out.println(i);

        }

        /*System.out.println("Max Heap");
         HeapMax<Integer> h = new HeapMax<Integer>(2);
         h.add(10);
         h.add(12);
         h.add(6);
         h.add(7);
         h.add(3);
        
         System.out.println("Iterator");
         MyIterator<Integer> it = h.iterator();
         Integer temp = it.getFirst();
         System.out.println(temp);
         temp = it.getNext();
         it.remove();
         while (temp != null) {
         System.out.println(temp);
         temp = it.getNext();
         }
        
         System.out.println("Remo��o");
         Integer a = h.remove();
         while (a != null) {
         System.out.println(a);
         a = h.remove();
         }
        
         System.out
         .println("_______________________________________________________________________");
         System.out.println("Min Heap");
         HeapMin<Integer> heap = new HeapMin<Integer>(7);
         heap.add(10);
         heap.add(12);
         heap.add(6);
         heap.add(7);
         heap.add(3);
        
         System.out.println("Iterator");
         MyIterator<Integer> ite = heap.iterator();
         Integer temporario = ite.getFirst();
        
         System.out.println(temporario);
         temporario = ite.getNext();
         ite.remove();
        
         while (temporario != null) {
         System.out.println(temporario);
         temporario = ite.getNext();
         }
        
         System.out.println("Remo��o");
         Integer numero = heap.remove();
         while (numero != null) {
         System.out.println(numero);
         numero = heap.remove();
         }*/
    }
}
