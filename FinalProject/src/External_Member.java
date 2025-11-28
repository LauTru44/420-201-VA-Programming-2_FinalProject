
public class External_Member extends Person implements Payment{
	String job;
	String organization;
	
	public External_Member(int id_member, String name, String email, String job, String organization) {
		super(id_member, name, email);
		this.job = job;
		this.organization = organization;
		this.type="External_Member";
	}

	String displayMemberType() {
		return "External member";
	}
	
	@Override
	public double calculate(int days) {
		double finalFee=10;
		if (days >=7) {
			for (int i=0;i<(days-7);i++) {
			finalFee+=2;	
			}
			return finalFee;
		} 
		else {
			return finalFee;
		}
	}

	@Override
	public String toString() {
		return "External_Member [job=" + job + ", organization=" + organization + ", id_member=" + id_member + ", name="
				+ name + ", email=" + email + "]";
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
}
