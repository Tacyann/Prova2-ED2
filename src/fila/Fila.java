package fila;

import java.io.Serializable;
import colecoes.ColecaoNaoComparavel;


public abstract class Fila<E extends Comparable<E>> extends
		ColecaoNaoComparavel<E> implements Serializable {

	public abstract E remova();

	public abstract boolean insira(E obj);
}
