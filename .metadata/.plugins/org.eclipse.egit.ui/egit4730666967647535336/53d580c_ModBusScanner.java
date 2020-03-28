import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scanner.app.Option;
import com.scanner.app.OptionHandler;
import com.scanner.app.Permutation;
import com.scanner.app.SetModBusParam;

public class ModBusScanner {
	public static final String DEFAULT_BAUD_RATE = "9600";
	public static final String DEFAULT_SLAVE_ID = "1";
	public static final String DEFAULT_REGISTER_ADDRR = "30001";
	public static final String DEFAULT_PORT_NAME = "ttyS0";
	public static final boolean PRINT_PERMUTAIONS = true;
	public static Permutation perm = null;
	
	
	public static void showHelp() {
		System.out.println("------------------------------------------------------------------------");
		System.out.println("- This application scans over all the input arguments permutations. For-");
		System.out.println("- example:                                                             -");
		System.out.println("- java -jar ModBusScanner.jar -b 4800-5600 -p tty.SOC -s 1:5 -r  1:3   -");
		System.out.println("- Checking for these Registers: [1, 2]                                 -"); 
		System.out.println("- Checking for these SlaveIDs: [1, 2, 3, 4]                            -");		
		System.out.println("- Checking for these protIDs: [tty.SOC]                                -");		 
		System.out.println("- Checking for these BaudRates: [4800, 5600]                           -");
		System.out.println("- The 1th permutations is: [4800, tty.SOC, 1, 1]                       -");
		System.out.println("- The 2th permutations is: [4800, tty.SOC, 1, 2]                       -");
		System.out.println("- The 3th permutations is: [4800, tty.SOC, 2, 1]                       -");
		System.out.println("- The 4th permutations is: [4800, tty.SOC, 2, 2]                       -");
		System.out.println("- The 5th permutations is: [4800, tty.SOC, 3, 1]                       -");
		System.out.println("- The 6th permutations is: [4800, tty.SOC, 3, 2]                       -");
		System.out.println("- The 7th permutations is: [4800, tty.SOC, 4, 1]                       -");
		System.out.println("- The 8th permutations is: [4800, tty.SOC, 4, 2]                       -");
		System.out.println("- The 9th permutations is: [5600, tty.SOC, 1, 1]                       -");
		System.out.println("- The 10th permutations is: [5600, tty.SOC, 1, 2]                      -");
		System.out.println("- The 11th permutations is: [5600, tty.SOC, 2, 1]                      -");
		System.out.println("- The 12th permutations is: [5600, tty.SOC, 2, 2]                      -");
		System.out.println("- The 13th permutations is: [5600, tty.SOC, 3, 1]                      -");
		System.out.println("- The 14th permutations is: [5600, tty.SOC, 3, 2]                      -");
		System.out.println("- The 15th permutations is: [5600, tty.SOC, 4, 1]                      -");
		System.out.println("- The 16th permutations is: [5600, tty.SOC, 4, 2]                      -");
		System.out.println("------------------------------------------------------------------------");
	}
	
	public static void init(List<Option> optionsList) {
		Map<List<Character>, List<String>> optionMap = new HashMap<List<Character>, List<String>>();
		for(Option option: optionsList) {
			List<Character> key = new ArrayList<Character>();
			List<String> value = new ArrayList<String>();
			key.add(option.getFlag());
			key.add(option.isRange());
			key.add(option.isSet());
			value.addAll(option.getOption());
			optionMap.put(key, option.getOption());
		}
		System.out.println(optionMap);
		
		OptionHandler optionHandler = new OptionHandler(optionMap);
		// Now I need to create a list [[baudRates][portNames][slaveIDs]] and create all the permutations!
		List<List<String>> inputList = new ArrayList<List<String>>();
		if(optionHandler.getBaudRate().size() != 0) {
			inputList.add(optionHandler.getBaudRate());
		} else {
			System.out.println("BaudRate is defaulted to " + DEFAULT_BAUD_RATE);
			List<String> tempList = new ArrayList<String>();
			tempList.add(DEFAULT_BAUD_RATE);
			inputList.add(tempList);
		}
		if(optionHandler.getPortName().size() != 0) {
			inputList.add(optionHandler.getPortName());
		} else {
			System.out.println("PortName is defaulted to " + DEFAULT_PORT_NAME);
			List<String> tempList = new ArrayList<String>();
			tempList.add(DEFAULT_PORT_NAME);
			inputList.add(tempList);
		}
		if(optionHandler.getSlaveID().size() != 0) {
			inputList.add(optionHandler.getSlaveID());
		} else {
			System.out.println("SlaveID is defaulted to " + DEFAULT_SLAVE_ID);
			List<String> tempList = new ArrayList<String>();
			tempList.add(DEFAULT_SLAVE_ID);
			inputList.add(tempList);
		}
		if(optionHandler.getRegisterAddrr().size() != 0) {
			inputList.add(optionHandler.getRegisterAddrr());
		} else {
			System.out.println("RegisteAddress is defaulted to " + DEFAULT_REGISTER_ADDRR);
			List<String> tempList = new ArrayList<String>();
			tempList.add(DEFAULT_REGISTER_ADDRR);
			inputList.add(tempList);
		}
		
		perm = new Permutation(inputList);
		if(PRINT_PERMUTAIONS) {
			for(int j = 0; j < perm.results.size(); j++) {
				System.out.println("The " + (j + 1) + "th permutations is: " + perm.results.get(j));
			}
		}
		
	}

