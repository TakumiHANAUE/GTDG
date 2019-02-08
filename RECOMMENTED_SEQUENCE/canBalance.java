// Problem Reference: https://codingbat.com/prob/p158767

public class canBalance {
    public static void main(String[] args)
    {
        boolean ret = false;
        int[] nums = {10, 10};
        ret = func_canBalance(nums);
        System.out.println(ret);
    }

    public static boolean func_canBalance(int[] nums)
    {
        boolean can_balance = false;

        // sum up numbers
        int total = 0;
        int[] sub_total = new int[nums.length];
        for (int i = 0; i < nums.length; i++)
        {
            total += nums[i];
            sub_total[i] = total;
        }

        // total num is odd
        if ( total % 2 != 0 )
        {
            return false;
        }

        int half_total = total / 2;
        for (int i = 0; i < sub_total.length; i++)
        {
            // sub total matches half total
            if (sub_total[i] == half_total)
            {
                can_balance = true;
                break;
            }
            // over half total; cannot balance
            if (sub_total[i] > half_total)
            {
                break;
            }
        }

        return can_balance;
    }
}