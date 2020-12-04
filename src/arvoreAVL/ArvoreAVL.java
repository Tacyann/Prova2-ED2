package arvoreAVL;

import colecoes.ColecaoComparavel;
import colecoes.MyIterator;
import fila.FilaEnc;

public class ArvoreAVL<E extends Comparable<E>> extends ColecaoComparavel<E> {

	private NoAVL<E> raiz;

	private int height(NoAVL<E> no) {
		return no == null ? -1 : no.getAltura();
	}

	public int balanco(NoAVL<E> no) {
		return height(no.getEsq()) - height(no.getDir());
	}

	private NoAVL<E> rotacaoParaDireita(NoAVL<E> no) {
		NoAVL<E> p = no.getEsq();
		no.setEsq(p.getDir());
		p.setDir(no);
		no.setAltura(Math.max(height(no.getEsq()), height(no.getDir())) + 1);
		p.setAltura(Math.max(height(p.getEsq()), no.getAltura()) + 1);
		return p;
	}

	private NoAVL<E> rotacaoParaEsquerda(NoAVL<E> no) {
		NoAVL<E> p = no.getDir();
		no.setDir(p.getEsq());
		p.setEsq(no);
		no.setAltura(Math.max(height(no.getEsq()), height(no.getDir())) + 1);
		p.setAltura(Math.max(height(p.getDir()), no.getAltura()) + 1);
		return p;
	}

	private NoAVL<E> rotacaoDuplaEsquerdaDireita(NoAVL<E> no) {
		no.setEsq(rotacaoParaEsquerda(no.getEsq()));
		return rotacaoParaDireita(no);
	}

	private NoAVL<E> rotacaoDuplaDireitaEsquerda(NoAVL<E> no) {
		no.setDir(rotacaoParaDireita(no.getDir()));
		return rotacaoParaEsquerda(no);
	}

	private NoAVL<E> insira(E obj, NoAVL<E> no) {

		if (no == null) {
			no = new NoAVL<E>(obj);
		} else if (no.isDeletado() && (obj.compareTo(no.getObj()) == 0)) {
			no.setDeletado(false);
			no.setObj(obj);
			return no;
		} else {
			if (obj.compareTo(no.getObj()) < 0) {
				no.setEsq(insira(obj, no.getEsq()));
				if (height(no.getEsq()) - height(no.getDir()) == 2)
					if (obj.compareTo(no.getEsq().getObj()) < 0)
						no = rotacaoParaDireita(no);
					else
						no = rotacaoDuplaEsquerdaDireita(no);
			} else if (obj.compareTo(no.getObj()) >= 0) {
				no.setDir(insira(obj, no.getDir()));
				if (height(no.getDir()) - height(no.getEsq()) == 2)
					if (obj.compareTo(no.getDir().getObj()) > 0)
						no = rotacaoParaEsquerda(no);
					else
						no = rotacaoDuplaDireitaEsquerda(no);
			}
		}
		no.setAltura(Math.max(height(no.getEsq()), height(no.getDir())) + 1);
		return no;
	}

	@Override
	public boolean add(E obj) {
		if (contains(obj))
			return false;
		NoAVL<E> no = insira(obj, raiz);
		raiz = no;
		numItens++;
		return true;
	}

	@Override
	public void clear() {
		raiz = null;

	}

	private class IteratorArvorevAVL implements MyIterator<E> {
		private E atual;
		private FilaEnc<E> fila = new FilaEnc<E>();

		private void simetrica(NoAVL<E> no) {
			if (no != null) {
				simetrica(no.getEsq());
				// insere na fila na ordem crescente
				if (!no.isDeletado())
					fila.insira(no.getObj());
				simetrica(no.getDir());
			}
		}

		private E get() {
			if (fila.isEmpty())
				return null;
			atual = fila.remova();
			return atual;
		}

		@Override
		public E getFirst() {
			simetrica(raiz);
			return get();
		}

		@Override
		public E getNext() {
			return get();
		}

		@Override
		public void remove() {
			ArvoreAVL.this.remove(atual);
		}

	}

	@Override
	public MyIterator<E> iterator() {
		return new IteratorArvorevAVL();
	}

	private NoAVL<E> acharNo(E obj) {
		NoAVL<E> atual = raiz;
		int comparacao;
		// achar no
		while (atual != null) {
			comparacao = obj.compareTo(atual.getObj());
			if (comparacao == 0)
				break;
			else {
				if (comparacao < 0)
					atual = atual.getEsq();
				else
					atual = atual.getDir();
			}
		}
		return atual;
	}

	@Override
	public boolean remove(E obj) {
		NoAVL achado = acharNo(obj);
		if (achado != null) {
			if (achado.isDeletado())
				return false;
			achado.setDeletado(true);
			numItens--;
			return true;
		}
		return false;
	}

	@Override
	public E retrieve(E obj) {
		NoAVL<E> achado = acharNo(obj);
		if (achado != null && !achado.isDeletado())
			return achado.getObj();
		return null;
	}

	// *************************************************************
	// Impresao da arvore
	// *************************************************************
	private class DescNo<E extends Comparable<E>> implements
			Comparable<DescNo<E>> {
		int nivel;
		int ident;
		NoAVL<E> no;

		@Override
		public int compareTo(DescNo<E> a) {
			return no.getObj().compareTo(a.no.getObj());
		}

	}

	public void desenhe(int larguraTela) {
		int balanco;
		char deletado;
		if (raiz == null)
			return;
		String brancos = "                                                        "
				+ "                                                        "
				+ "                                                        ";
		int largTela = larguraTela;
		int ident = largTela / 2;
		int nivelAnt = 0;
		int nivel = 0;
		int offset;
		NoAVL<E> pTemp;
		String linha = "";

		DescNo<E> descNo;
		FilaEnc<DescNo<E>> fila = new FilaEnc<DescNo<E>>();

		descNo = new DescNo<E>();

		descNo.nivel = 0;
		descNo.ident = ident;
		descNo.no = raiz;
		fila.insira(descNo);

		while (!fila.isEmpty()) {
			descNo = fila.remova();
			ident = descNo.ident;
			pTemp = descNo.no;
			nivel = descNo.nivel;
			balanco = height((NoAVL<E>) pTemp.getEsq())
					- height((NoAVL<E>) pTemp.getDir());
			if (pTemp.isDeletado())
				deletado = 'D';
			else
				deletado = 'V';
			if (nivel == nivelAnt) {
				linha = linha + brancos.substring(0, ident - linha.length())
						+ pTemp.getObj().toString() + "(" + balanco + ")"
						+ deletado;
			} else {
				System.out.println(linha + "\n\n");
				linha = brancos.substring(0, ident) + pTemp.getObj().toString()
						+ "(" + balanco + ")" + deletado;
				nivelAnt = nivel;
			}
			nivel = nivel + 1;
			offset = (int) (largTela / Math.round(Math.pow(2, nivel + 1)));
			if (pTemp.getEsq() != null) {
				descNo = new DescNo<E>();
				descNo.ident = ident - offset;
				descNo.nivel = nivel;
				descNo.no = pTemp.getEsq();
				fila.insira(descNo);
			}
			if (pTemp.getDir() != null) {
				descNo = new DescNo<E>();
				descNo.ident = ident + offset;
				descNo.nivel = nivel;
				descNo.no = pTemp.getDir();
				fila.insira(descNo);
			}
		}
		System.out.println(linha);
	}

}
