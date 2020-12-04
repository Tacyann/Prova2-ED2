package heapBinario;

import colecoes.MyIterator;

public class HeapMax<E extends Comparable<E>> extends Heap<E> {
	@Override
	protected void borbulheParaCima(int indice) {
		int indicePai = (indice - 1) / 2;
		Comparable infoTemp = heapArray[indice];

		while ((indice > 0) && (heapArray[indicePai].compareTo(infoTemp) < 0)) {
			heapArray[indice] = heapArray[indicePai];
			indice = indicePai;
			indicePai = (indice - 1) / 2;
		}
		heapArray[indice] = infoTemp;
	}

	@Override
	protected void borbulheParaBaixo(int indice) {
		int maiorFilho = 0;
		int filhoDireito, filhoEsquerdo;

		Comparable infoTemp = heapArray[indice];

		while ((2 * indice) + 2 <= numItens) {
			filhoEsquerdo = 2 * indice + 1;
			filhoDireito = filhoEsquerdo + 1;
			// procura menor filho
			if ((filhoDireito <= numItens - 1)
					&& (heapArray[filhoDireito]
							.compareTo(heapArray[filhoEsquerdo]) > 0))
				maiorFilho = filhoDireito;
			else
				maiorFilho = filhoEsquerdo;

			if (infoTemp.compareTo(heapArray[maiorFilho]) > 0)
				break;

			heapArray[indice] = heapArray[maiorFilho];
			indice = maiorFilho;
		}
		heapArray[indice] = infoTemp;
	}

	public HeapMax() {
		super();
	}

	public HeapMax(int capInicial) {
		super(capInicial);
	}

}
