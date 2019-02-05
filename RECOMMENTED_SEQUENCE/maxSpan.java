// Problem Reference: https://codingbat.com/prob/p189576

public class maxSpan {
    public static void main(String[] args)
    {
        int maxspan = 0;
        int[] nums = {1, 4, 2, 1, 4, 4, 4};
        maxspan = func_maxSpan(nums);
        System.out.println(maxspan);
    }

    public static int func_maxSpan(int[] nums)
    {
        // ex. nums[] = {1, 4, 2, 1, 4, 4, 4}
        // nums[0] = 1; leftmost nums[0], rightmost nums[3] -> span = 4
        // nums[1] = 4; leftmost nums[1], rightmost nums[6] -> span = 6
        // nums[2] = 2; leftmost nums[2], rightmost nums[2] -> span = 1
        // => maxSpan = 6 (span of 4)
        int maxspan = 0;
        int leftindex;
        int leftnum = 0;
        for (leftindex = 0; leftindex < nums.length; leftindex++)
        {
            leftnum = nums[leftindex];
            int rightindex;
            int rightnum = 0;
            int span = 0;
            // finding rightmost value
            for (rightindex = (nums.length - 1); rightindex >= leftindex; rightindex--)
            {
                rightnum = nums[rightindex];
                if (leftnum == rightnum)
                {
                    span = rightindex - (leftindex - 1);
                    break;
                }
            }
            // update maxspan
            if (span > maxspan)
            {
                maxspan = span;
            }
        }
        return maxspan;
    }
}