package arvoreBusca;

import java.io.Serializable;

import arvoreSplay.ArvoreSplay;
import colecoes.ColecaoComparavel;
import colecoes.MyIterator;
import fila.FilaEnc;

public class ArvoreBusca<E extends Comparable<E>> extends ColecaoComparavel<E>
		implements Serializable {

	protected NoArvoreBin<E> raiz;

	@Override
	public void clear() {
		raiz = null;
	}

	private class IteratorArvBin implements MyIterator<E> {
		private E atual;
		private FilaEnc<E> fila = new FilaEnc<E>();

		private void simetrica(NoArvoreBin<E> no) {
			if (no != null) {
				simetrica(no.getEsq());
				// insere na fila na ordem crescente
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
			ArvoreBusca.this.remove(atual);
		}

	}

	@Override
	public MyIterator<E> iterator() {
		return new IteratorArvBin();
	}

	protected NoArvoreBin<E> acharNo(E obj) {
		NoArvoreBin<E> atual = raiz;
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
		NoArvoreBin<E> achado = acharNo(obj);
		// achou então achado é diferente de null
		if (achado != null) {
			// Verifica se o no tem dois filhos
			if (achado.getDir() != null && achado.getEsq() != null) {
				// procura na subarvore esquerda o no que esta mais a direita
				NoArvoreBin<E> pTemp = null;
				pTemp = achado.getEsq();

				while (pTemp.getDir() != null)
					pTemp = pTemp.getDir();

				// copia o objeto do no mais a direita que desejamos remover
				achado.setObj(pTemp.getObj());
				achado = pTemp;

			}

			if (achado == raiz) {
				if (raiz.getEsq() != null)
					raiz = raiz.getEsq();
				else
					raiz = raiz.getDir();
				if (raiz != null)
					raiz.setPai(null);
				numItens--;
				return true;
			} else {
				int direcao = 1;
				NoArvoreBin<E> pai = achado.getPai();
				NoArvoreBin<E> filho;

				if (achado == pai.getEsq())
					direcao = -1;
				filho = pai.getFilho(direcao);

				if (filho.getEsq() != null) {
					pai.setFilho(direcao, filho.getEsq());
					filho.getEsq().setPai(pai);
				} else {
					pai.setFilho(direcao, filho.getDir());
					if (filho.getDir() != null)
						filho.getDir().setPai(pai);
				}
				numItens--;
				return true;
			}
		}
		return false;

	}

	@Override
	public E retrieve(E obj) {
		NoArvoreBin<E> achado = acharNo(obj);
		if (achado != null)
			return achado.getObj();
		return null;
	}

	@Override
	public boolean add(E obj) {
		NoArvoreBin<E> pai = null, p = raiz;
		int comparacao, direcao = 0;

		// achar no
		if (raiz != null) {
			while (p != null) {
				comparacao = obj.compareTo(p.getObj());
				if (comparacao == 0)
					return false;
				if (comparacao < 0) {
					if (p.getEsq() == null) {
						pai = p;
						direcao = -1;
						break;
					}
					p = p.getEsq();

				} else {
					if (p.getDir() == null) {
						pai = p;
						direcao = 1;
						break;
					}
					p = p.getDir();
				}
			}
		}
		insert(obj, pai, direcao);
		return true;
	}

	protected void insert(E obj, NoArvoreBin<E> pai, int direcao) {
		NoArvoreBin<E> novoNo = new NoArvoreBin<E>(obj, pai);
		if (pai == null) {
			raiz = novoNo;
		} else {
			if (direcao == -1)
				pai.setEsq(novoNo);
			else
				pai.setDir(novoNo);
		}
		numItens++;
	}

	// *************************************************************
	// Impressao da arvore na forma normal
	// *************************************************************
	static class DescNo<E extends Comparable<E>> implements
			Comparable<DescNo<E>> {
		int nivel;
		int ident;
		NoArvoreBin<E> no;

		@Override
		public int compareTo(DescNo<E> o) {
			return no.getObj().compareTo(o.no.getObj());
		}
	}

	public void desenhe(int larguraTela) {
		if (raiz == null)
			return;
		StringBuffer brancos1 = new StringBuffer();
		String brancos = "                                                        ";
		brancos1.append(brancos);
		brancos1.append(brancos);
		brancos1.append(brancos);
		brancos1.append(brancos);
		int largTela = larguraTela;
		int ident = largTela / 2;
		int nivelAnt = 0;
		int nivel = 0;
		int offset;
		NoArvoreBin<E> pTemp;
		StringBuffer linha1 = new StringBuffer(200);
		String linha = "";

		DescNo<E> descNo;
		FilaEnc<DescNo<E>> fila = new FilaEnc<DescNo<E>>();

		descNo = new DescNo<E>();

		descNo.nivel = 0;
		descNo.ident = ident;
		descNo.no = raiz;
		fila.insira(descNo);
		String pai;
		while (!fila.isEmpty()) {
			descNo = fila.remova();
			ident = descNo.ident;
			pTemp = descNo.no;
			nivel = descNo.nivel;
			if (pTemp.getPai() == null)
				pai = "  ";
			else
				pai = pTemp.getPai().getObj().toString();
			if (nivel == nivelAnt) {
				linha1.append(brancos1.substring(0, ident - linha1.length())
						+ pTemp.getObj().toString());
				linha = linha + brancos1.substring(0, ident - linha.length())
						+ pTemp.getObj().toString() + "(h=" + pTemp.getAltura()
						+ ")";
			} else {
				System.out.println(linha + "\n\n");
				linha1.setLength(0);
				linha1.append(brancos1.substring(0, ident)
						+ pTemp.getObj().toString());
				linha = brancos1.substring(0, ident)
						+ pTemp.getObj().toString() + "(h=" + pTemp.getAltura()
						+ ")";
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
		System.out.println(linha1);
	}

	public static void main(String[] args) {
		ArvoreBusca<Integer> as = new ArvoreBusca<Integer>();
		as.add(2);
		as.add(3);
		as.add(1);
		as.add(7);
		as.add(0);
		as.add(31);

		as.desenhe(200);

		System.out
				.println("-----------------------------------------------------------");
		System.out.println("busca 31");
		as.contains(31);
		as.desenhe(200);

		System.out
				.println("-----------------------------------------------------------");
		System.out.println("busca 7");
		as.contains(7);
		as.desenhe(200);

		System.out
				.println("-----------------------------------------------------------");
		System.out.println("busca 20");
		as.contains(20);
		as.desenhe(200);

		System.out
				.println("-----------------------------------------------------------");
		System.out.println("remove 1");
		as.remove(1);
		as.desenhe(200);
	}

}
