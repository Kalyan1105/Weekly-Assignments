package LoanServiceApp;

public class LoanService {
    public boolean isEligible(int age,double salary){
        if(age>=21&&age<=60){
            if(salary>=25000){
                return true;
            }
        }
        return false;
    }
    public double calculateEMI(double loanAmount,int tenureYears){
        double EMI=loanAmount/(tenureYears*12);
        if(loanAmount<=0) throw new IllegalArgumentException("Loan amount must be greater than 0");
        if(tenureYears<=0) throw new IllegalArgumentException("Tenure years must be greater than 0");
        return EMI;
    }
    public  String getLoanCategory(int creditScore){
        if(creditScore>=750) return "Premium";
        if(creditScore>=600) return "Standard";
        if(creditScore>=0)return "High Risk";
        return null;
    }

}
