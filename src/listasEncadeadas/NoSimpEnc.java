package listasEncadeadas;

import java.io.Serializable;

/* Refer�ncia: Data Structures and Algorithms in Java
 * Peter Drake
 */
public class NoSimpEnc<E> implements Serializable {
	private E obj;

	private NoSimpEnc<E> prox;

	// Cria um n� com o campo prox igual a null
	public NoSimpEnc(E obj) {
		this.obj = obj;
		this.prox = null;
	}

	// Cria um n� com uma refer�ncia ao pr�ximo n�
	public NoSimpEnc(E obj, NoSimpEnc<E> prox) {
		this.obj = obj;
		this.prox = prox;
	}

	// Retorna o objeto armazenado no n�
	public E getObj() {
		return obj;
	}

	// Retorna a refer�ncia ao pr�ximo n�
	public NoSimpEnc<E> getProx() {
		return prox;
	}

	// Altera a refer�ncia do objeto armazenado no n�
	public void setObj(E obj) {
		this.obj = obj;
	}

	// Altera a refer�ncia ao pr�ximo n�
	public void setProx(NoSimpEnc<E> prox) {
		this.prox = prox;
	}
}
