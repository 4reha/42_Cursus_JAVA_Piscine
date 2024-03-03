import java.util.Comparator;

public class LengthComparator implements Comparator<String> {

	@Override
	public int compare(String o2, String o1) {

		return (o1.length() - o2.length() == 0) ? o1.compareTo(o2) : o1.length() - o2.length();
	}

}
