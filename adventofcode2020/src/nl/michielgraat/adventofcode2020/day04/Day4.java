package nl.michielgraat.adventofcode2020.day04;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day4 {

	private static final String FILENAME = "day04.txt";

	private Passport parsePassport(String cur) {
		Passport p = new Passport();
		String[] parts = cur.trim().split("\\s+");
		for (String part : parts) {
			String key = part.substring(0, part.indexOf(":")).trim();
			String value = part.substring(part.indexOf(":") + 1).trim();
			switch (key) {
			case "byr":
				p.setByr(Integer.valueOf(value));
				break;
			case "iyr":
				p.setIyr(Integer.valueOf(value));
				break;
			case "eyr":
				p.setEyr(Integer.valueOf(value));
				break;
			case "hgt":
				p.setHgt(value);
				break;
			case "hcl":
				p.setHcl(value);
				break;
			case "ecl":
				p.setEcl(value);
				break;
			case "pid":
				p.setPid(value);
				break;
			case "cid":
				p.setCid(value);
				break;
			}
		}
		return p;
	}

	private List<Passport> getPassports(List<String> lines) {
		List<Passport> passports = new ArrayList<Passport>();
		String cur = "";
		for (String line : lines) {
			if (line.isEmpty()) {
				passports.add(parsePassport(cur));
				cur = "";
			} else {
				cur += " " + line;
			}
		}
		if (!cur.isEmpty()) passports.add(parsePassport(cur));
		return passports;
	}

	public int runPart2() {
		List<Passport> passports = getPassports(FileReader.getStringList(FILENAME));
		int nrValid = 0;
		for (Passport passport : passports) {
			if (passport.isReallyValid()) {
				nrValid++;
			}
		}
		return nrValid;
	}
	
	public int runPart1() {
		List<Passport> passports = getPassports(FileReader.getStringList(FILENAME));
		int nrValid = 0;
		for (Passport passport : passports) {
			if (passport.isValid()) {
				nrValid++;
			}
		}
		return nrValid;
	}

	public static void main(String[] args) {
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day4().runPart1());
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day4().runPart2());
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}

class Passport {
	Integer byr;
	Integer iyr;
	Integer eyr;
	String hgt;
	String hcl;
	String ecl;
	String pid;
	String cid;

	boolean isValid() {
		return byr != null && iyr != null && eyr != null && hgt != null && !hgt.isBlank() && hcl != null
				&& !hcl.isBlank() && ecl != null && !ecl.isBlank() && pid != null && !pid.isBlank();
	}
	
	boolean isReallyValid() {
		return validByr() && validIyr() && validEyr() && validHgt() && validHcl() && validEcl() && validPid();
	}

	private boolean validByr() {
		return byr != null && byr >= 1920 && byr <= 2002;
	}

	private boolean validIyr() {
		return iyr != null && iyr >= 2010 && iyr <= 2020;
	}

	private boolean validEyr() {
		return eyr != null && eyr >= 2020 && eyr <= 2030;
	}

	private boolean validHgt() {
		if (hgt == null || hgt.isBlank()) {
			return false;
		} else {
			String unit = hgt.substring(hgt.length() - 2);
			try {
				int value = Integer.valueOf(hgt.substring(0, hgt.length() - 2));
				if (unit.equals("cm")) {
					return value >= 150 && value <= 193;
				} else if (unit.equals("in")) {
					return value >= 59 && value <= 76;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	private boolean validHcl() {
		if (hcl != null && !hcl.isBlank() && hcl.startsWith("#")) {
			String remainder = hcl.substring(1);
			if (remainder.length() == 6) {
				return remainder.chars()
						.allMatch(c -> c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6'
								|| c == '7' || c == '8' || c == '9' || c == 'a' || c == 'b' || c == 'c' || c == 'd'
								|| c == 'e' || c == 'f');
			}
		}
		return false;
	}

	private boolean validEcl() {
		if (ecl != null && !ecl.isBlank() && ecl.length() == 3) {
			return ecl.equals("amb") || ecl.equals("blu") || ecl.equals("brn") || ecl.equals("gry") || ecl.equals("grn")
					|| ecl.equals("hzl") || ecl.equals("oth");
		}
		return false;
	}
	
	private boolean validPid() {
		if (pid != null && !pid.isBlank() && pid.length() == 9) {
			try {
				Integer.valueOf(pid);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	public Integer getByr() {
		return byr;
	}

	public void setByr(Integer byr) {
		this.byr = byr;
	}

	public Integer getIyr() {
		return iyr;
	}

	public void setIyr(Integer iyr) {
		this.iyr = iyr;
	}

	public Integer getEyr() {
		return eyr;
	}

	public void setEyr(Integer eyr) {
		this.eyr = eyr;
	}

	public String getHgt() {
		return hgt;
	}

	public void setHgt(String hgt) {
		this.hgt = hgt;
	}

	public String getHcl() {
		return hcl;
	}

	public void setHcl(String hcl) {
		this.hcl = hcl;
	}

	public String getEcl() {
		return ecl;
	}

	public void setEcl(String ecl) {
		this.ecl = ecl;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Override
	public String toString() {
		return "Passport [byr=" + byr + ", iyr=" + iyr + ", eyr=" + eyr + ", hgt=" + hgt + ", hcl=" + hcl + ", ecl="
				+ ecl + ", pid=" + pid + ", cid=" + cid + ", isReallyValid()=" + isReallyValid() + "]";
	}
}