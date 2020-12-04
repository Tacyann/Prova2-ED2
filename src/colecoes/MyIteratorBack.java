package colecoes;

public interface MyIteratorBack<E> {
	// Retorna o ultimo elemento da colecao.
	// Se no existir o primeiro elemento, retorna null
	E getLast();

	// Retorna elemento anterior da colecaoo.
	// Se no existir, retorna null
	E getPrior();

	// Remove da colecao o ulltimo elemento retornado
	void remove();

}
