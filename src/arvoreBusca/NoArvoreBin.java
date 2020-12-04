package arvoreBusca;

import java.io.Serializable;

public class NoArvoreBin<E extends Comparable<E>> implements Serializable {

	/** Contém o objeto associado a esse nó */
	private E obj;

	/** Referência à subárvore esquerda */
	private NoArvoreBin<E> esq;

	/** referência a subárvore direita */
	private NoArvoreBin<E> dir;

	// ** referência ao pai do nó
	private NoArvoreBin<E> pai;

	/** Cria um nó com filhos nulos (folha) */
	public NoArvoreBin(E obj, NoArvoreBin<E> pai) {
		this.obj = obj;
		this.pai = pai;
	}

	/** Cria um nó com as respectivas subárvores esquerda e direita. */
	public NoArvoreBin(E obj, NoArvoreBin<E> pai, NoArvoreBin<E> esq,
			NoArvoreBin<E> dir) {
		this(obj, pai);
		this.esq = esq;
		this.dir = dir;
	}

	public int getAltura() {
		int altura = 0;
		NoArvoreBin<E> atual = pai;
		while (atual != null) {
			altura++;
			atual = atual.pai;
		}
		return altura;
	}

	public NoArvoreBin<E> getPai() {
		return pai;
	}

	public void setPai(NoArvoreBin<E> pai) {
		this.pai = pai;
	}

	/** Retorna o objeto associado a esse nó. */
	public E getObj() {
		return obj;
	}

	/** Retorna a referência da subárvore esquerda. */
	public NoArvoreBin<E> getEsq() {
		return esq;
	}

	/** Retorna a referência da subárvore direita. */
	public NoArvoreBin<E> getDir() {
		return dir;
	}

	/** Substitui o objeto armazenado no no por obj. */
	public void setObj(E obj) {
		this.obj = obj;
	}

	/** Substitui a referência da subárvore esquerda por esq. */
	public void setEsq(NoArvoreBin<E> esq) {
		this.esq = esq;
	}

	/** Substitui a referência da subárvore direita por dir. */
	public void setDir(NoArvoreBin<E> dir) {
		this.dir = dir;
	}

	/**
	 * Retorna o filho do nó de acordo com o valor de direcao. Se direcao < 0
	 * retorna o filho esquerdo caso contrário retorna o filho direito.
	 */
	public NoArvoreBin<E> getFilho(int direcao) {
		if (direcao < 0)
			return esq;
		else
			return dir;
	}

	/**
	 * Atribui o filho do nó de acordo com o valor de direcao. Se direcao < 0
	 * atribui o filho esquerdo, caso contrário, atribui o filho direito.
	 */
	public void setFilho(int direcao, NoArvoreBin<E> filho) {
		if (direcao < 0)
			esq = filho;
		else
			dir = filho;
	}

}
