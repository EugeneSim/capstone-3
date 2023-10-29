package com.example.demooauth;

import java.security.Principal; 
import java.time.LocalDate; 
import java.util.Collections; 
import java.util.List; 
import java.util.stream.Collectors; 
 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model; 
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
 
import jakarta.validation.Valid; 
 
@Controller 
 
public class MyController { 
    private static final Logger logger = LoggerFactory 
    .getLogger(MyController.class); 
 
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
 
    @Autowired 
    private UserRepository userRepository; 
    @Autowired 
    private RoleRepository roleRepository; 
    @Autowired 
    public Studentrepo repo; 
 
 
 
    // show the staff_email, name, role list 
    @RequestMapping("/") 
    public String showStaffMain(Users users, Model model, Principal principal) { 
        List<Users> usersList = (List<Users>) userRepository.findAll(); 
        System.out.println(principal.getName()); 
        model.addAttribute("usersList", usersList); 
        model.addAttribute("username", principal.getName()); 
        model.addAttribute("principal", principal); 
        return "index"; 
    } 
 
    @RequestMapping("/new") 
    public String addUser(Users users, Model model) { 
        List<Role> listRoles = (List<Role>) roleRepository.findAll(); 
        model.addAttribute("listRoles", listRoles); 
        model.addAttribute("users", users); 
        return "adduser"; 
    } 
 
    // once press the submit button for teller form 
    @RequestMapping("/save") 
    public String saveUser(@Valid Users users,BindingResult bindingResult) { 
        if (bindingResult.hasErrors()) { 
   logger.info("Returning index.jsp page"); 
            System.out.println(bindingResult.getAllErrors()); 
            return "new"; 
   // return "redirect:/new"; 
  } 
        users.setPassword(passwordEncoder.encode(users.getPassword())); 
        users.setUserRoles(users.getUserRoles()); 
        userRepository.save(users); 
        return "redirect:/"; 
    } 
    // delete staff 
    @RequestMapping("/delete/{id}") 
    public String delUser(@PathVariable("id") Long id, Model model) { 
        userRepository.deleteById(id); 
        return "redirect:/"; 
    } 
 
 
 @GetMapping("/form") 
 public String showForm(Users users, Model model) { 
         
  Student student = new Student(null, null, null, null, null, null, null,0.0,0, null); 
  model.addAttribute("student", student); 
  return "form"; 
 } 
 
 @PostMapping("/form/result")  
 public String submitForm(@Valid Student student, BindingResult bindingResult, Model model) { 
  if (bindingResult.hasErrors()) { 
    
   System.out.println(bindingResult); 
   return "form"; 
  } else { 
    
   try {   
    repo.save(new Student(student.getName(), student.getNric(), student.getphoneNumber(), 
                student.getEmail(), student.getBirthDate(), student.getacctPwd(), student.getuserId(),0.0,0,"open")); 
    return "/Custlogin"; 
   } catch (Exception e) { 
    // bindingResult.rejectValue("username", "error.student", "Username already exists"); 
    return "form"; 
   } 
  } 
    } 

    @GetMapping("/result") 
    public String showLogins(Model model) { 
     return "result"; 
    }   
 
 @GetMapping("/Custlogin") 
 public String showLogin(Model model) { 
  return "Custlogin"; 
 }
@PostMapping("/checklogin") 
 public String checkLogin( 
   @RequestParam String userId, 
   @RequestParam String password, 
   RedirectAttributes redirectAttributes) { 
  // Search for a user in the database with the given username 
  Student student = repo.findByUserId(userId); 
 
  if (student != null && student.getacctPwd().equals(password)) { 
   // Successful login 
   // You may want to add the user to the session or perform other actions 
             
   return "redirect:/cust?userId=" + userId; // Redirect to a success page 
             
  } else { 
   // Failed login 
   // You can add an error message to be displayed on the login page 
   redirectAttributes.addFlashAttribute("loginError", "Invalid username or password"); 
   return "redirect:/Custlogin"; // Redirect back to the login page 
  } 
 } 
 
