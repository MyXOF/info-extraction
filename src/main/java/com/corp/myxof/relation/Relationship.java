package com.corp.myxof.relation;

import java.util.HashSet;
import java.util.Set;

public class Relationship {
	private String person;
	private Set<String> masters = new HashSet<>();
	private Set<String> apprentices = new HashSet<>();
	private Set<String> family = new HashSet<>();
	private Set<String> parters = new HashSet<>();
	
	public Relationship(String person){
		this.person = person;
	}
	
	public void addMaster(String master){
		masters.add(master);
	}
	
	public void addApprentice(String apprentice){
		apprentices.add(apprentice);
	}
	
	public void addMember(String member){
		family.add(member);
	}
	
	public void addParter(String parter){
		parters.add(parter);
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Person: ").append(person).append("\n")
			.append("Master: ").append(masters).append("\n")
			.append("Apprentices: ").append(apprentices).append("\n")
			.append("Family: ").append(family).append("\n")
			.append("Paters: ").append(parters).append("\n");
		
		return builder.toString();
	}
	
	public Set<String> getMasters() {
		masters.remove(person);
		return masters;
	}

	public void setMasters(Set<String> masters) {
		this.masters = masters;
	}

	public Set<String> getApprentices() {
		apprentices.remove(person);
		return apprentices;
	}

	public void setApprentices(Set<String> apprentices) {
		this.apprentices = apprentices;
	}

	public Set<String> getFamily() {
		family.remove(person);
		return family;
	}

	public void setFamily(Set<String> family) {
		this.family = family;
	}

	public Set<String> getParters() {
		parters.remove(person);
		return parters;
	}

	public void setParters(Set<String> parters) {
		this.parters = parters;
	}
}
