package dao.queryobjects;

public class PaireStringInt {

	  private String left;
	  private int right;

	  public PaireStringInt(String left, int right) {
	    this.left = left;
	    this.right = right;
	  }

	  public String getLeft() { return left; }
	  public int getRight() { return right; }

	  @Override
	  public int hashCode() { return left.hashCode() + right; }

	  @Override
	  public boolean equals(Object o) {
	    if (!(o instanceof PaireStringInt)) return false;
	    PaireStringInt pairo = (PaireStringInt) o;
	    return this.left.equals(pairo.getLeft()) &&
	           this.right == pairo.getRight();
	  }
	  
	  

}