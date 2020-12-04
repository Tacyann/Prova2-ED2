package fila;

import vetor.Vetor;
import colecoes.MyIterator;

public class FilaArray<E extends Comparable<E>> extends Fila<E> {

	private Vetor<E> vet;
	private int nMax;
	private int inicio;
	private int fim;

	@Override
	public void clear() {
		vet.clear();
	}

	@Override
	public MyIterator<E> iterator() {
		return vet.iterator();
	}

	@Override
	public E remova() {
		E temp = vet.removeAt(inicio);
		if (inicio == nMax) {
			inicio = 0;
		} else {
			inicio++;
		}
		return temp;
	}

	@Override
	public boolean insira(E obj) {
		if (vet.size() != nMax) {
			if (fim == nMax) {
				fim = 0;
			} else {
				fim++;
			}
			vet.insertAt(fim, obj);
			return true;
		}
		return false;
	}

	public FilaArray(int max) {
		vet = new Vetor<E>(max);
		inicio = 0;
		fim = 0;
		nMax = max - 1;
	}
}
