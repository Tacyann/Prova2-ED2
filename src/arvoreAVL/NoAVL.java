package arvoreAVL;

import java.io.Serializable;

public class NoAVL<E> implements Serializable{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Cont�m o objeto associado a esse n�. */
	private E obj;
 
	/** Refer�ncia � sub�rvore esquerda. */
	private NoAVL<E> esq;

	/** refer�ncia a sub�rvore direita. */
	private NoAVL<E> dir;
	
	private int altura;
	private boolean deletado;

	public boolean isDeletado() {
   	return deletado;
   }

	public void setDeletado(boolean deletado) {
   	this.deletado = deletado;
   }

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	/** Cria um n� com filhos nulos (folha) */
	public NoAVL(E obj) {
		this.obj = obj;
		this.esq = null;
		this.dir = null;
		this.deletado = false;
		this.altura = 0;
	}

	/** Cria um n� com as respectivas sub�rvores esquerda e direita. */
	public NoAVL(E obj, NoAVL<E> esq, NoAVL<E> dir) {
		this.obj = obj;
		this.esq = esq;
		this.dir = dir;
		this.deletado = false;
		this.altura = 0;
	}

	/** Retorna o objeto associado a esse n�. */
	public E getObj() {
		return obj;
	}

	/** Retorna a refer�ncia da sub�rvore esquerda. */
	public NoAVL<E> getEsq() {
		return esq;
	}

	/** Retorna a refer�ncia da sub�rvore direita. */
	public NoAVL<E> getDir() {
		return dir;
	}

	/** Retorna true se o n� � uma folha. */
	public boolean ehFolha() {
		return (esq == null) && (dir == null);
	}

	/** Substitui o objeto armazenado no n� por obj. */
	public void setObj(E obj) {
		this.obj = obj;
	}

	/** Substitui a refer�ncia da sub�rvore esquerda por esq. */
	public void setEsq(NoAVL<E> esq) {
		this.esq = esq;
	}

	/** Substitui a refer�ncia da sub�rvore direita por dir. */
	public void setDir(NoAVL<E> dir) {
		this.dir = dir;
	}
	
	/** Retorna o filho do n� de acordo com o valor de direcao. Se direcao < 0
	 * retorna o filho esquerdo caso contr�rio retorna o filho direito.
	 */
	public NoAVL<E> getFilho(int direcao) {
		if (direcao < 0)
			return esq;
		else
			return dir;
	}

	/** Atribui o filho do n� de acordo com o valor de direcao. Se direcao < 0
	 * atribui o filho esquerdo, caso contr�rio, atribui o filho direito.
	 */
	public void setFilho(int direcao, NoAVL<E> filho) {
		if (direcao < 0)
			esq = filho;
		else
			dir = filho;
	}

}
