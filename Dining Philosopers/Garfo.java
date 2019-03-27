package br.cedeteg.unicentro.decomp;

public class Garfo{

	public boolean usando;
	public String nome;

	public Garfo(String nome){
		this.nome = nome;
	}

	public synchronized void pega(){
		System.out.print(nome);
		this.usando = true;
	}
	public synchronized void solta(){
		Log.msg (" largou " + nome);
		this.usando = false ;
	}
}
