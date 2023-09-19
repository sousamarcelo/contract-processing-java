package model.services;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	
	
	private OnlinePaymentService paymentService;

	public ContractService(OnlinePaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	public void processContract(Contract contract, Integer months) {
		double originalInstallment = contract.getTotalValue() /  months;
		Double InterestOriginalInstallment = null;
		Double TaxInterestOriginalInstallment = null;
			
		
				
		for (int i = 1; i <= months; i++) {
			LocalDate dueDate = contract.getDate().plusMonths(i);
			InterestOriginalInstallment = paymentService.interest(originalInstallment, i);
			TaxInterestOriginalInstallment = paymentService.paymentFee(InterestOriginalInstallment + originalInstallment);
			double totalOriginalInstallment = originalInstallment + InterestOriginalInstallment + TaxInterestOriginalInstallment;
					
			contract.getInstallments().add(new Installment(dueDate, totalOriginalInstallment));
			
			
		}
	}
	
	

}