	public static void main(String[] args) {
		List<String> argList = new ArrayList<String>();
		List<Option> optionsList = new ArrayList<Option>();
		// Parsing the input arguments
		for(int i = 0; i < args.length; i++) {
			boolean hasSemiCol = false;
			boolean hasComma = false;
			// Handeling help case
			if(args[0].equals("-h") || args[0].equals("-H")) {
				showHelp();
				System.exit(0);
			}
			switch (args[i].charAt(0)) {
			case '-':
				if (args[i].length() < 2) {
					throw new IllegalArgumentException("Not valid argument: " + args[i]);
				} else if(args.length - 1 == i) {
					throw new IllegalArgumentException("Expected arg after: " + args[i]);
				}
				// check the format of the arguments!
				for(int len = 0; len < args[i + 1].length(); len++) {
					if(args[i + 1].charAt(len) == ' ') {
						throw new IllegalArgumentException("The argumets should not have space! Put comma as a separator "
								+ "and put semicolon for the range.");
					}
					if(args[i + 1].charAt(len) == ':') {
						// It is a range.
						hasSemiCol = true;
					}
					if(args[i + 1].charAt(len) == ',') {
						// It is set of values
						hasComma = true;
					}
				}
				ArrayList<String> argV = new ArrayList<String>();
				if(hasComma) {
					for(int j = 0; j < args[i + 1].split("[,]").length; j++) {
						argV.add(args[i + 1].split("[,]")[j]);
					}
				}
				if(hasSemiCol) {
					// When it is a range, after you split it, it should not have more than two values right!? like 1:10
					int lenAfterSplit = args[i + 1].split("[:]").length;
					if(lenAfterSplit > 2 || lenAfterSplit < 2) {
						throw new IllegalArgumentException("The format of the argument when it is range is start:end! No space!");
					} else {
						for(int j = 0; j < args[i + 1].split("[:]").length; j++) {
							argV.add(args[i + 1].split("[:]")[j]);
						}
					}
				}
				// this is for the case that does not have a semicolon or comma (one value).
				if(!hasComma && !hasSemiCol ) {
					argV.add(args[i + 1]);
					hasComma = true;
				}
				Option option = new Option(args[i].charAt(1), argV, hasComma, hasSemiCol);
				optionsList.add(option);
				// because we pick two elems each time
				i++;
			}
		}
		//Initialize the options
		init(optionsList);
		// Now run!
		List<SetModBusParam> modBusList = new ArrayList<SetModBusParam>();
		for(List<String> elem: perm.results) {
			int baudRate = Integer.parseInt(elem.get(0));
			String portName = elem.get(1);
			int slaveID = Integer.parseInt(elem.get(2));
			int registerAddrr = Integer.parseInt(elem.get(3));
			modBusList.add(new SetModBusParam(portName, baudRate, registerAddrr, slaveID));
		}
		// Now go over all the modBus objects and try to connect it will be faster if I delete this other loop
		for(SetModBusParam param: modBusList) {
			param.getConnect();
			final long t0 = System.currentTimeMillis();
			param.checkData();
		}
	}
}
