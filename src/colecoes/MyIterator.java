package colecoes;

import java.io.Serializable;

public interface MyIterator<E> extends Serializable {
	// Retorna o primeiro elemento da colecao.
	// Se no existir o primeiro elemento, retorna null
	E getFirst();

	// Retorna o proximo elemento da colecao.
	// Se no existir o proximo elemento, retorna null
	E getNext();

	// Remove da colecao o ultimo elemento retornado
	void remove();
}
