package com.dagurasu.libraryapi.api.service;

import java.util.List;

public interface EmailService {

	void sendEmails(String mensagem, List<String> mailList);

}
