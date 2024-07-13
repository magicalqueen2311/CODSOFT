import java.util.Random;
import java.util.Scanner;
class RandomNumber
{
    public static void main(String args[])
    {
int answer,guess;
final int MAX=5;
Scanner keyboard=new Scanner(System.in);
Random rand=new Random();
answer=rand.nextInt(MAX)+1;
System.out.println("guess the number between 1 and 5:");
guess=keyboard.nextInt();
if(guess==answer)
{
    System.out.println("Good job,the number was"+answer);
}
else{
    System.out.println("Sorry,the number was"+answer);
}

    }
}