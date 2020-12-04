package colecoes;

import java.util.Comparator;

public class Sort {
	public static void selecao(Object[] a, Comparator c) {
		int iMenor;

		for (int i = 0; i < a.length - 1; i++) {
			iMenor = i;

			for (int cont = i + 1; cont < a.length; cont++)
				if (c.compare(a[cont], a[iMenor]) == -1)
					iMenor = cont;

			if (iMenor != i) {
				Object temp = a[iMenor];
				a[iMenor] = a[i];
				a[i] = temp;
			}
		}
	}

	public static void bolha(Object[] a, Comparator c) {
		boolean troquei;
		int fimArray = a.length - 1;
		do {
			troquei = false;
			for (int cont = 0; cont < fimArray; cont++) {
				if (c.compare(a[cont], a[cont + 1]) == 1) {
					Object temp = a[cont];
					a[cont] = a[cont + 1];
					a[cont + 1] = temp;
					troquei = true;
				}
				fimArray--;
			}
		} while (troquei);
	}

	public static void insercao(Object[] vetor, Comparator c) {
		Object temp;
		int k;

		for (int cont = 0; cont < vetor.length; cont++) {
			temp = vetor[cont];
			k = cont;

			while (k > 0 && c.compare(temp, vetor[k - 1]) == -1) {
				vetor[k] = vetor[k - 1];
				k--;
			}
			vetor[k] = temp;
		}
	}

	public static void quickSort(Object[] vetor, Comparator c) {
		qSort(vetor, c, 0, vetor.length - 1);
	}

	private static void qSort(Object[] vetor, Comparator c, int indPrimeiro,
			int indUltimo) {
		int indPivo;

		if (indPrimeiro < indUltimo) {
			indPivo = particao(vetor, c, indPrimeiro, indUltimo);
			qSort(vetor, c, indPrimeiro, indPivo - 1);
			qSort(vetor, c, indPivo + 1, indUltimo);
		}
	}

	private static int particao(Object[] vetor, Comparator c, int indPrimeiro,
			int indUltimo) {
		int i, j, indPivo;
		Object temp, pivo;

		pivo = vetor[indPrimeiro];
		i = indPrimeiro;
		j = indUltimo;
		do {
			while (c.compare(vetor[i], pivo) <= 0 && i < indUltimo)
				i++;

			while ((c.compare(vetor[j], pivo) == 1 && j > indPrimeiro))
				j--;

			if (i < j) {
				temp = vetor[i];
				vetor[i] = vetor[j];
				vetor[j] = temp;
			}
		} while (i < j);

		indPivo = j;
		temp = vetor[indPrimeiro];
		vetor[indPrimeiro] = vetor[indPivo];
		vetor[indPivo] = temp;
		return indPivo;
	}
}
