public class Bakery extends Thread {

	// Variáveis para as threads
	public int threadId;
	public static final int conteAte = 20;
	public static final int threadsN = 5;
	public static volatile int count = 0; // volatil significa que as mudanças de valores serão refletidas em todas as threds durante a execução
                                          // terão o mesmo valor na fila

	// Variáveis globais
	private static volatile boolean[] escolha = new boolean[threadsN]; // Array com valores booleanos para cada thread,
																	   // para saber se a thread quer entrar na área crítica ou não. 
	private static volatile int[] ticket = new int[threadsN]; // O ticket é usado para definir a prioridade
	
	// Construtor, simplesmente gera as Threads
	public Bakery(int id){
		threadId = id; //cada thread recebe um identificador.
	}

	public void run(){
		int scale = 2; //escala ??cada
		for (int i = 0; i < conteAte; i++){ // contagem até 200
			lock(threadId); //travamendo da thread i;
			
				// Início da área crítica
				count = count + 1;
				System.out.println("REGIAO CRITICA -> Processo " + threadId + " e o contador e: " + count);
				
				try {
					sleep((int) (Math.random() * scale)); //numero aleatorio para tempo da thread. Alternancia aleatoria.
				} catch (InterruptedException e) { /* nice */ }
				// Fim da área crítica
			unlock(threadId);	
		}
	}

	// Método para o travamento
	public void lock(int id){
		// Isso significa que o thread atual (com id = id) está interessado em entrar na seção crítica.
		escolha[id] = true;

		// Encontre o valor máximo e adicione 1 para obter o próximo ticket disponível.
		ticket[id] = findMax() + 1;
		escolha[id] = false; //nao escolhe mais

		// System.out.println("Thread " + id + " obteve bilhete em Lock");

		for (int j = 0; j < threadsN; j++) {

			// Se a Thread j for a thread atual, vá no próximo tópico.
			if (j == id)
				continue;
			
			// Espere se a thread j estiver escolhendo agora.
			while (escolha[j]) { 
				/* nothing */ 
			}

			while (ticket[j] != 0 && (ticket[id] > ticket[j] || (ticket[id] == ticket[j] && id > j))) { 
				/* nothing */ 
			}			 
		} // for
	}

	/*
	 * Method que sai do bloqueio(lock).
	 */

	//desbloqueio
	private void unlock(int id) {
		ticket[id] = 0;
		// System.out.println("Thread " + id + " unlock");
	}

	/*
	 * Method que encontra o valor maximo no ticked array.
	 */
	private int findMax() {
		
		int m = ticket[0];

		for (int i = 1; i < ticket.length; i++) {
			if (ticket[i] > m)
				m = ticket[i];
		}
		return m;
	}

	public static void main(String[] args) {

		//inicialização das variaveis globais (nao necessario).
		for (int i = 0; i < threadsN; i++) {
			escolha[i] = false;
			ticket[i] = 0;
		}

		Bakery[] threads = new Bakery[threadsN]; // Array de threads.

		// Inicializando as threads.
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Bakery(i); // thread recebe um Id.
			threads[i].start();
		}

		// aguardar todas as threads para terminar.
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("\nProcessados: " + count);
		System.out.println("\nEsperados e: " + (conteAte * threadsN));
	} // main

}