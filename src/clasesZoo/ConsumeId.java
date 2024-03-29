package clasesZoo;
// Generated 14-feb-2022 13:47:46 by Hibernate Tools 4.0.1.Final

/**
 * ConsumeId generated by hbm2java
 */
public class ConsumeId implements java.io.Serializable {

	private int idAnimal;
	private int idAlimento;

	public ConsumeId() {
	}

	public ConsumeId(int idAnimal, int idAlimento) {
		this.idAnimal = idAnimal;
		this.idAlimento = idAlimento;
	}

	public int getIdAnimal() {
		return this.idAnimal;
	}

	public void setIdAnimal(int idAnimal) {
		this.idAnimal = idAnimal;
	}

	public int getIdAlimento() {
		return this.idAlimento;
	}

	public void setIdAlimento(int idAlimento) {
		this.idAlimento = idAlimento;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ConsumeId))
			return false;
		ConsumeId castOther = (ConsumeId) other;

		return (this.getIdAnimal() == castOther.getIdAnimal()) && (this.getIdAlimento() == castOther.getIdAlimento());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdAnimal();
		result = 37 * result + this.getIdAlimento();
		return result;
	}

}
