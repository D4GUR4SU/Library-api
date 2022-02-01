package com.dagurasu.libraryapi.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dagurasu.libraryapi.api.model.entity.Loan;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

	private static final String CRON_LATE_LOANS = "0 0 0 1/1 * ?";
	
	@Value("${application.mail.late.loans.message}")
	private String mensagem;
	
	private final LoanService loanService;
	private final EmailService emailService;
	
	@Scheduled(cron = CRON_LATE_LOANS)
	public void sendMailToLateLoans() {
		
		List<Loan> allLateLoans = loanService.getAllLateLoans();
		List<String> mailList = allLateLoans
				.stream()
				.map(loan -> loan.getCustomerEmail())
				.collect(Collectors.toList());
		
		
		emailService.sendEmails(mensagem, mailList);
	}
}
