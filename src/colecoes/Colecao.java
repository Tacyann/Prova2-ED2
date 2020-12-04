package colecoes;

import java.io.Serializable;
import java.util.Comparator;

public interface Colecao<E> extends Serializable {
	int size();

	boolean isEmpty();

	void clear();

	MyIterator<E> iterator();

	Object[] toArray();

	Object[] sort(Comparator<E> comparador);
}