 @GetMapping("/back") 
 public String backHome() { 
  return "redirect:/form"; 
 } 
 
    @Autowired 
    private Transactionrepo tr; 
 
    @GetMapping("/cust") 
    public String showCust(Model model, @RequestParam String userId){ 
        
        Student student = repo.findByUserId(userId); 
        if(student != null) { 
            model.addAttribute("student", student); 
            List<Transaction> transactions = tr.findByStudent_AcctNumOrderByCurrdateDesc(student.getAcctNum()); 
            Collections.reverse(transactions); 
            List<Transaction> latestTransactions = transactions.stream().limit(3).collect(Collectors.toList()); 
            model.addAttribute("transactions", latestTransactions); 
        } else { 
            model.addAttribute("error", "No customer found."); 
        } 
        return "custmain"; 
    } 
 
    @GetMapping("/getTransactionHistory") 
    public ResponseEntity<List<Transaction>> getTransactionHistory(@RequestParam String userId) { 
        Student student = repo.findByUserId(userId); 
 
        if (student != null) { 
            // Assuming you have a method in your repository to fetch transactions by customer 
            List<Transaction> transactionHistory = tr.findByStudent_AcctNumOrderByCurrdateDesc(student.getAcctNum()); 
 
            // Return the transaction history as a JSON response 
            return ResponseEntity.ok(transactionHistory); 
        } else { 
            // If customer not found, return a 404 Not Found status 
            return ResponseEntity.notFound().build(); 
        } 
    } 
 
 
    @PostMapping(value = "/deposit", consumes = "application/json") 
    public ResponseEntity<Object> depositMoney(@RequestBody DepositRequest request) { 
        int acctNum = request.getAcctNum(); 
        Student student = repo.findById(acctNum).orElse(null); 
     
        if (student != null) { 
            double depositAmount = request.getDepositAmount(); 
            student.setBalance(student.getBalance() + depositAmount); 
            repo.save(student); 
             
            Transaction transaction = new Transaction(); 
            transaction.setAmount(depositAmount); 
            transaction.setAcctOpen(true); 
            transaction.setActionPerformed("Deposit"); 
            transaction.setBalanceBefore(student.getBalance() - depositAmount); 
            transaction.setBalanceAfter(student.getBalance()); 
            transaction.setCurrdate(LocalDate.now()); 
            transaction.setStudent(student); 
         
            tr.save(transaction); 
 
            DepositResponse response = new DepositResponse("Deposit successful", student.getBalance()); 
            return ResponseEntity.ok(response); 
        } else { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found."); 
        } 
    } 
 
