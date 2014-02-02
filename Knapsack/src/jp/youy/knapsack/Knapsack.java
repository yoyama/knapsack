package jp.youy.knapsack;

import java.util.Arrays;
import java.util.LinkedList;

public class Knapsack {

	public static class Good {
		public int weight  = 0;
		public int value = 0;
		public Good() {}
		public Good(int weight, int value) {
			this.weight = weight;
			this.value = value;
		}
	}
	public static void main(String[] args) {
		Good[] goods = { new Good(2,2), new Good(3,4), new Good(5,7), new Good(7, 11), new Good(9, 14) };
		int max_weight = 22;
		Knapsack ks = new Knapsack();
		Good[] ans = ks.solveDP(goods, max_weight);
		System.out.printf("Goods:");
		for(Good good: goods) {
			System.out.printf("(%d %d) ", good.weight, good.value);
		}
		System.out.println("");
		
		int sum_w = 0;
		int sum_v = 0;
		System.out.printf("(weight, value) = ");
		for(Good good: ans) {
			System.out.printf("(%d %d) ", good.weight, good.value);
			sum_w += good.weight;
			sum_v += good.value;
		}
		System.out.println("");
		System.out.printf("Sum:(%d %d)\n", sum_w, sum_v);
		

	}

	public Good[] solveDP(Good[] goods, int max_weight) {
		int[][] dp = initArray(goods.length, max_weight);
		int[] idxes = new int[max_weight+1];
		int[] maxes = new int[max_weight+1];
		Arrays.fill(idxes, -1);
		Arrays.fill(maxes, -1);
		maxes[0] = 0;
		for(int i = 1; i <= goods.length; i++) {
			Good good = goods[i-1];
			for(int w = max_weight; w >= good.weight; w--) {
				int prev_value = maxes[w-good.weight];
				if(w != good.weight &&  prev_value < 0) {
					continue;
				}
				int add_value = prev_value + good.value;
				if(dp[i-1][w] < add_value) {
					dp[i][w] = add_value;
					idxes[w] = i-1;
				}
				else {
					dp[i][w] = dp[i-1][w];
				}
				maxes[w] = dp[i][w];
			}
		}
		printDP(dp, idxes, maxes, max_weight);
		
		LinkedList<Good> ans_list = new LinkedList<>();
		int idx = max_weight;
		while(idx > 0) {
			if(idxes[idx] < 0) {
				idx--;
				continue;
			}
			int good_idx = idxes[idx];
			ans_list.add(goods[good_idx]);
			idx = idx - goods[good_idx].weight;
		}
		return ans_list.toArray(new Good[0]);
	}
	
	private void printDP(int[][] dp, int[] idxes, int[]maxes, int max_weight) {
		for(int j = 0; j <= max_weight; j++) {
			System.out.printf("%2d ", j);
		}
		System.out.println("");
		for(int j = 0; j <= max_weight; j++) {
			System.out.printf("---", j);
		}
		System.out.println("");
		for(int i = 0; i < dp.length; i++) {
			for(int j = 0; j <= max_weight; j++) {
				System.out.printf("%2s ", (dp[i][j] >= 0)? dp[i][j]: "-");
			}
			System.out.println("");
		}
		System.out.println("");
		for(int j = 0; j <= max_weight; j++) {
			System.out.printf("%2d ", idxes[j]);
		}
		System.out.println("");
		for(int j = 0; j <= max_weight; j++) {
			System.out.printf("%2d ", maxes[j]);
		}
		System.out.println("");
		for(int j = 0; j <= max_weight; j++) {
			System.out.printf("---", j);
		}
		System.out.println("");
		for(int j = 0; j <= max_weight; j++) {
			System.out.printf("%2d ", j);
		}
		System.out.println("");
		
	}
	
	private int[][] initArray(int num_goods, int max_weight){
		int[][] ar = new int[num_goods+1][];
		for(int i = 0; i < num_goods+1; i++) {
			ar[i] = new int[max_weight+1];
			Arrays.fill(ar[i], -2);
		}
		ar[0][0] = 0;
		return ar;
	}
}
