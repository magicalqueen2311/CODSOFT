import java.util.Scanner;
class GradeCalculator
{
    public static void main(String args[])
    {
    Scanner sc=new Scanner(System.in);
    System.out.println("enter no of subjects:");
    int newSubjects=sc.nextInt();
    if(newSubjects<=0)
    {
        System.out.println("enter the valid subjects:");
        return;
    }
    int totalMarks=0;
    int maxMarksPerSubject=100;
    for(int i=1;i<=newSubjects;i++)
    {
        System.out.println("enter marks contained in subject"+i+"(out of 100)");
        int marks=sc.nextInt();
        if(marks<0||marks>maxMarksPerSubject)
        {
            System.out.println("marks should be in the range of 0 to 100.please enter valid marks");
            i--;
        }
        else{
            totalMarks+=marks;
        }
    }
    double averagePercentage=(double)totalMarks/(newSubjects*maxMarksPerSubject)*100;
    System.out.println("Totalmarks:"+totalMarks);
    System.out.println("averagepercentage:"+averagePercentage+"%");
    String grade;
    if(averagePercentage>=90)
    {
        grade="A+";
    }
    else if(averagePercentage>=80)
    {
        grade="A";
    }
    else if(averagePercentage>=70)
    {
        grade="B";
    }
    else if(averagePercentage>=60)
    {
        grade="C";
    }
    else if(averagePercentage>=50)
    {
        grade="D";
    }
    else{
        grade="F";
    }
    System.out.println("Grade:"+grade);
    }
    }    