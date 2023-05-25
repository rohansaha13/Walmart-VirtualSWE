public class PowerOfTwoMaxHeap<T extends Comparable<T>> {
    private int degree;
    private T[] heap;
    private int size;

    @SuppressWarnings("unchecked")
    public PowerOfTwoMaxHeap(int degree) {
        if (degree < 0) {
            throw new IllegalArgumentException("Degree must be a non-negative integer.");
        }
        this.degree = degree;
        this.heap = (T[]) new Comparable[16];
        this.size = 0;
    }

    public void insert(T value) {
        if (size == heap.length) {
            resizeHeap();
        }
        heap[size++] = value;
        bubbleUp(size - 1);
    }

    public T popMax() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty.");
        }

        T max = heap[0];
        heap[0] = heap[--size];
        heap[size] = null;
        bubbleDown(0);
        return max;
    }

    private void bubbleUp(int childIdx) {
        T child = heap[childIdx];
        while (childIdx > 0) {
            int parentIdx = (childIdx - 1) / degree;
            T parent = heap[parentIdx];
            if (child.compareTo(parent) <= 0) {
                break;
            }
            heap[childIdx] = parent;
            childIdx = parentIdx;
        }
        heap[childIdx] = child;
    }

    private void bubbleDown(int parentIdx) {
        T parent = heap[parentIdx];
        while (true) {
            int maxChildIdx = getMaxChildIndex(parentIdx);
            if (maxChildIdx == -1) {
                break;
            }
            T maxChild = heap[maxChildIdx];
            if (parent.compareTo(maxChild) >= 0) {
                break;
            }
            heap[parentIdx] = maxChild;
            parentIdx = maxChildIdx;
        }
        heap[parentIdx] = parent;
    }

    private int getMaxChildIndex(int parentIdx) {
        int startChildIdx = parentIdx * degree + 1;
        int endChildIdx = Math.min(startChildIdx + degree, size);
        if (startChildIdx >= endChildIdx) {
            return -1; // No child nodes
        }

        int maxChildIdx = startChildIdx;
        for (int i = startChildIdx + 1; i < endChildIdx; i++) {
            if (heap[i].compareTo(heap[maxChildIdx]) > 0) {
                maxChildIdx = i;
            }
        }
        return maxChildIdx;
    }

    private void resizeHeap() {
        int newCapacity = heap.length * 2;
        heap = Arrays.copyOf(heap, newCapacity);
    }
}
