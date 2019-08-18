// Ploblem Reference: https://codingbat.com/prob/p192570

public class withoutString {
    public static void main(String[] args)
    {
        var retstr = new String();
        retstr = func_withoutString("THIS is a FISH", "iS");
        System.out.println(retstr);
    }
    
    public static String func_withoutString(String base, String remove) {
        String outstr = new String();
        String small_base = base.toLowerCase();
        String small_remove = remove.toLowerCase();
        String replacement = new String();
        for (int i = 0; i < remove.length(); i++)
        {
            replacement = replacement.concat(String.valueOf('\0'));
        }

        small_base = small_base.replaceAll(small_remove, replacement);
        
        for (int j = 0; j < small_base.length(); j++)
        {
            char jth_small_char = small_base.charAt(j);
            if (jth_small_char != '\0')
            {
                char jth_char = base.charAt(j);
                outstr = outstr.concat(String.valueOf(jth_char));
            }
        }
        return outstr;
    }
}