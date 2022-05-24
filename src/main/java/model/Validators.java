package model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Validators {
    static Pattern validEmailPattern=Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    static Pattern adressregex = Pattern.compile("^[^\\[\\]]+,[^\\[\\]]+,[^\\[\\]]+,[^\\[\\]]+$");
    static Pattern expireDateregex = Pattern.compile("^(?=.*[0-9])(?=.*[/]).{5}$");
    static Pattern postalCoderegex= Pattern.compile("^(?=.*[0-9])(?=.*[-]).{6}$");
    static String ptVisa = "^4[0-9]{6,}$";
    static String ptAmeExp = "^3[47][0-9]{5,}$";
    static String ptMasterCard = "^5[1-5][0-9]{5,}$";
    static String ptDinClb = "^3(?:0[0-5]|[68][0-9])[0-9]{4,}$";
    static String ptDiscover = "^6(?:011|5[0-9]{2})[0-9]{3,}$";
    static String ptJcb = "^(?:2131|1800|35[0-9]{3})[0-9]{3,}$";
    static Pattern PASSWORD_PATTERN=Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[@#$%^&*()_+~`=]).{8,}$");

    public static Boolean addressValidator(String adress){
            String[] s=adress.split(",");
            return (!adress.isEmpty() && adressregex.matcher(adress).matches() && postCodeValidator( s[3]));
        }
        public static Boolean emailValidator(String email){
            return (!email.isEmpty() && validEmailPattern.matcher(email).matches());
        }
        public static Boolean postCodeValidator(String postcode){

            return (!postcode.isEmpty() && postalCoderegex.matcher(postcode).matches());
        }
        public static Boolean expireDateValidator(String exDate){

            return (!exDate.isEmpty() && expireDateregex.matcher(exDate).matches());
        }
        public static Boolean debitCardValidator(String debitCardNumber){
            if(debitCardNumber.isEmpty())return false;

            List<String> listOfPattern=new ArrayList<>();
            listOfPattern.add(ptVisa);
            listOfPattern.add(ptMasterCard);
            listOfPattern.add(ptAmeExp);
            listOfPattern.add(ptDinClb);
            listOfPattern.add(ptDiscover);
            listOfPattern.add(ptJcb);
            for(String p:listOfPattern){
                if(debitCardNumber.matches(p)){
                    return true;
                }
            }
            return false;
        }
        public static Boolean cvvValidator(String cvv){
            return cvv.length() == 3;
        }
        public static Boolean passwordValidator(String password){
            return  PASSWORD_PATTERN.matcher(password).matches();
        }


    }


