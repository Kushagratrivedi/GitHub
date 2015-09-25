
public class AVLDemo {

	public static void main(String[] args) 
	{
		AVLTree avt = new AVLTree();
		avt.root = avt.insert(1, avt.root);
		avt.insert(2, avt.root);
		avt.insert(3, avt.root);
		avt.insert(4, avt.root);
		avt.insert(5, avt.root);
		avt.insert(6, avt.root);
		avt.insert(7, avt.root);
		avt.insert(8, avt.root);
		avt.insert(9, avt.root);
		avt.preOrder(avt.root, "root");
		System.out.println("Before Deletion: Rank of node 8 is: "+avt.getRank(8, avt.root));
		avt.spiralOrder(avt.root);
		//avt.inorderNonRecursive();
		System.out.println("\n Deletion \n");
		avt.delete(1, avt.root);
		//avt.delete(8, avt.root);
		avt.delete(3, avt.root);
		avt.preOrder(avt.root, "Root");
		
		System.out.println("Done");
		
		System.out.println(avt.isBst1(avt.root));
		System.out.println(avt.isBst2(avt.root));

		avt.inorder(avt.root);
		System.out.println();
		System.out.print("Inorder Successor of "+avt.root.left.right+" is ");
		System.out.println(avt.inOrderSuccessor(avt.root.left.right));
		
		System.out.println(avt.commonAncestor(avt.root, avt.get(7, avt.root), avt.get(10, avt.root)));
		
		System.out.println("After Deletion: Rank of node 8 is: "+avt.getRank(8, avt.root));
	}

}
