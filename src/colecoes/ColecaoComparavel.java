package colecoes;

public abstract class ColecaoComparavel<E extends Comparable<E>> extends 
		ColecaoNaoComparavel<E> {  
	private static final long serialVersionUID = -7246798780951112506L;

	public boolean contains(E obj) {
		return (retrieve(obj) != null);
	}

	public abstract boolean remove(E obj);

	public abstract E retrieve(E obj);

	public abstract boolean add(E obj);
}
