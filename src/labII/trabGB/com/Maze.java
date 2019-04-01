package labII.trabGB.com;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Maze implements IMaze {

	//********************************************** Variables ********************************************************
	private Point matriz[][];
	private int numLinhas, numColunas;
	private char elementosLinha[];
	private boolean hasEmptyRoute = false;

	Stack<Point> s = new LinkedStack<>();
	Queue<Point> q = new LinkedQueue<>();

	//********************************************** Load *************************************************************
	@Override
	public void load(File file) throws IOException {
		try (BufferedReader inputStream = new BufferedReader(new FileReader(file))) {
			String line;
			int indiceLinha = 0;

			while ((line = inputStream.readLine()) != null) {

				if (indiceLinha == 0) {
					// Pega o numero de linhas e colunas da matriz, convertendo de String para int
					numLinhas = Integer.parseInt(line.split(" ")[0]);
					numColunas = Integer.parseInt(line.split(" ")[1]);

					matriz = new Point[numLinhas][numColunas];
					// matrizBooleano = new boolean[numLinhas][numColunas];

				} else {
					elementosLinha = line.toCharArray();// Converte toda a linha para array de char

					for (int coluna = 0; coluna < elementosLinha.length; coluna++) {
						Point p = new Point(indiceLinha - 1, coluna, elementosLinha[coluna]);
						matriz[indiceLinha - 1][coluna] = p;
					}
				}
				indiceLinha++;
			}
		}
	}

	//********************************************** findOut **********************************************************
	@Override
	public void findOut() {
		//Inicio da leitura na primeira posição (0,0)
		s.push(matriz[0][0]);
		matriz[s.top().getRow()][s.top().getCol()].setVisited();
		addRastreamentoQueue(s.top().getRow(), s.top().getCol(), s.top().getSymbol());

		while (!s.isEmpty() && matriz[s.top().getRow()][s.top().getCol()].getSymbol() != '#') {
			
			matriz[s.top().getRow()][s.top().getCol()].setVisited();
			
			if (isPosicaoValida(s.top().getRow() + 1, s.top().getCol())
					&& !matriz[s.top().getRow() + 1][s.top().getCol()].isVisited()
					&& isSimboloValido(s.top().getRow() + 1, s.top().getCol())) {

				s.push(matriz[s.top().getRow() + 1][s.top().getCol()]);				
				addRastreamentoQueue(s.top().getRow(), s.top().getCol(), s.top().getSymbol());

			} else if (isPosicaoValida(s.top().getRow(), s.top().getCol() + 1)
					&& !matriz[s.top().getRow()][s.top().getCol() + 1].isVisited()
					&& isSimboloValido(s.top().getRow(), s.top().getCol() + 1)) {

				s.push(matriz[s.top().getRow()][s.top().getCol() + 1]);		
				addRastreamentoQueue(s.top().getRow(), s.top().getCol(), s.top().getSymbol());

			} else if (isPosicaoValida(s.top().getRow() + 1, s.top().getCol())
					&& !matriz[s.top().getRow() + 1][s.top().getCol()].isVisited()
					&& isSimboloValido(s.top().getRow() + 1, s.top().getCol())) {

				s.push(matriz[s.top().getRow() + 1][s.top().getCol()]);				
				addRastreamentoQueue(s.top().getRow(), s.top().getCol(), s.top().getSymbol());

			} else if (isPosicaoValida(s.top().getRow(), s.top().getCol() - 1)
					&& !matriz[s.top().getRow()][s.top().getCol() - 1].isVisited()
					&& isSimboloValido(s.top().getRow(), s.top().getCol() - 1)) {

				s.push(matriz[s.top().getRow()][s.top().getCol() - 1]);
				addRastreamentoQueue(s.top().getRow(), s.top().getCol(), s.top().getSymbol());

			} else {
				q.enqueue(s.top());
				s.pop().setAbandoned();
				
				if (s.isEmpty()) {
					this.hasEmptyRoute = true;
					break;
				}
				
				//Se a nova posição já foi visitada, antes do abandono do caminho, insere novamente como visitada na fila
				if (isPosicaoValida(s.top().getRow(), s.top().getCol())
						&& matriz[s.top().getRow()][s.top().getCol()].isVisited()
						&& isSimboloValido(s.top().getRow(), s.top().getCol())) {
					
					addRastreamentoQueue(s.top().getRow(), s.top().getCol(), s.top().getSymbol());
				}
			}
		}
		marcarRota();
	}
	//********************************************** Private methods *************************************************
	
	private boolean isSimboloValido(int x, int y) {
		if (matriz[x][y].getSymbol() == '.' || matriz[x][y].getSymbol() == '#'
				&& !(x > numLinhas - 1 || x < 0) && !(y > numColunas - 1 || y < 0))
			return true;
		return false;
	}

	private boolean isPosicaoValida(int x, int y) {
		if (x > numLinhas - 1 || x < 0) 
			return false;
		if (y > numColunas - 1 || y < 0) 
			return false;
		return true;
	}
	
	private void addRastreamentoQueue(int x, int y, char c) {
		Point p = new Point(x,y,c);
		p.setVisited();
		q.enqueue(p);
	}

	private void marcarRota() {
		int numElementos = s.numElements();
		for(int i = 0; i < numElementos ; i++) {
			if(i != 0) //Não marcar o ponto EXIT com o *
				matriz[s.top().getRow()][s.pop().getCol()].setValid();
			else 
				s.pop();
		}
	}
	
	//********************************************** Show *************************************************************
	@Override
	public void show() {
		if (hasEmptyRoute) {
			System.out.println();
			System.out.println("Nao ha caminho que resolva este labirinto");
		}
		else {
			for (int i = 0; i < matriz.length; i++) {
				System.out.println();
				for (int j = 0; j < matriz[i].length; j++) {
					System.out.print(matriz[i][j].getSymbol());
					System.out.print(" ");
				}
			}
		}
	}
	
	//********************************************** showTracking *****************************************************
	@Override
	public void showTracking() {
		System.out.println();
		//Se tiver uma rota, setar o status do ponto de saida como EXIT
		if (!s.isEmpty())
		q.back().setExitPoint();
		
		while (!q.isEmpty()) {
			System.out.println(q.dequeue().toString());
		}
	}

}
