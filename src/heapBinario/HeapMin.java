package heapBinario;

import colecoes.MyIterator;

public class HeapMin<E extends Comparable<E>> extends Heap<E> {

	@Override
	protected void borbulheParaCima(int indice) {
		int indicePai = (indice - 1) / 2;
		Comparable infoTemp = heapArray[indice];

		while ((indice > 0) && (heapArray[indicePai].compareTo(infoTemp) > 0)) {
			heapArray[indice] = heapArray[indicePai];
			indice = indicePai;
			indicePai = (indice - 1) / 2;
		}
		heapArray[indice] = infoTemp;
	}

	@Override
	protected void borbulheParaBaixo(int indice) {
		int menorFilho = 0;
		int filhoDireito, filhoEsquerdo;

		Comparable infoTemp = heapArray[indice];

		while ((2 * indice) + 2 <= numItens) {
			filhoEsquerdo = 2 * indice + 1;
			filhoDireito = filhoEsquerdo + 1;
			// procura menor filho
			if ((filhoDireito <= numItens - 1)
					&& (heapArray[filhoDireito]
							.compareTo(heapArray[filhoEsquerdo]) < 0))
				menorFilho = filhoDireito;
			else
				menorFilho = filhoEsquerdo;

			if (infoTemp.compareTo(heapArray[menorFilho]) < 0)
				break;

			heapArray[indice] = heapArray[menorFilho];
			indice = menorFilho;
		}
		heapArray[indice] = infoTemp;
	}

	public HeapMin() {
		super();
	}

	public HeapMin(int capInicial) {
		super(capInicial);
	}

}
