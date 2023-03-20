package policy;


public class HealthPolicy {

	private short basicCost, dependantsTotalCost = 0, roomTypeCost = 0, 
			inpatientCost = 0, extraCareTotalCost, extraCareCost = 50, 
			policyTotalCostBeforeVAT; // +80 if age > 50 // 0 < 18
	private float VATRate = 0.21f, VATCost, policyTotalCostAfterVAT;
	private byte age, dependantNumber, dependantAge, extraCareAmmount; //dependant 1=250 2=150 3=100 better of making dependant class 4=50 5> free up to 6
	private String dependantName, userName, extraCareChoices; // dep name < 18
	private char inpatient, roomType, extraCareInput, extraCareChoiceType;
	private boolean userInput; // Boolean control to check user inputs 
	private Dependant[] arr;
	
	public HealthPolicy () {
		basicCost = 426;
	}
	
	byte getAge () {
		return age;
	}
	
	byte getDependantNumber() {
		return dependantNumber;
	}
	
	public void readAge() {
		userInput = false;
		
		while (userInput != true) {
			try {
				System.out.print("Please, Enter Your Age: ");
				age = SkillsDemo2Main.reader.nextByte();
				
				if(age < 0) {
					throw new Exception();
				} else {
					userInput = true;	
				}
			} catch (Exception e) {
				System.out.println("Please, Enter Your Age In Positive Numbers!\n");
				SkillsDemo2Main.reader.nextLine();
				userInput = false;
			}
		}
		
		if (age > 50) {
			this.basicCost = (short) (this.basicCost + 80);
		}
		SkillsDemo2Main.reader.nextLine();
	}
	
	public void readName () {
		System.out.print("Please, Enter Your Name: ");
		userName = SkillsDemo2Main.reader.nextLine();
		
		
	}
	
	public void readNumDependents () {
		userInput = false;
		
		while (userInput != true) {
			try {
				System.out.print("\nHow Many Dependants Would You Like to Add On The Same Policy?(6 MAX): ");
				dependantNumber = SkillsDemo2Main.reader.nextByte();
				
				if(dependantNumber > 6) {
					throw new Exception();
				} else {
					userInput = true;	
				}
				
				
			} catch (Exception e) {
				System.out.println("Please, Enter Only Positive Numbers Up To 6 Dependants!\n");
				SkillsDemo2Main.reader.nextLine();
				userInput = false;
			}
		}
		SkillsDemo2Main.reader.nextLine();
		
	}
	
	public void createDependants() {
		
		arr = new Dependant[dependantNumber];
		
		for (int i = 0; i < dependantNumber; i ++) {
			System.out.print("\nName Of Dependant Number " + (i+1) + ": ");
			dependantName = SkillsDemo2Main.reader.nextLine();
			
			userInput = false;
			
			while (userInput != true) {
				try {
					System.out.print("Age Of Dependant Number " + (i+1) + ": ");
					dependantAge = SkillsDemo2Main.reader.nextByte();
					
					
					if(dependantAge < 0 || dependantAge >= 18) {
						throw new Exception();
					} else {
						userInput = true;	
					}
				} catch (Exception e) {
					System.out.println("\nPlease, Dependant Age Must Be a Positive Number And Under 18!\n");
					SkillsDemo2Main.reader.nextLine();
					userInput = false;
				}
			}
			
			switch ((i+1)) {
			case 1:
				arr[i] = new Dependant(dependantName, dependantAge, (short)250);
				break;
			case 2:
				arr[i] = new Dependant(dependantName, dependantAge, (short)150);
				break;
			case 3: 
				arr[i] = new Dependant(dependantName, dependantAge, (short)100);
				break;
			case 4:
				arr[i] = new Dependant(dependantName, dependantAge, (short)50);
				break;
			case 5,6:
				arr[i] = new Dependant(dependantName, dependantAge, (short)0);
				break;
			}
			SkillsDemo2Main.reader.nextLine();
		}
		
		for(int j = 0; j < arr.length; j++) {
			dependantsTotalCost = (short) (dependantsTotalCost + arr[j].getDependantCost());
		}
		
	}
	
