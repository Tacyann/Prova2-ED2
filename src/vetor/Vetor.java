package vetor;

import java.io.Serializable;

import colecoes.MyIterator;

@SuppressWarnings("serial")
public class Vetor<E> implements Serializable {
	private final int CAPACIDADE_DEFAULT = 100;
	protected E[] lista;
	protected int incremento, capacidadeInicial;
	protected int numItens;

	private class VetorIterador implements MyIterator<E> {

		private int indiceCorrente;

		@Override
		public E getFirst() {
			if (size() == 0)
				return null;
			indiceCorrente = 0;
			return lista[0];
		}

		@Override
		public E getNext() {
			indiceCorrente++;
			if (indiceCorrente >= size())
				return null;
			E obj = lista[indiceCorrente];
			return obj;
		}

		public void remove() {
			removeAt(indiceCorrente);
			indiceCorrente--;
		}

	}

	@SuppressWarnings("unchecked")
	public Vetor() {
		lista = (E[]) new Object[CAPACIDADE_DEFAULT];
		incremento = 10;
		capacidadeInicial = CAPACIDADE_DEFAULT;
	}

	@SuppressWarnings("unchecked")
	public Vetor(int capacidadeInicial) {
		lista = (E[]) new Object[capacidadeInicial];
		this.incremento = 10;
		this.capacidadeInicial = capacidadeInicial;
	}

	@SuppressWarnings("unchecked")
	public Vetor(int capacidadeInicial, int incremento) {
		lista = (E[]) new Object[capacidadeInicial];
		this.incremento = incremento;
		this.capacidadeInicial = capacidadeInicial;
	}

	@SuppressWarnings("unchecked")
	protected void redimensione() {
		if (size() == lista.length) {
			E[] novoVetor = (E[]) new Object[size() + incremento];
			System.arraycopy(lista, 0, novoVetor, 0, size());
			lista = novoVetor;
		}
	}

	@SuppressWarnings("unchecked")
	public void clear() {
		if (numItens > capacidadeInicial)
			lista = (E[]) new Object[capacidadeInicial];
		else if (numItens > 0)
			for (int cont = 0; cont < numItens; cont++)
				lista[cont] = null;
	}

	public E elementAt(int indice) {
		if (indice < 0 || indice > numItens - 1)
			return null;

		return lista[indice];
	}

	public void insertAtEnd(E obj) {
		if (lista.length == numItens)
			redimensione();

		lista[numItens] = obj;
		numItens++;

	}

	public void insertAtBegin(E obj) {
		insertAt(0, obj);
	}

	public boolean insertAt(int indice, E obj) {
		// numItens marca aprimeira posição vazia
		if (lista.length == numItens)
			redimensione();

		if (indice > numItens)
			lista[numItens] = obj;
		else {
			for (int cont = numItens; cont > indice; cont--)
				lista[cont] = lista[cont - 1];

			lista[indice] = obj;
		}
		numItens++;
		return true;
	}

	public E removeFromBegin() {
		return removeAt(0);
	}

	public E removeFromEnd() {
		if (numItens == 0)
			return null;

		// se não entrar no primeiro if quer dizer que ele vai remover algum
		// objeto;

		numItens--;
		E temp = lista[numItens - 1];
		lista[numItens - 1] = null;
		return temp;

	}

	public E removeAt(int indice) {
		E temp = null;

		if (indice < numItens) {
			if (indice == numItens - 1)
				lista[indice] = null;
			else {
				for (int cont = indice; cont < numItens - 1; cont++)
					lista[cont] = lista[cont + 1];
				lista[numItens - 1] = null;
			}
			numItens--;
		}

		return temp;
	}

	public boolean replace(int indice, E obj) {
		if (indice < numItens && lista[indice] != null) {
			lista[indice] = obj;
			return true;

		} else
			return false;
	}

	public boolean isEmpty() {
		return (numItens == 0);
	}

	public int size() {
		return numItens;
	}

	public MyIterator<E> iterator() {
		return new VetorIterador();
	}

}
