package listasEncadeadas;

import java.io.Serializable;


import colecoes.MyIterator;
import colecoes.MyIteratorBack;

public class ListaEncOrd<E extends Comparable<E>> implements Serializable {
	protected NoDupEnc<E> noCabeca;
	protected int numItens;

	private class ListaDupEncIterator implements MyIterator<E> {

		private NoDupEnc<E> no;

		@Override
		public E getFirst() {
			no = noCabeca.getProx();
			return no.getObj();
		}

		@Override
		public E getNext() {
			no = no.getProx();
			return no.getObj();
		}

		@Override
		public void remove() {
			no.remove();
			numItens--;
		}

		public ListaDupEncIterator() {
			no = noCabeca;
		}
	}

	private class IteratorBlack implements MyIteratorBack<E> {

		private NoDupEnc<E> noAtual;

		@Override
		public E getLast() {
			noAtual = noCabeca.getAnt();
			return noAtual.getObj();
		}

		@Override
		public E getPrior() {
			noAtual = noAtual.getAnt();
			return noAtual.getObj();
		}

		@Override
		public void remove() {
			// numa classe interna para chamar um metodo de mesmo nome da classe
			// � so colocar o nome da classe.this.nome do metodo;
			// ListaEncOrd.this.remove(noAtual);
			// noAtual.remove(); pode ser usado desta forma.
			noAtual.remove();
			numItens--;
		}

		public IteratorBlack() {
			noAtual = noCabeca;
		}
	}

	public ListaEncOrd() {
		noCabeca = new NoDupEnc<E>();
	}

	public NoDupEnc<E> getNoCabeca() {
		return noCabeca;
	}

	public void clear() {
		noCabeca.setAnt(noCabeca);
		noCabeca.setProx(noCabeca);
	}

	public boolean isEmpty() {
		return (numItens == 0);
	}

	private NoDupEnc<E> noAnterior(E obj) {
		if (numItens == 0)
			return noCabeca;
		else {
			NoDupEnc<E> atual = noCabeca.getProx();
			NoDupEnc<E> ant = noCabeca;

			while (atual != noCabeca) {
				if (atual.getObj().compareTo(obj) >= 0)
					return ant;
				atual = atual.getProx();
				ant = ant.getProx();
			}
			return noCabeca.getAnt();
		}
	}

	public void insert(E obj) {
		NoDupEnc<E> noAnt = noAnterior(obj);
		NoDupEnc<E> no;
		no = new NoDupEnc<E>(obj, noAnt, noAnt.getProx());

		noAnt.getProx().setAnt(no);
		noAnt.setProx(no);
		numItens++;
	}

	public E removeFromBegin() {
		E obj = noCabeca.getProx().getObj();
		noCabeca.setProx(noCabeca.getProx().getProx());
		noCabeca.getProx().setAnt(noCabeca);
		numItens--;
		return obj;

	}

	public E remove(NoDupEnc<E> p) {
		E obj = p.getObj();
		p.getAnt().setProx(p.getProx());
		p.getProx().setAnt(p.getAnt());
		numItens--;
		return obj;

	}

	public E removeFromEnd() {
		E obj = noCabeca.getAnt().getObj();
		noCabeca.setAnt(noCabeca.getAnt().getAnt());
		noCabeca.getAnt().setProx(noCabeca);
		numItens--;
		return obj;
	}

	public int size() {
		return numItens;
	}

	public MyIterator<E> iterator() {
		return new ListaDupEncIterator();
	}

	public MyIteratorBack<E> iteratorBlack() {
		return new IteratorBlack();
	}

}
