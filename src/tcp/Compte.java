package tcp;

import java.util.Vector;

public class Compte {
	private int id;
	private String nom;
	private float solde;
	public static int vbl=1;
	private Vector<String> operation;
	
	public Compte(String nom, float solde) {
		super();
		this.nom = nom;
		this.solde = solde;
		this.id=vbl;
		vbl++;
		operation = new Vector<String>();
	}

	

		
	public Compte() {
		super();
		operation = new Vector<String>();
	}
	
	public void addOperation(String s) {
		this.operation.add(s);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public float getSolde() {
		return solde;
	}

	public void setSolde(float solde) {
		this.solde = solde;
	}
	
	

	public Vector<String> getOperation() {
		return operation;
	}




	public void setOperation(Vector<String> operation) {
		this.operation = operation;
	}




	@Override
	public String toString() {
		return "Compte [id=" + id + ", nom=" + nom + ", solde=" + solde + ", operation=" + operation + "]";
	}




	

	
	
	
	
	
}