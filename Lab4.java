package Lab;
	
	import java.io.BufferedReader;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.math.BigDecimal;
	import java.text.DecimalFormat;
	import java.util.ArrayList;
	import java.util.List;

public class Lab4{
	
	private List<String[]> transactions = new ArrayList<String[]>();
	private String date, type, vendor;
	private double amount;
	private double balance;
	private double previousBalance;
	private DecimalFormat formatter = new DecimalFormat("$#,###.00");
	private boolean hasFees = true;
	
	private void msg(String msg){
		System.out.print(msg);
	}
	
	private void br(){
		System.out.println();
	}
	
	private static void process(List<String[]>transactions){
		Lab4 obj = new Lab4();
		int decimal = 2;
		double num;
		
		obj.msg("DATE | TRANSACTION | VENDOR | AMOUNT | BALANCE");
		for(String[] transaction : transactions){
			obj.date = transaction[0];
			obj.type = transaction[1];
			obj.vendor = transaction[2];
			
			// Convert string into double number
			obj.amount = Double.parseDouble(transaction[3]);
			BigDecimal bd = new BigDecimal(obj.amount);
			// round to the second decimal place
			bd = bd .setScale(decimal, BigDecimal.ROUND_HALF_UP);
			// Convert BigDecimal into a double
			num = bd.doubleValue();
			
			switch(obj.type.toLowerCase()){
				case "credit":
					obj.msg(obj.date + " ");
					obj.msg(obj.type + " ");
					obj.msg(obj.vendor.toLowerCase() + " ");
					obj.msg(obj.formatter.format(num) + " ");
					obj.balance = obj.balance +  num; 
					obj.msg("Balance: " + obj.formatter.format(obj.balance) + "\n");
					obj.br();
					break;
				case "debit":
					obj.msg(obj.date);
					obj.msg(obj.type);
					obj.msg(obj.vendor.toLowerCase() + " ");
					obj.msg(obj.formatter.format(num) + "\n");
					obj.balance = obj.balance - num;
					obj.msg("Balance " + obj.formatter.format(obj.balance));
					obj.br();
					break;
				default:
					obj.msg(obj.date);
					obj.msg(obj.type);
					obj.msg(obj.vendor.toLowerCase() + " ");
					obj.msg(obj.formatter.format(num) + "\n");
					obj.balance = obj.balance - num;
					obj.msg("Balance " + obj.formatter.format(obj.balance));
					obj.br();
					break;
			}
		}
		obj.msg("NEW BALANCE: " + obj.formatter.format(obj.balance) + "\n");
		obj.previousBalance = obj.balance;
		obj.balance = 0;
		
		// Balance is greater than zero, no interest fee applies.
		if(obj.balance > 0){
			obj.hasFees = false;
		} 
		// Or the balance is less than zero, interest fee does apply.
		else {
			obj.hasFees = true;
		}
		
		if(obj.hasFees){
			double interest = .10;
			double newBalance = obj.previousBalance * interest;
			obj.msg("You have a balance of " + obj.balance + "\n");
			obj.msg("You are charged a 10% fee of " + obj.previousBalance * interest + "\n");
			obj.msg("Your new balance is: " + obj.formatter.format(newBalance));
		}
	}
	
		public static void main(String[] args){
			// This method will read data from a CSV file
			String filename = "C:/Users/CuriousChloe/Desktop/Files/CCStatement.csv";
			String dataRow;
			Lab4 obj = new Lab4();
			
			try{
				// Open the file
				BufferedReader br = new BufferedReader(new FileReader(filename));
				
				// Read the data as long as it's not empty
				while((dataRow = br.readLine()) != null){
					// Parse the data by commas
					String[] line = dataRow.split(",");
					// Add the data to collection
					obj.transactions.add(line);
				}
			br.close();	
			
			}catch(FileNotFoundException e ){
				e.printStackTrace();
			}catch(IOException f){
				f.printStackTrace();
			}
			
			process(obj.transactions);
	}
}
