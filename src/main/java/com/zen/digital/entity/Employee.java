package com.zen.digital.entity;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "employees")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeCode;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;
    private String maritalStatus;
    private String bloodGroup;

    private String personalEmail;
    private String officialEmail;
    private String personalPhone;
    private String officialPhone;

    private String currentAddress;
    private String permanentAddress;
    private String city;
    private String state;
    private String country;
    private String pincode;

    private String department;
    private String designation;
    private String reportingManager;
    private String workLocation;
    private LocalDate dateOfJoining;
    private LocalDate dateOfConfirmation;
    private LocalDate dateOfResignation;
    private String employmentStatus;
    private String employmentType;

    private String panNumber;
    private String aadharNumber;
    private String passportNumber;
    private String drivingLicenseNumber;
    private String taxIdentificationNumber;
    private String socialSecurityNumber;

    private String bankName;
    private String bankAccountNumber;
    private String ifscCode;
    private String uanNumber;
    private String esicNumber;
    private String salaryStructureId;

    private String emergencyContactName;
    private String emergencyContactRelationship;
    private String emergencyContactPhone;
    private String emergencyContactAddress;

    private String skills;
    private String certifications;
    private String hobbies;
    private String languagesKnown;
    private Integer totalExperienceYears;
    private String previousEmployers;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getPersonalEmail() {
		return personalEmail;
	}
	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}
	public String getOfficialEmail() {
		return officialEmail;
	}
	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}
	public String getPersonalPhone() {
		return personalPhone;
	}
	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}
	public String getOfficialPhone() {
		return officialPhone;
	}
	public void setOfficialPhone(String officialPhone) {
		this.officialPhone = officialPhone;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getReportingManager() {
		return reportingManager;
	}
	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}
	public String getWorkLocation() {
		return workLocation;
	}
	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public LocalDate getDateOfConfirmation() {
		return dateOfConfirmation;
	}
	public void setDateOfConfirmation(LocalDate dateOfConfirmation) {
		this.dateOfConfirmation = dateOfConfirmation;
	}
	public LocalDate getDateOfResignation() {
		return dateOfResignation;
	}
	public void setDateOfResignation(LocalDate dateOfResignation) {
		this.dateOfResignation = dateOfResignation;
	}
	public String getEmploymentStatus() {
		return employmentStatus;
	}
	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}
	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}
	public String getTaxIdentificationNumber() {
		return taxIdentificationNumber;
	}
	public void setTaxIdentificationNumber(String taxIdentificationNumber) {
		this.taxIdentificationNumber = taxIdentificationNumber;
	}
	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getUanNumber() {
		return uanNumber;
	}
	public void setUanNumber(String uanNumber) {
		this.uanNumber = uanNumber;
	}
	public String getEsicNumber() {
		return esicNumber;
	}
	public void setEsicNumber(String esicNumber) {
		this.esicNumber = esicNumber;
	}
	public String getSalaryStructureId() {
		return salaryStructureId;
	}
	public void setSalaryStructureId(String salaryStructureId) {
		this.salaryStructureId = salaryStructureId;
	}
	public String getEmergencyContactName() {
		return emergencyContactName;
	}
	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}
	public String getEmergencyContactRelationship() {
		return emergencyContactRelationship;
	}
	public void setEmergencyContactRelationship(String emergencyContactRelationship) {
		this.emergencyContactRelationship = emergencyContactRelationship;
	}
	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}
	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}
	public String getEmergencyContactAddress() {
		return emergencyContactAddress;
	}
	public void setEmergencyContactAddress(String emergencyContactAddress) {
		this.emergencyContactAddress = emergencyContactAddress;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public String getCertifications() {
		return certifications;
	}
	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	public String getLanguagesKnown() {
		return languagesKnown;
	}
	public void setLanguagesKnown(String languagesKnown) {
		this.languagesKnown = languagesKnown;
	}
	public Integer getTotalExperienceYears() {
		return totalExperienceYears;
	}
	public void setTotalExperienceYears(Integer totalExperienceYears) {
		this.totalExperienceYears = totalExperienceYears;
	}
	public String getPreviousEmployers() {
		return previousEmployers;
	}
	public void setPreviousEmployers(String previousEmployers) {
		this.previousEmployers = previousEmployers;
	}

	// Default constructor
	public Employee() {
	}

	// All-args constructor
	public Employee(Long id, String employeeCode, String firstName, String lastName, LocalDate dateOfBirth,
					String gender, String nationality, String maritalStatus, String bloodGroup, String personalEmail,
					String officialEmail, String personalPhone, String officialPhone, String currentAddress,
					String permanentAddress, String city, String state, String country, String pincode,
					String department, String designation, String reportingManager, String workLocation,
					LocalDate dateOfJoining, LocalDate dateOfConfirmation, LocalDate dateOfResignation,
					String employmentStatus, String employmentType, String panNumber, String aadharNumber,
					String passportNumber, String drivingLicenseNumber, String taxIdentificationNumber,
					String socialSecurityNumber, String bankName, String bankAccountNumber, String ifscCode,
					String uanNumber, String esicNumber, String salaryStructureId, String emergencyContactName,
					String emergencyContactRelationship, String emergencyContactPhone, String emergencyContactAddress,
					String skills, String certifications, String hobbies, String languagesKnown,
					Integer totalExperienceYears, String previousEmployers) {
		this.id = id;
		this.employeeCode = employeeCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.nationality = nationality;
		this.maritalStatus = maritalStatus;
		this.bloodGroup = bloodGroup;
		this.personalEmail = personalEmail;
		this.officialEmail = officialEmail;
		this.personalPhone = personalPhone;
		this.officialPhone = officialPhone;
		this.currentAddress = currentAddress;
		this.permanentAddress = permanentAddress;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
		this.department = department;
		this.designation = designation;
		this.reportingManager = reportingManager;
		this.workLocation = workLocation;
		this.dateOfJoining = dateOfJoining;
		this.dateOfConfirmation = dateOfConfirmation;
		this.dateOfResignation = dateOfResignation;
		this.employmentStatus = employmentStatus;
		this.employmentType = employmentType;
		this.panNumber = panNumber;
		this.aadharNumber = aadharNumber;
		this.passportNumber = passportNumber;
		this.drivingLicenseNumber = drivingLicenseNumber;
		this.taxIdentificationNumber = taxIdentificationNumber;
		this.socialSecurityNumber = socialSecurityNumber;
		this.bankName = bankName;
		this.bankAccountNumber = bankAccountNumber;
		this.ifscCode = ifscCode;
		this.uanNumber = uanNumber;
		this.esicNumber = esicNumber;
		this.salaryStructureId = salaryStructureId;
		this.emergencyContactName = emergencyContactName;
		this.emergencyContactRelationship = emergencyContactRelationship;
		this.emergencyContactPhone = emergencyContactPhone;
		this.emergencyContactAddress = emergencyContactAddress;
		this.skills = skills;
		this.certifications = certifications;
		this.hobbies = hobbies;
		this.languagesKnown = languagesKnown;
		this.totalExperienceYears = totalExperienceYears;
		this.previousEmployers = previousEmployers;
	}

	// Builder pattern
	public static EmployeeBuilder builder() {
		return new EmployeeBuilder();
	}

	public static class EmployeeBuilder {
		private Long id;
		private String employeeCode;
		private String firstName;
		private String lastName;
		private LocalDate dateOfBirth;
		private String gender;
		private String nationality;
		private String maritalStatus;
		private String bloodGroup;
		private String personalEmail;
		private String officialEmail;
		private String personalPhone;
		private String officialPhone;
		private String currentAddress;
		private String permanentAddress;
		private String city;
		private String state;
		private String country;
		private String pincode;
		private String department;
		private String designation;
		private String reportingManager;
		private String workLocation;
		private LocalDate dateOfJoining;
		private LocalDate dateOfConfirmation;
		private LocalDate dateOfResignation;
		private String employmentStatus;
		private String employmentType;
		private String panNumber;
		private String aadharNumber;
		private String passportNumber;
		private String drivingLicenseNumber;
		private String taxIdentificationNumber;
		private String socialSecurityNumber;
		private String bankName;
		private String bankAccountNumber;
		private String ifscCode;
		private String uanNumber;
		private String esicNumber;
		private String salaryStructureId;
		private String emergencyContactName;
		private String emergencyContactRelationship;
		private String emergencyContactPhone;
		private String emergencyContactAddress;
		private String skills;
		private String certifications;
		private String hobbies;
		private String languagesKnown;
		private Integer totalExperienceYears;
		private String previousEmployers;

		public EmployeeBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public EmployeeBuilder employeeCode(String employeeCode) {
			this.employeeCode = employeeCode;
			return this;
		}

		public EmployeeBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public EmployeeBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public EmployeeBuilder dateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		public EmployeeBuilder gender(String gender) {
			this.gender = gender;
			return this;
		}

		public EmployeeBuilder nationality(String nationality) {
			this.nationality = nationality;
			return this;
		}

		public EmployeeBuilder maritalStatus(String maritalStatus) {
			this.maritalStatus = maritalStatus;
			return this;
		}

		public EmployeeBuilder bloodGroup(String bloodGroup) {
			this.bloodGroup = bloodGroup;
			return this;
		}

		public EmployeeBuilder personalEmail(String personalEmail) {
			this.personalEmail = personalEmail;
			return this;
		}

		public EmployeeBuilder officialEmail(String officialEmail) {
			this.officialEmail = officialEmail;
			return this;
		}

		public EmployeeBuilder personalPhone(String personalPhone) {
			this.personalPhone = personalPhone;
			return this;
		}

		public EmployeeBuilder officialPhone(String officialPhone) {
			this.officialPhone = officialPhone;
			return this;
		}

		public EmployeeBuilder currentAddress(String currentAddress) {
			this.currentAddress = currentAddress;
			return this;
		}

		public EmployeeBuilder permanentAddress(String permanentAddress) {
			this.permanentAddress = permanentAddress;
			return this;
		}

		public EmployeeBuilder city(String city) {
			this.city = city;
			return this;
		}

		public EmployeeBuilder state(String state) {
			this.state = state;
			return this;
		}

		public EmployeeBuilder country(String country) {
			this.country = country;
			return this;
		}

		public EmployeeBuilder pincode(String pincode) {
			this.pincode = pincode;
			return this;
		}

		public EmployeeBuilder department(String department) {
			this.department = department;
			return this;
		}

		public EmployeeBuilder designation(String designation) {
			this.designation = designation;
			return this;
		}

		public EmployeeBuilder reportingManager(String reportingManager) {
			this.reportingManager = reportingManager;
			return this;
		}

		public EmployeeBuilder workLocation(String workLocation) {
			this.workLocation = workLocation;
			return this;
		}

		public EmployeeBuilder dateOfJoining(LocalDate dateOfJoining) {
			this.dateOfJoining = dateOfJoining;
			return this;
		}

		public EmployeeBuilder dateOfConfirmation(LocalDate dateOfConfirmation) {
			this.dateOfConfirmation = dateOfConfirmation;
			return this;
		}

		public EmployeeBuilder dateOfResignation(LocalDate dateOfResignation) {
			this.dateOfResignation = dateOfResignation;
			return this;
		}

		public EmployeeBuilder employmentStatus(String employmentStatus) {
			this.employmentStatus = employmentStatus;
			return this;
		}

		public EmployeeBuilder employmentType(String employmentType) {
			this.employmentType = employmentType;
			return this;
		}

		public EmployeeBuilder panNumber(String panNumber) {
			this.panNumber = panNumber;
			return this;
		}

		public EmployeeBuilder aadharNumber(String aadharNumber) {
			this.aadharNumber = aadharNumber;
			return this;
		}

		public EmployeeBuilder passportNumber(String passportNumber) {
			this.passportNumber = passportNumber;
			return this;
		}

		public EmployeeBuilder drivingLicenseNumber(String drivingLicenseNumber) {
			this.drivingLicenseNumber = drivingLicenseNumber;
			return this;
		}

		public EmployeeBuilder taxIdentificationNumber(String taxIdentificationNumber) {
			this.taxIdentificationNumber = taxIdentificationNumber;
			return this;
		}

		public EmployeeBuilder socialSecurityNumber(String socialSecurityNumber) {
			this.socialSecurityNumber = socialSecurityNumber;
			return this;
		}

		public EmployeeBuilder bankName(String bankName) {
			this.bankName = bankName;
			return this;
		}

		public EmployeeBuilder bankAccountNumber(String bankAccountNumber) {
			this.bankAccountNumber = bankAccountNumber;
			return this;
		}

		public EmployeeBuilder ifscCode(String ifscCode) {
			this.ifscCode = ifscCode;
			return this;
		}

		public EmployeeBuilder uanNumber(String uanNumber) {
			this.uanNumber = uanNumber;
			return this;
		}

		public EmployeeBuilder esicNumber(String esicNumber) {
			this.esicNumber = esicNumber;
			return this;
		}

		public EmployeeBuilder salaryStructureId(String salaryStructureId) {
			this.salaryStructureId = salaryStructureId;
			return this;
		}

		public EmployeeBuilder emergencyContactName(String emergencyContactName) {
			this.emergencyContactName = emergencyContactName;
			return this;
		}

		public EmployeeBuilder emergencyContactRelationship(String emergencyContactRelationship) {
			this.emergencyContactRelationship = emergencyContactRelationship;
			return this;
		}

		public EmployeeBuilder emergencyContactPhone(String emergencyContactPhone) {
			this.emergencyContactPhone = emergencyContactPhone;
			return this;
		}

		public EmployeeBuilder emergencyContactAddress(String emergencyContactAddress) {
			this.emergencyContactAddress = emergencyContactAddress;
			return this;
		}

		public EmployeeBuilder skills(String skills) {
			this.skills = skills;
			return this;
		}

		public EmployeeBuilder certifications(String certifications) {
			this.certifications = certifications;
			return this;
		}

		public EmployeeBuilder hobbies(String hobbies) {
			this.hobbies = hobbies;
			return this;
		}

		public EmployeeBuilder languagesKnown(String languagesKnown) {
			this.languagesKnown = languagesKnown;
			return this;
		}

		public EmployeeBuilder totalExperienceYears(Integer totalExperienceYears) {
			this.totalExperienceYears = totalExperienceYears;
			return this;
		}

		public EmployeeBuilder previousEmployers(String previousEmployers) {
			this.previousEmployers = previousEmployers;
			return this;
		}

		public Employee build() {
			return new Employee(id, employeeCode, firstName, lastName, dateOfBirth, gender, nationality,
							  maritalStatus, bloodGroup, personalEmail, officialEmail, personalPhone, officialPhone,
							  currentAddress, permanentAddress, city, state, country, pincode, department,
							  designation, reportingManager, workLocation, dateOfJoining, dateOfConfirmation,
							  dateOfResignation, employmentStatus, employmentType, panNumber, aadharNumber,
							  passportNumber, drivingLicenseNumber, taxIdentificationNumber, socialSecurityNumber,
							  bankName, bankAccountNumber, ifscCode, uanNumber, esicNumber, salaryStructureId,
							  emergencyContactName, emergencyContactRelationship, emergencyContactPhone,
							  emergencyContactAddress, skills, certifications, hobbies, languagesKnown,
							  totalExperienceYears, previousEmployers);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return Objects.equals(id, employee.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", employeeCode='" + employeeCode + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", department='" + department + '\'' +
				", designation='" + designation + '\'' +
				'}';
	}
}


