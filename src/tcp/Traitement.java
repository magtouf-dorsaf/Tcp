package tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Traitement extends Thread {
	private Socket sc;

	public Traitement(Socket sc) {
		super();
		this.sc = sc;
	}

	public void run() {
		try {

			BufferedReader in_socket = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			PrintWriter out_socket = new PrintWriter(sc.getOutputStream(), true);

			while (true) {
				String msg = in_socket.readLine();
				if (msg.startsWith("CREATION")) {
					String nomCompte = msg.substring(8, msg.length());
					Compte c = new Compte(nomCompte, 0);

					boolean test = false;
					for (Compte com : Serveur.listeComptes) {
						if (com.getNom().equals(nomCompte)) {
							test = true;
						}
					}
					if (test == true) {
						out_socket.println("Ce nom existe déja .. Tu dois le changer");
					} else {
						Serveur.listeComptes.add(c);
						out_socket.println("Le compte est cree avec SUCCEES.. votre numéro est : " + c.getId());
						while (true) {
							msg = in_socket.readLine();
							if (msg.startsWith("CREDIT")) {
								String credit = msg.substring(6, msg.length());
								float cr = Float.valueOf(credit);

								c.setSolde(cr);
								c.addOperation("creddit"+cr);
								out_socket.println(c);

							} else if (msg.startsWith("DEBIT")) {
								String debit = msg.substring(5, msg.length());
								float db = Float.valueOf(debit);
								float credit = c.getSolde();
								float res = credit - db;
								if (res >= 0) {
									c.setSolde(res);
									out_socket.println("Operation  avec SUCCEES.. votre solde " + c.getSolde());
								} else {
									out_socket.println("SOLDE INSUFFISANT " + c.getSolde());

								}

							} else if (msg.startsWith("SOLDE")) {
								out_socket.println("Votre SOLDE est" + c.getSolde());

							} else if (msg.startsWith("TRANSFERT")) {
								String tab[] = msg.split("#");
								String dest = tab[1];
								String montant = tab[2];
								float m = Float.valueOf(montant);
								String text = "TRANSFERT :"+"destination : "+dest+"  //montant:"+montant+"***";
								c.addOperation("credit"+m);
								out_socket.println("Votre HISTO est" + c.getOperation());

								for (Compte cp : Serveur.listeComptes) {
									if (cp.getNom().equals(dest)) {
										float courant = cp.getSolde();
										cp.setSolde(courant + m);
										c.setSolde(c.getSolde() - m);
										out_socket.println("Votre SOLDE est" + c.getSolde());
										//String text2 = "RECU :"+"from : "+c.getNom()+"  //montant:"+montant+"***";
										//cp.addOperation(text2);
										//out_socket.println("Votre HISTO est" + cp.getOperation());

									} else {
										out_socket.println("Ce Compte n'existe pas");

									}
								}
							}
						}
					}

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}