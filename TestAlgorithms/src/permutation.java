import java.util.ArrayList;
import java.util.List;

public class permutation{
	public static void main(String[] args) {
		List<Integer> nums = new ArrayList<Integer>();
		nums.add(1);
		nums.add(2);
		nums.add(1);
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		result = findPerms(nums);
		System.out.println(result);
		
	}
	static List<List<Integer>> findPerms(List<Integer> nums){
		if (nums == null) return null;
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		List<Integer> currPath = new ArrayList<Integer>();
		Boolean[] used = new Boolean[nums.size()];
		for(int i = 0; i < nums.size(); i++) {
			used[i] = false;
		}
		helper(nums, res, currPath, used);
		return res;
	}
	static void helper(List<Integer> nums, List<List<Integer>> res, List<Integer> currPath, Boolean[] used) {
		if (currPath.size() == nums.size()) {
			res.add(new ArrayList<Integer>(currPath));
		}
		for (int i = 0; i < nums.size(); i++) {
			if (!used[i]) {
				used[i] = true;
				currPath.add(new Integer(nums.get(i)));
				helper(nums, res, currPath, used);
				currPath.remove(currPath.size() - 1);
				used[i] = false;
			}
		}
	}
}