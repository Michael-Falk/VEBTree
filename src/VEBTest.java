import static org.junit.Assert.*;

import org.junit.Test;


public class VEBTest {
	VanEmdeBoasTree<String> v;
	Integer[] nums = new Integer[]{0,1,2,3,4,5,6,7};
	String[] strs = new String[]{"0", "1", "2", "3", "4", "5", "6", "7"};

	@Test(expected=IllegalArgumentException.class)
	public void testIllegalSize() {
		v = new VanEmdeBoasTree<String>(3);
	}
	
	@Test
	public void testBaseInit() {
		v = buildBase();
		assertEquals(null, v.getMin());
		assertEquals(null, v.getMax());
	}
	
	@Test
	public void testBaseInsert() {
		v = buildBase();
		v.insert(nums[0], strs[0]);
		assertEquals(nums[0], v.getMin().getKey());
		assertEquals(nums[0], v.getMax().getKey());
	}
	
	@Test
	public void testBaseInsertNewMax() {
		v = buildBase();
		v.insert(nums[0], strs[0]);
		v.insert(nums[1], strs[1]);
		assertEquals(nums[0], v.getMin().getKey());
		assertEquals(nums[1], v.getMax().getKey());
	}
	
	@Test
	public void testBaseInsertNewMin() {
		v = buildBase();
		v.insert(nums[0], strs[0]);
		v.insert(nums[1], strs[1]);
		assertEquals(nums[0], v.getMin().getKey());
		assertEquals(nums[1], v.getMax().getKey());
	}
	
	@Test
	public void testBasefindSuccessor() {
		v = buildBase();
		v.insert(nums[1], strs[1]);
		v.insert(nums[0], strs[1]);
		assertEquals(nums[1], v.findSuccessor(nums[0]).getKey());
	}
	
	@Test
	public void testBasefindPredecessor() {
		v = buildBase();
		v.insert(nums[1], strs[1]);
		v.insert(nums[0], strs[0]);
		assertEquals(nums[0], v.findPredecessor(nums[1]).getKey());
	}
	
	@Test
	public void testBaseDelete() {
		v = buildBase();
		v.insert(nums[0], strs[0]);
		v.insert(nums[1], strs[1]);
		assertEquals(strs[0], v.delete(nums[0]));
		assertEquals(nums[1], v.getMin().getKey());
		v.insert(nums[0], strs[0]);
		assertEquals(nums[0], v.getMin().getKey());
		assertEquals(strs[1], v.delete(nums[1]));
		assertEquals(nums[0], v.getMax().getKey());
	}
	
	@Test
	public void testBiggerInit() {
		v = buildBigger();
		assertEquals(null, v.getMin());
		assertEquals(null, v.getMax());
	}
	
	@Test
	public void testBiggerInsert1() {
		v = buildBigger();
		fillBigger(v);
		assertEquals(nums[0], v.getMin().getKey());
		assertEquals(nums[7], v.getMax().getKey());
	}
	
