package com.corp.myxof.relation;

import java.util.HashSet;
import java.util.Set;

public class Relationship {
	private Set<String> masters = new HashSet<>();
	private Set<String> apprentices = new HashSet<>();
	private Set<String> family = new HashSet<>();
	private Set<String> parters = new HashSet<>();
	
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

	public Set<String> getMasters() {
		return masters;
	}

	public void setMasters(Set<String> masters) {
		this.masters = masters;
	}

	public Set<String> getApprentices() {
		return apprentices;
	}

	public void setApprentices(Set<String> apprentices) {
		this.apprentices = apprentices;
	}

	public Set<String> getFamily() {
		return family;
	}

	public void setFamily(Set<String> family) {
		this.family = family;
	}

	public Set<String> getParters() {
		return parters;
	}

	public void setParters(Set<String> parters) {
		this.parters = parters;
	}
}
