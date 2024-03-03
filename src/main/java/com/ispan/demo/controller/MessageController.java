package com.ispan.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ispan.demo.model.Messages;
import com.ispan.demo.service.MessageService;

@Controller
public class MessageController {
	
	@Autowired
	private MessageService mService;
	
	@GetMapping("/message/add")
	public String addPage(Model model) {
		// ModelAndView
		Messages lastestMsg = mService.findLatest();
		
		model.addAttribute("lastestMsg", lastestMsg);
		
		return "message/addMsgPage";
	}
	
	@PostMapping("/message/add")
	public String postAddMessage(@RequestParam("text") String text,Model model) {
		Messages m1 = new Messages();
		m1.setText(text);
		
		mService.insert(m1);
		
        Messages lastestMsg = mService.findLatest();
		
		model.addAttribute("lastestMsg", lastestMsg);
		
		return "message/addMsgPage";
	}
//@GetMapping("/message/page")
//public String showMessages(@RequestParam(name="p",defaultValue="1") Integer pageNumber,Model model) {
//	Page<Messages> page=mService.findByPage(pageNumber);
//	model.addAttribute("page",page);
//	return "message/showMessages";
//}
	@GetMapping("/message/page")
    @PreAuthorize("isAuthenticated()") // 只有已经登录的用户才能访问
    public String showMessages(@RequestParam(name="p",defaultValue="1") Integer pageNumber,Model model) {
        Page<Messages> page=mService.findByPage(pageNumber);
        model.addAttribute("page",page);
        return "message/showMessages";
    }
@DeleteMapping("/message/delete")
public String deleteMsg(@RequestParam Integer id) {
	mService.deleteById(id);
	return "redirect:/message/page";
}

@GetMapping("/message/edit")
public String editPage(@RequestParam("id") Integer id,Model model) {
	Messages msg = mService.findById(id);
	
	model.addAttribute("messages", msg);
	
	return "message/editMessagePage";
}

@PutMapping("/message/edit")
public String sendEditMsg(@ModelAttribute("messages") Messages msg) {
	mService.insert(msg);
	return "redirect:/message/page";
}

}