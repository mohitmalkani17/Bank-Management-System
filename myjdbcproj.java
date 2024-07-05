package pro;
import java.sql.*; import java.io.*;

public class myjdbcproj {


public static void main(String[] args) throws NumberFormatException, IOException {
  String url = "jdbc:oracle:thin:@localhost:1521:xe"; String user = "system";
  String password = "123";


String query = "SELECT ID, NAME FROM std";
try (Connection con = DriverManager.getConnection(url, user, password);
Statement stmt = con.createStatement();
) {



BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
int ch = 0;
 
do {
	System.out.println("\n\n***** Banking Management System*****");
    System.out.println("1. Show Customer Records"); 
    System.out.println("2. Add Customer Record"); 
    System.out.println("3. Delete Customer Record");
    System.out.println("4. Update Customer Record for any attribute except Customer Number");
    System.out.println("5. Show Account Details of a Customer Account");
    System.out.println("6. Show Loan Details of a Customer"); 
    System.out.println("7. Deposit Money to an Account"); 
    System.out.println("8. Withdraw Money from an Account");
    System.out.println("9. Exit the Program");
    System.out.println("Enter your choice(1-9):");
    ch=Integer.parseInt(br.readLine());
    switch(ch) {
     case 1:
     String sqlstr = "select * from CUSTOMER"; ResultSet rs = stmt.executeQuery(sqlstr);
     int a = 0; 
     while(rs.next()) {
      System.out.print('\n'+rs.getString("CUST_NO")+'\t');
      System.out.print('\t'+rs.getString("NAME"));
      System.out.print('\t'+rs.getString("PHONE_NO"));
      System.out.print('\t'+rs.getString("CITY")); a++;
}
System.out.println();
System.out.println("The number of rows selected is " +a);
break;
case 2:
System.out.println("Enter cust_no: \t"); 
String cust_no = br.readLine();
System.out.println("Enter the name: \t");
String name = br.readLine();
System.out.println("Enter the phone: \t");
long phone = Long.parseLong(br.readLine()); 
System.out.println("Enter the city: \t");
String city = br.readLine();
String insertstmt = "insert into customer values('"+cust_no+"','"+name+"','"+phone+"','"+city+"')";
int n =stmt.executeUpdate(insertstmt); 
System.out.println(n+"Row Inserted"); 
break;
case 3:
System.out.println("Enter a cust_no for deletion: \t"); 
String cust = br.readLine();
String deletestmt = "delete from customer where cust_no='\"+cust+\"'";

n = stmt.executeUpdate(deletestmt); 
System.out.println(n+"Row deleted"); 
break;
 
case 4:
System.out.println("Enter 1: For Name 2: For Phone no 3: For City to update:");

int c1=Integer.parseInt(br.readLine()); 
switch(c1)
{
case 1:
System.out.println("Enter Cust No");
cust_no =br.readLine(); 
System.out.println("Enter updated name");
String updatedName =br.readLine();
String queryupdateName = "Update Customer set Name = '" + updatedName + "' where Cust_no = '"+cust_no+"'";
n =stmt.executeUpdate(queryupdateName);
System.out.println(n+"Updated Successfully");
break;
case 2:

System.out.println("Enter Cust No");
 
cust_no =br.readLine(); 
System.out.println("Enter updated Phone");
long updatedPhone =Long.parseLong(br.readLine());
String queryupdatePhone = "Update Customer set PHONE_NO = '" + updatedPhone + "' where Cust_no = '"+cust_no+"'";
n =stmt.executeUpdate(queryupdatePhone); 
System.out.println(n+"Updated Successfully");
break; 
case 3:

System.out.println("Enter Cust No"); 
cust_no =br.readLine();
System.out.println("Enter updated City");
String updatedCity =br.readLine();
String queryupdateCity = "Update Customer set CITY = '" + updatedCity + "' where Cust_no = '"+cust_no+"'";
n =stmt.executeUpdate(queryupdateCity);
System.out.println(n+"Updated Successfully");
break;
}
break; 
case 5:
System.out.println("Enter Cust No"); 
cust_no = br.readLine();
String queryAccDeatils= "SELECT A.ACCOUNT_NO, A.TYPE, A.BALANCE, B.BRANCH_CODE,B.BRANCH_NAME,B.BRANCH_CITY FROM ACCOUNT A, BRANCH B, CUSTOMER C, DEPOSITOR D WHERE D.ACCOUNT_NO = A.ACCOUNT_NO AND A.BRANCH_CODE = B.BRANCH_CODE AND C.CUST_NO = D.CUST_NO AND C.CUST_NO ='"+cust_no+"'";
rs = stmt.executeQuery(queryAccDeatils); System.out.format("Acc. No.: \tType: \tBalance: \tBranch Code: \tBranch Name: \t\tBranch City:\n");
while(rs.next()) {
String account_no = rs.getString("ACCOUNT_NO"); 
String type = rs.getString("TYPE");
Long balance = rs.getLong("BALANCE");
String branch_code = rs.getString("BRANCH_CODE");
String branch_name = rs.getString("BRANCH_NAME");
String branch_city = rs.getString("BRANCH_CITY");
System.out.format("%s \t\t%s \t %d\t\t %s\t\t %s\t %s\t\n", account_no, type, balance, branch_code, branch_name, branch_city);
}
break; 
case 6:

System.out.println("Enter Cust No"); cust_no = br.readLine();
String queryLoanNumber = "SELECT C.*, L.LOAN_NO, L.AMOUNT, B.* FROM LOAN L, CUSTOMER C, BRANCH B WHERE L.BRANCH_CODE = B.BRANCH_CODE AND C.CUST_NO = L.CUST_NO AND C.CUST_NO = '" + cust_no + "'";
 
rs = stmt.executeQuery(queryLoanNumber);


int rowCount = 0;


while (rs.next()) {
String ccust = rs.getString("CUST_NO"); String cname = rs.getString("NAME"); Long cphone = rs.getLong("PHONE_NO"); String ccity = rs.getString("CITY");
String loan_no = rs.getString("LOAN_NO"); Long amount = rs.getLong("AMOUNT");
String branch_code = rs.getString("BRANCH_CODE");
String branch_name = rs.getString("BRANCH_NAME");
String branch_city = rs.getString("BRANCH_CITY");
System.out.format("Cust. No.: %s, Name: %s, Phone No.: %d, City: %s, Loan No.: %s\n, Amount: %d, Branch Code: %s, Branch Name: %s, Branch City: %s\n", ccust, cname, cphone, ccity, loan_no, amount, branch_code, branch_name, branch_city);
rowCount++;
}
 System.out.println(rowCount + " rows affected"); break;
case 7:
System.out.println("Enter Account No"); String acc_no = br.readLine();
System.out.println("Enter the amount to deposite"); long amt = Long.parseLong(br.readLine());
String queryAddMoney = "Update ACCOUNT set BALANCE = BALANCE +" + amt + "where ACCOUNT_NO ='"+acc_no+"'";
n =stmt.executeUpdate(queryAddMoney); System.out.println(n+"Balance Updated"); 
break;
case 8:
System.out.println("Enter Account No"); acc_no = br.readLine(); 
System.out.println("Enter the amount to be withdrawn ");
amt = Long.parseLong(br.readLine());
String queryCheckBalance = "SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_No = '" + acc_no + "'";
rs = stmt.executeQuery(queryCheckBalance);
 if (rs.next()) {
long bal = rs.getLong(1); if (amt <= bal) {
String querySubMoney = "UPDATE ACCOUNT SET BALANCE = BALANCE - " + amt + " WHERE ACCOUNT_NO = '" +
acc_no + "'";
int nP = stmt.executeUpdate(querySubMoney); System.out.println(nP + " Balance Updated");
} else {
System.out.println("Insufficient balance");
 }
} else {
System.out.println("No account found with account number: " + acc_no);
 
}
rs.close();



break;
case 9:
System.out.println("Visit us next time"); stmt.close();
con.close(); System.exit(0);
break;
 
default:


 System.out.println("Enter from 1 to 9 nothing else  please again ");
 break;
 
}
} while(ch!=9);

} 
catch (SQLException e) {
	System.err.println("Error retrieving records: " +e.getMessage());
}


}

}
