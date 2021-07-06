package com.cognizant.bankmvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cognizant.bankmvc.exception.CustomerNotFoundException;
import com.cognizant.bankmvc.feign.AccountFeign;
import com.cognizant.bankmvc.feign.AuthenticationFeign;
import com.cognizant.bankmvc.feign.CustomerFeign;
import com.cognizant.bankmvc.feign.TransactionFeign;
import com.cognizant.bankmvc.model.Account;
import com.cognizant.bankmvc.model.AccountInput;
import com.cognizant.bankmvc.model.AppUser;
import com.cognizant.bankmvc.model.CustomerEntity;
import com.cognizant.bankmvc.model.Transaction;
import com.cognizant.bankmvc.model.TransactionInput;

@Controller
public class MvcController {

	HttpSession session;

	@Autowired
	private AuthenticationFeign authFeign;

	@Autowired
	private CustomerFeign customerFeign;

	@Autowired
	private AccountFeign accountFeign;

	@Autowired
	private TransactionFeign transactionFeign;
	

	/*
	 * Display home page
	 */
	@RequestMapping("/")
	public ModelAndView home(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		session.invalidate();
//		try {
//			request.getSession(false);
//			String role = (String) session.getAttribute("role");
//			if (role.equalsIgnoreCase("EMPLOYEE")) {
//				return new ModelAndView("redirect:/dashboard");
//			} else {
//				return new ModelAndView("redirect:/customerdashboard");
//			}
//		} catch (Exception e) {
			return new ModelAndView("home");
//		}
	}

	
	/*
	 * Display employee login page
	 */
	@GetMapping("/employeelogin")
	public ModelAndView employeeLogin(@RequestParam(defaultValue = "") String msg, Model model) {
		model.addAttribute("role", "EMPLOYEE");
		model.addAttribute("msg", msg);
		model.addAttribute("login", new AppUser());
		return new ModelAndView("login");
	}

	
	/*
	 * Display customer login page
	 */
	@GetMapping("/customerlogin")
	public ModelAndView customerLogin(@RequestParam(defaultValue = "") String msg, Model model) {
		model.addAttribute("role", "CUSTOMER");
		model.addAttribute("msg", msg);
		model.addAttribute("login", new AppUser());
		return new ModelAndView("login");
	}

	
	/*
	 * Login user
	 */
	@PostMapping("/login")
	public ModelAndView login(@ModelAttribute("login") AppUser user, HttpServletRequest request, Model model) throws CustomerNotFoundException {
		session = request.getSession();
		String token = "Bearer ";
		
		AppUser loginUser = null;
		try {
			loginUser = authFeign.login(user);
		} catch (Exception ex) {
			String dir = "";
			if (user.getRole().equalsIgnoreCase("EMPLOYEE")) {
				dir = "employeelogin";
			} else {
				dir = "customerlogin";
			}
			return new ModelAndView("redirect:/" + dir + "?msg=Invalid Credentials");
		}
		token += loginUser.getAuthToken();
		session.setAttribute("userId", loginUser.getUserid());
		session.setAttribute("token", token);
		if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
			CustomerEntity customer = customerFeign.getCustomerDetails(token, loginUser.getUserid());
			model.addAttribute("customer", customer);
			model.addAttribute("accountinput", new AccountInput());
			return new ModelAndView("redirect:/customerdashboard");
		} else {
			String role = authFeign.getRole(user.getUserid());

			if (user.getRole().equalsIgnoreCase(role)) {
				return new ModelAndView("redirect:/dashboard");
			}

		}
		model.addAttribute("msg", "Invalid Credentials");
		return new ModelAndView("login");
	}


	/*
	 * Logout user
	 */
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		try {
			request.getSession(false);
			session.removeAttribute("token");
			session.removeAttribute("userId");
			session.invalidate();
			return new ModelAndView("home");
		} catch (Exception e) {
			return new ModelAndView("home");
		}
	}

	
																		/**
																		 * EMPLOYEE
																		 * FUNCTIONALITIES
																		 */
	
	/*
	 * display employee dashboard
	 */
	@GetMapping("/dashboard")
	public ModelAndView showdashboard(@RequestParam(defaultValue = "", name = "msg") String msg,
			@RequestParam(defaultValue = "", name = "custmsg") String custmsg,
			@RequestParam(defaultValue = "", name = "accmsg") String accmsg,
			@RequestParam(defaultValue = "", name = "deletemsg") String deletemsg,
			@RequestParam(defaultValue = "", name = "viewmsg") String viewmsg,
			@RequestParam(defaultValue = "", name = "servicemsg") String servicemsg, Model model) {

		if (session == null) {
			return new ModelAndView("redirect:/");
		}

		model.addAttribute("custmsg", custmsg);
		model.addAttribute("viewmsg", viewmsg);
		model.addAttribute("accmsg", accmsg);
		model.addAttribute("deletemsg", deletemsg);
		model.addAttribute("servicemsg", servicemsg);
		model.addAttribute("msg", msg);
		return new ModelAndView("dashboard");
	}
	
	
	/*
	 * Show create customer form page
	 */
	@GetMapping("/createCustomer")
	public ModelAndView createCustomerPage(HttpServletRequest request, Model model, @ModelAttribute("customer") CustomerEntity customer) {
		try {
			request.getSession(false);
			model.addAttribute("role", "EMPLOYEE");
			return new ModelAndView("createcustomer");
		} catch (Exception e) {
			return new ModelAndView("home");
		}
	}
	
	
	/*
	 * Create customer
	 */
	@PostMapping("/createCustomer")
	public ModelAndView createCustomer(@Valid @ModelAttribute("customer") CustomerEntity customer,  BindingResult result, Model model,
			RedirectAttributes redirectAttributes){

		try {
			model.addAttribute("role", "EMPLOYEE");
			String token = (String) session.getAttribute("token");
			
			if (result.hasErrors())
				return new ModelAndView("createcustomer");
			
			customerFeign.saveCustomer(token, customer);
			model.addAttribute("role", "EMPLOYEE");
			model.addAttribute("customerId", customer.getUserid());
			model.addAttribute("account", new Account());
			return new ModelAndView("createaccount");
		} catch (Exception ex) {

			return new ModelAndView("redirect:/logout");
		}
	}

	
	/*
	 * Show create account form page
	 */
	@PostMapping("/createAccount")
	public ModelAndView showCreateAccountPage(HttpServletRequest request, Model model, @ModelAttribute("account") Account account, RedirectAttributes redirectAttributes) {
		try {
			String custid = request.getParameter("customerId");
			String token = (String) session.getAttribute("token");
			customerFeign.getCustomerDetails(token, custid);
			model.addAttribute("role", "EMPLOYEE");
			model.addAttribute("customerId", custid);
//			model.addAttribute("account", new Account());
			return new ModelAndView("createaccount");
		} catch (Exception e) {
			return new ModelAndView("redirect:/dashboard?accmsg=Invalid CustomerID");
		}
	}

	/*
	 * Create account
	 */
	@PostMapping("/finishedAccountCreation")
	public ModelAndView createAccount(@Valid @ModelAttribute("account") Account account,  BindingResult result, Model model) {
		model.addAttribute("role", "EMPLOYEE");
		CustomerEntity customer = null;
		try {
			String token = (String) session.getAttribute("token");
			customer = customerFeign.getCustomerDetails(token, account.getCustomerId());
		} catch (Exception ex) {
			return new ModelAndView("redirect:/createAccount?msg=Invalid CustomerID");
		}
		
		List<Account> list = new ArrayList<Account>();
		list.add(account);
		customer.setAccounts(list);
		try {
			String token = (String) session.getAttribute("token");
	
			if (result.hasErrors())
				return new ModelAndView("createaccount");
			
			accountFeign.createAccount(token, account.getCustomerId(), account);
		} catch (Exception ex) {
			return new ModelAndView("redirect:/createCustomer?accmsg=Account Not created");
		}
		return new ModelAndView("redirect:/dashboard?accmsg=Account Created Successfully");
	}

	
	/*
	 * view customer from employee dashboard
	 */
	@GetMapping("/viewCustomer")
	public ModelAndView viewCustomer(HttpServletRequest request, Model model) {
		model.addAttribute("role", "EMPLOYEE");
		try {
			String id = request.getParameter("userId");
			String token = (String) session.getAttribute("token");
			CustomerEntity customer = customerFeign.getCustomerDetails(token, id);
			model.addAttribute("customer", customer);
			return new ModelAndView("viewcustomer");
		} catch (Exception ex) {
			return new ModelAndView("redirect:/dashboard?viewmsg=Invalid CustomerID");
		}
	}
	
	
	/*
	 * deposit money from employee dashboard
	 */
	@PostMapping("/deposit")
	public ModelAndView deposit(HttpServletRequest request) {
		long accountId = Long.parseLong(request.getParameter("accountId"));
		double amount = Double.parseDouble(request.getParameter("amount"));
		
		AccountInput input = new AccountInput(accountId, amount);

		try {
			String token = (String) session.getAttribute("token");
			accountFeign.deposit(token, input);
			return new ModelAndView("redirect:/dashboard?msg=Amount Deposited to the Account");
		} catch (Exception ex) {

			return new ModelAndView("redirect:/dashboard?msg=Provide correct Details");
		}

	}

	
	/*
	 * delete customer from employee dashboard
	 */
	@PostMapping("/deleteCustomer")
	public ModelAndView deleteCustomer(HttpServletRequest request, Model model) {
		try {
			String token = (String) session.getAttribute("token");
			String customerId = (String) request.getParameter("customerId");
			customerFeign.deleteCustomer(token, customerId);
			return new ModelAndView("redirect:/dashboard?deletemsg=Customer Deleted Successfully");
		} catch (Exception e) {
			return new ModelAndView("redirect:/dashboard?deletemsg=Not Deleted..Wrong Customer ID");
		}
	}

	
																	/**
																	 * CUSTOMER
																	 * FUNCTIONALITIES
																	 */
	
	/*
	 * display customer dashboard
	 */
	
	@GetMapping("/customerdashboard")
	public ModelAndView showcustomerdashboard(@RequestParam(defaultValue = "", name = "msg") String msg,
			@RequestParam(defaultValue = "", name = "transfermsg") String transfermsg, Model model) {
		if (session == null) {
			return new ModelAndView("redirect:/403");
		}
		model.addAttribute("msg", msg);
		model.addAttribute("transfermsg", transfermsg);
		String token = (String) session.getAttribute("token");
		String userId = (String) session.getAttribute("userId");
		CustomerEntity customer = customerFeign.getCustomerDetails(token, userId);
		model.addAttribute("customer", customer);
		model.addAttribute("accountinput", new AccountInput());
		return new ModelAndView("customerdashboard");
	}

	
	/*
	 * withdraw from customer dashboard
	 */
	@PostMapping("/withdraw")
	public ModelAndView withdraw(HttpServletRequest request) {
		long accountId = Long.parseLong(request.getParameter("accountId"));
		double amount = Double.parseDouble(request.getParameter("amount"));
		AccountInput input = new AccountInput(accountId, amount);
		try {
			String token = (String) session.getAttribute("token");
			accountFeign.withdraw(token, input);
			return new ModelAndView("redirect:/customerdashboard?msg=Amount Withdrawn from the Account");
		} catch (Exception ex) {

			return new ModelAndView("redirect:/customerdashboard?msg=Provide correct Details");
		}
	}

	
	/*
	 * transfer from customer dashboard
	 */
	@PostMapping("/transfer")
	public ModelAndView transaction(HttpServletRequest request) {
		long sourceAccountId = Long.parseLong(request.getParameter("accountId"));
		long targetAccountId = Long.parseLong(request.getParameter("targetAccount"));
		String reference = (request.getParameter("reference"));

		double amount = Double.parseDouble(request.getParameter("amount"));
		AccountInput sourceInput = new AccountInput(sourceAccountId, amount);
		AccountInput targetInput = new AccountInput(targetAccountId, amount);
		TransactionInput transaction = new TransactionInput();
		transaction.setSourceAccount(sourceInput);
		transaction.setTargetAccount(targetInput);
		transaction.setReference(reference);
		transaction.setAmount(amount);

		try {
			String token = (String) session.getAttribute("token");
			accountFeign.transaction(token, transaction);
			return new ModelAndView("redirect:/customerdashboard?transfermsg=Amount has been transferred");
		} catch (Exception ex) {
			System.out.println(ex);
			return new ModelAndView("redirect:/customerdashboard");
		}
	}

	
	/*
	 * display transactions list in customer dashboard
	 */
	@PostMapping("/customerdashboard/showtransactions")
	public ModelAndView showTransactionsInCustomerdashboard(HttpServletRequest request, Model model) {

		if (session == null) {
			return new ModelAndView("redirect:/403");
		}

		String token = (String) session.getAttribute("token");
		String userId = (String) session.getAttribute("userId");
		CustomerEntity customer = customerFeign.getCustomerDetails(token, userId);
		System.out.println(customer);
		model.addAttribute("customer", customer);
		model.addAttribute("accountinput", new AccountInput());
		long accountId = Long.parseLong(request.getParameter("accountId"));
		List<Transaction> transactions = transactionFeign.getTransactionsByAccId(token, accountId);
		model.addAttribute("transactions", transactions);
		return new ModelAndView("customerdashboard");
	}


	
	/*
	 * error page
	 */
	@GetMapping("/403")
	public ModelAndView errorpage(Model model) {
		return new ModelAndView("403");
	}
}
