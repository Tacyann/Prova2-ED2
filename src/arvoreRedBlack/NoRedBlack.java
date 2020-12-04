package arvoreRedBlack;

//ReferEncia: Data Structures and Algorithms in Java - Peter Drake
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

	/** Parent of this node. */
	private NoRedBlack<E> pai;

	/** Filho direito do no. */
	private NoRedBlack<E> dir;

	/** Usado para construir a sentinela. */
	protected NoRedBlack() {
		color = BLACK;
		// All other fields are irrelevant
	}

	/**
	 * The new node is red and both of its children are sentinel. The node's
	 * parent is NOT set by this constructor.
	 */
	public NoRedBlack(E obj, NoRedBlack<E> sentinel) {
		color = RED;
		this.obj = obj;
		pai = sentinel;
		esq = sentinel;
		dir = sentinel;
	}

	/**
	 * Retorna o no esquerdo (if direction is negative) or right (otherwise)
	 * child.
	 */
	public NoRedBlack<E> getFilho(int direcao) {
		if (direcao < 0) {
			return esq;
		}
		return dir;
	}

	/** Return the color of this node. */
	public boolean getColor() {
		return color;
	}

	/** Return the item associated with this node. */
	public E getObj() {
		return obj;
	}

	/** Retorna o filho do no esquerdo. */
	public NoRedBlack<E> getEsq() {
		return esq;
	}

	/** Return this node's parent. */
	public NoRedBlack<E> getPai() {
		return pai;
	}

	/** Return this node's right child. */
	public NoRedBlack<E> getDir() {
		return dir;
	}

	/** Return true if this node is black. */
	public boolean isBlack() {
		return color == BLACK;
	}

	/** Return true if this node is red. */
	public boolean isRed() {
		return color == RED;
	}

	/**
	 * Set this node's left (if direction is negative) or right (otherwise)
	 * child.
	 */
	public void setFilho(int direcao, NoRedBlack<E> filho) {
		if (direcao < 0) {
			esq = filho;
		} else {
			dir = filho;
		}
	}

	/** Make this node black. */
	public void setBlack() {
		color = BLACK;
	}

	/** Set the color of this node. */
	public void setColor(boolean color) {
		this.color = color;
	}

	/** Set the item associated with this node. */
	public void setObj(E obj) {
		this.obj = obj;
	}

	/** Set the parent of this node. */
	public void setPai(NoRedBlack<E> pai) {
		this.pai = pai;
	}

	/** Make this node red. */
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