	public void roomType() {
		userInput = false;
		while(userInput != true) {
			try {
				System.out.print("\nWould You Like To Add Inpatient Care? (Y/N): ");
				inpatient = SkillsDemo2Main.reader.next().toLowerCase().charAt(0);
				if(inpatient == 'y') {
					inpatientCost = 200;
					
					//while
					while(userInput !=true) {
						try {
							System.out.print("\nAdding Inpatient Care Grants You With A Semi-Private Room.\nWould You Like To Change To A Private Room? (Y/N): ");
							roomType = SkillsDemo2Main.reader.next().toLowerCase().charAt(0);
							if(roomType == 'y') {
								roomTypeCost = 100;
								userInput = true;
							} else if(roomType == 'n'){
								userInput = true;
							} else {
								throw new Exception("Please, Enter Only Y (YES) To Change To a Private Room Or N (NO).");
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
							userInput = false;
						}
					}
				}
				else if(inpatient == 'n') {
					userInput = true;
				}
				else {
					throw new Exception("Please, Enter Only Y for YES or N to NO\n");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				userInput = false;
			}
		}
	
	}

	public void extraCares() {
		userInput = false;
		while(userInput != true) {
			try {
				System.out.println("\nWe Also Provide The Following Extra Cares:");
				System.out.println("A) Orthopaedic care\nB) Ophthalmic care\nC) Maternity care\nD) Fertility care\nE) Psychiatric care");
				System.out.print("Would You Like To Add Any Additional Extra Care? (Y/N): ");
				extraCareInput = SkillsDemo2Main.reader.next().toLowerCase().charAt(0);
				
				if(extraCareInput == 'y') {
					while(userInput != true) {
						try {
							System.out.print("\nYou Can Choose Up To Two Extra Cares. How Many Extra Cares Would You Like To Add? (1 or 2): ");
							extraCareAmmount = SkillsDemo2Main.reader.nextByte();
							
							switch(extraCareAmmount) {
							case 1:
								System.out.println("\nPlease, Choose 1 Of The Following:");
								System.out.print("A) Orthopaedic care\nB) Ophthalmic care\nC) Maternity care\nD) Fertility care\nE) Psychiatric care\n");
								userInput = false;
								while(userInput != true) {
									try {
										System.out.print("Enter Extra Care Choice: ");
										extraCareChoiceType = SkillsDemo2Main.reader.next().toLowerCase().charAt(0);
										
										
										if(	extraCareChoiceType != 'a' &&
											extraCareChoiceType != 'b' &&
											extraCareChoiceType != 'c' &&
											extraCareChoiceType != 'd' &&
											extraCareChoiceType != 'e') {
											throw new Exception("Please, Choose One Of The Letters A, B, C, D or E.");
										} else{
											extraCareChoices = extraCareChoices + extraCareChoiceType;
											extraCareTotalCost = (short)(extraCareTotalCost + 50);
											userInput = true;
										}
									} catch (Exception e) {
										System.out.println(e.getMessage());
										userInput = false;
									}
								};
								break;
							case 2:
								System.out.println("\nPlease, Choose 2 Of The Following:");
								System.out.print("A) Orthopaedic care\nB) Ophthalmic care\nC) Maternity care\nD) Fertility care\nE) Psychiatric care\n");
								char prevChoice = 'x';
								for(int i = 0; i < extraCareAmmount; i++) {
									userInput = false;
									while(userInput != true) {
										try {
											System.out.print("Enter Extra Care Choice " + (i+1) + ": ");
											extraCareChoiceType = SkillsDemo2Main.reader.next().toLowerCase().charAt(0);
											if(	extraCareChoiceType != 'a' &&
												extraCareChoiceType != 'b' &&
												extraCareChoiceType != 'c' &&
												extraCareChoiceType != 'd' &&
												extraCareChoiceType != 'e') {
												throw new Exception("Please, Choose One Of The Letters A, B, C, D or E.\n");
											} else if(extraCareChoiceType != prevChoice) {
												extraCareChoices = extraCareChoices + extraCareChoiceType;
												extraCareTotalCost = (short)(extraCareTotalCost + 50);
												prevChoice = extraCareChoiceType;
												userInput = true;
											} else {
												throw new Exception("You Already Selected This Extra Care. Please, Choose A Different One.");
											}
										} catch (Exception e) {
											System.out.println(e.getMessage());
											userInput = false;
										}
									}
								}
								break;
							default:
								throw new Exception();
							}
						} catch (Exception e) {
							System.out.println("Please, Enter 1 or 2 Choices.");
							SkillsDemo2Main.reader.nextLine();
						}
					}
				} else if(extraCareInput == 'n') {
					extraCareAmmount = 0;
					extraCareTotalCost = 0;
					userInput = true;
				} else {
					throw new Exception();
				}
				
			} catch (Exception e) {
				System.out.println("Please, Enter Only Y (yes) or N (no).");
			}
		}
	}
	
	public short getTotalCostBeforeVAT() {
		policyTotalCostBeforeVAT = (short)(basicCost + dependantsTotalCost + inpatientCost + roomTypeCost + extraCareTotalCost);
		this.VATCost = policyTotalCostBeforeVAT * VATRate;
		return policyTotalCostBeforeVAT;
		
	}
	
	public float getTotalCostAfterVAT() {
		policyTotalCostAfterVAT = policyTotalCostBeforeVAT + VATCost ;
		return policyTotalCostAfterVAT;
		
	}
	
	void printPolicyDetails () {
		System.out.printf("\n\n%65s\n\n\n", "HCI Healthcare Policy");
		System.out.printf("%-40s%s\n\n", "Name", this.userName);
		System.out.printf("%-40s%s\n", "Number of dependants", this.dependantNumber);
		//for loop for dependants
		if(arr != null) {
			System.out.printf("\n%-40s%-28s%-7s%s\n\n", "", "NAME OF DEPENDANTS", "AGE", "COST");
			for(int i=0; i < arr.length; i++) {
				arr[i].displayData();
			}
		}
		System.out.printf("\n%-75s€%s\n\n", "Basic cost (outpatient care):", this.basicCost);
		
		if(inpatient == 'y') {
			System.out.printf("%-40s%-35s€%s\n\n", "Inpatient Cost:","YES",this.inpatientCost);
		} else {
			System.out.printf("%-40s%-35s€%s\n\n", "Inpatient Cost:","NO", this.inpatientCost);
		}
		
		if(roomType == 'y') {
			System.out.printf("%-40s%-35s€%s\n\n", "Room Type:","Private",this.roomTypeCost);
		} else if(roomType == 'n') {
			System.out.printf("%-40s%-35s€%s\n\n", "Room Type:","Semi-Private",this.roomTypeCost);
		} else {
			System.out.printf("%-40s%-35s€%s\n\n", "Room Type:","NA",this.roomTypeCost);
		}
		
		
		if(extraCareChoices != null) {
			System.out.printf("%-40s%-35s%s\n\n", "Additional Extras:","EXTRA CARE TYPE","COST");
			for(int i = 0; i < extraCareChoices.length(); i++) {
				switch(extraCareChoices.charAt(i)) {
				case 'a':
					System.out.printf("%-40s%-35s€%s\n", "","Orthopaedic care",this.extraCareCost);
					break;
				case 'b':
					System.out.printf("%-40s%-35s€%s\n", "","Ophthalmic care",this.extraCareCost);
					break;
				case 'c':
					System.out.printf("%-40s%-35s€%s\n", "","Maternity care",this.extraCareCost);
					break;
				case 'd':
					System.out.printf("%-40s%-35s€%s\n", "","Fertility care",this.extraCareCost);
					break;
				case 'e':
					System.out.printf("%-40s%-35s€%s\n", "","Psychiatric care",this.extraCareCost);
					break;
				
				}
			}
		}else {
			System.out.printf("%-40s%-35s\n", "Additional Extras:","NONE");
		}
		
		System.out.printf("\n%-75s€%s\n\n", "Total before VAT:", getTotalCostBeforeVAT());
		System.out.printf("%-75s€%.2f\n\n", "VAT:",this.VATCost);
		System.out.printf("%-75s€%.2f\n\n", "Total:", getTotalCostAfterVAT());
	}
}