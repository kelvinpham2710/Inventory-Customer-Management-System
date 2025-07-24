/*
 * The program creates a class MaxHeap with default constructor, get and set methods
 * Hold methods used to create, insert, and remove elements from the heap
 */


public class MaxHeap {
	
	private DelInfo[] heapArray;
	
	private int maxHeapSize;
	private int actualHeapSize;
	
	public MaxHeap(int maxSize) {
		maxHeapSize = maxSize;
		actualHeapSize = 0;
		heapArray = new DelInfo[maxSize];
	}
	
	public int getActualSize() {
		return actualHeapSize;
	}
	
	private int getParentPosition(int position) {
		return (position - 1) / 2;
	}
	
	private int getLeftChildPosition(int position) {
		return (2 * position);
	}
	
	private int getRightChildPosition(int position) {
		return (2 * position) + 1;
	}
	
	private boolean isLeaf(int position) {
		return position > (actualHeapSize / 2) && position <= maxHeapSize;
	}
	
	private void swap(int num1, int num2) {
		DelInfo temp = heapArray[num1];
		heapArray[num1] = heapArray[num2];
		heapArray[num2] = temp;
	}
	
	public void insertNode(DelInfo delInfo) {
		
		if(actualHeapSize < maxHeapSize) {
			heapArray[actualHeapSize] = delInfo;
			int currentPosition = actualHeapSize;
			
			while(heapArray[currentPosition].getTVBuy() > heapArray[getParentPosition(currentPosition)].getTVBuy()) {
				swap(currentPosition, getParentPosition(currentPosition));
				currentPosition = getParentPosition(currentPosition);
			}
			actualHeapSize++;
		}
		else {
			System.out.println("Heap is full.");
		}
	}
	
	public void balanceHeap() {
		for(int i = 0; i < actualHeapSize / 2; i++) {
			maximizer(i);
		}
	}
	
	private void maximizer(int position) {
		
		/*
		if(!isLeaf(position)) {
			int left = getLeftChildPosition(position);
			int right = getRightChildPosition(position);
			
			if(right < actualHeapSize && heapArray[left].getTVBuy() < heapArray[right].getTVBuy()) {
				if(heapArray[position].getTVBuy() < heapArray[right].getTVBuy()) {
					swap(position, right);
					maximizer(right);
				}
			}
			else {
				if(heapArray[position].getTVBuy() < heapArray[left].getTVBuy()) {
					swap(position, left);
					maximizer(left);
				}
			}
			
		}//end of if else
		*/
		
		if(!isLeaf(position)) {
			
			int left = getLeftChildPosition(position);
			int right = getRightChildPosition(position);
			
			if(heapArray[position].getTVBuy() < heapArray[left].getTVBuy() || heapArray[position].getTVBuy() < heapArray[right].getTVBuy()) {
				if(heapArray[left].getTVBuy() > heapArray[right].getTVBuy()) {
					swap(position, left);
					maximizer(left);
				}
				else {
					swap(position, right);
					maximizer(right);
				}//end of if else
				
			}
			
		}
		
	}
	
	public DelInfo removeRoot() {
		/*
		try {
			DelInfo root = heapArray[0];
			heapArray[0] = heapArray[actualHeapSize - 1];
			actualHeapSize--;
			maximizer(0);
			return root;
		} catch(NoSuchElementException e) {
			System.out.println("Heap is empty.");
		}
		return null;
		*/
		
		DelInfo root = heapArray[0];
		heapArray[0] = heapArray[--actualHeapSize];
		maximizer(0);
		return root;
	}
	
}
