package challenge;

import java.time.LocalDate;

public class Player {

	private String name;
	private Double releaseValueClause;
	private LocalDate birth;
	private Double wageValue;

	Player(String name, LocalDate birth, Double wageValue) {
		this.name = name;
		this.birth = birth;
		this.wageValue = wageValue;
	}

	Player(String name, Double releaseValueClause) {
		this.name = name;
		this.releaseValueClause = releaseValueClause;
	}

	String getName() {
		return this.name;
	}

	Double getReleaseValueClause() {
		return this.releaseValueClause;
	}

	LocalDate getBirth() {
		return this.birth;
	}

	Double getWageValue() {
		return this.wageValue;
	}

}
