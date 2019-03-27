package br.cedeteg.unicentro.decomp;

class Filosofo extends Thread{
	private String nome;
	private Garfo gEsquerdo;
	private Garfo gDireito;

	public Filosofo(String nome, Garfo esquerdo, Garfo direito){
		this.nome = nome;
		gEsquerdo = esquerdo;
		gDireito = direito;
	}
 
	public void comer() throws InterruptedException{
		System.out.println();
		if(!gEsquerdo.usando){
			if(!gDireito.usando){
				System.out.print(nome + " pegou ");
				gEsquerdo.pega();
				System.out.print(" e ");
				gDireito.pega();

				System.out.println();
				Log.msg(nome + " está comendo.");
				
				Thread.sleep(1000);

				System.out.print(nome);
				gDireito.solta();
				System.out.print(nome);
		 		gEsquerdo.solta();
			}
		}		
		pensar();
	}

	public void pensar(){
		 	Log.msg(nome + " está pensando.");
		 	Log.Delay(1000);
		
	}

	public void run(){
		for(int i=0; i<=10; i++){
			try {
				comer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}