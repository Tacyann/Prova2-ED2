package listasEncadeadas;

import java.io.Serializable;

import colecoes.MyIterator;

public class ListaSimpEnc<E extends Comparable<E>> implements Serializable {

	protected NoSimpEnc<E> inicio;
	protected NoSimpEnc<E> fim;
	protected int numItens;

	private class ListaSimpEncIterator implements MyIterator<E> {
		NoSimpEnc<E> atual, antAtual;

		@Override
		public E getFirst() {
			if (numItens > 0) {
				antAtual = null;
				atual = inicio;
				return atual.getObj();
			}
			return null;
		}

		@Override
		public E getNext() {
			antAtual = atual;
			atual = atual.getProx();
			if (atual != null)
				return atual.getObj();
			return null;
		}

		@Override
		public void remove() {
			if (atual == inicio) {
				inicio = atual.getProx();
			} else {
				antAtual.setProx(atual.getProx());
				atual = antAtual;
			}
			numItens--;
		}

		public ListaSimpEncIterator() {
			atual = inicio;
		}
	}

	// Feito Retorna a refer�ncia ao in�cio da lista
	public NoSimpEnc<E> getInicio() {
		return inicio;

	}

	// Feito Retorna a refer�ncia ao final da lista
	public NoSimpEnc<E> getFim() {
		return fim;

	}

	// Feito Esvazia a lista
	public void clear() {
		inicio = null;
		fim = null;
	}

	// Feito Informa se a lista est� vazia ou n�o
	public boolean isEmpty() {
		if (inicio == null)
			return true;
		return false;

	}

	// Insere um n� no in�cio da lista
	public void insertAtBegin(E obj) {
		NoSimpEnc<E> novoNo = new NoSimpEnc<E>(obj);
		if (inicio == null) {
			inicio = novoNo;
			fim = novoNo;

		} else {
			novoNo.setProx(inicio);
			inicio = novoNo;
		}
		numItens++;
	}

	// Feito Insere um n� no final da lista
	public void insertAtEnd(E obj) {
		NoSimpEnc<E> novoNo = new NoSimpEnc<E>(obj);
		if (inicio == null) {
			inicio = novoNo;
			fim = novoNo;
		} else {
			fim.setProx(novoNo);
			fim = novoNo;
		}
		numItens++;
	}

	// Feito Insere um n� ap�s o n� apontado por p
	public void insertAfter(E obj, NoSimpEnc<E> p) {
		NoSimpEnc<E> novoNo = new NoSimpEnc<E>(obj, p.getProx());
		if (p == fim)
			fim = novoNo;
		p.setProx(novoNo);
		numItens++;
	}

	// Feito Remover o primeiro n� da lista,
	// retornando a refer�ncia ao objeto que se
	// encontra armazenado nele.
	// Se a lista estiver vazia retorna null
	public E removeFromBegin() {
		if (inicio == null)
			return null;
		else {
			E objeto = inicio.getObj();
			inicio = inicio.getProx();
			if (inicio == null)
				fim = null;
			numItens--;
			return objeto;
		}
	}

	// Remover o n� que segue o n� apontado por p,
	// retornando a refer�ncia ao objeto que se encontra
	// armazenado nele.
	// Se a lista estiver vazia ou se n�o existir o
	// pr�ximo n�, retorna null
	public E removeAfter(NoSimpEnc<E> p) {
		if (p == null)
			return null;
		else {
			E objeto = p.getObj();
			if (p.getProx() == fim)
				fim = p;
			else
				p.setProx((p.getProx()).getProx());
			numItens--;
			return objeto;
		}
	}

	// Retorna o tamanho da lista
	public int size() {
		return numItens;

	}

	public MyIterator<E> iterator() {
		return new ListaSimpEncIterator();
	}
}
