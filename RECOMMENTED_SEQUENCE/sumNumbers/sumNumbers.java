// Ploblem Reference: https://codingbat.com/prob/p121193

public class sumNumbers {
    public static void main(String[] args)
    {
        int ret = 0;
        ret = func_sumNumbers("aa11b33");
        System.out.println(ret);
    }
    
    public static int func_sumNumbers(String str) {
        int sum = 0;
        String numStr = new String();

        for (int i = 0; i < str.length(); i++)
        {
            char ith_char = str.charAt(i);
            if (Character.isDigit(ith_char) == true)
            {
                numStr = numStr.concat(String.valueOf(ith_char));
            }
            // not digit
            else
            {
                // Numbers are in numStr
                if (numStr.length() > 0)
                {
                    sum += Integer.parseInt(numStr);
                    numStr = new String();
                }
            }
        }
        // add last Number
        if (numStr.length() > 0)
        {
            sum += Integer.parseInt(numStr);
        }

        return sum;
    }
}