    @PostMapping(value = "/withdraw", consumes = "application/json") 
    public ResponseEntity<Object> withdrawMoney(@RequestBody WithdrawRequest request) { 
    int acctNum = request.getAcctNum(); 
    Student student = repo.findById(acctNum).orElse(null); 
 
    if (student != null) { 
        double withdrawAmount = request.getWithdrawAmount();
if (withdrawAmount > 0 && withdrawAmount <= student.getBalance()) { 
            student.setBalance(student.getBalance() - withdrawAmount); 
            repo.save(student); 
 
            Transaction transaction = new Transaction(); 
            transaction.setAmount(withdrawAmount); 
            transaction.setAcctOpen(true); 
            transaction.setActionPerformed("Withdraw"); 
            transaction.setBalanceBefore(student.getBalance() + withdrawAmount); 
            transaction.setBalanceAfter(student.getBalance()); 
            transaction.setCurrdate(LocalDate.now()); 
            transaction.setStudent(student); 
         
            tr.save(transaction); 
             
            WithdrawResponse response = new WithdrawResponse("Withdrawal successful", student.getBalance()); 
            return ResponseEntity.ok(response); 
        } else { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid withdrawal amount."); 
        } 
    } else { 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found."); 
    } 
} 
    @PostMapping(value = "/transfer", consumes = "application/json") 
    public ResponseEntity<Object> transferMoney(@RequestBody TransferRequest request) { 
    int senderAcctNum = request.getSenderAcctNum(); 
    int receiverAcctNum = request.getReceiverAcctNum(); 
    double transferAmount = request.getTransferAmount(); 
     
    Student sender = repo.findById(senderAcctNum).orElse(null); 
    Student receiver = repo.findById(receiverAcctNum).orElse(null); 
 
    if (sender != null && receiver != null) { 
        if (transferAmount > 0 && transferAmount <= sender.getBalance()) { 
            sender.setBalance(sender.getBalance() - transferAmount); 
            receiver.setBalance(receiver.getBalance() + transferAmount); 
            repo.save(sender); 
            repo.save(receiver); 
 
            Transaction senderTransaction = new Transaction(); 
            senderTransaction.setAmount(transferAmount); 
            senderTransaction.setAcctOpen(true); 
            senderTransaction.setActionPerformed("Transfer to ID: " + receiver.getAcctNum()); 
            senderTransaction.setBalanceBefore(sender.getBalance() + transferAmount); 
            senderTransaction.setBalanceAfter(sender.getBalance()); 
            senderTransaction.setCurrdate(LocalDate.now()); 
            senderTransaction.setStudent(sender); 
         
            tr.save(senderTransaction); 
 
            Transaction receiverTransaction = new Transaction(); 
            receiverTransaction.setAmount(transferAmount); 
            receiverTransaction.setAcctOpen(true); 
            receiverTransaction.setActionPerformed("Receive from ID: " + sender.getAcctNum()); 
            receiverTransaction.setBalanceBefore(receiver.getBalance() - transferAmount); 
            receiverTransaction.setBalanceAfter(receiver.getBalance()); 
            receiverTransaction.setCurrdate(LocalDate.now()); 
            receiverTransaction.setStudent(receiver); 
         
            tr.save(receiverTransaction); 
             
            TransferResponse response = new TransferResponse("Transfer successful", sender.getBalance()); 
            return ResponseEntity.ok(response); 
        } else { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid transfer amount or insufficient balance."); 
        } 
    } else { 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sender or receiver not found."); 
    } 
} 
    @GetMapping("/validateAccount") 
    public ResponseEntity<Boolean> validateAccount(@RequestParam("acctNum") int acctNum) { 
  Student receiver = repo.findById(acctNum).orElse(null); 
    boolean isValid = receiver != null; 
    return ResponseEntity.ok(isValid); 
} 



    //Eugene I ((VIEW THE FULL CUSTOMER INFORMATION))
    @RequestMapping(value = "/account")
    public String showAccount(Model model,@RequestParam int param) {
        Student student = repo.findByAcctNum(param); 
    System.out.println(student.getuserId());
    model.addAttribute("student_name", student.getName());
    model.addAttribute("student_acctnum", student.getAcctNum());
    model.addAttribute("student_balance", student.getBalance());
    model.addAttribute("student_birthDate", student.getBirthDate());
    model.addAttribute("student_email", student.getEmail());
    model.addAttribute("student_nric", student.getNric());
    model.addAttribute("student_phone", student.getphoneNumber());
    model.addAttribute("student_user", student.getuserId());
        return "editaccount";
    
}

    // Eugene II
    @PostMapping(value = "/account/edited")
    public String updateAccount(@ModelAttribute Student updatedCustomer , @RequestParam int param, Model model) {
    Student existingCustomer = repo.findByAcctNum(param);
        System.out.println(param);
        // Update the properties of the existing customer with the values from the updatedCustomer
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setphoneNumber(updatedCustomer.getphoneNumber());

        repo.save(existingCustomer);
        Student student = repo.findByAcctNum(param);
        model.addAttribute("student_name", student.getName());
        model.addAttribute("student_acctnum", student.getAcctNum());
        model.addAttribute("student_balance", student.getBalance());
        model.addAttribute("student_birthDate", student.getBirthDate());
        model.addAttribute("student_email", student.getEmail());
        model.addAttribute("student_nric", student.getNric());
        model.addAttribute("student_phone", student.getphoneNumber());
        model.addAttribute("student_user", student.getuserId());

        return "updatesuccess"; 
}
 

}
