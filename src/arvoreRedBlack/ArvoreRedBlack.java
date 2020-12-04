package arvoreRedBlack;

import java.util.Scanner;
import java.util.Stack;
import colecoes.ColecaoComparavel;
import colecoes.MyIterator;
import fila.FilaEnc;
import keyboard.Keyboard;

public class ArvoreRedBlack<E extends Comparable<E>> extends
		ColecaoComparavel<E> {

	/** The root node of this tree. */
	NoRedBlack<E> raiz;

	/** All "null" node references actually point to this node. */
	private NoRedBlack<E> sentinel;	

	private class ArvoreIterator implements MyIterator<E> {
		private E atual;
		private FilaEnc<E> fila = new FilaEnc<E>();

		private void simetrica(NoRedBlack<E> no) {
			Stack<NoRedBlack<E>> pilha = new Stack<NoRedBlack<E>>();
			NoRedBlack<E> noTemp = null;

			// Coloca na pilha todos os nos da esqueda
			while (no != sentinel) {
				pilha.add(no);
				no = no.getEsq();
			}

			while (!pilha.isEmpty()) {

				// tira o ultimo no da pilha e add na fila
				noTemp = pilha.pop();
				fila.insira(noTemp.getObj());

				// pega o no da direita e coloca na pilha e repete o percuso
				// simetrico nos nos da esqueda
				noTemp = noTemp.getDir();
				if (noTemp != sentinel) {
					pilha.add(noTemp);

					while (noTemp.getEsq() != sentinel) {
						pilha.add(noTemp.getEsq());
						noTemp = noTemp.getEsq();
					}
				}

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
			ArvoreRedBlack.this.remove(atual);
		}
	}

	/** The tree is initially empty. */
	public ArvoreRedBlack() {
		sentinel = new NoRedBlack<E>();
		raiz = sentinel;
	}

	private void rotacaoParaEsquerda(NoRedBlack<E> no) {
		if (no.getDir() == sentinel)
			return;
		NoRedBlack<E> filho = no.getDir();
		NoRedBlack<E> pai = no.getPai();
		no.setDir(filho.getEsq());
		if (no.getDir() != sentinel)
			no.getDir().setPai(no);
		filho.setPai(no.getPai());

		if (pai != sentinel) {
			if (pai.getEsq() == no)
				pai.setEsq(filho);
			else
				pai.setDir(filho);
		} else {
			raiz = filho;
			filho.setPai(sentinel);
		}
		filho.setEsq(no);
		no.setPai(filho);
	}

	private void rotacaoParaDireita(NoRedBlack<E> no) {
		if (no.getEsq() == sentinel)
			return;
		NoRedBlack<E> filho = no.getEsq();
		NoRedBlack<E> pai = no.getPai();
		no.setEsq(filho.getDir());
		if (no.getEsq() != sentinel)
			no.getEsq().setPai(no);
		filho.setPai(no.getPai());

		if (pai != sentinel) {
			if (pai.getEsq() == no)
				pai.setEsq(filho);
			else
				pai.setDir(filho);
		} else {
			raiz = filho;
			filho.setPai(sentinel);
		}

		filho.setDir(no);
		no.setPai(filho);
	}

	// Caso5: O pai do no e' vermelho e o tio e' preto.
	// Alem disso, o no e o pai sao filhos esquerdos(ou direitos)
	private void insertCase5(NoRedBlack<E> no) {
		NoRedBlack<E> avo = no.getAvo();
		no.getPai().setBlack();
		avo.setRed();
		if (no.isFilhoEsquerdo())
			rotacaoParaDireita(avo);
		else
			rotacaoParaEsquerda(avo);
	}

	// Caso4: O no e' filho direito(ou esquerdo) do pai, o pai e' filho
	// esquerdo(ou direito) do avo e o tio e' preto
	private void insertCase4(NoRedBlack<E> no) {
		NoRedBlack<E> avo = no.getAvo();
		if (no.isFilhoDireito() && no.getPai().isFilhoEsquerdo()) {
			rotacaoParaEsquerda(no.getPai());
			no = no.getEsq();
		} else if ((no.isFilhoEsquerdo()) && no.getPai().isFilhoDireito()) {
			rotacaoParaDireita(no.getPai());
			no = no.getDir();
		}
		insertCase5(no);
	}

	// Caso3: O tio e o pai do no sao vermelhos e o avo e' preto
	private void insertCase3(NoRedBlack<E> no) {
		NoRedBlack<E> tio = no.getTio();
		NoRedBlack<E> avo = no.getAvo();
		if (tio != sentinel && tio.isRed()) {
			no.getPai().setBlack();
			tio.setBlack();
			avo.setRed();
			insertCase1(avo);
		} else
			insertCase4(no);
	}

	// Caso2: O pai do no' e' preto
	private void insertCase2(NoRedBlack<E> no) {
		if (no.getPai().isBlack())
			return;
		else
			insertCase3(no);
	}

	// Caso1: A raiz e' vermelha
	private void insertCase1(NoRedBlack<E> no) {
		if (no == raiz)
			no.setBlack();
		else
			insertCase2(no);
	}

	// Caso 1: O no corrente e' a raiz
	private void deleteCase1(NoRedBlack<E> no) {
		if (no.getPai() == sentinel)
			return;
		else
			deleteCase2(no);
	}

	// Caso 2: O irmao do no corrente e' vermelho
	private void deleteCase2(NoRedBlack<E> no) {
		NoRedBlack<E> s = no.getIrmao();
		if (s == sentinel)
			return;
		if (s.isRed()) {
			no.getPai().setRed();
			s.setBlack();
			if (no.isFilhoEsquerdo())
				rotacaoParaEsquerda(no.getPai());
			else
				rotacaoParaDireita(no.getPai());
		}
		deleteCase3(no);
	}

	// Caso 3: O irmao do no corrente e' preto,
	// o pai e' preto e os filhos do irmao sao pretos
	private void deleteCase3(NoRedBlack<E> no) {
		NoRedBlack<E> s = no.getIrmao();
		if (s == sentinel)
			return;
		if (no.getPai().isBlack() && s.isBlack() && s.getEsq().isBlack()
				&& s.getDir().isBlack()) {
			s.setRed();
			deleteCase1(no.getPai());
		} else
			deleteCase4(no);
	}

	// Caso 4: O irmao do no corrente e' preto,
	// o pai e' vermelho e os filhos do irmao sao pretos
	private void deleteCase4(NoRedBlack<E> no) {
		NoRedBlack<E> s = no.getIrmao();
		if (s == sentinel)
			return;
		if (no.getPai().isRed() && s.isBlack() && s.getEsq().isBlack()
				&& s.getDir().isBlack()) {
			s.setRed();
			no.getPai().setBlack();
		} else
			deleteCase5(no);
	}

	// Caso 5: O no e' filho esquerdo do pai,
	// o irmao e' preto, o filho esquerdo do irmao e' vermelho
	// e o filho direito do irmao e' preto
	private void deleteCase5(NoRedBlack<E> no) {
		NoRedBlack<E> s = no.getIrmao();
		if (s == sentinel)
			return;
		if (no.isFilhoEsquerdo() && s.isBlack() && s.getEsq().isRed()
				&& s.getDir().isBlack()) {
			s.setRed();
			s.getEsq().setBlack();
			rotacaoParaDireita(s);
		} else if (no.isFilhoDireito() && s.isBlack() && s.getDir().isRed()
				&& s.getEsq().isBlack()) {
			s.setRed();
			s.getDir().setBlack();
			rotacaoParaEsquerda(s);
		}
		deleteCase6(no);
	}

	// Caso 6: O no e' filho esquerdo do pai,
	// o irmao e' preto, o filho esquerdo do irmao e' vermelho
	private void deleteCase6(NoRedBlack<E> no) {
		NoRedBlack<E> s = no.getIrmao();
		if (s == sentinel)
			return;
		s.setColor(no.getPai().getColor());
		no.getPai().setBlack();
		if (no.isFilhoEsquerdo()) {
			s.getDir().setBlack();
			rotacaoParaEsquerda(no.getPai());
		} else {
			s.getEsq().setBlack();
			rotacaoParaDireita(no.getPai());
		}

	}

	@Override
	public void clear() {
		raiz = sentinel;
		numItens = 0;
	}

	@Override
	public MyIterator<E> iterator() {
		return new ArvoreIterator();
	}

	@Override
	public boolean remove(E obj) {
		NoRedBlack<E> achado = acharNo(obj);
		// achou então atual é diferente de null
		if (achado != sentinel) {
			// Verifica se o no tem dois filhos
			if (achado.getDir() != sentinel && achado.getEsq() != sentinel) {
				// procura na subarvore esquerda o no que esta mais a direita
				NoRedBlack<E> pTemp = achado.getEsq();

				while (pTemp.getDir() != sentinel)
					pTemp = pTemp.getDir();

				// copia o objeto do no mais a direita que desejamos remover
				achado.setObj(pTemp.getObj());
				achado = pTemp;
				// agora removo achado

			}

			if (achado == raiz) {
				if (raiz.getEsq() != sentinel)
					raiz = raiz.getEsq();
				else
					raiz = raiz.getDir();
				if (raiz != sentinel)
					raiz.setPai(sentinel);

			} else {
				int direcao = 1;
				NoRedBlack<E> pai = achado.getPai();
				NoRedBlack<E> filho;

				if (achado == pai.getEsq())
					direcao = -1;
				filho = pai.getFilho(direcao);

				if (filho.getEsq() != sentinel) {
					pai.setFilho(direcao, filho.getEsq());
					filho.getEsq().setPai(pai);
				} else {
					pai.setFilho(direcao, filho.getDir());
					if (filho.getDir() != sentinel)
						filho.getDir().setPai(pai);
				}
			}
			numItens--;
			deleteCase1(achado);
			return true;
		}
		return false;

	}

	// acha o no pai para add, se o no for colocado como rais retorna sentinel,
	// se achar retorna null
	private NoRedBlack<E> acharNoPaiAdd(E obj) {
		NoRedBlack<E> pai = sentinel;
		NoRedBlack<E> atual = raiz;
		int comparacao;
		// achar no para add
		while (atual != sentinel) {
			comparacao = obj.compareTo(atual.getObj());
			if (comparacao == 0)
				return null;
			else {
				pai = atual;
				if (comparacao < 0)
					atual = atual.getEsq();
				else
					atual = atual.getDir();
			}
		}
		return pai;
	}

	private NoRedBlack<E> acharNo(E obj) {
		NoRedBlack<E> atual = raiz;
		int comparacao;
		// achar no
		while (atual != sentinel) {
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
	public E retrieve(E obj) {
		NoRedBlack<E> no = acharNo(obj);
		if (no != sentinel) {
			return no.getObj();
		}
		return null;
	}

	@Override
	public boolean add(E obj) {
		NoRedBlack<E> noPai = acharNoPaiAdd(obj);

		if (noPai != null) {
			NoRedBlack<E> no = new NoRedBlack<E>(obj, sentinel);
			if (noPai != sentinel) {
				if (obj.compareTo(noPai.getObj()) > 0)
					noPai.setDir(no);
				else
					noPai.setEsq(no);
				no.setPai(noPai);
			} else {
				// se no pai for sentinel que dizer que o no vai ser raiz
				raiz = no;
			}
			insertCase1(no);
			numItens++;
			return true;
		}
		return false;
	}

	// *************************************************************
	// Impressao da arvore
	// *************************************************************
	static class DescNo<E extends Comparable<E>> implements
			Comparable<DescNo<E>> {
		int nivel;
		int ident;
		NoRedBlack<E> no;

		@Override
		public int compareTo(DescNo<E> o) {
			return no.getObj().compareTo(o.no.getObj());
		}
	}

	public void desenhe(int larguraTela) {
		if (raiz == sentinel)
			return;
		String brancos = "                                                        "
				+ "                                                        "
				+ "                                                        ";
		int largTela = larguraTela;
		int ident = largTela / 2;
		int nivelAnt = 0;
		int nivel = 0;
		int offset;
		NoRedBlack<E> pTemp;
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

			if (nivel == nivelAnt) {
				linha = linha + brancos.substring(0, ident - linha.length())
						+ pTemp.toString();
			} else {
				System.out.println(linha + "\n\n");
				linha = brancos.substring(0, ident) + pTemp.toString();
				nivelAnt = nivel;
			}
			nivel = nivel + 1;
			offset = (int) (largTela / Math.round(Math.pow(2, nivel + 1)));
			if (pTemp.getEsq() != sentinel) {
				descNo = new DescNo<E>();
				descNo.ident = ident - offset;
				descNo.nivel = nivel;
				descNo.no = pTemp.getEsq();
				fila.insira(descNo);
			}
			if (pTemp.getDir() != sentinel) {
				descNo = new DescNo<E>();
				descNo.ident = ident + offset;
				descNo.nivel = nivel;
				descNo.no = pTemp.getDir();
				fila.insira(descNo);
			}
		}
		System.out.println(linha);
	}
	public static void main(String[] args) {
		Integer valor, apagar;
		boolean remover, add;
		Scanner leitura = new Scanner(System.in);
		try{
			ArvoreRedBlack<Integer> arvore = new ArvoreRedBlack<Integer>();
		int opcao;
		do {
            Keyboard.clrscr();
            opcao = Keyboard.menu("Adicionar Nó/Consulta Nó/Imprimir Arvore/ Excluir No/Terminar");
            switch (opcao){
                case 1 :
				System.out.println("Numero do Nó:");
				valor = leitura.nextInt();
				add = arvore.add(valor);
				String str = Boolean.toString (add);
				System.out.print("Nó:"+valor+" Adiconado!");
				System.out.println();
                break;
                case 2 :
				System.out.print("Procurar o Nó: ");
				int num = leitura.nextInt();
				if(arvore.contains(num)){
				System.out.print("Nó "+arvore.acharNo(num)+" Encontrado!");
				System.out.println();
				}else{
				System.out.print("Nó "+num+" Não Encontrado!!!");
				System.out.println();
				}
				break;
                case 3 :
				arvore.desenhe(100);
				System.out.println();
				break;
				case 4 :	
				System.out.println("Informe o Nó a ser Excluido:");
				apagar = leitura.nextInt();
				remover = arvore.remove(apagar);
				String stri = Boolean.toString (remover);
				System.out.print("Nó "+apagar+" Excluido:");
				System.out.println();
				break;
                }
        } while (opcao != 5);
    }finally {
		leitura.close();
	}
	}
}

