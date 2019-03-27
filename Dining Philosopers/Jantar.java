package br.cedeteg.unicentro.decomp;

public class Jantar{

	public static void main(String[] args){

		int x=10;

		Log.msg(String.valueOf(x));
 
		Garfo[] garfos = new Garfo[5];

		//Gerar os garfos
		for (int i=0; i < garfos.length; i++){
			garfos[i] = new Garfo("garfo" + i);
		}
		
		//Gerar os filósofos
		Filosofo[] filosofos = new Filosofo[5];
		filosofos[0] = new Filosofo("Filosofo #1 ", garfos[0], garfos[1]);
		filosofos[1] = new Filosofo("Filosofo #2 ", garfos[1], garfos[2]);
		filosofos[2] = new Filosofo("Filosofo #3 ", garfos[2], garfos[3]);
		filosofos[3] = new Filosofo("Filosofo #4 ", garfos[3], garfos[4]);
		filosofos[4] = new Filosofo("Filosofo #5 ", garfos[4], garfos[0]);

		//Inicializar os filósofos
		for (int i=0; i < filosofos.length; i++){
			Log.msg(i + " está sentando...");
			Thread t= new Thread(filosofos[i]);
			t.start();
		}
}
}