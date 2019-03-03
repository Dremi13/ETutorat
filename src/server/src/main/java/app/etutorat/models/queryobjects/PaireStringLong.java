package app.etutorat.models.queryobjects;

public class PaireStringLong {

	  private String left;
	  private Long right;

	  public PaireStringLong(String left, long right) {
	    this.left = left;
	    this.right = right;
	  }

	  public String getLeft() { return left; }
	  public long getRight() { return right; }

	  @Override
	  public int hashCode() { return left.hashCode() + right.hashCode(); }

	  @Override
	  public boolean equals(Object o) {
	    if (!(o instanceof PaireStringLong)) return false;
	    PaireStringLong pairo = (PaireStringLong) o;
	    return this.left.equals(pairo.getLeft()) &&
	           this.right == pairo.getRight();
	  }
	  
	  

}