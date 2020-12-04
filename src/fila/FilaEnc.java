package fila;

import listasEncadeadas.ListaSimpEnc;
import colecoes.MyIterator;



public class FilaEnc<E extends Comparable<E>> extends Fila<E> {

	private ListaSimpEnc<E> list;

	private class Iterator<E extends Comparable<E>> implements MyIterator<E> {

		private MyIterator<E> it;

		@Override
		public E getFirst() {
			return it.getFirst();
		}

		@Override
		public E getNext() {
			return it.getNext();
		}

		@Override
		public void remove() {
			it.remove();
			numItens--;
		}

		public Iterator() {
			it = (MyIterator<E>) list.iterator();
		}
	}

	@Override
	public MyIterator<E> iterator() {
		return new Iterator();
	}

	@Override
	public void clear() {
		list.clear();
		numItens = 0;
	}

	@Override
	public E remova() {
		E temp = list.removeFromBegin();
		if (temp == null)
			return null;
		numItens--;
		return temp;
	}

	@Override
	public boolean insira(E obj) {
		list.insertAtEnd(obj);
		numItens++;
		return true;
	}

	@Override
	public int size() {
		return numItens;
	}

	public FilaEnc() {
		list = new ListaSimpEnc<E>();
	}
}
