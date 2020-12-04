package arvoreSplay;

import arvoreBusca.ArvoreBusca;
import arvoreBusca.NoArvoreBin;
import arvoreRedBlack.NoRedBlack;

public class ArvoreSplay<E extends Comparable<E>> extends ArvoreBusca<E> {

	private void rotacaoParaEsquerda(NoArvoreBin<E> no) {
		if (no.getDir() == null)
			return;
		NoArvoreBin<E> filho = no.getDir();
		NoArvoreBin<E> pai = no.getPai();
		no.setDir(filho.getEsq());
		if (no.getDir() != null)
			no.getDir().setPai(no);
		filho.setPai(no.getPai());

		if (pai != null) {
			if (pai.getEsq() == no)
				pai.setEsq(filho);
			else
				pai.setDir(filho);
		} else {
			raiz = filho;
			filho.setPai(null);
		}
		filho.setEsq(no);
		no.setPai(filho);
	}

	private void rotacaoParaDireita(NoArvoreBin<E> no) {
		if (no.getEsq() == null)
			return;
		NoArvoreBin<E> filho = no.getEsq();
		NoArvoreBin<E> pai = no.getPai();
		no.setEsq(filho.getDir());
		if (no.getEsq() != null)
			no.getEsq().setPai(no);
		filho.setPai(no.getPai());

		if (pai != null) {
			if (pai.getEsq() == no)
				pai.setEsq(filho);
			else
				pai.setDir(filho);
		} else {
			raiz = filho;
			filho.setPai(null);
		}

		filho.setDir(no);
		no.setPai(filho);
	}

	// pai == raiz
	private void zig(NoArvoreBin<E> no) {
		if (no.getPai().getDir() == no)
			rotacaoParaEsquerda(no.getPai());
		else
			rotacaoParaDireita(no.getPai());
	}

	// filho direito do pai e avo ou esquerdo do pai e avo
	private void zigzig(NoArvoreBin<E> no) {
		NoArvoreBin<E> pai = no.getPai();
		NoArvoreBin<E> avo = pai.getPai();
		if (no == pai.getDir() && pai == avo.getDir()) {
			rotacaoParaEsquerda(pai);
			rotacaoParaEsquerda(avo);
			return;
		}

		if (no == pai.getEsq() && pai == avo.getEsq()) {
			rotacaoParaDireita(pai);
			rotacaoParaDireita(avo);
			return;
		}
	}

	private void zigzag(NoArvoreBin<E> no) {
		NoArvoreBin<E> pai = no.getPai();
		NoArvoreBin<E> avo = pai.getPai();

		if (no == pai.getDir() && pai == avo.getEsq()) {
			rotacaoParaEsquerda(pai);
			rotacaoParaDireita(avo);
			return;
		}

		if (no == pai.getEsq() && pai == avo.getDir()) {
			rotacaoParaDireita(pai);
			rotacaoParaEsquerda(avo);
			return;
		}

	}

	// escolhe o melhor metodo
	private void difusao(NoArvoreBin<E> no) {
		if (no.getPai() == raiz)
			zig(no);
		else {
			NoArvoreBin<E> pai = no.getPai();
			NoArvoreBin<E> avo = pai.getPai();
			if (no == pai.getDir() && pai == avo.getDir() || no == pai.getEsq()
					&& pai == avo.getEsq())
				zigzig(no);
			else
				zigzag(no);
		}
	}

	@Override
	protected void insert(E obj, NoArvoreBin<E> pai, int direcao) {
		NoArvoreBin<E> novoNo = new NoArvoreBin<E>(obj, pai);
		if (pai == null) {
			raiz = novoNo;
		} else {
			if (direcao == -1)
				pai.setEsq(novoNo);
			else
				pai.setDir(novoNo);
			// rotações
			while (raiz != novoNo)
				difusao(novoNo);
		}
		numItens++;
	}

	// acha o ultimo no antes de null caso não encontre o obj
	private NoArvoreBin<E> acharPenultimoNo(E obj) {
		NoArvoreBin<E> atual = raiz;
		NoArvoreBin<E> retorno = null;

		int comparacao;
		// achar no
		while (atual != null) {
			retorno = atual;
			comparacao = obj.compareTo(atual.getObj());
			if (comparacao == 0)
				return null;
			else {
				if (comparacao < 0)
					atual = atual.getEsq();
				else
					atual = atual.getDir();
			}
		}
		return retorno;
	}

	@Override
	protected NoArvoreBin<E> acharNo(E obj) {
		NoArvoreBin<E> noAchado = super.acharNo(obj);
		NoArvoreBin<E> noPenultimo = null;

		if (noAchado == null) {
			noPenultimo = acharPenultimoNo(obj);
		}

		if (noAchado != null) {
			// rotações
			while (raiz != noAchado)
				difusao(noAchado);
		}

		if (noPenultimo != null) {
			// rotações
			while (raiz != noPenultimo)
				difusao(noPenultimo);

		}
		return noAchado;
	}
	
	public static void main(String[] args) {
		ArvoreSplay<Integer> as = new ArvoreSplay<Integer>();
		as.add(2);
		as.add(3);
		as.add(1);
		as.add(7);
		as.add(0);
		as.add(31);
		
		as.desenhe(200);
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("busca 31");
		as.contains(31);
		as.desenhe(200);
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("busca 7");
		as.contains(7);
		as.desenhe(200);
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("busca 20");
		as.contains(20);
		as.desenhe(200);
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("remove 1");
		as.remove(7);
		as.desenhe(200);
	}
}