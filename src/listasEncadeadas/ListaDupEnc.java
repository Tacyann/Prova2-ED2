package listasEncadeadas;

import colecoes.MyIterator;
import colecoes.MyIteratorBack;

//Lista duplamente encadeada com no cabeça
public class ListaDupEnc<E> {

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
		}

		public ListaDupEncIterator() {
			no = noCabeca.getProx();
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
			// é so colocar o nome da classe.this.nome do metodo;
			ListaDupEnc.this.remove(noAtual);
			// noAtual.remove(); pode ser usado desta forma.
		}

		public IteratorBlack() {
			noAtual = noCabeca;
		}
	}

	public ListaDupEnc() {
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

	public void insertAtBegin(E obj) {
		NoDupEnc<E> no = new NoDupEnc<E>(obj, noCabeca, noCabeca.getProx());
		noCabeca.getProx().setAnt(no);
		noCabeca.setProx(no);
		numItens++;
	}

	public void insertAtEnd(E obj) {
		NoDupEnc<E> no = new NoDupEnc<E>(obj, noCabeca.getAnt(), noCabeca);
		noCabeca.getAnt().setProx(no);
		noCabeca.setAnt(no);
		numItens++;
	}

	public void insertAfter(E obj, NoDupEnc<E> p) {
		NoDupEnc<E> no = new NoDupEnc<E>(obj, p, p.getProx());
		p.getProx().setAnt(no);
		p.setProx(no);
		numItens++;
	}

	public void insertBefore(E obj, NoDupEnc<E> p) {
		NoDupEnc<E> no = new NoDupEnc<E>(obj, p.getAnt(), p);
		p.getAnt().setProx(no);
		p.setAnt(no);
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
