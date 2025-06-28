package org.ruby.userauthservive.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class SendEmailDto {
    private String toEmail;
    private String subject;
    private String body;

}