	@Test
	public void testBiggerInsert2() {
		v = buildBigger();
		sillyFillBigger(v);
		assertEquals(nums[0], v.getMin().getKey());
		assertEquals(nums[7], v.getMax().getKey());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalIllegal() {
		v = buildBigger();
		v.insert(9, "9");
	}
	
	@Test
	public void testContains(){
		v = buildBigger();
		fillBigger(v);
		assertTrue(v.contains(nums[0]));
		assertTrue(v.contains(nums[1]));
		assertTrue(v.contains(nums[2]));
		assertTrue(v.contains(nums[3]));
		assertTrue(v.contains(nums[4]));
		assertTrue(v.contains(nums[5]));
		assertTrue(v.contains(nums[6]));
		assertTrue(v.contains(nums[7]));
	}
	
	@Test
	public void testBiggerfindSuccessor1() {
		v = buildBigger();
		fillBigger(v);
		assertEquals(nums[1], v.findSuccessor(nums[0]).getKey());
		assertEquals(strs[1], v.findSuccessor(nums[0]).getValue());
		assertEquals(nums[2], v.findSuccessor(nums[1]).getKey());
		assertEquals(strs[2], v.findSuccessor(nums[1]).getValue());
		assertEquals(nums[3], v.findSuccessor(nums[2]).getKey());
		assertEquals(strs[3], v.findSuccessor(nums[2]).getValue());
		assertEquals(nums[4], v.findSuccessor(nums[3]).getKey());
		assertEquals(strs[4], v.findSuccessor(nums[3]).getValue());
		assertEquals(nums[5], v.findSuccessor(nums[4]).getKey());
		assertEquals(strs[5], v.findSuccessor(nums[4]).getValue());
		assertEquals(nums[6], v.findSuccessor(nums[5]).getKey());
		assertEquals(strs[6], v.findSuccessor(nums[5]).getValue());
		assertEquals(nums[7], v.findSuccessor(nums[6]).getKey());
		assertEquals(strs[7], v.findSuccessor(nums[6]).getValue());
	}
	
	@Test
	public void testBiggerfindSuccessor2() {
		v = buildBigger();
		sillyFillBigger(v);
		assertEquals(nums[1], v.findSuccessor(nums[0]).getKey());
		assertEquals(strs[1], v.findSuccessor(nums[0]).getValue());
		assertEquals(nums[2], v.findSuccessor(nums[1]).getKey());
		assertEquals(strs[2], v.findSuccessor(nums[1]).getValue());
		assertEquals(nums[3], v.findSuccessor(nums[2]).getKey());
		assertEquals(strs[3], v.findSuccessor(nums[2]).getValue());
		assertEquals(nums[4], v.findSuccessor(nums[3]).getKey());
		assertEquals(strs[4], v.findSuccessor(nums[3]).getValue());
		assertEquals(nums[5], v.findSuccessor(nums[4]).getKey());
		assertEquals(strs[5], v.findSuccessor(nums[4]).getValue());
		assertEquals(nums[6], v.findSuccessor(nums[5]).getKey());
		assertEquals(strs[6], v.findSuccessor(nums[5]).getValue());
		assertEquals(nums[7], v.findSuccessor(nums[6]).getKey());
		assertEquals(strs[7], v.findSuccessor(nums[6]).getValue());
	}
	
	@Test
	public void testBiggerfindPredecessor1() {
		v = buildBigger();
		fillBigger(v);
		assertEquals(nums[0], v.findPredecessor(nums[1]).getKey());
		assertEquals(strs[0], v.findPredecessor(nums[1]).getValue());
		assertEquals(nums[1], v.findPredecessor(nums[2]).getKey());
		assertEquals(strs[1], v.findPredecessor(nums[2]).getValue());
		assertEquals(nums[2], v.findPredecessor(nums[3]).getKey());
		assertEquals(strs[2], v.findPredecessor(nums[3]).getValue());
		assertEquals(nums[3], v.findPredecessor(nums[4]).getKey());
		assertEquals(strs[3], v.findPredecessor(nums[4]).getValue());
		assertEquals(nums[4], v.findPredecessor(nums[5]).getKey());
		assertEquals(strs[4], v.findPredecessor(nums[5]).getValue());
		assertEquals(nums[5], v.findPredecessor(nums[6]).getKey());
		assertEquals(strs[5], v.findPredecessor(nums[6]).getValue());
		assertEquals(nums[6], v.findPredecessor(nums[7]).getKey());
		assertEquals(strs[6], v.findPredecessor(nums[7]).getValue());
	}
	
	@Test
	public void testBiggerfindPredecessor2() {
		v = buildBigger();
		sillyFillBigger(v);
		assertEquals(nums[0], v.findPredecessor(nums[1]).getKey());
		assertEquals(strs[0], v.findPredecessor(nums[1]).getValue());
		assertEquals(nums[1], v.findPredecessor(nums[2]).getKey());
		assertEquals(strs[1], v.findPredecessor(nums[2]).getValue());
		assertEquals(nums[2], v.findPredecessor(nums[3]).getKey());
		assertEquals(strs[2], v.findPredecessor(nums[3]).getValue());
		assertEquals(nums[3], v.findPredecessor(nums[4]).getKey());
		assertEquals(strs[3], v.findPredecessor(nums[4]).getValue());
		assertEquals(nums[4], v.findPredecessor(nums[5]).getKey());
		assertEquals(strs[4], v.findPredecessor(nums[5]).getValue());
		assertEquals(nums[5], v.findPredecessor(nums[6]).getKey());
		assertEquals(strs[5], v.findPredecessor(nums[6]).getValue());
		assertEquals(nums[6], v.findPredecessor(nums[7]).getKey());
		assertEquals(strs[6], v.findPredecessor(nums[7]).getValue());
	}
	
	@Test
	public void testOverWrite() {
		v = buildBigger();
		fillBigger(v);
		String newVal = "pickles";
		v.insert(nums[0], newVal);
		assertEquals(newVal, v.getMin().getValue());
	}
	
	@Test
	public void testBiggerDelete1() {
		v = buildBigger();
		fillBigger(v);
		Integer i = v.findSuccessor(nums[0]).getKey(), j = v.findPredecessor(nums[7]).getKey();
		for(int a = 0; a < 8; a++) {
			assertEquals(v.delete(a), strs[a]);
			assertEquals(v.contains(a), false);
		}
	}
	
	@Test
	public void testBiggester() {
		v = buildBiggester();
		fillBiggester(v);
		for(int i = 0; i < 2048; i++) {
			assertEquals(v.delete(i), "hey bby i'm numba "+i);
			assertEquals(v.contains(i), false);
		}
	}
	
	/** My Little Factory **/
	private VanEmdeBoasTree<String> buildBase(){
		return new VanEmdeBoasTree<String>(2);
	}
	
	private VanEmdeBoasTree<String> buildBigger(){
		return new VanEmdeBoasTree<String>(8);
	}
	
	private VanEmdeBoasTree<String> buildBiggester() {
		return new VanEmdeBoasTree<String>(2048);
	}
	
	private void fillBiggester(VanEmdeBoasTree<String> v){
		for(int i = 0; i < 2048; i++) {
			v.insert(i, "hey bby i'm numba "+i);
		}
	}
	private void fillBigger(VanEmdeBoasTree<String> v){
		v.insert(nums[0], strs[0]);
		v.insert(nums[1], strs[1]);
		v.insert(nums[2], strs[2]);
		v.insert(nums[3], strs[3]);
		v.insert(nums[4], strs[4]);
		v.insert(nums[5], strs[5]);
		v.insert(nums[6], strs[6]);
		v.insert(nums[7], strs[7]);
	}
	
	private void sillyFillBigger(VanEmdeBoasTree<String> v){
		v.insert(nums[4], strs[4]);
		v.insert(nums[2], strs[2]);
		v.insert(nums[1], strs[1]);
		v.insert(nums[7], strs[7]);
		v.insert(nums[0], strs[0]);
		v.insert(nums[5], strs[5]);
		v.insert(nums[6], strs[6]);
		v.insert(nums[3], strs[3]);
	}
}
