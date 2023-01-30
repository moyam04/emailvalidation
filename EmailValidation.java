//Manuel Moya Valdivia 260510582
import java.util.Scanner;

public class EmailValidation{

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your e-mail address: ");
        String email = input.next();

        if (exactlyOneAt(email)){
            System.out.println("Your e-mail has exactly one @: " + exactlyOneAt(email));
            System.out.println("This is your prefix: " + getPrefix(email));
            System.out.println("This is your domain: " + getDomain(email));
            System.out.println("Your prefix is valid: " + isValidPrefix(getPrefix(email)));
            System.out.println("Your domain is valid: " + isValidDomain(getDomain(email)));
        }
        System.out.println("Your email is valid: " + isValidEmail(email));
    }

    //This method is created to simplify code.
        //This method returns true if the character is a letter of the alphabet.
            //It will be invoked in isAlphanumeric() & isValidDomain()
    public static boolean isAlphabet(char character){
        return character >= 'A' && character <= 'Z' || character >= 'a' && character <= 'z';
    }

    //To validate Alphanumeric character.
    public static boolean isAlphanumeric(char character) {
        return isAlphabet(character) || character >= '0' && character <= '9';
    }

    //To validate Prefix Character.
    public static boolean isValidPrefixChar(char character) {
        return isAlphanumeric(character) || character == '.' || character == '-' || character == '_';
    }

    //To validate Domain characters.
    public static boolean isValidDomainChar(char character){
        return isAlphanumeric(character) || character == '.' || character == '-';
    }

    //Method to confirm that the first and last letters are alphanumeric.
        //This method takes String as input and returns boolean.
            //This method invokes the isAlphanumeric() method.
                //This method is tested directly in isValidPrefix() and indirectly in isValidDomain().
    public static boolean firstAndLast(String text){
        char first = text.charAt(0);
        char last = text.charAt(text.length()-1);
        return isAlphanumeric(first) && isAlphanumeric(last);
    }

    //To confirm no underscores, dashes or periods are together.
        //This method is tested directly in isValidPrefix() and indirectly in isValidDomain().
    public static boolean mustFollow(String text){
        boolean check = true;
        for (int i = 1; i < text.length(); i++){
            char current = text.charAt(i);
            char previous = text.charAt(i - 1);
            if ((current == '.' || current == '_' || current == '-') && (previous == '.' || previous == '_' || previous == '-')) {
                check = false;
                break;
            }
        }  return check;
    }

    //This method obtains the first portion of the domain.
        //This method is similar to get Prefix; however, we are looking for the last occurrence of '.'
            //This method is invoked in portionBooleans()
    public static String domainFirstPortion(String domain){
        int periodLastLocation = domain.lastIndexOf('.');
        return domain.substring(0,periodLastLocation);
    }

    //This method obtains the second portion of the domain.
        //This method is similar to getDomain; however, we are looking for the last occurrence of '.'
            //This method is invoked in portionBooleans()
    public static String domainSecondPortion(String domain){
        int periodLastLocation = domain.lastIndexOf('.');
        return domain.substring(periodLastLocation + 1);
    }
    //This method tests conditions 2 to 5 for isValidDomain().
        //This method assumes there is a period; otherwise, it will not be invoked.
            //This method takes a string and returns boolean. This method is invoked in isValidDomain().
    public static boolean portionBooleans(String text){
        boolean condition2 = domainFirstPortion(text).length() >= 1; //Condition 2
        boolean condition3 = domainSecondPortion(text).length() >= 2; //Condition 3
        boolean condition4 = mustFollow(text); //Condition 4
        boolean condition5 = firstAndLast(text); //Condition 5
            return condition2 && condition3 && condition4 && condition5;
    }
    //To validate there is exactly one "At"
    public static boolean exactlyOneAt(String email){
        char ch = '@';
        int count = 0;
        for (int i = 0; i<email.length(); i++){
            char match = email.charAt(i);
            if (match==ch){
            count++;
        }
        }
        return (count == 1);
    }

    //To obtain the Prefix
    public static String getPrefix(String email){
        int prefixLocationOfAt = email.indexOf('@');
        return email.substring(0,prefixLocationOfAt);
    }

    //To obtain the Domain
    public static String getDomain(String email){
        int domainLocationOfAt = email.indexOf('@');
        return email.substring(domainLocationOfAt + 1);
}

    //To confirm if the Prefix is valid
        //If condition 1 is false, the method returns false and no more conditions will be tested.
    public static boolean isValidPrefix(String prefix) {

        //Condition 1 is that the prefix is at least one character.
            //If false, isValidPrefix returns false and other conditions won't be tested.
        if (!(prefix.length() >= 1)) {
            return false;

        //If the above returns true, the conditions below will be tested.
        }else{
        boolean condition2 = true; //<<---The Prefix has valid Prefix characters.
        boolean condition3 = mustFollow(prefix); //<---No underscores, periods or dashes are together.
        boolean condition4 = firstAndLast(prefix); //<---The first and last characters are alphanumeric.

        //Testing condition 2 with a loop.
            // If at any point it contains an invalid character, it breaks and returns false for condition 2.
        for (int i = 0; i < prefix.length(); i++) {
            char alpha = prefix.charAt(i);
            if (!isValidPrefixChar(alpha)){
                condition2 = false;
                break;
            }
        }
        return condition2 && condition3 && condition4;
    }
    }

    //To confirm if the Domain is valid
        //If condition1 is false, the method returns a false value and no other conditions will be tested.
            //If condition1 is true, the method will invoke related methods to test remaining conditions.
    public static boolean isValidDomain(String domain){

        //condition1 = It is made up of two portions separated by a period.
        //condition2 = The first portion contains at least one character.
        //condition3 = The second portion contains at least two characters.
        //condition4 = No periods or dashes are together.
        //condition5 = The first and last characters are alphanumeric
        //condition6 = The first portion only contains alphanumeric characters, periods and dashes.
        //condition7 = The second portion only contains letters of the alphabet.

        int periodLastLocation = domain.lastIndexOf('.');  //<<---Condition 1
        if (periodLastLocation == -1){
           return false;
        }else{

            //Invoking portionBooleans to test conditions 2 to 5
            boolean conditions2to5 = portionBooleans(domain);


            boolean condition6 = true;
            boolean condition7 = true;

            //Testing condition 6 and returning a value.
            //Invoking domainFirstPortion and isValidDomainChar ().
            for (int i = 0; i < domainFirstPortion(domain).length(); i++) {
                char alpha = domainFirstPortion(domain).charAt(i);
                if (!isValidDomainChar(alpha)){
                    condition6 = false;
                    break;
                }
            }

            //Testing for condition 7.
            //Invoking domainSecondPortion and isAlphabet().
            for (int i = 0; i < domainSecondPortion(domain).length(); i++) {
                char alpha = domainSecondPortion(domain).charAt(i);
                if (!isAlphabet(alpha)){
                    condition7 = false;
                    break;
                }
            }
            return conditions2to5 && condition6 && condition7;
        }
    }

    //To confirm if the e-mail is valid
        //If the email has exactly one "at" and its prefix and domain are valid, the email is valid
    public static boolean isValidEmail(String email){
        return exactlyOneAt(email) && isValidPrefix(getPrefix(email)) && isValidDomain(getDomain(email));
    }
    }