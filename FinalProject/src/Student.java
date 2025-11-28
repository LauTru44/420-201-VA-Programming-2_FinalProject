
public class Student extends Person implements Payment{
	String schoolName;
	int grade;
	
	public Student(int id_member, String name, String email, String schoolName,int grade) {
		super(id_member, name, email);
		this.schoolName = schoolName;
		this.grade = grade;
		this.type="Student";
	}

	String displayMemberType() {
		return "Student";
	}
	
	@Override
	public double calculate(int days) {
		double finalFee=5;
		if (days >=7) {
			for (int i=0;i<(days-7);i++) {
			finalFee+=1;	
			}
			return finalFee;
		} 
		else {
			return finalFee;
		}
	}

	@Override
	public String toString() {
		return "Student [schoolName=" + schoolName + ", grade=" + grade + ", id_member=" + id_member + ", name=" + name
				+ ", email=" + email + "]";
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

}
