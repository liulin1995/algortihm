

public class TestClient{
	
	public static void main(string [] args)
	{
		int N = StdIn.readInt();
		UF uf =new UF(N);
		
		while(!StdIn.isEmpty())
		{
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if(!uf.connected(p,q))
			{
				uf.union(p,q);
				StdOut.println(p + " " + q);
			}
		}
	}
}