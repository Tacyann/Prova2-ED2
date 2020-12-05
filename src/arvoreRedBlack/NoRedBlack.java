package arvoreRedBlack;
public class NoRedBlack<E> {
	/** No de cor Preta. */
	public static final boolean BLACK = false;

	/** No de cor vermelha. */
	public static final boolean RED = true;

	/** Cor do no, BLACK or RED. */
	private boolean color;

	/** Item associated with this node. */
	public E obj;

	/** Filho esquerdo do no. */
	private NoRedBlack<E> esq;

	/** Pai deste no. */
	private NoRedBlack<E> pai;

	/** Filho direito do no. */
	private NoRedBlack<E> dir;

	/** Usado para construir a sentinela. */
	protected NoRedBlack() {
		color = BLACK;
	}

	
	/**O novo no e vermelho e seus dois filhos são sentinela. 
	 * O no pai NÃO e definido por este construtor.*/
	public NoRedBlack(E obj, NoRedBlack<E> sentinel) {
		color = RED;
		this.obj = obj;
		pai = sentinel;
		esq = sentinel;
		dir = sentinel;
	}

	/**
	 * Retornar o no esquerdo (se a direção for negativa) 
	 * ou direita (caso contrário) criança.*/
	public NoRedBlack<E> getFilho(int direcao) {
		if (direcao < 0) {
			return esq;
		}
		return dir;
	}

	/** Retornar a cor deste no. */
	public boolean getColor() {
		return color;
	}

	/** Retornar o item associado a este no. */
	public E getObj() {
		return obj;
	}

	/** Retornar o filho do no esquerdo. */
	public NoRedBlack<E> getEsq() {
		return esq;
	}

	/** Retornar o pai deste no. */
	public NoRedBlack<E> getPai() {
		return pai;
	}

	/** Retornar o filho direito deste no. */
	public NoRedBlack<E> getDir() {
		return dir;
	}

	/** Retornar verdadeiro se este no for preto. */
	public boolean isBlack() {
		return color == BLACK;
	}

	/** Retornar verdadeiro se este no for vermelho. */
	public boolean isRed() {
		return color == RED;
	}

	/**
	 Define a esquerda deste no (se a direção for negativa) 
	 ou direita (caso contrario)crianca.*/
	public void setFilho(int direcao, NoRedBlack<E> filho) {
		if (direcao < 0) {
			esq = filho;
		} else {
			dir = filho;
		}
	}

	/** Define como preto */
	public void setBlack() {
		color = BLACK;
	}

	/** Define a cor do no. */
	public void setColor(boolean color) {
		this.color = color;
	}

	/** Define o item associado a este no. */
	public void setObj(E obj) {
		this.obj = obj;
	}

	/** Define o pai deste no. */
	public void setPai(NoRedBlack<E> pai) {
		this.pai = pai;
	}

	/** Define como vermelho. */
	public void setRed() {
		color = RED;
	}

	public void setDir(NoRedBlack<E> dir) {
		this.dir = dir;
	}

	public void setEsq(NoRedBlack<E> esq) {
		this.esq = esq;
	}

	public boolean isFilhoDireito() {
		E obj = pai.getDir().getObj();
		if (obj != null)
			return obj.equals(obj);
		return false;
	}

	public boolean isFilhoEsquerdo() {
		E obj = pai.getEsq().getObj();
		if (obj != null)
			return obj.equals(obj);
		return false;
	}

	public NoRedBlack<E> getAvo() {
		return pai.getPai();
	}

	public NoRedBlack<E> getIrmao() {
		if (isFilhoDireito())
			return pai.getEsq();
		return pai.getDir();
	}

	public NoRedBlack<E> getTio() {
		if (pai.isFilhoDireito())
			return getAvo().getEsq();
		return getAvo().getDir();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NoRedBlack)
			return (((NoRedBlack<E>) obj).getObj().equals(this.obj));
		return false;
	}
	
	@Override
	public String toString() {
		if (isBlack())
			return obj.toString() + "(BLACK)";
		return obj.toString() + "(RED)";
	}
}
