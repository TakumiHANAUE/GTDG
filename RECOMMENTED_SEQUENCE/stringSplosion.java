// Ploblem Reference: https://codingbat.com/prob/p117334

public class stringSplosion {
    public static void main(String[] args)
    {
        var retstr = new String();
        retstr = func_stringSplosion("Code");
        System.out.println(retstr);
    }
    
    public static String func_stringSplosion(String str) {
        String outstr = new String();

        for (int i = 1; i < str.length(); i++)
        {
            String substr = str.substring(0, i);
            outstr = outstr.concat(substr);
        }
        outstr = outstr.concat(str);

        return outstr;
    }
}