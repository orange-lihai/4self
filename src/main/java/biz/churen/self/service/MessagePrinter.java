package biz.churen.self.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lihai5 on 2017/5/31.
 */
// @Component
public class MessagePrinter {
  final private MessageService service;

  // @Autowired
  public MessagePrinter(MessageService service) {
    this.service = service;
  }

  public void printMessage() {
    System.out.println(this.service.getMessage());
  }
}
