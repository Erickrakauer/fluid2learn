package pt.c01interfaces.s01knowledge.s02app.actors;

import java.util.ArrayList;

import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;

public class Enquirer implements IEnquirer {
	
    IObjetoConhecimento obj;
	
	public Enquirer() {
		
	}
	
	private String isAsked(ArrayList<IDeclaracao> old, IDeclaracao decl) {
		for (int i = 0; i < old.size(); i++) {
			if (decl.getPropriedade().equalsIgnoreCase(old.get(i).getPropriedade())) {
				return old.get(i).getValor();
			}
		}
		return "asked";
	}
	
	@Override
	public void connect(IResponder responder) {
        IBaseConhecimento bc = new BaseConhecimento();
		String animals[] = bc.listaNomes();
		int animal_number = 0;
		boolean acertei;
		
		obj = bc.recuperaObjeto(animals[animal_number]);
		
		IDeclaracao decl = obj.primeira();
		ArrayList<IDeclaracao> old = new ArrayList<IDeclaracao>(); 
		
		while (decl != null && animal_number < animals.length) {
			old.add(decl);
			String pergunta = decl.getPropriedade();
			String respostaEsperada = decl.getValor();
			String resposta = null;
			
			resposta = isAsked(old, decl);
			System.out.println("resp " + resposta);
			if (!resposta.equalsIgnoreCase("asked")) {
				resposta = responder.ask(pergunta);
				System.out.println(pergunta);
			}
			
			if (resposta.equalsIgnoreCase(respostaEsperada)) {
				decl = obj.proxima();
			} else {
				animal_number++;
				obj = bc.recuperaObjeto(animals[animal_number]);
				decl = obj.primeira();
			}
			
		}
		if (animal_number == animals.length)
			acertei = responder.finalAnswer("nao sei");
		else 
			acertei = responder.finalAnswer(animals[animal_number]);
		
		
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");

	}
}