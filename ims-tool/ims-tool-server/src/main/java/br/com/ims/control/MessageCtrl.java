package br.com.ims.control;

import java.util.List;

import br.com.ims.persistence.MessageDao;
import br.com.ims.tool.entity.Message;

public class MessageCtrl {
	
	public static List<Message> findAll(){
		return MessageDao.findAll();
	}
}
